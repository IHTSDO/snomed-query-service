package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.domain.Concept;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.util.Version;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Set;

public class ReleaseWriter implements AutoCloseable {

	private final IndexWriter iwriter;

	public ReleaseWriter(ReleaseStore releaseStore) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, releaseStore.createAnalyzer());
		iwriter = new IndexWriter(releaseStore.getDirectory(), config);
	}

	public void addConcept(Concept concept) throws IOException {
		Document doc = new Document();
		doc.add(new LongField(Concept.ID, concept.getId(), Field.Store.YES));
		doc.add(new StringField(Concept.ACTIVE, concept.isActive() ? "1" : "0", Field.Store.YES));
		doc.add(new StringField(Concept.FSN, concept.getFsn(), Field.Store.YES));
		final MultiValueMap<String, String> attributes = concept.getAttributes();
		for (String type : attributes.keySet()) {
			for (String value : attributes.get(type)) {
				doc.add(new StringField(type, value, Field.Store.YES));
			}
		}
		final Set<Long> ancestorIds = concept.getAncestorIds();
		for (Long ancestorId : ancestorIds) {
			doc.add(new LongField(Concept.ANCESTOR, ancestorId, Field.Store.YES));
		}
		iwriter.addDocument(doc);
	}

	@Override
	public void close() throws IOException {
		iwriter.close();
	}
}
