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

public class DiskReleaseStore implements ReleaseStore {

	private final Directory directory;
	private final File directoryFile;

	public DiskReleaseStore() {
		try {
			directoryFile = new File("index");
			directoryFile.mkdirs();
			directory = new NIOFSDirectory(directoryFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Analyzer createAnalyzer() {
		return new StandardAnalyzer(Version.LUCENE_40);
	}

	public Directory getDirectory() {
		return directory;
	}

	public boolean isIndexExisting() {
		return directoryFile.isDirectory() && directoryFile.list().length > 0;
	}

	public void destroy() throws IOException {
		FileUtils.deleteDirectory(directoryFile);
	}
}
