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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReleaseWriter implements AutoCloseable {

	private final IndexWriter iwriter;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public ReleaseWriter(ReleaseStore releaseStore) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(releaseStore.createAnalyzer());
		iwriter = new IndexWriter(releaseStore.getDirectory(), config);
	}

	public void addConcept(Concept concept, boolean isStatedRelationship) throws IOException, ParseException {
		List<Document> documents = new ArrayList<>();
		documents.add(getConceptDocument(concept, isStatedRelationship));
		iwriter.addDocuments(documents);
	}

	private void addCardinalityToDocument(Concept concept, Document doc, boolean isStatedRelationship) throws ParseException {
		final MultiValueMap<String, String> attributeGroups = new LinkedMultiValueMap<>();
		Date maxDate = dateFormat.parse(concept.getEffectiveTime());
		for (Relationship relationship : concept.getRelationships()) {
			if (relationship != null) {
				if (isStatedRelationship && "900000000000011006".equals(relationship.getCharacteristicTypeId())) {
					continue;
				} else if (!isStatedRelationship && "900000000000010007".equals(relationship.getCharacteristicTypeId())) {
					continue;
				}
				if (relationship.getEffectiveTime() != null && !relationship.getEffectiveTime().isEmpty() && 
						dateFormat.parse(relationship.getEffectiveTime()).after(maxDate)) {
					maxDate = dateFormat.parse(relationship.getEffectiveTime());
				}
				attributeGroups.add(relationship.getTypeId(), relationship.getRelationshipGroup());
			}
		}
		if (concept.getConcreteRelationships() != null && !isStatedRelationship) {
			for (ConcreteRelationship concreteRelationship : concept.getConcreteRelationships()) {
				if (concreteRelationship.getEffectiveTime() != null && !concreteRelationship.getEffectiveTime().isEmpty() &&
						dateFormat.parse(concreteRelationship.getEffectiveTime()).after(maxDate)) {
					maxDate = dateFormat.parse(concreteRelationship.getEffectiveTime());
				}
				attributeGroups.add(concreteRelationship.getTypeId(), concreteRelationship.getRelationshipGroup());
			}
		}
		doc.add(new StringField(ConceptFieldNames.EFFECTIVE_TIME, dateFormat.format(maxDate), Field.Store.YES));
		addAttributeCardinalityDocument(doc, attributeGroups);
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
				doc.add(new StringField(key + ConceptFieldNames.TOTAL_GROUPS, String.valueOf(distinctGroups.size()), Field.Store.YES));
				List<Integer> countList = new ArrayList<>(groupCountMap.values());
				java.util.Collections.sort(countList);
				// attribute in group cardinality
				if (!countList.isEmpty()) {
					Integer maxGroup = countList.get(countList.size()-1);
					doc.add(new StringField(key + ConceptFieldNames.GROUP_CARDINALITY, maxGroup.toString(), Field.Store.YES));
				}
			}
			//attribute cardinality
			doc.add(new StringField(key + ConceptFieldNames.CARDINALITY, String.valueOf(totalCount), Field.Store.YES));
		}
	}

	private Document getConceptDocument(Concept concept, boolean isStatedRelationship) throws ParseException {
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
					conceptDoc.add(new StringField(entry.getKey(), value.replace("#","").replace("\"",""),
							Field.Store.YES));
				}
			}
		}
		final Set<Long> ancestorIds = concept.getInferredAncestorIds();
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
