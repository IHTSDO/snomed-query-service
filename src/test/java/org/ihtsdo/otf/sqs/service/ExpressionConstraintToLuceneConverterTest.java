package org.ihtsdo.otf.sqs.service;

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
	public void test_simpleExpressionConstraint_conceptReference() {
		final String ecQuery = "307824009";
		final String expectedLuceneQuery = "id:307824009";
		assertConversion(ecQuery, expectedLuceneQuery);
	}

	@Test
	public void test_simpleExpressionConstraint_descendantOrSelfOf() {
		final String parse = parser.parse("<< 307824009");
		Assert.assertEquals("(id:307824009 OR ancestor:307824009)", parse);
	}

	@Test
	public void test_simpleExpressionConstraint_descendantOf() {
		assertConversion("<307824009", "ancestor:307824009");
	}

	@Test
	public void test_simpleExpressionConstraint_ancestorOrSelfOf() {
		assertConversion(">>307824009", "ANCESTOR_OR_SELF_OF(307824009)");
	}

	@Test
	public void test_simpleExpressionConstraint_ancestorOf() {
		assertConversion(">307824009", "ANCESTOR_OF(307824009)");
	}

	@Test
	public void test_simpleExpressionConstraint_wildcard() {
		assertConversion("*", "id:*");
	}

	@Test
	public void test_simpleExpressionConstraint_memberOf() {
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
	public void test_refinedExpressionConstraint_wildcardAttributeNameAndConceptValue()  {
		parser.parse("307824009:*=34164001");
	}
	
	@Test
	public void test_refinedEclWithMultipleDomainsExclusion() {
		assertConversion("(*:272741003=*) MINUS (<<91723000 OR <<723264001)",
				"(id:* AND 272741003:*) NOT ((id:91723000 OR ancestor:91723000) OR (id:723264001 OR ancestor:723264001))");
	}
	
	@Test
	public void test_refinedEclWithOneDomainExclusion() {
		assertConversion("(*:272741003=*) MINUS <<91723000",
				"(id:* AND 272741003:*) NOT (id:91723000 OR ancestor:91723000)");

	}

	@Test(expected = UnsupportedOperationException.class)
	public void test_nestedExpressionOnAttributeName() {
		assertConversion("(*:<<272741003=*) MINUS <<91723000",
				" ");

	}

	@Test
	public void test_refinedEclWithConcreteValueMinRangeOnly() {
		assertConversion("<< 373873005: 3264475007 >= #10",
				"(id:373873005 OR ancestor:373873005) AND 3264475007_value:[10 TO *]");
	}

	@Test
	public void test_refinedEclWithConcreteValueMaxRangeOnly() {
		assertConversion("<< 373873005: 3264475007 < #10",
				"(id:373873005 OR ancestor:373873005) AND 3264475007_value:{* TO 10}");
	}

	@Test
	public void test_refinedEclWithConcreteValueNotEqualTo() {
		assertConversion("<< 373873005: 3264475007 != #1",
				"(id:373873005 OR ancestor:373873005) AND 3264475007_value:{1 TO *} OR 3264475007_value:{* TO 1}");
	}

	@Test
	public void test_refinedEclWithConcreteValueRangeInclusive() {
		assertConversion("<< 373873005: 3264475007 >= #1 AND 3264475007 <= #10",
				"(id:373873005 OR ancestor:373873005) AND 3264475007_value:[1 TO *] AND 3264475007_value:[* TO 10]");
	}

	@Test
	public void test_refinedEclWithConcreteValueRangeExclusive() {
		assertConversion("<< 373873005: 3264475007 > #1 AND 3264475007 < #10",
				"(id:373873005 OR ancestor:373873005) AND 3264475007_value:{1 TO *} AND 3264475007_value:{* TO 10}");
	}

	@Test
	public void test_refinedEclWithConcreteValueRange() {
		assertConversion("<< 373873005: [0..*] { ([0..1] 3264475007 >= #10 AND [0..1] 3264475007 <= #20) }",
				"(id:373873005 OR ancestor:373873005) AND (3264475007_totalGrp:[0 TO *] AND 3264475007_grpCard:[0 TO 1] AND 3264475007_value:[10 TO *] AND 3264475007_grpCard:[0 TO 1] AND 3264475007_value:[* TO 20])");
	}

	@Test
	public void test_refinedEclWithConcreteValue() {
		assertConversion("<< 373873005: [0..*] { ([0..1] 3264475007 = #20) }",
				"(id:373873005 OR ancestor:373873005) AND (3264475007_totalGrp:[0 TO *] AND 3264475007_grpCard:[0 TO 1] AND 3264475007_value:[20 TO 20])");
	}

	@Test
	public void testEclQueryWithMultipleDomains() {
		String ecl = "(<< 363787002 |Observable entity (observable entity)| OR << 386053000 |Evaluation procedure (procedure)|): [0..1] { [0..1] 370134009 |Time aspect| = << 7389001 |Time frame (qualifier value)| }";
		assertConversion(ecl,
				"((id:363787002 OR ancestor:363787002) OR (id:386053000 OR ancestor:386053000)) AND 370134009_totalGrp:[0 TO 1] AND 370134009_grpCard:[0 TO 1] AND 370134009:ATTRIBUTE_DESCENDANT_OR_SELF_OF(7389001)");
	}

	@Test
	public void testEclQueryWithNotEqualToOperator() {
		assertConversion("<< 363787002:370134009 != << 7389001",
				"(id:363787002 OR ancestor:363787002) AND 370134009: (* NOT ATTRIBUTE_DESCENDANT_OR_SELF_OF(7389001))");
	}


	private void assertConversion(String ecQuery, String expectedLuceneQuery) {
		Assert.assertEquals(expectedLuceneQuery, parser.parse(ecQuery));
	}

}
