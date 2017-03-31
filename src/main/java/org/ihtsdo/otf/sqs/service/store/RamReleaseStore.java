package org.ihtsdo.otf.sqs.service.store;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;

public class RamReleaseStore implements ReleaseStore {

	private final Directory directory;

	public RamReleaseStore() {
		directory = new RAMDirectory();
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

	public void destroy() throws IOException {
	}
}
