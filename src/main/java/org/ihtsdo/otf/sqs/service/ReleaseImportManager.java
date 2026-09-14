package org.ihtsdo.otf.sqs.service;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import java.util.Map;

import org.ihtsdo.otf.snomedboot.ReleaseImportException;
import org.ihtsdo.otf.snomedboot.ReleaseImporter;
import org.ihtsdo.otf.snomedboot.domain.Concept;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.snomedboot.factory.implementation.HighLevelComponentFactoryAdapterImpl;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStore;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStoreComponentFactoryImpl;
import org.ihtsdo.otf.sqs.service.store.DiskReleaseStore;
import org.ihtsdo.otf.sqs.service.store.RamReleaseStore;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReleaseImportManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ReleaseImporter releaseImporter;
	private final ComponentStore componentStore;

	public ReleaseImportManager() {
		componentStore = new ComponentStore();
		releaseImporter = new ReleaseImporter();
	}

	public ReleaseStore openExistingReleaseStore(File indexDirectory) {
		final ReleaseStore releaseStore = new DiskReleaseStore(indexDirectory);
		if (releaseStore.isIndexExisting()) {
			return releaseStore;
		} else {
			throw new IllegalStateException("Release store does not exist");
		}
	}

	public ReleaseStore loadReleaseFilesToDiskBasedIndex(File releaseDirectory, LoadingProfile loadingProfile, File indexDirectory) throws ReleaseImportException, IOException {
		return loadReleaseFilesToStore(releaseDirectory, loadingProfile, new DiskReleaseStore(indexDirectory));
	}

	public ReleaseStore loadReleaseFilesToMemoryBasedIndex(File releaseDirectory, LoadingProfile loadingProfile) throws ReleaseImportException, IOException {
		return loadReleaseFilesToStore(releaseDirectory, loadingProfile, new RamReleaseStore());
	}

	private ReleaseStore loadReleaseFilesToStore(File releaseDirectory, LoadingProfile loadingProfile, ReleaseStore releaseStore) throws ReleaseImportException, IOException {
		final ComponentStoreComponentFactoryImpl componentFactory = new ComponentStoreComponentFactoryImpl(componentStore);
		releaseImporter.loadSnapshotReleaseFiles(releaseDirectory.getPath(), loadingProfile,
				new HighLevelComponentFactoryAdapterImpl(loadingProfile, componentFactory, componentFactory), false);
		final Map<Long, ? extends Concept> conceptMap = componentStore.getConcepts();
		return writeToIndex(conceptMap, releaseStore, loadingProfile);
	}

	public boolean isReleaseStoreExists(File indexDirectory) {
		return new DiskReleaseStore(indexDirectory).isIndexExisting();
	}

	/** Concepts built per parallel batch; bounds the pending documents. */
	private static final int BUILD_BATCH_SIZE = 4096;

	/**
	 * Overridable so a test can cross a batch boundary without building
	 * {@value #BUILD_BATCH_SIZE} concepts.
	 */
	int buildBatchSize() {
		return BUILD_BATCH_SIZE;
	}

	protected ReleaseStore writeToIndex(Map<Long, ? extends Concept> conceptMap, ReleaseStore releaseStore, LoadingProfile loadingProfile) throws IOException, ReleaseImportException {
		logger.info("All in memory. Using approx {} MB of memory.", formatAsMB(Runtime.getRuntime().totalMemory()));
		logger.info("Writing to index...");

		try (final ReleaseWriter releaseWriter = new ReleaseWriter(releaseStore)) {
			// Documents are BUILT across cores and WRITTEN in the original
			// iteration order. Construction is the expensive half - cardinality
			// grouping per relationship, 722,404 concepts per index - while the
			// write order fixes the docids, and Lucene returns equal-scoring
			// hits in docid order. Writing concurrently would keep every result
			// set identical and still reorder it, so a validation report would
			// name a different sample of failing concepts run to run.
			//
			// Chunked so the pending documents are bounded: this runs while the
			// whole concept map is still on the heap, which is where the phase
			// already peaks.
			final int batchSize = buildBatchSize();
			final List<Concept> batch = new ArrayList<>(batchSize);
			long conceptsAdded = 0;
			for (Concept concept : conceptMap.values()) {
				if (!concept.isActive() && !loadingProfile.isInactiveConcepts()) {
					continue;
				}
				batch.add(concept);
				if (batch.size() == batchSize) {
					conceptsAdded = writeBatch(releaseWriter, batch, loadingProfile, conceptsAdded);
					batch.clear();
				}
			}
			conceptsAdded = writeBatch(releaseWriter, batch, loadingProfile, conceptsAdded);

			logger.info("{} concepts added to index in total.", conceptsAdded);
			logger.info("Closing index writer.");
		}
		conceptMap.clear();
		System.gc();
		logger.info("Finished creating index. Using approx {} MB of memory.", formatAsMB(Runtime.getRuntime().totalMemory()));

		return releaseStore;
	}

	/**
	 * Builds a batch's documents across all cores, then writes them in the
	 * order the concepts were given.
	 */
	private long writeBatch(ReleaseWriter releaseWriter, List<Concept> batch,
			LoadingProfile loadingProfile, long conceptsAdded) throws IOException, ReleaseImportException {
		if (batch.isEmpty()) {
			return conceptsAdded;
		}
		final boolean stated = loadingProfile.isStatedRelationships();
		final List<Document> documents;
		try {
			documents = batch.parallelStream()
					.map(concept -> releaseWriter.buildDocument(concept, stated))
					.toList();
		} catch (IllegalArgumentException e) {
			// Content this index cannot represent - an effective time that is
			// not a date. Reported as an import failure, which is what this
			// method declares, rather than as an unchecked exception escaping
			// past the contract.
			throw new ReleaseImportException(e.getMessage(), e);
		}

		long added = conceptsAdded;
		for (Document document : documents) {
			releaseWriter.addDocument(document);
			added++;
			if (added % 100000 == 0) {
				logger.info("{} concepts added to index...", added);
			}
		}
		return added;
	}

	private String formatAsMB(long bytes) {
		return NumberFormat.getInstance().format((bytes / 1024) / 1024);
	}

}
