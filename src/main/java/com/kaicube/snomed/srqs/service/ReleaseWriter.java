package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.domain.Concept;
import com.kaicube.snomed.srqs.domain.Relationship;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
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
		documents.add(getConceptDocument(concept));
		iwriter.addDocuments(documents);
	}

	private Document getConceptDocument(Concept concept) {
		Document conceptDoc = new Document();
		conceptDoc.add(new StringField("type", "concept", Field.Store.YES));
		conceptDoc.add(new StringField(Concept.ID, concept.getId().toString(), Field.Store.YES));
		conceptDoc.add(new StringField(Concept.ID, concept.getId().toString(), Field.Store.YES));
		conceptDoc.add(new StringField(Concept.ACTIVE, concept.isActive() ? "1" : "0", Field.Store.YES));
		conceptDoc.add(new StringField(Concept.FSN, concept.getFsn(), Field.Store.YES));
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

	@Override
	public void close() throws IOException {
		iwriter.close();
	}
}
