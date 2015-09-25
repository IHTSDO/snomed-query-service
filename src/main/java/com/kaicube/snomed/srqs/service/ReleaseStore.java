package com.kaicube.snomed.srqs.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class ReleaseStore {

	private final Directory directory;

	public ReleaseStore() {
		directory = new RAMDirectory();
	}

	public void addConcept(String id) {

	}

	public Analyzer createAnalyzer() {
		return new StandardAnalyzer(Version.LUCENE_40);
	}

	public Directory getDirectory() {
		return directory;
	}
}
