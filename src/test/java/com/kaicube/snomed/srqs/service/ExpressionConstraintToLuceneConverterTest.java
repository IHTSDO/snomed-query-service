package com.kaicube.snomed.srqs.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExpressionConstraintToLuceneConverterTest {

	private ExpressionConstraintToLuceneConverter parser;

	@Before
	public void setup() {
		parser = new ExpressionConstraintToLuceneConverter();
	}

	@Test
	public void test_simpleExpressionConstraint_conceptReference() throws Exception {
		Assert.assertEquals("id:307824009", parser.parse("307824009"));
	}

	@Test
	public void test_simpleExpressionConstraint_descendantOrSelfOf() throws Exception {
		final String parse = parser.parse("<< 307824009");
		Assert.assertEquals("(id:307824009 OR ancestor:307824009)", parse);
	}

	@Test
	public void test_simpleExpressionConstraint_descendantOf() throws Exception {
		Assert.assertEquals("ancestor:307824009", parser.parse("<307824009"));
	}

	@Test
	public void test_simpleExpressionConstraint_ancestorOrSelfOf() throws Exception {
		Assert.assertEquals("ancestorOrSelfOf(307824009)", parser.parse(">>307824009"));
	}

	@Test
	public void test_simpleExpressionConstraint_ancestorOf() throws Exception {
		Assert.assertEquals("ancestorOf(307824009)", parser.parse(">307824009"));
	}

	@Test
	public void test_simpleExpressionConstraint_wildcard() throws Exception {
		Assert.assertEquals("id:*", parser.parse("*"));
	}

	@Test
	public void test_simpleExpressionConstraint_memberOf() throws Exception {
		Assert.assertEquals("memberOf:900000000000509007", parser.parse("^900000000000509007"));
	}

	@Test
	public void test_refinedExpressionConstraint_attributeNameAndWildcardValue() {
		Assert.assertEquals("id:307824009 AND 260686004:*", parser.parse("307824009:260686004=*"));
	}

	@Test
	public void test_refinedExpressionConstraint_attributeNameAndConceptValue() {
		Assert.assertEquals("id:307824009 AND 363699004:34164001", parser.parse("307824009:363699004=34164001"));
	}

	@Test
	public void test_refinedExpressionConstraint_wildcardConcept_attributeNameAndConceptValue() {
		Assert.assertEquals("id:* AND 363699004:34164001", parser.parse("*:363699004=34164001"));
	}

	@Test
	public void test_refinedExpressionConstraint_wildcardConcept_attributeValueDescendantOf() {
		Assert.assertEquals("id:* AND 260686004:attributeDescendantOf(129264002)", parser.parse("*:260686004=<129264002"));
	}

	@Test
	public void test_refinedExpressionConstraint_wildcardConcept_attributeValueDescendantOrSelf() {
		Assert.assertEquals("id:* AND 260686004:attributeDescendantOrSelfOf(129264002)", parser.parse("*:260686004=<<129264002"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void test_refinedExpressionConstraint_wildcardAttributeNameAndConceptValue() {
		parser.parse("307824009:*=34164001");
	}

}
