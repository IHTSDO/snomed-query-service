package org.ihtsdo.otf.sqs.service;

import org.ihtsdo.otf.snomedboot.ReleaseImporter;
import org.ihtsdo.otf.snomedboot.domain.Concept;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.sqs.service.store.DiskReleaseStore;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Map;

public class ReleaseImportManager {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private ReleaseImporter releaseImporter;

	public ReleaseImportManager() {
		releaseImporter = new ReleaseImporter();
	}

	public ReleaseStore openExistingReleaseStore() {
		final ReleaseStore releaseStore = new DiskReleaseStore();
		if (releaseStore.isIndexExisting()) {
			return releaseStore;
		} else {
			throw new IllegalStateException("Release store does not exist");
		}
	}

	public ReleaseStore loadReleaseFiles(File releaseDirectory, LoadingProfile loadingProfile) throws IOException, InterruptedException {
		final Map<Long, ? extends org.ihtsdo.otf.snomedboot.domain.Concept> conceptMap =
				releaseImporter.loadReleaseFiles(releaseDirectory.getPath(), loadingProfile);
		return writeToIndex(conceptMap, new DiskReleaseStore());
	}

	public boolean isReleaseStoreExists() {
		return new DiskReleaseStore().isIndexExisting();
	}

	protected ReleaseStore writeToIndex(Map<Long, ? extends org.ihtsdo.otf.snomedboot.domain.Concept> conceptMap, ReleaseStore releaseStore) throws IOException {
		logger.info("All in memory. Using approx {} MB of memory.", formatAsMB(Runtime.getRuntime().totalMemory()));
		logger.info("Writing to index...");

		try (final ReleaseWriter releaseWriter = new ReleaseWriter(releaseStore)) {
			long conceptsAdded = 0;
			for (Concept concept : conceptMap.values()) {
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

		conceptMap.clear();
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

	private String formatAsMB(long bytes) {
		return NumberFormat.getInstance().format((bytes / 1024) / 1024);
	}

}
