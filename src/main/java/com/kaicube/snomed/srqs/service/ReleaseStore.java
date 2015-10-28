package com.kaicube.snomed.srqs.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ReleaseStore {

	private final Directory directory;
	private final File directoryFile;

	public ReleaseStore() {
		try {
			directoryFile = Files.createTempDirectory(getClass().getCanonicalName()).toFile();
			directory = new NIOFSDirectory(directoryFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void addConcept(String id) {

	}

	public Analyzer createAnalyzer() {
		return new StandardAnalyzer(Version.LUCENE_40);
	}

	public Directory getDirectory() {
		return directory;
	}

	public void destroy() throws IOException {
		FileUtils.deleteDirectory(directoryFile);
	}
}
