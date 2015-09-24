package com.kaicube.snomed.srqs;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.util.Version;

import java.io.IOException;

public class ReleaseWriter implements AutoCloseable {

	private final IndexWriter iwriter;

	public ReleaseWriter(ReleaseStore releaseStore) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, releaseStore.createAnalyzer());
		iwriter = new IndexWriter(releaseStore.getDirectory(), config);
	}


	public void addConcept(String id) throws IOException {
		Document doc = new Document();
		doc.add(new Field("id", id, TextField.TYPE_STORED));
		iwriter.addDocument(doc);
	}

	@Override
	public void close() throws IOException {
		iwriter.close();
	}
}
