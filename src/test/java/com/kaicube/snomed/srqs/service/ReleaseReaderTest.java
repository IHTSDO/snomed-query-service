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

	@Test
	public void testParseRefinedExpressionConstraintWithNameAndValueUsingEquals() throws Exception {
		final ELQuery query = releaseReader.parseQuery("* : 116676008 = 79654002");
		Assert.assertTrue(query.isFocusConceptWildcard());
		Assert.assertEquals("116676008", query.getAttributeName());
		Assert.assertEquals(ELQuery.ExpressionComparisonOperator.equals, query.getAttributeOperator());
		Assert.assertEquals("79654002", query.getAttributeValue());
	}

	@Test
	public void testParseRefinedExpressionConstraintWithNameAndValueUsingNotEquals() throws Exception {
		final ELQuery query = releaseReader.parseQuery("* : 116676008 != 79654002");
		Assert.assertTrue(query.isFocusConceptWildcard());
		Assert.assertEquals("116676008", query.getAttributeName());
		Assert.assertEquals(ELQuery.ExpressionComparisonOperator.notEquals, query.getAttributeOperator());
		Assert.assertEquals("79654002", query.getAttributeValue());
	}

	@Test
	public void testParseRefsetMemberFocusConcept() throws Exception {
		final ELQuery query = releaseReader.parseQuery("^447566000");
		Assert.assertFalse(query.isFocusConceptWildcard());
		Assert.assertEquals("447566000", query.getMemberOfRefsetId());
	}
}
