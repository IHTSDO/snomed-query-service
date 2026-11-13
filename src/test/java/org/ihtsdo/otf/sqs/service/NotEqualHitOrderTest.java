package org.ihtsdo.otf.sqs.service;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.service.store.RamReleaseStore;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Hit order of an out-of-range ({@code !=}) query.
 *
 * <p>Results are ordered by score. The range chain that used to answer
 * {@code !=} is one constant-scoring range per gap between consecutive excluded
 * ids, so a concept scores one point for every gap it holds a value in. Callers
 * that keep only the first N results depend on that order, so the term-set
 * rendering must reproduce it rather than return every match with the same
 * score in index order.
 */
public class NotEqualHitOrderTest {

	private static final String METHOD = "260686004";

	private SnomedQueryService snomedQueryService;

	/**
	 * Excluding 300000001 and 600000001 leaves three gaps:
	 * below 300000001, between the two, and above 600000001.
	 * Concepts are written so index order is the reverse of score order.
	 */
	@Before
	public void setup() throws Exception {
		ReleaseStore releaseStore = new RamReleaseStore();
		try (IndexWriter writer = new IndexWriter(releaseStore.getDirectory(), new IndexWriterConfig(releaseStore.createAnalyzer()))) {
			writer.addDocument(concept(100000001L, "300000001"));                         // only an excluded value: no match
			writer.addDocument(concept(100000002L, "400000001", "500000001"));            // one gap, two values in it
			writer.addDocument(concept(100000003L, "200000001"));                         // one gap
			writer.addDocument(concept(100000004L, "200000001", "400000001"));            // two gaps
			writer.addDocument(concept(100000005L, "200000001", "400000001", "700000001")); // three gaps
		}
		snomedQueryService = new SnomedQueryService(releaseStore);
	}

	@Test
	public void conceptsHoldingValuesInMoreGapsComeFirst() throws Exception {
		assertEquals(List.of(100000005L, 100000004L, 100000002L, 100000003L), ids());
	}

	@Test
	public void termSetOrderMatchesTheRangeChain() throws Exception {
		List<Long> termSetForm = ids();
		System.setProperty(SnomedQueryService.RANGE_FORM_PROPERTY, "true");
		try {
			assertEquals(ids(), termSetForm);
		} finally {
			System.clearProperty(SnomedQueryService.RANGE_FORM_PROPERTY);
		}
	}

	private List<Long> ids() throws Exception {
		return snomedQueryService.eclQueryReturnConceptIdentifiers(
				"*:" + METHOD + " != (300000001 OR 600000001)", 0, -1).conceptIds();
	}

	private static Document concept(long id, String... methods) {
		Document doc = new Document();
		doc.add(new StringField(ConceptFieldNames.TYPE, ConceptFieldNames.TYPE_CONCEPT, Field.Store.YES));
		doc.add(new StringField(ConceptFieldNames.ID, Long.toString(id), Field.Store.YES));
		doc.add(new NumericDocValuesField(ConceptFieldNames.ID_DOC_VALUES, id));
		for (String method : methods) {
			doc.add(new StringField(METHOD, method, Field.Store.YES));
		}
		return doc;
	}
}
