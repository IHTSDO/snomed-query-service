package com.kaicube.snomed.srqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReleaseImporter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public ReleaseStore loadReleaseZip(String zipFilePath) throws IOException {
		logger.info("Loading release archive {}", zipFilePath);
		final ReleaseStore releaseStore = new ReleaseStore();
		try (final ReleaseWriter releaseWriter = new ReleaseWriter(releaseStore)) {
			try (final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
				ZipEntry nextEntry;
				while ((nextEntry = zipInputStream.getNextEntry()) != null) {
					if (nextEntry.getName().contains("sct2_Concept_Snapshot")) {
						loadConcepts(zipInputStream, releaseWriter);
					}
				}
			}
		}
		return releaseStore;
	}

	private void loadConcepts(ZipInputStream conceptsFileStream, ReleaseWriter releaseWriter) throws IOException {
		long conceptsLoaded = 0L;
		final BufferedReader reader = new BufferedReader(new InputStreamReader(conceptsFileStream));
		String line;
		reader.readLine(); // discard header line
		while ((line = reader.readLine()) != null) {
			final String[] values = line.split("\\t");
			releaseWriter.addConcept(values[0]);
			conceptsLoaded++;
			if (conceptsLoaded > 100) {
				break;
			}
		}
		logger.info("{} concepts loaded", conceptsLoaded);
	}

}
