package org.ihtsdo.otf.sqs.service;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.util.Version;
import org.ihtsdo.otf.snomedboot.domain.Concept;
import org.ihtsdo.otf.snomedboot.domain.Description;
import org.ihtsdo.otf.snomedboot.domain.Relationship;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.domain.DescriptionFieldNames;
import org.ihtsdo.otf.sqs.domain.RelationshipFieldNames;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReleaseWriter implements AutoCloseable {

	private final IndexWriter iwriter;

	public ReleaseWriter(ReleaseStore releaseStore) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, releaseStore.createAnalyzer());
		iwriter = new IndexWriter(releaseStore.getDirectory(), config);
	}

	public void addConcept(Concept concept, boolean inferredFormMode) throws IOException {
		List<Document> documents = new ArrayList<>();
		for (Relationship relationship : concept.getRelationships()) {
			documents.add(getRelationshipDocument(relationship));
		}
		for (Description description : concept.getDescriptions()) {
			documents.add(getDescriptionDocument(description));
		}
		documents.add(getConceptDocument(concept, inferredFormMode));
		iwriter.addDocuments(documents);
	}

	private Document getConceptDocument(Concept concept, boolean inferredFormMode) {
		Document conceptDoc = new Document();
		conceptDoc.add(new StringField("type", "concept", Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.ID, concept.getId().toString(), Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.EFFECTIVE_TIME, concept.getEffectiveTime(), Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.ACTIVE, concept.isActive() ? "1" : "0", Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.MODULE_ID, concept.getModuleId(), Field.Store.YES));
		conceptDoc.add(new StringField(ConceptFieldNames.DEFINITION_STATUS_ID, concept.getDefinitionStatusId(), Field.Store.YES));
		conceptDoc.add(new TextField(ConceptFieldNames.FSN, concept.getFsn(), Field.Store.YES));
		final MultiValueMap<String, String> attributes = inferredFormMode ? concept.getInferredAttributes() : concept.getStatedAttributes();
		for (String type : attributes.keySet()) {
			for (String value : attributes.get(type)) {
				conceptDoc.add(new StringField(type, value, Field.Store.YES));
			}
		}
		for (Long ancestorId : inferredFormMode ? concept.getInferredAncestorIds() : concept.getStatedAncestorIds()) {
			conceptDoc.add(new StringField(ConceptFieldNames.ANCESTOR, ancestorId.toString(), Field.Store.YES));
		}
		for (Long memberRefsetId : concept.getMemberOfRefsetIds()) {
			conceptDoc.add(new StringField(ConceptFieldNames.MEMBER_OF, memberRefsetId.toString(), Field.Store.YES));
		}
		return conceptDoc;
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
