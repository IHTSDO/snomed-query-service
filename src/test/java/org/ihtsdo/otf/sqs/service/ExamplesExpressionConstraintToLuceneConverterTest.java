package org.ihtsdo.otf.sqs.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ExamplesExpressionConstraintToLuceneConverterTest {

	private ExpressionConstraintToLuceneConverter parser;

	@Before
	public void setup() {
		parser = new ExpressionConstraintToLuceneConverter();
	}

	@Test
	public void testExample_Self_1() {
		assertConversion(
				"404684003 |clinical finding|",

				"id:404684003"
		);
	}

	@Test
	public void testExample_DescendantOf_1() {
		assertConversion(
				"< 404684003 |clinical finding|",

				"ancestor:404684003"
		);
	}

	@Test
	public void testExample_DescendantOrSelfOf_1() {
		assertConversion(
				"<< 73211009 |diabetes mellitus|",

				"(id:73211009 " +
						"OR ancestor:73211009)"
		);
	}

	@Test
	public void testExample_AncestorOf_1() {
		assertConversion(
				"> 40541001 |acute pulmonary edema|",

				"ANCESTOR_OF(40541001)"
		);
	}

	@Test
	public void testExample_AncestorOrSelfOf_1() {
		assertConversion(
				">> 40541001|acute pulmonary edema|",

				"ANCESTOR_OR_SELF_OF(40541001)"
		);
	}

	@Test
	public void testExample_MemberOf_1() {
		assertConversion(
				"^ 700043003 |example problem list concepts reference set |",

				"memberOf:700043003"
		);
	}

	@Test
	public void testExample_Any_1() {
		assertConversion(
				"*",

				"id:*"
		);
	}

	@Test
	public void testExample_Attribute_1() {
		assertConversion(
				"< 19829001 |disorder of lung|:  " +
						"116676008 |associated morphology| = 79654002 |edema|",

				"ancestor:19829001 " +
						"AND 116676008:79654002"
		);
	}

	@Test
	public void testExample_Attribute_2() {
		assertConversion(
				"< 19829001 |disorder of lung|:  " +
						"116676008 |associated morphology| = << 79654002 |edema|",

				"ancestor:19829001 " +
						"AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(79654002)"
		);
	}

	@Test
	public void testExample_Attribute_3() {
		assertConversion(
				"< 404684003 |clinical finding|: " +
						"363698007 |finding site| = << 39057004 |pulmonary valve structure|,  " +
						"116676008 |associated morphology| = << 415582006 |stenosis|",

				"ancestor:404684003 " +
						"AND 363698007:ATTRIBUTE_DESCENDANT_OR_SELF_OF(39057004) " +
						"AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(415582006)"
		);
	}

	@Test
	public void testExample_Attribute_4() {
		assertConversion(
				"* : 246075003 |causative agent| = 387517004 |paracetamol|",

				"id:* " +
						"AND 246075003:387517004"
		);
	}

	@Test
	public void testExample_AttributeGroup_1() {
		assertConversion(
				"< 404684003 |clinical finding|: " +
						"{ 363698007 |finding site| = << 39057004 |pulmonary valve structure|, " +
						"  116676008 |associated morphology| = << 415582006 |stenosis|}, " +
						"{ 363698007 |finding site| = << 53085002 |right ventricular structure|,  " +
						"  116676008 |associated morphology| = << 56246009 |hypertrophy|}",

				"ancestor:404684003 AND 363698007:ATTRIBUTE_DESCENDANT_OR_SELF_OF(39057004) "
				+ "AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(415582006) "
				+ "AND 363698007:ATTRIBUTE_DESCENDANT_OR_SELF_OF(53085002) "
				+ "AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(56246009)"
		);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testExample_NestedAttribute_1() {
		assertConversion(
				"< 404684003 |clinical finding|: " +
						"47429007 |associated with| = (< 404684003 |clinical finding|:  " +
						"116676008 |associated morphology| = << 55641003 |infarct|)",

				"ancestor:404684003 " +
						"AND 47429007: ( ATTRIBUTE_DESCENDANT_OF(404684003) " +
						"AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(55641003) ) "
		);
	}

	@Test
	public void testExample_AttributeOperator_1() {
		assertConversion(
				"<< 404684003 |clinical finding|: " +
						"<< 47429007 |associated with| = << 267038008 |edema|",

				"(id:404684003 " +
						"OR ancestor:404684003) " +
						"AND 47429007:ATTRIBUTE_DESCENDANT_OR_SELF_OF(267038008)"
		);
	}

	// TODO support ConcreteValues example, attributeGroup is not currently supported.
	@Test(expected = UnsupportedOperationException.class)
	public void testExample_ConcreteValues_1() {
		assertConversion(
				"< 27658006 |amoxicillin |: " +
						"411116001 |has dose form| = << 385049006 |capsule|, " +
						"{ 111115 |has basis of strength| = ( 111115 |amoxicillin only|: " +
						"   111115 |strength magnitude| >= #500, " +
						"   111115 |strength unit| = 258684004 |mg|)}",

				""
		);
	}

	// TODO support ConcreteValues example, attributeGroup is not currently supported.
	@Test(expected = UnsupportedOperationException.class)
	public void testExample_ConcreteValues_2() {
		assertConversion(
				"< 27658006 |amoxicillin |: " +
						"411116001 |has dose form| = << 385049006 |capsule|, " +
						"{ 111115 |has basis of strength| = ( 111115 |amoxicillin only|: " +
						"   111115 |strength magnitude| >= #500,   111115 |strength magnitude| <= #800,  " +
						"   111115 |strength unit| = 258684004 |mg|)}",

				""
		);
	}

	// TODO support ConcreteValues example, stringComparisonOperator is not currently supported.
	@Test(expected = UnsupportedOperationException.class)
	public void testExample_ConcreteValues_3() {
		assertConversion(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"111115 |trade name| = \"PANADOL\"",

				""
		);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testExample_ReverseOf_1() {
		assertConversion(
				"< 105590001 |substance|:  " +
						"R 127489000 |has active ingredient| = 111115 |TRIPHASIL tablet|",

				"ancestor:105590001 " +
						"AND 127489000:111115"
		);
	}

	// TODO support AnyAttributeNameValue example, wildcard attributeName is not currently supported.
	@Test(expected = UnsupportedOperationException.class)
	public void testExample_AnyAttributeNameValue_1() {
		assertConversion(
				"< 404684003 |clinical finding|: * = 79654002 |edema|",

				""
		);
	}

	@Test
	public void testExample_AnyAttributeNameValue_2() {
		assertConversion(
				"< 404684003 |clinical finding|: 116676008 |associated morphology| = *",

				"ancestor:404684003 " +
						"AND 116676008:*"
		);
	}

	@Test
	public void testExample_AttributeCardinality_1() {
		assertConversion(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..3] 127489000 |has active ingredient| = < 105590001 |substance|",

				"ancestor:373873005 AND 127489000_card:[1 TO 3] AND 127489000:ATTRIBUTE_DESCENDANT_OF(105590001)"
		);
	}

	@Test
	public void testExample_AttributeCardinality_2() {
		assertConversion(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..1] 127489000 |has active ingredient| = < 105590001 |substance|",

				"ancestor:373873005 AND 127489000_card:[1 TO 1] AND 127489000:ATTRIBUTE_DESCENDANT_OF(105590001)"
		);
	}

	@Test
	public void testExample_AttributeCardinality_3() {
		assertConversion(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[0..1] 127489000 |has active ingredient| = < 105590001 |substance|",

				"ancestor:373873005 AND 127489000_card:[0 TO 1] AND 127489000:ATTRIBUTE_DESCENDANT_OF(105590001)"
		);
	}

	@Test
	public void testExample_AttributeCardinality_4() {
		assertConversion(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..*] 127489000 |has active ingredient| = < 105590001 |substance|",

				"ancestor:373873005 AND 127489000_card:[1 TO *] AND 127489000:ATTRIBUTE_DESCENDANT_OF(105590001)"
		);
	}

	@Test
	public void testExample_AttributeCardinality_5() {
		assertConversion(
				"< 404684003 |clinical finding|: " +
						"[1..1] 363698007 |finding site| = < 91723000 |anatomical structure|",

				"ancestor:404684003 AND 363698007_card:[1 TO 1] AND 363698007:ATTRIBUTE_DESCENDANT_OF(91723000)"
		);
	}

	@Test
	public void testExample_AttributeCardinality_6() {
		assertConversion(
				"< 404684003 |clinical finding|:  " +
						"[2..*] 363698007 |finding site| = < 91723000 |anatomical structure|",

				"ancestor:404684003 AND 363698007_card:[2 TO *] AND 363698007:ATTRIBUTE_DESCENDANT_OF(91723000)"
		);
	}

	@Test
	public void testExample_AttributeCardinality_7() {
		assertConversion(
				"< 404684003 |clinical finding|:  " +
						"{ [2..*] 363698007 |finding site| = < 91723000 |anatomical structure| }",
				"ancestor:404684003 AND 363698007_totalGrp:[1 TO *] AND 363698007_grpCard:[2 TO *] AND 363698007:ATTRIBUTE_DESCENDANT_OF(91723000)"
		);
	}

	@Test
	public void testExample_AttributeGroupCardinality_1() {
		assertConversion(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..3] { [1..*] 127489000 |has active ingredient| = < 105590001 |substance|}",

				"ancestor:373873005 AND 127489000_totalGrp:[1 TO 3] AND 127489000_grpCard:[1 TO *] AND 127489000:ATTRIBUTE_DESCENDANT_OF(105590001)"
		);
	}

	@Test
	public void testExample_AttributeGroupCardinality_2() {
		assertConversion(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[0..1] { 127489000 |has active ingredient| = < 105590001 |substance|}",

				"ancestor:373873005 AND 127489000_totalGrp:[0 TO 1] AND 127489000:ATTRIBUTE_DESCENDANT_OF(105590001)"
		);
	}

	@Test
	public void testExample_AttributeGroupCardinality_3() {
		assertConversion(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..*] { 127489000 |has active ingredient| = < 105590001 |substance|}",

				"ancestor:373873005 AND 127489000_totalGrp:[1 TO *] AND 127489000:ATTRIBUTE_DESCENDANT_OF(105590001)"
		);
	}

	@Test
	public void testExample_AttributeGroupCardinality_4() {
		assertConversion(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..*] { [1..*] 127489000 |has active ingredient| = < 105590001 |substance|}",

				"ancestor:373873005 AND 127489000_totalGrp:[1 TO *] AND 127489000_grpCard:[1 TO *] AND 127489000:ATTRIBUTE_DESCENDANT_OF(105590001)"
		);
	}

	@Test
	public void testExample_AttributeGroupCardinality_5() {
		assertConversion(
				"< 404684003 |clinical finding|: " +
						"[1..1] { 363698007 |finding site| = < 91723000 |anatomical structure|}",

				"ancestor:404684003 AND 363698007_totalGrp:[1 TO 1] AND 363698007:ATTRIBUTE_DESCENDANT_OF(91723000)"
		);
	}

	@Test
	public void testExample_AttributeGroupCardinality_6() {
		assertConversion(
				"< 404684003 |clinical finding|:  " +
						"[0..0] { [2..*] 363698007 |finding site| = < 91723000 |anatomical structure|}",

				"ancestor:404684003 AND 363698007_totalGrp:[0 TO 0] AND 363698007_grpCard:[2 TO *] AND 363698007:ATTRIBUTE_DESCENDANT_OF(91723000)"
		);
	}

	@Test
	public void testExample_AttributeConjunctionDisjunction_1() {
		assertConversion(
				"< 404684003 |clinical finding|: " +
						"363698007 |finding site| = << 39057004 |pulmonary valve structure| AND  " +
						"116676008 |associated morphology| = << 415582006 |stenosis|",

				"ancestor:404684003 " +
						"AND 363698007:ATTRIBUTE_DESCENDANT_OR_SELF_OF(39057004) " +
						"AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(415582006)"
		);
	}

	@Test
	public void testExample_AttributeConjunctionDisjunction_2() {
		assertConversion(
				"< 404684003 |clinical finding|:  " +
						"116676008 |associated morphology| = << 55641003 |infarct| OR  " +
						"42752001 |due to| = << 22298006 |myocardial infarction|",

				"ancestor:404684003 " +
						"AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(55641003) " +
						"OR 42752001:ATTRIBUTE_DESCENDANT_OR_SELF_OF(22298006)"
		);
	}

	@Test
	public void testExample_AttributeConjunctionDisjunction_3() {
		assertConversion(
				"< 404684003 |clinical finding|: " +
						"( 363698007 |finding site| = << 39057004 |pulmonary valve structure| AND  " +
						"116676008 |associated morphology| = << 415582006 |stenosis| ) AND " +
						"42752001 |due to| = << 445238008|malignant carcinoid tumor|",

				"ancestor:404684003 " +
						"AND  ( 363698007:ATTRIBUTE_DESCENDANT_OR_SELF_OF(39057004) " +
						"AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(415582006) )  " +
						"AND 42752001:ATTRIBUTE_DESCENDANT_OR_SELF_OF(445238008)"
		);
	}

	@Test
	public void testExample_AttributeConjunctionDisjunction_4() {
		assertConversion(
				"< 404684003 |clinical finding| :  " +
						"( 363698007 |finding site| = << 39057004 |pulmonary valve structure| AND  " +
						"116676008 |associated morphology| = << 415582006 |stenosis|) OR " +
						"42752001 |due to| = << 445238008|malignant carcinoid tumor|",

				"ancestor:404684003 " +
						"AND  ( 363698007:ATTRIBUTE_DESCENDANT_OR_SELF_OF(39057004) " +
						"AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(415582006) )  " +
						"OR 42752001:ATTRIBUTE_DESCENDANT_OR_SELF_OF(445238008)"
		);
	}

	@Test
	public void testExample_SimpleConjunctionDisjunction_1() {
		assertConversion(
				"< 19829001 |disorder of lung| AND < 301867009 |edema of trunk|",

				"ancestor:19829001 " +
						"AND ancestor:301867009"
		);
	}

	@Test
	public void testExample_SimpleConjunctionDisjunction_2() {
		assertConversion(
				"< 19829001 |disorder of lung| OR < 301867009 |edema of trunk|",

				"ancestor:19829001 " +
						"OR ancestor:301867009"
		);
	}

	@Test
	public void testExample_SimpleConjunctionDisjunction_3() {
		assertConversion(
				"< 19829001|disorder of lung| AND ^ 700043003 |example problem list concepts reference set|",

				"ancestor:19829001 " +
						"AND memberOf:700043003"
		);
	}

	@Test
	public void testExample_SimpleConjunctionDisjunction_4() {
		assertConversion(
				"(< 19829001 |disorder of lung| AND < 301867009 |edema of trunk|) AND  " +
						"^ 700043003 |example problem list concepts reference set|",

				" ( ancestor:19829001 " +
						"AND ancestor:301867009 )  " +
						"AND memberOf:700043003"
		);
	}

	@Test
	public void testExample_SimpleConjunctionDisjunction_5() {
		assertConversion(
				"(< 19829001 |disorder of lung| AND < 301867009 |edema of trunk|) OR  " +
						"^ 700043003 |example problem list concepts reference set|",

				" ( ancestor:19829001 " +
						"AND ancestor:301867009 )  " +
						"OR memberOf:700043003"
		);
	}

	@Test
	public void testExample_AttributeGroupConjunctionDisjunction_1() {
		assertConversion(
				"< 404684003 |clinical finding|: " +
						"{ 363698007 |finding site| = << 39057004 |pulmonary valve structure|, " +
						"   116676008 |associated morphology| = << 415582006 |stenosis|} OR " +
						"{ 363698007 |finding site| = << 53085002 |right ventricular structure|, " +
						"   116676008 |associated morphology| = << 56246009 |hypertrophy|}",

				"ancestor:404684003 AND 363698007:ATTRIBUTE_DESCENDANT_OR_SELF_OF(39057004) "
				+ "AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(415582006) "
				+ "OR 363698007:ATTRIBUTE_DESCENDANT_OR_SELF_OF(53085002) "
				+ "AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(56246009)"
		);
	}

	@Test
	public void testExample_AttributeValueConjunctionDisjunction_1() {
		assertConversion(
				"^ 450990004 |adverse drug reactions reference set for GP/FP health issue|: 246075003 |causative agent| = " +
						"(< 373873005 |pharmaceutical / biologic product| OR < 105590001 |substance|)",

				"memberOf:450990004 " +
						"AND 246075003: ( ATTRIBUTE_DESCENDANT_OF(373873005) " +
						"OR ATTRIBUTE_DESCENDANT_OF(105590001) ) "
		);
	}

	@Test
	public void testExample_AttributeValueConjunctionDisjunction_2() {
		assertConversion(
				"< 404684003 |clinical finding|: 116676008 |associated morphology| = " +
						"(<< 56208002|ulcer| AND << 50960005|hemorrhage|)",

				"ancestor:404684003 " +
						"AND 116676008: ( ATTRIBUTE_DESCENDANT_OR_SELF_OF(56208002) " +
						"AND ATTRIBUTE_DESCENDANT_OR_SELF_OF(50960005) ) "
		);
	}

	@Test
	public void testExample_ExclusionSimpleExpressions_1() {
		assertConversion(
				"<< 19829001 |disorder of lung| MINUS << 301867009 |edema of trunk|",

				"(id:19829001 " +
						"OR ancestor:19829001) " +
						"NOT (id:301867009 " +
						"OR ancestor:301867009)"
		);
	}

	@Test
	public void testExample_ExclusionSimpleExpressions_2() {
		assertConversion(
				"<< 19829001 |disorder of lung| MINUS ^ 700043003 |example problem list concepts reference set|",

				"(id:19829001 " +
						"OR ancestor:19829001) " +
						"NOT memberOf:700043003"
		);
	}

	@Test
	public void testExample_ExclusionAttributeValues_1() {
		assertConversion(
				"< 404684003 |clinical finding|: 116676008 |associated morphology| = " +
						"((<< 56208002 |ulcer| AND << 50960005 |hemorrhage|) MINUS << 26036001 |obstruction|)",

				"ancestor:404684003 " +
						"AND 116676008: (  ( ATTRIBUTE_DESCENDANT_OR_SELF_OF(56208002) " +
						"AND ATTRIBUTE_DESCENDANT_OR_SELF_OF(50960005) )  " +
						"NOT ATTRIBUTE_DESCENDANT_OR_SELF_OF(26036001) ) "
		);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testExample_NotEqualToAttributeValue_1() {
		assertConversion(
				"< 404684003 |clinical finding|:  " +
						"116676008 |associated morphology| !=  << 26036001 |obstruction|",

				"ancestor:404684003 " +
						"AND 116676008:-ATTRIBUTE_DESCENDANT_OR_SELF_OF(26036001)"
		);
	}

	@Test
	public void testExample_NotEqualToAttributeValue_2() {
		assertConversion(
				"< 404684003 |clinical finding|:  " +
						"[0..0] 116676008 |associated morphology| =  << 26036001 |obstruction|",

				"ancestor:404684003 AND 116676008_card:[0 TO 0] AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(26036001)"
		);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testExample_NotEqualToAttributeValue_3() {
		assertConversion(
				"< 404684003 |clinical finding|:  " +
						"[0..0] 116676008 |associated morphology| != << 26036001 |obstruction|",

				"ancestor:404684003 " +
						"AND 116676008:-ATTRIBUTE_DESCENDANT_OR_SELF_OF(26036001)"
		);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testExample_NotEqualToAttributeValue_4() {
		assertConversion(
				"< 404684003 |clinical finding|:  " +
						"[0..0] 116676008 |associated morphology| !=  << 26036001 |obstruction| and " +
						"[1..*] 116676008 |associated morphology| =   << 26036001 |obstruction|",

				"ancestor:404684003 " +
						"AND 116676008:-ATTRIBUTE_DESCENDANT_OR_SELF_OF(26036001) " +
						"AND 116676008:ATTRIBUTE_DESCENDANT_OR_SELF_OF(26036001)"
		);
	}

	@Test
	public void testExample_UnaryOperators_1() {
		assertConversion(
				"< ^ 700043003 |example problem list concepts reference set|",

				"memberOf:700043003"
		);
	}

	@Test
	public void testExample_BinaryOperators_1() {
		assertConversion(
				"(< 19829001|disorder of lung| OR ^ 700043003 |example problem list concepts reference set|)  " +
						"MINUS ^ 450976002|disorders and diseases reference set for GP/FP reason for encounter|",

				" ( ancestor:19829001 " +
						"OR memberOf:700043003 )  " +
						"NOT memberOf:450976002"
		);
	}

	@Test
	public void testExample_BinaryOperators_2() {
		assertConversion(
				"(< 19829001|disorder of lung| MINUS ^ 700043003 |example problem list concepts reference set|) MINUS " +
						"^ 450976002|disorders and diseases reference set for GP/FP reason for encounter|",

				" ( ancestor:19829001 " +
						"NOT memberOf:700043003 )  " +
						"NOT memberOf:450976002"
		);
	}

	@Test
	public void testExample_BinaryOperators_3() {
		assertConversion(
				"< 19829001|disorder of lung| OR ^ 700043003 |example problem list concepts reference set| OR " +
						"^ 450976002|disorders and diseases reference set for GP/FP reason for encounter|",

				"ancestor:19829001 " +
						"OR memberOf:700043003 " +
						"OR memberOf:450976002"
		);
	}

	private void assertConversion(String ecQuery, String expectedLuceneQuery) {
		Assert.assertEquals(expectedLuceneQuery, parser.parse(ecQuery));
	}

//	Used to generate test cases for all examples
//	@Test
	public void generate() throws IOException {
		final File file = new File("/Users/kaikewley/code/SNOMEDCT-Languages/SnomedCTExpressionConstraintLanguage/ECL Examples");
		for (File file1 : file.listFiles()) {
			if (file1.isFile() && file1.getName().endsWith(".txt")) {
				final String[] split = file1.getName().split(" ");
				final String testName = split[5];
				final String testNumber = split[6].substring(1, 2);
				String example = new String(Files.readAllBytes(file1.toPath()));
				String parse = "";
				boolean unsupported = false;
				String message = "";
				try {
						parse = parser.parse(example);
				} catch (UnsupportedOperationException e) {
					message = e.getMessage();
					unsupported = true;
				}
				if (unsupported) {
					System.out.println("\t//@Test TODO support " + testName + " example, " + message);
				} else {
					System.out.println("\t@Test");
				}
				example = example.replace("\"", "\\\"");
				example = example.replaceAll("\n", " \" +\n\t\t\t\"");
				parse = parse.replace(" AND ", " \" +\n\t\t\t\"AND ");
				parse = parse.replace(" NOT ", " \" +\n\t\t\t\"NOT ");
				parse = parse.replace(" OR ", " \" +\n\t\t\t\"OR ");
				System.out.println(
						"\tpublic void testExample_" + testName + "_" + testNumber + "() {\n" +
						"\t\tassertConversion(\n\t\t\t\"" + example + "\",\n\n\t\t\t\"" + parse + "\"\n\t\t\t);\n" +
						"\t}\n"
				);
			}
		}
	}

}
