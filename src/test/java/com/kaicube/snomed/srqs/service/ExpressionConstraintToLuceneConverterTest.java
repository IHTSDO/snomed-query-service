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
		final String ecQuery = "307824009";
		final String expectedLuceneQuery = "id:307824009";
		assertConversion(ecQuery, expectedLuceneQuery);
	}

	@Test
	public void test_simpleExpressionConstraint_descendantOrSelfOf() throws Exception {
		final String parse = parser.parse("<< 307824009");
		Assert.assertEquals("(id:307824009 OR ancestor:307824009)", parse);
	}

	@Test
	public void test_simpleExpressionConstraint_descendantOf() throws Exception {
		assertConversion("<307824009", "ancestor:307824009");
	}

	@Test
	public void test_simpleExpressionConstraint_ancestorOrSelfOf() throws Exception {
		assertConversion(">>307824009", "ANCESTOR_OR_SELF_OF(307824009)");
	}

	@Test
	public void test_simpleExpressionConstraint_ancestorOf() throws Exception {
		assertConversion(">307824009", "ANCESTOR_OF(307824009)");
	}

	@Test
	public void test_simpleExpressionConstraint_wildcard() throws Exception {
		assertConversion("*", "id:*");
	}

	@Test
	public void test_simpleExpressionConstraint_memberOf() throws Exception {
		assertConversion("^900000000000509007", "memberOf:900000000000509007");
	}

	@Test
	public void test_refinedExpressionConstraint_attributeNameAndWildcardValue() {
		assertConversion("307824009:260686004=*", "id:307824009 AND 260686004:*");
	}

	@Test
	public void test_refinedExpressionConstraint_attributeNameAndConceptValue() {
		assertConversion("307824009:363699004=34164001", "id:307824009 AND 363699004:34164001");
	}

	@Test
	public void test_refinedExpressionConstraint_wildcardConcept_attributeNameAndConceptValue() {
		assertConversion("*:363699004=34164001", "id:* AND 363699004:34164001");
	}

	@Test
	public void test_refinedExpressionConstraint_wildcardConcept_attributeValueDescendantOf() {
		assertConversion("*:260686004=<129264002", "id:* AND 260686004:ATTRIBUTE_DESCENDANT_OF(129264002)");
	}

	@Test
	public void test_refinedExpressionConstraint_wildcardConcept_attributeValueDescendantOrSelf() {
		assertConversion("*:260686004=<<129264002", "id:* AND 260686004:ATTRIBUTE_DESCENDANT_OR_SELF_OF(129264002)");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void test_refinedExpressionConstraint_wildcardAttributeNameAndConceptValue() {
		parser.parse("307824009:*=34164001");
	}

	private void assertConversion(String ecQuery, String expectedLuceneQuery) {
		Assert.assertEquals(expectedLuceneQuery, parser.parse(ecQuery));
	}

}
