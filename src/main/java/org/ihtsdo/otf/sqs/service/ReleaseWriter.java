package org.ihtsdo.otf.sqs.service;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.ihtsdo.otf.snomedboot.domain.Concept;
import org.ihtsdo.otf.snomedboot.domain.ConcreteRelationship;
import org.ihtsdo.otf.snomedboot.domain.Relationship;
import org.ihtsdo.otf.snomedboot.domain.Description;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.*;

public class ReleaseWriter implements AutoCloseable {

	private final IndexWriter iwriter;

	public ReleaseWriter(ReleaseStore releaseStore) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(releaseStore.createAnalyzer());
		iwriter = new IndexWriter(releaseStore.getDirectory(), config);
	}

	public void addConcept(Concept concept, boolean isStatedRelationship) throws IOException {
		addDocument(buildDocument(concept, isStatedRelationship));
	}

	/**
	 * Builds a concept's document without writing it.
	 *
	 * <p>Separated from {@link #addDocument(Document)} so callers can build
	 * across cores and still write in a fixed order. Document construction is
	 * the expensive half - cardinality grouping per relationship - while the
	 * order documents are written in decides their docids, and equal-scoring
	 * hits come back in docid order. Writing concurrently would leave result
	 * sets identical but reorder them, so a validation report would name a
	 * different sample of failing concepts from one run to the next.
	 */
	public Document buildDocument(Concept concept, boolean isStatedRelationship) {
		return getConceptDocument(concept, isStatedRelationship);
	}

	/** Writes one prepared document. Call in a deterministic order. */
	public void addDocument(Document document) throws IOException {
		iwriter.addDocuments(List.of(document));
	}

	/**
	 * Records the latest effective time across a concept and its relationships.
	 *
	 * <p>RF2 effective times are fixed-width {@code yyyyMMdd}, so the latest one
	 * is the lexicographic maximum and no date parsing is needed. This used to
	 * parse each value into a {@link Date} through a {@code SimpleDateFormat}
	 * held on this writer, and then format the winner back to the same string -
	 * per concept and per relationship, 722,404 concepts per index.
	 *
	 * <p>That formatter was shared mutable state, which is what stopped the
	 * write loop being parallelised: {@code SimpleDateFormat} under concurrency
	 * returns wrong dates rather than throwing, so the failure would have been
	 * silently wrong effective times in the index. Comparing strings removes the
	 * hazard and the parsing at once.
	 */
	private void addCardinalityToDocument(Concept concept, Document doc, boolean isStatedRelationship) {
		final MultiValueMap<String, String> attributeGroups = new LinkedMultiValueMap<>();
		String maxEffectiveTime = requireEffectiveTime(concept.getEffectiveTime(), concept.getId());
		for (Relationship relationship : concept.getRelationships()) {
			if (relationship != null) {
				if (isStatedRelationship && "900000000000011006".equals(relationship.getCharacteristicTypeId())) {
					continue;
				} else if (!isStatedRelationship && "900000000000010007".equals(relationship.getCharacteristicTypeId())) {
					continue;
				}
				maxEffectiveTime = later(maxEffectiveTime, relationship.getEffectiveTime(), concept.getId());
				attributeGroups.add(relationship.getTypeId(), relationship.getRelationshipGroup());
			}
		}
		if (concept.getConcreteRelationships() != null && !isStatedRelationship) {
			for (ConcreteRelationship concreteRelationship : concept.getConcreteRelationships()) {
				maxEffectiveTime = later(maxEffectiveTime, concreteRelationship.getEffectiveTime(), concept.getId());
				attributeGroups.add(concreteRelationship.getTypeId(), concreteRelationship.getRelationshipGroup());
			}
		}
		doc.add(new StringField(ConceptFieldNames.EFFECTIVE_TIME, maxEffectiveTime, Field.Store.YES));
		addAttributeCardinalityDocument(doc, attributeGroups);
	}

	/** The later of two {@code yyyyMMdd} effective times, ignoring blanks. */
	private static String later(String current, String candidate, Object conceptId) {
		if (candidate == null || candidate.isEmpty()) {
			return current;
		}
		requireEffectiveTime(candidate, conceptId);
		return candidate.compareTo(current) > 0 ? candidate : current;
	}

	/**
	 * Rejects an effective time that is not {@code yyyyMMdd}.
	 *
	 * <p>Comparing these as strings is only equivalent to comparing dates while
	 * they really are fixed-width and numeric, so the shape is checked rather
	 * than assumed.
	 *
	 * <p>This is STRICTER than the date parsing it replaces, deliberately. That
	 * parser was lenient: it read {@code 2015-07-31} as 7 December 2014 and
	 * indexed {@code 20141207} without complaint, and normalised {@code
	 * 20230230} to {@code 20230302}. An index is not a place to quietly correct
	 * a release - nothing downstream can tell that the effective time it is
	 * reading was invented here - so a value that is not a date is now a failed
	 * import instead.
	 */
	private static String requireEffectiveTime(String value, Object conceptId) {
		if (value == null || value.length() != 8) {
			throw new IllegalArgumentException(
					"Concept " + conceptId + " has effectiveTime '" + value + "', which is not yyyyMMdd.");
		}
		for (int i = 0; i < 8; i++) {
			if (value.charAt(i) < '0' || value.charAt(i) > '9') {
				throw new IllegalArgumentException(
						"Concept " + conceptId + " has effectiveTime '" + value + "', which is not yyyyMMdd.");
			}
		}
		return value;
	}

	private void addAttributeCardinalityDocument(Document doc, final MultiValueMap<String, String> attributeGroups) {
		for (String key : attributeGroups.keySet()) {
			int totalCount = 0;
			Iterator<String> iterator = attributeGroups.get(key).iterator();
			List<String> roleGroups = new ArrayList<>();
			Map<String,Integer> groupCountMap = new HashMap<>();
			while (iterator.hasNext()) {
				String grp = iterator.next();
				totalCount++;
				if (!"0".equals(grp)) {
					roleGroups.add(grp);
					if (groupCountMap.containsKey(grp)) {
						groupCountMap.put(grp, groupCountMap.get(grp) + 1);
					} else {
						groupCountMap.put(grp, 1);
					}
				}
			}
			Set<String> distinctGroups = new HashSet<>(roleGroups);
			if (!distinctGroups.isEmpty()) {
				doc.add(new FloatPoint(key + ConceptFieldNames.TOTAL_GROUPS, distinctGroups.size()));
				List<Integer> countList = new ArrayList<>(groupCountMap.values());
				java.util.Collections.sort(countList);
				// attribute in group cardinality
				if (!countList.isEmpty()) {
					Integer maxGroup = countList.get(countList.size()-1);
					doc.add(new FloatPoint(key + ConceptFieldNames.GROUP_CARDINALITY, maxGroup));
				}
			}
			//attribute cardinality
			doc.add(new FloatPoint(key + ConceptFieldNames.CARDINALITY, totalCount));
		}
	}

	private Document getConceptDocument(Concept concept, boolean isStatedRelationship) {
		Document conceptDoc = new Document();
		conceptDoc.add(new StringField("type", "concept", Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.ID, concept.getId().toString(), Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.ACTIVE, concept.isActive() ? "1" : "0", Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.MODULE_ID, concept.getModuleId(), Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.DEFINITION_STATUS_ID, concept.getDefinitionStatusId(), Field.Store.YES));
		for (Description description : concept.getDescriptions()) {
			conceptDoc.add(new StringField(ConceptFieldNames.DESCRIPTION_IDS, description.getId().toString(), Field.Store.YES));
		}
		if (concept.getFsn() == null) {
			throw new IllegalStateException("FSN can't be null for concept:" + concept.getId());
		}
		conceptDoc.add(new TextField(ConceptFieldNames.FSN, concept.getFsn(), Field.Store.YES));
		conceptDoc.add(new SortedNumericDocValuesField(ConceptFieldNames.FSN_LENGTH, concept.getFsn() != null ? concept.getFsn().length() : 100));

		final Map<String, Set<String>> attributes = isStatedRelationship ? concept.getStatedAttributes() : concept.getInferredAttributes();
		for (String type : attributes.keySet()) {
			for (String value : attributes.get(type)) {
				conceptDoc.add(new StringField(type, value, Field.Store.YES));
			}
		}
		// concrete values
		if (!isStatedRelationship && concept.getInferredConcreteAttributes() != null) {
			for (Map.Entry<String, Set<String>> entry: concept.getInferredConcreteAttributes().entrySet()) {
				for (String value : entry.getValue()) {
					// The string field is used for exact match and NOT query
					if (value.startsWith("#")) {
						conceptDoc.add(new FloatPoint( entry.getKey() + "_value", Float.parseFloat(value.replace("#",""))));
					} else {
						conceptDoc.add(new StringField(entry.getKey(), value.replace("\"",""), Field.Store.YES));
					}
				}
			}
		}
		final Set<Long> ancestorIds = isStatedRelationship ? concept.getStatedAncestorIds() : concept.getInferredAncestorIds();
		for (Long ancestorId : ancestorIds) {
			conceptDoc.add(new StringField(ConceptFieldNames.ANCESTOR, ancestorId.toString(), Field.Store.YES));
		}
		for (Long memberRefsetId : concept.getMemberOfRefsetIds()) {
			conceptDoc.add(new StringField(ConceptFieldNames.MEMBER_OF, memberRefsetId.toString(), Field.Store.YES));
		}
		addCardinalityToDocument(concept, conceptDoc, isStatedRelationship);
		return conceptDoc;
	}

	@Override
	public void close() throws IOException {
		iwriter.close();
	}
}
