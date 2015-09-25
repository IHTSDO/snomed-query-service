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
		final ReleaseReader.ExpressionConstraintListener listener = releaseReader.parseQuery("39928001");
		Assert.assertEquals("39928001", listener.focusConcept);
		Assert.assertFalse(listener.descendantOf);
		Assert.assertFalse(listener.ancestorOf);
		Assert.assertTrue(listener.includeSelf);
	}

	@Test
	public void testParseDescendantOfQuery() throws Exception {
		final ReleaseReader.ExpressionConstraintListener listener = releaseReader.parseQuery("< 39928001");
		Assert.assertEquals("39928001", listener.focusConcept);
		Assert.assertTrue(listener.descendantOf);
		Assert.assertFalse(listener.ancestorOf);
		Assert.assertFalse(listener.includeSelf);
	}

	@Test
	public void testParseDescendantOrSelfOfQuery() throws Exception {
		final ReleaseReader.ExpressionConstraintListener listener = releaseReader.parseQuery("<< 39928001");
		Assert.assertEquals("39928001", listener.focusConcept);
		Assert.assertTrue(listener.descendantOf);
		Assert.assertFalse(listener.ancestorOf);
		Assert.assertTrue(listener.includeSelf);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testParseRefinedExpressionConstraint() throws Exception {
		releaseReader.parseQuery("<< 39928001 : 116676008 = 79654002");
	}
}