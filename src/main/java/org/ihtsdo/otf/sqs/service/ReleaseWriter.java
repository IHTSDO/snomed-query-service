package org.ihtsdo.otf.sqs.service;

import org.ihtsdo.otf.sqs.domain.Concept;
import org.ihtsdo.otf.sqs.domain.Description;
import org.ihtsdo.otf.sqs.domain.Relationship;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.util.Version;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReleaseWriter implements AutoCloseable {

	private final IndexWriter iwriter;

	public ReleaseWriter(ReleaseStore releaseStore) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, releaseStore.createAnalyzer());
		iwriter = new IndexWriter(releaseStore.getDirectory(), config);
	}

	public void addConcept(Concept concept) throws IOException {
		List<Document> documents = new ArrayList<>();
		for (Relationship relationship : concept.getRelationships()) {
			documents.add(getRelationshipDocument(relationship));
		}
		for (Description description : concept.getDescriptions()) {
			documents.add(getDescriptionDocument(description));
		}
		documents.add(getConceptDocument(concept));
		iwriter.addDocuments(documents);
	}

	private Document getConceptDocument(Concept concept) {
		Document conceptDoc = new Document();
		conceptDoc.add(new StringField("type", "concept", Field.Store.YES));
		conceptDoc.add(new StringField(Concept.ID, concept.getId().toString(), Field.Store.YES));
		conceptDoc.add(new StringField(Concept.EFFECTIVE_TIME, concept.getEffectiveTime(), Field.Store.YES));
		conceptDoc.add(new StringField(Concept.ACTIVE, concept.isActive() ? "1" : "0", Field.Store.YES));
		conceptDoc.add(new StringField(Concept.MODULE_ID, concept.getModuleId(), Field.Store.YES));
		conceptDoc.add(new StringField(Concept.DEFINITION_STATUS_ID, concept.getDefinitionStatusId(), Field.Store.YES));
		conceptDoc.add(new TextField(Concept.FSN, concept.getFsn(), Field.Store.YES));
		final MultiValueMap<String, String> attributes = concept.getAttributes();
		for (String type : attributes.keySet()) {
			for (String value : attributes.get(type)) {
				conceptDoc.add(new StringField(type, value, Field.Store.YES));
			}
		}
		final Set<Long> ancestorIds = concept.getAncestorIds();
		for (Long ancestorId : ancestorIds) {
			conceptDoc.add(new StringField(Concept.ANCESTOR, ancestorId.toString(), Field.Store.YES));
		}
		for (Long memberRefsetId : concept.getMemberOfRefsetIds()) {
			conceptDoc.add(new StringField(Concept.MEMBER_OF, memberRefsetId.toString(), Field.Store.YES));
		}
		return conceptDoc;
	}

	private Document getRelationshipDocument(Relationship relationship) {
		Document doc = new Document();
		doc.add(new StringField(Relationship.ID, relationship.getId(), Field.Store.YES));
		doc.add(new StringField(Relationship.EFFECTIVE_TIME, relationship.getEffectiveTime(), Field.Store.YES));
		doc.add(new StringField(Relationship.ACTIVE, relationship.getActive(), Field.Store.YES));
		doc.add(new StringField(Relationship.MODULE_ID, relationship.getModuleId(), Field.Store.YES));
		doc.add(new StringField(Relationship.SOURCE_ID, relationship.getSourceId(), Field.Store.YES));
		doc.add(new StringField(Relationship.DESTINATION_ID, relationship.getDestinationId(), Field.Store.YES));
		doc.add(new StringField(Relationship.RELATIONSHIP_GROUP, relationship.getRelationshipGroup(), Field.Store.YES));
		doc.add(new StringField(Relationship.TYPE_ID, relationship.getTypeId(), Field.Store.YES));
		doc.add(new StringField(Relationship.CHARACTERISTIC_TYPE_ID, relationship.getCharacteristicTypeId(), Field.Store.YES));
		doc.add(new StringField(Relationship.MODIFIER_ID, relationship.getModifierId(), Field.Store.YES));
		return doc;
	}

	private Document getDescriptionDocument(Description description) {
		Document doc = new Document();
		doc.add(new StringField(Description.ID, description.getId(), Field.Store.YES));
		doc.add(new StringField(Description.TERM, description.getTerm(), Field.Store.YES));
		doc.add(new StringField(Description.CONCEPT_ID, description.getConceptId(), Field.Store.YES));
		return doc;
	}

	@Override
	public void close() throws IOException {
		iwriter.close();
	}
}
