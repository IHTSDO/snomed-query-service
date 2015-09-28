package com.kaicube.snomed.srqs.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReleaseReaderTest {

	private ReleaseReader releaseReader;

	@Before
	public void setup() {
		releaseReader = new ReleaseReader();
	}

	@Test
	public void testParseQuery() throws Exception {
		final ELQuery query = releaseReader.parseQuery("39928001");
		Assert.assertEquals("39928001", query.getFocusConceptId());
		Assert.assertFalse(query.isDescendantOf());
		Assert.assertFalse(query.isAncestorOf());
		Assert.assertTrue(query.isIncludeSelf());
	}

	@Test
	public void testParseDescendantOfQuery() throws Exception {
		final ELQuery query = releaseReader.parseQuery("< 39928001");
		Assert.assertEquals("39928001", query.getFocusConceptId());
		Assert.assertTrue(query.isDescendantOf());
		Assert.assertFalse(query.isAncestorOf());
		Assert.assertFalse(query.isIncludeSelf());
	}

	@Test
	public void testParseDescendantOrSelfOfQuery() throws Exception {
		final ELQuery query = releaseReader.parseQuery("<< 39928001");
		Assert.assertEquals("39928001", query.getFocusConceptId());
		Assert.assertTrue(query.isDescendantOf());
		Assert.assertFalse(query.isAncestorOf());
		Assert.assertTrue(query.isIncludeSelf());
	}

	@Test
	public void testParseRefinedExpressionConstraintWithName() throws Exception {
		releaseReader.parseQuery("<< 39928001 : 116676008");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testParseRefinedExpressionConstraintWithNameAndValue() throws Exception {
		releaseReader.parseQuery("<< 39928001 : 116676008 = 79654002");
	}
}
