package org.ihtsdo.otf.sqs.service;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.StoredFields;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Documents are built across cores but must be WRITTEN in the order the
 * concept map iterates. Write order fixes the docids, and Lucene returns
 * equal-scoring hits in docid order, so reordering would leave every result
 * set identical while changing which concepts a truncated validation report
 * names - a silent, run-to-run difference on identical input.
 */
public class IndexWriteOrderTest {

	/**
	 * A batch size of 4 against the 18-concept test taxonomy, so the boundary
	 * flush runs several times and the final partial batch runs too. The
	 * default 4,096 would take one tail flush and never exercise the seam.
	 */
	private static final int SMALL_BATCH = 4;

	@Test
	public void docidOrderMatchesIterationOrderAcrossBatchBoundaries() throws Exception {
		// Larger than the taxonomy, so every concept goes in one batch and the
		// boundary flush never runs: the serial reference order.
		List<String> serial = writeAndReadIds(1000);
		List<String> batched = writeAndReadIds(SMALL_BATCH);

		assertTrue("test taxonomy must span several batches", serial.size() > SMALL_BATCH * 2);
		assertEquals("docid order must not depend on the batch size", serial, batched);
	}

	private List<String> writeAndReadIds(int batchSize) throws Exception {
		TestReleaseImportManager importManager = new TestReleaseImportManager(true) {
			@Override
			int buildBatchSize() {
				return batchSize;
			}
		};
		ReleaseStore store = importManager.buildTestTaxonomy(
				LoadingProfile.light.withoutInactiveConcepts());
		List<String> ids = new ArrayList<>();
		try (IndexReader reader = DirectoryReader.open(store.getDirectory())) {
			StoredFields storedFields = reader.storedFields();
			for (LeafReaderContext leaf : reader.leaves()) {
				for (int doc = 0; doc < leaf.reader().maxDoc(); doc++) {
					String id = storedFields.document(leaf.docBase + doc).get(ConceptFieldNames.ID);
					if (id != null) {
						ids.add(id);
					}
				}
			}
		}
		return ids;
	}
}
