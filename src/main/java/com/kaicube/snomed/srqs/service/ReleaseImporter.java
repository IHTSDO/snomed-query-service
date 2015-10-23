package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.domain.Concept;
import com.kaicube.snomed.srqs.domain.ConceptConstants;
import com.kaicube.snomed.srqs.domain.Relationship;
import com.kaicube.snomed.srqs.domain.rf2.ComponentFields;
import com.kaicube.snomed.srqs.domain.rf2.DescriptionFields;
import com.kaicube.snomed.srqs.domain.rf2.RefsetFields;
import com.kaicube.snomed.srqs.domain.rf2.RelationshipFields;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.NumberFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReleaseImporter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Long2ObjectMap<Concept> concepts;

	public ReleaseImporter() {
		concepts = new Long2ObjectOpenHashMap<>();
	}

	public ReleaseStore loadReleaseZip(String releaseDirPath, LoadingMode loadingMode) throws IOException {
		File zipFile = findZipFilePath(releaseDirPath);
		logger.info("Loading release archive {}", zipFile.getAbsolutePath());
		try (final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
			ZipEntry nextEntry;
			while ((nextEntry = zipInputStream.getNextEntry()) != null) {
				final String entryName = nextEntry.getName();
				if (entryName.contains("sct2_Concept_Snapshot")) {
					loadConcepts(zipInputStream);
				} else if (entryName.contains("sct2_Relationship_Snapshot")) {
					loadRelationships(zipInputStream, loadingMode);
				} else if (entryName.contains("sct2_Description_Snapshot")) {
					loadDescriptions(zipInputStream);
				} else if (entryName.contains("der2_") && entryName.contains("Snapshot")) {
					loadRefsets(zipInputStream);
				}
			}
		}

		return writeToIndex();
	}

	protected ReleaseStore writeToIndex() throws IOException {
		logger.info("All in memory. Using approx {} MB of memory.", formatAsMB(Runtime.getRuntime().totalMemory()));
		logger.info("Writing to index...");

		final ReleaseStore releaseStore = new ReleaseStore();
		try (final ReleaseWriter releaseWriter = new ReleaseWriter(releaseStore)) {
			long conceptsAdded = 0;
			for (Concept concept : concepts.values()) {
				if (concept.isActive()) {
					releaseWriter.addConcept(concept);
					conceptsAdded++;
					if (conceptsAdded % 100000 == 0) {
						logger.info("{} active concepts added to index...", conceptsAdded);
					}
				}
			}
			logger.info("{} concepts added to index in total.", conceptsAdded);
			logger.info("Closing index writer.");
		}

		concepts = null;
		System.gc();
		logger.info("Finished creating index. Using approx {} MB of memory.", formatAsMB(Runtime.getRuntime().totalMemory()));

		return releaseStore;
	}

	private File findZipFilePath(String releaseDirPath) throws FileNotFoundException {
		final File releaseDir = new File(releaseDirPath);
		if (!releaseDir.isDirectory()) {
			throw new FileNotFoundException("Could not find release directory.");
		}
		final File[] zips = releaseDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".zip");
			}
		});
		if (zips.length == 0) {
			throw new FileNotFoundException("Please place a SNOMED-CT RF2 release zip file in the release directory. Content will be loaded from there.");
		}
		return zips[0];
	}

	private void loadConcepts(ZipInputStream zipInputStream) throws IOException {
		readLines(zipInputStream, new ValuesHandler() {
			@Override
			public void handle(String[] values) {
				final Concept concept = getCreateConcept(new Long(values[ComponentFields.id]));
				concept.update(values);
			}
		}, "concepts");
	}

	private void loadRelationships(ZipInputStream zipInputStream, final LoadingMode loadingMode) throws IOException {
		readLines(zipInputStream, new ValuesHandler() {
			@Override
			public void handle(String[] values) {
				if (values[RelationshipFields.active].equals("1")) {
					final Concept concept = getCreateConcept(values[RelationshipFields.sourceId]);
					final String type = values[RelationshipFields.typeId];
					final String value = values[RelationshipFields.destinationId];
					concept.addAttribute(type, value);
					if (type.equals(ConceptConstants.isA)) {
						concept.addParent(getCreateConcept(value));
					}
					if (loadingMode == LoadingMode.full) {
						concept.addRelationship(new Relationship(values));
					}
				}
			}
		}, "relationships");
	}

	private void loadDescriptions(ZipInputStream zipInputStream) throws IOException {
		readLines(zipInputStream, new ValuesHandler() {
			@Override
			public void handle(String[] values) {
				if ("1".equals(values[DescriptionFields.active]) && ConceptConstants.FSN.equals(values[DescriptionFields.typeId])) {
					final Concept concept = getCreateConcept(new Long(values[DescriptionFields.conceptId]));
					concept.setFsn(values[DescriptionFields.term]);
				}
			}
		}, "descriptions");
	}

	private void loadRefsets(ZipInputStream zipInputStream) throws IOException {
		readLines(zipInputStream, new ValuesHandler() {
			@Override
			public void handle(String[] values) {
				if ("1".equals(values[DescriptionFields.active])) {
					final String referencedComponentId = values[RefsetFields.referencedComponentId];
					if (Concept.isConceptId(referencedComponentId)) {
						getCreateConcept(new Long(referencedComponentId)).addMemberOfRefsetId(new Long(values[RefsetFields.refsetId]));
					}
				}
			}
		}, "reference set members");
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

	protected Long2ObjectMap<Concept> getConcepts() {
		return concepts;
	}
}
