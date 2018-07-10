package org.ihtsdo.otf.sqs.service;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

import org.ihtsdo.otf.snomedboot.ReleaseImportException;
import org.ihtsdo.otf.snomedboot.ReleaseImporter;
import org.ihtsdo.otf.snomedboot.domain.Concept;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentFactoryImpl;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStore;
import org.ihtsdo.otf.sqs.service.store.DiskReleaseStore;
import org.ihtsdo.otf.sqs.service.store.RamReleaseStore;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReleaseImportManager {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private ReleaseImporter releaseImporter;
	private final ComponentStore componentStore;

	public ReleaseImportManager() {
		componentStore = new ComponentStore();
		releaseImporter = new ReleaseImporter();
	}

	public ReleaseStore openExistingReleaseStore() {
		final ReleaseStore releaseStore = new DiskReleaseStore(new File("index"));
		if (releaseStore.isIndexExisting()) {
			return releaseStore;
		} else {
			throw new IllegalStateException("Release store does not exist");
		}
	}

	public ReleaseStore loadReleaseFilesToDiskBasedIndex(File releaseDirectory, LoadingProfile loadingProfile, File indexDirectory) throws ReleaseImportException, IOException {
		return loadReleaseFiledToStore(releaseDirectory, loadingProfile, new DiskReleaseStore(indexDirectory));
	}

	public ReleaseStore loadReleaseFilesToMemoryBasedIndex(File releaseDirectory, LoadingProfile loadingProfile) throws ReleaseImportException, IOException {
		return loadReleaseFiledToStore(releaseDirectory, loadingProfile, new RamReleaseStore());
	}

	private ReleaseStore loadReleaseFiledToStore(File releaseDirectory, LoadingProfile loadingProfile, ReleaseStore releaseStore) throws ReleaseImportException, IOException {
		releaseImporter.loadSnapshotReleaseFiles(releaseDirectory.getPath(), loadingProfile, new ComponentFactoryImpl(componentStore));
		final Map<Long, ? extends Concept> conceptMap = componentStore.getConcepts();
		return writeToIndex(conceptMap, releaseStore, loadingProfile);
	}

	public boolean isReleaseStoreExists(File indexDirectory) {
		return new DiskReleaseStore(indexDirectory).isIndexExisting();
	}

	protected ReleaseStore writeToIndex(Map<Long, ? extends Concept> conceptMap, ReleaseStore releaseStore, LoadingProfile loadingProfile) throws IOException, ReleaseImportException {
		logger.info("All in memory. Using approx {} MB of memory.", formatAsMB(Runtime.getRuntime().totalMemory()));
		logger.info("Writing to index...");

		try (final ReleaseWriter releaseWriter = new ReleaseWriter(releaseStore)) {
			long conceptsAdded = 0;
			for (Concept concept : conceptMap.values()) {
				if (concept.isActive() || loadingProfile.isInactiveConcepts()) {
					releaseWriter.addConcept(concept, loadingProfile.isStatedRelationships());
					conceptsAdded++;
					if (conceptsAdded % 100000 == 0) {
						logger.info("{} concepts added to index...", conceptsAdded);
					}
				}
			}
			logger.info("{} concepts added to index in total.", conceptsAdded);
			logger.info("Closing index writer.");
		} catch (ParseException e) {
			throw new ReleaseImportException("Failed to parse date.", e);
		}

		conceptMap.clear();
		System.gc();
		logger.info("Finished creating index. Using approx {} MB of memory.", formatAsMB(Runtime.getRuntime().totalMemory()));

		return releaseStore;
	}

	private String formatAsMB(long bytes) {
		return NumberFormat.getInstance().format((bytes / 1024) / 1024);
	}

}
