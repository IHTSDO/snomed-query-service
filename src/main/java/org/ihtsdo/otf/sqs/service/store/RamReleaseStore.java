package org.ihtsdo.otf.sqs.service.store;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

public class RamReleaseStore implements ReleaseStore {

	private final Directory directory;

	public RamReleaseStore() {
		directory = new ByteBuffersDirectory();
	}

	public Analyzer createAnalyzer() {
		return new StandardAnalyzer();
	}

	public Directory getDirectory() {
		return directory;
	}

	public boolean isIndexExisting() {
		return false;
	}

	public void destroy() {
	}
}
