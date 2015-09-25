package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.domain.Concept;
import com.kaicube.snomed.srqs.domain.ConceptConstants;
import com.kaicube.snomed.srqs.domain.rf2.RelationshipFields;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReleaseImporter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Long2ObjectMap<Concept> concepts;

	public ReleaseImporter() {
		concepts = new Long2ObjectOpenHashMap<>();
	}

	public ReleaseStore loadReleaseZip(String zipFilePath) throws IOException {
		logger.info("Loading release archive {}", zipFilePath);
		try (final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
			ZipEntry nextEntry;
			while ((nextEntry = zipInputStream.getNextEntry()) != null) {
				if (nextEntry.getName().contains("sct2_Concept_Snapshot")) {
					loadConcepts(zipInputStream);
				} else if (nextEntry.getName().contains("sct2_Relationship_Snapshot")) {
					loadRelationships(zipInputStream);
				}
			}
		}
		logger.info("All in memory. Using approx {} MB of memory.", formatAsMB(Runtime.getRuntime().totalMemory()));
		logger.info("Writing to Lucene...");

		final ReleaseStore releaseStore = new ReleaseStore();
		try (final ReleaseWriter releaseWriter = new ReleaseWriter(releaseStore)) {
			long conceptsAdded = 0;
			for (Concept concept : concepts.values()) {
				releaseWriter.addConcept(concept);
				conceptsAdded++;
				if (conceptsAdded % 100000 == 0) {
					logger.info("{} concepts added to Lucene.", conceptsAdded);
				}
			}
			logger.info("{} concepts added to Lucene in total.", conceptsAdded);
			logger.info("Closing Lucene writer.");
		}

		concepts = null;
		System.gc();
		logger.info("Written to Lucene. Using approx {} MB of memory.", formatAsMB(Runtime.getRuntime().totalMemory()));

		return releaseStore;
	}

	private void loadRelationships(ZipInputStream zipInputStream) throws IOException {
		readLines(zipInputStream, new ValuesHandler() {
			@Override
			public void handle(String[] values) {
				if (values[RelationshipFields.active].equals("1") && new Long(values[RelationshipFields.typeId]).equals(ConceptConstants.isA)){
					final Concept concept = getCreateConcept(values[RelationshipFields.sourceId]);
					concept.addParent(getCreateConcept(values[RelationshipFields.destinationId]));
				}
			}
		}, "relationships");
	}

	private void loadConcepts(ZipInputStream zipInputStream) throws IOException {
		readLines(zipInputStream, new ValuesHandler() {
			@Override
			public void handle(String[] values) {
				// id	effectiveTime	active	moduleId	definitionStatusId
				final Concept concept = getCreateConcept(new Long(values[0]));
				concept.setActive("1".equals(values[2]));
			}
		}, "concepts");
	}

	private Concept getCreateConcept(String id) {
		return getCreateConcept(new Long(id));
	}

	private Concept getCreateConcept(Long id) {
		Concept concept = concepts.get(id);
		if (concept == null) {
			concept = new Concept(id);
			concepts.put(id, concept);
		}
		return concept;
	}

	private void readLines(ZipInputStream conceptsFileStream, ValuesHandler valuesHandler, String componentType) throws IOException {
		logger.info("Reading {} ", componentType);
		long linesRead = 0L;
		final BufferedReader reader = new BufferedReader(new InputStreamReader(conceptsFileStream));
		String line;
		reader.readLine(); // discard header line
		while ((line = reader.readLine()) != null) {
			valuesHandler.handle(line.split("\\t"));
			linesRead++;
			if (linesRead % 100000 == 0) {
				logger.info("{} {} read", linesRead, componentType);
			}
		}
		logger.info("{} {} read in total", linesRead, componentType);
	}

	private String formatAsMB(long bytes) {
		return NumberFormat.getInstance().format((bytes / 1024) / 1024);
	}

	private interface ValuesHandler {
		void handle(String[] values);
	}
}
