package org.ihtsdo.otf.sqs.service;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.FilterLeafReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.index.SlowCodecReaderWrapper;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.service.store.RamReleaseStore;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Concept ids are read from doc values rather than by decompressing a
 * stored-fields block per hit.
 *
 * <p>The read path deliberately falls back to the stored field when an index
 * predates the doc-values field, so a broken writer would leave every query
 * correct and merely slow - and silently so. These tests pin the writer
 * separately from the reader for that reason.
 */
public class ConceptIdDocValuesTest {

	private ReleaseStore releaseStore;
	private SnomedQueryService snomedQueryService;

	@Before
	public void setup() throws Exception {
		releaseStore = new TestReleaseImportManager(true)
				.buildTestTaxonomy(LoadingProfile.light.withoutInactiveConcepts());
		snomedQueryService = new SnomedQueryService(releaseStore);
	}

	/**
	 * The writer half: every concept document must carry the numeric id, or the
	 * reader silently falls back and the optimisation does nothing.
	 */
	@Test
	public void everyConceptDocumentCarriesTheNumericId() throws Exception {
		final DirectoryReader reader = DirectoryReader.open(releaseStore.getDirectory());
		final IndexSearcher searcher = new IndexSearcher(reader);

		final TopDocs conceptDocs = searcher.search(
				new TermQuery(new Term("type", "concept")), Integer.MAX_VALUE);
		assertTrue("test taxonomy must hold concepts, or this proves nothing",
				conceptDocs.scoreDocs.length > 0);

		int withDocValues = 0;
		for (LeafReaderContext leaf : reader.leaves()) {
			final NumericDocValues docValues =
					leaf.reader().getNumericDocValues(ConceptFieldNames.ID_DOC_VALUES);
			if (docValues == null) {
				continue;
			}
			for (ScoreDoc scoreDoc : conceptDocs.scoreDocs) {
				final int local = scoreDoc.doc - leaf.docBase;
				if (local < 0 || local >= leaf.reader().maxDoc()) {
					continue;
				}
				if (docValues.advanceExact(local)) {
					final long fromDocValues = docValues.longValue();
					final String fromStored =
							searcher.storedFields().document(scoreDoc.doc).get(ConceptFieldNames.ID);
					assertEquals("numeric id must agree with the stored id",
							Long.parseLong(fromStored), fromDocValues);
					withDocValues++;
				}
			}
		}
		assertEquals("every concept document must carry " + ConceptFieldNames.ID_DOC_VALUES,
				conceptDocs.scoreDocs.length, withDocValues);
	}

	/**
	 * The reader half: ids must come back in hit order, not docid order. Callers
	 * page with offset/limit and report the first N failures, so a reordering
	 * would change which instances a validation report names.
	 */
	@Test
	public void idsAreReturnedInHitOrderNotDocidOrder() throws Exception {
		final String ecl = "<<138875005 |SNOMED CT Concept|";
		final List<Long> fromService =
				snomedQueryService.eclQueryReturnConceptIdentifiers(ecl, 0, -1).conceptIds();
		assertFalse("expression must match something", fromService.isEmpty());

		// Oracle: the same query the converter emits, read the old way, in the
		// order the searcher hands the hits back.
		final IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(releaseStore.getDirectory()));
		final BooleanQuery query = new BooleanQuery.Builder()
				.add(new TermQuery(new Term(ConceptFieldNames.ID, "138875005")), BooleanClause.Occur.SHOULD)
				.add(new TermQuery(new Term(ConceptFieldNames.ANCESTOR, "138875005")), BooleanClause.Occur.SHOULD)
				.build();
		final TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE);
		final List<Long> expected = new ArrayList<>();
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			expected.add(Long.parseLong(
					searcher.storedFields().document(scoreDoc.doc).get(ConceptFieldNames.ID)));
		}

		assertEquals("doc-values read must preserve the searcher's hit order", expected, fromService);
	}

	/** Paging must still apply after the change, and to the same sequence. */
	@Test
	public void offsetAndLimitStillSelectTheSameSlice() throws Exception {
		final String ecl = "<<138875005 |SNOMED CT Concept|";
		final List<Long> all = snomedQueryService.eclQueryReturnConceptIdentifiers(ecl, 0, -1).conceptIds();
		assertTrue("need several hits to slice", all.size() >= 3);

		final ConceptIdResultsHolder page = new ConceptIdResultsHolder(
				snomedQueryService.eclQueryReturnConceptIdentifiers(ecl, 1, 2).conceptIds());
		assertNotNull(page.ids);
		assertEquals("offset must skip exactly one hit of the same sequence",
				all.subList(1, Math.min(all.size(), 1 + page.ids.size())), page.ids);
	}

	/**
	 * An index written before the doc-values field existed must still read
	 * correctly, through the stored id, and in the same order.
	 *
	 * <p>The old index is this one copied with the doc-values field hidden, so
	 * every other field, and the document order, is identical.
	 */
	@Test
	public void indexWithoutTheNumericIdFallsBackToTheStoredId() throws Exception {
		final RamReleaseStore oldStore = new RamReleaseStore();
		try (DirectoryReader reader = DirectoryReader.open(releaseStore.getDirectory());
			 IndexWriter writer = new IndexWriter(oldStore.getDirectory(), new IndexWriterConfig(oldStore.createAnalyzer()))) {
			for (LeafReaderContext leaf : reader.leaves()) {
				writer.addIndexes(SlowCodecReaderWrapper.wrap(new WithoutNumericId(leaf.reader())));
			}
		}
		try (DirectoryReader oldReader = DirectoryReader.open(oldStore.getDirectory())) {
			for (LeafReaderContext leaf : oldReader.leaves()) {
				assertNull("the copy must not carry " + ConceptFieldNames.ID_DOC_VALUES,
						leaf.reader().getNumericDocValues(ConceptFieldNames.ID_DOC_VALUES));
			}
		}

		final SnomedQueryService oldIndexService = new SnomedQueryService(oldStore);
		for (String ecl : List.of("<<138875005 |SNOMED CT Concept|", "*:260686004 != 129264002", "<<71388002")) {
			final List<Long> expected = snomedQueryService.eclQueryReturnConceptIdentifiers(ecl, 0, -1).conceptIds();
			assertFalse(ecl + " must match something", expected.isEmpty());
			assertEquals(ecl, expected, oldIndexService.eclQueryReturnConceptIdentifiers(ecl, 0, -1).conceptIds());
		}
	}

	/** Hides the numeric id, as an index written by the previous version lacks it. */
	private static final class WithoutNumericId extends FilterLeafReader {

		private WithoutNumericId(LeafReader in) {
			super(in);
		}

		@Override
		public FieldInfos getFieldInfos() {
			final List<FieldInfo> kept = new ArrayList<>();
			for (FieldInfo fieldInfo : in.getFieldInfos()) {
				if (!ConceptFieldNames.ID_DOC_VALUES.equals(fieldInfo.name)) {
					kept.add(fieldInfo);
				}
			}
			return new FieldInfos(kept.toArray(new FieldInfo[0]));
		}

		@Override
		public NumericDocValues getNumericDocValues(String field) throws IOException {
			return ConceptFieldNames.ID_DOC_VALUES.equals(field) ? null : in.getNumericDocValues(field);
		}

		@Override
		public CacheHelper getCoreCacheHelper() {
			return null;
		}

		@Override
		public CacheHelper getReaderCacheHelper() {
			return null;
		}
	}

	private static final class ConceptIdResultsHolder {
		private final List<Long> ids;

		private ConceptIdResultsHolder(List<Long> ids) {
			this.ids = ids;
		}
	}
}
