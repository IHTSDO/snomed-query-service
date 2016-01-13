package com.kaicube.snomed.srqs.service.store;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;

public class RamReleaseStore implements ReleaseStore {

	private final Directory directory;

	public RamReleaseStore() {
		directory = new RAMDirectory();
	}

	public Analyzer createAnalyzer() {
		return new StandardAnalyzer(Version.LUCENE_40);
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
