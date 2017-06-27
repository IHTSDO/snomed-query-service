package org.ihtsdo.otf.sqs.service;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.ihtsdo.otf.snomedboot.domain.Concept;
import org.ihtsdo.otf.snomedboot.domain.Description;
import org.ihtsdo.otf.snomedboot.domain.Relationship;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.domain.DescriptionFieldNames;
import org.ihtsdo.otf.sqs.domain.RelationshipFieldNames;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.mangofactory.swagger.models.Collections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReleaseWriter implements AutoCloseable {

	private final IndexWriter iwriter;

	public ReleaseWriter(ReleaseStore releaseStore) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(releaseStore.createAnalyzer());
		iwriter = new IndexWriter(releaseStore.getDirectory(), config);
	}

	public void addConcept(Concept concept) throws IOException {
		List<Document> documents = new ArrayList<>();
		for (Relationship relationship : concept.getRelationships()) {
			if (relationship != null) {
				documents.add(getRelationshipDocument(relationship));
			} 
		}
		for (Description description : concept.getDescriptions()) {
			documents.add(getDescriptionDocument(description));
		}
		documents.add(getConceptDocument(concept));
		iwriter.addDocuments(documents);
	}

	private Document getCardinalityDocument(Concept concept, Document doc) {
		final MultiValueMap<String, String> statedAttributeGroups = new LinkedMultiValueMap<>();
		final MultiValueMap<String, String> inferredAttributeGroups = new LinkedMultiValueMap<>();
		for (Relationship relationship : concept.getRelationships()) {
			if ("900000000000011006".equals(relationship.getCharacteristicTypeId())) {
				inferredAttributeGroups.add(relationship.getTypeId(), relationship.getRelationshipGroup());
			} else {
				statedAttributeGroups.add(relationship.getTypeId(), relationship.getRelationshipGroup());
			}
		}
		//attributeCardinality
		for (String key : inferredAttributeGroups.keySet()) {
			doc.add(new StringField(key + ConceptFieldNames.CARDINALITY, String.valueOf(inferredAttributeGroups.get(key).size()), Field.Store.YES));
		}
		//attributeGroupCardinality
		for (String key : inferredAttributeGroups.keySet()) {
			Iterator<String> itrator = inferredAttributeGroups.get(key).iterator();
			List<String> roleGroups = new ArrayList<>();
			Map<String,Integer> groupCountMap = new HashMap<>();
			while (itrator.hasNext()) {
				String grp = itrator.next();
				if (!"0".equals(grp)) {
					roleGroups.add(grp);
					if (groupCountMap.containsKey(grp)) {
						groupCountMap.put(grp, groupCountMap.get(grp).intValue() + 1);
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
				if (!countList.isEmpty()) {
					Integer maxGroup = countList.get(countList.size()-1);
					doc.add(new StringField(key + ConceptFieldNames.GROUP_CARDINALITY, maxGroup.toString(), Field.Store.YES));
				}
			}
		}
		return doc;
	}

	private Document getConceptDocument(Concept concept) {
		Document conceptDoc = new Document();
		conceptDoc.add(new StringField("type", "concept", Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.ID, concept.getId().toString(), Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.EFFECTIVE_TIME, concept.getEffectiveTime(), Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.ACTIVE, concept.isActive() ? "1" : "0", Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.MODULE_ID, concept.getModuleId(), Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.DEFINITION_STATUS_ID, concept.getDefinitionStatusId(), Field.Store.YES));
		if (concept.getFsn() == null) {
			throw new IllegalStateException("FSN can't be null for concept:" + concept.getId());
		}
		conceptDoc.add(new TextField(ConceptFieldNames.FSN, concept.getFsn(), Field.Store.YES));
		conceptDoc.add(new SortedNumericDocValuesField(ConceptFieldNames.FSN_WORD_COUNT, concept.getFsn().split(" ").length));
		final MultiValueMap<String, String> attributes = concept.getInferredAttributes();
		for (String type : attributes.keySet()) {
			for (String value : attributes.get(type)) {
				conceptDoc.add(new StringField(type, value, Field.Store.YES));
			}
		}
		final Set<Long> ancestorIds = concept.getInferredAncestorIds();
		for (Long ancestorId : ancestorIds) {
			conceptDoc.add(new StringField(ConceptFieldNames.ANCESTOR, ancestorId.toString(), Field.Store.YES));
		}
		for (Long memberRefsetId : concept.getMemberOfRefsetIds()) {
			conceptDoc.add(new StringField(ConceptFieldNames.MEMBER_OF, memberRefsetId.toString(), Field.Store.YES));
		}
		return getCardinalityDocument(concept, conceptDoc);
	}

	private Document getRelationshipDocument(org.ihtsdo.otf.snomedboot.domain.Relationship relationship) {
		Document doc = new Document();
		doc.add(new StringField(RelationshipFieldNames.ID, relationship.getId(), Field.Store.YES));
		doc.add(new StringField(RelationshipFieldNames.EFFECTIVE_TIME, relationship.getEffectiveTime(), Field.Store.YES));
		doc.add(new StringField(RelationshipFieldNames.ACTIVE, relationship.getActive(), Field.Store.YES));
		doc.add(new StringField(RelationshipFieldNames.MODULE_ID, relationship.getModuleId(), Field.Store.YES));
		doc.add(new StringField(RelationshipFieldNames.SOURCE_ID, relationship.getSourceId(), Field.Store.YES));
		doc.add(new StringField(RelationshipFieldNames.DESTINATION_ID, relationship.getDestinationId(), Field.Store.YES));
		doc.add(new StringField(RelationshipFieldNames.RELATIONSHIP_GROUP, relationship.getRelationshipGroup(), Field.Store.YES));
		doc.add(new StringField(RelationshipFieldNames.TYPE_ID, relationship.getTypeId(), Field.Store.YES));
		doc.add(new StringField(RelationshipFieldNames.CHARACTERISTIC_TYPE_ID, relationship.getCharacteristicTypeId(), Field.Store.YES));
		doc.add(new StringField(RelationshipFieldNames.MODIFIER_ID, relationship.getModifierId(), Field.Store.YES));
		return doc;
	}

	private Document getDescriptionDocument(Description description) {
		Document doc = new Document();
		doc.add(new StringField(DescriptionFieldNames.ID, description.getId(), Field.Store.YES));
		doc.add(new StringField(DescriptionFieldNames.TERM, description.getTerm(), Field.Store.YES));
		doc.add(new StringField(DescriptionFieldNames.CONCEPT_ID, description.getConceptId(), Field.Store.YES));
		return doc;
	}

	@Override
	public void close() throws IOException {
		iwriter.close();
	}
}
