package org.ihtsdo.otf.sqs.service;

import org.ihtsdo.otf.snomedboot.ReleaseImportException;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.sqs.service.dto.ConceptResult;
import org.ihtsdo.otf.sqs.service.exception.ServiceException;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This test requires the SNOMED-CT International Release published July 2015 extracted within the release directory.
 */
public class ExamplesIntegrationTestManual {

	private ReleaseReader releaseReader;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Before
	public void setup() throws IOException, ReleaseImportException {
		final ReleaseImportManager releaseImportManager = new ReleaseImportManager();
		final ReleaseStore releaseStore;
		if (releaseImportManager.isReleaseStoreExists()) {
			logger.info("Using existing release store");
			releaseStore = releaseImportManager.openExistingReleaseStore();
		} else {
			releaseStore = releaseImportManager.loadReleaseFiles(new File("release"), LoadingProfile.light.withRefset("447563008"));
		}
		releaseReader = new ReleaseReader(releaseStore);
	}

	@Test
	public void testExpressionConstraintQuery_wildcardFocusConcept() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*", 0, -1).getItems();
		Assert.assertEquals(317057, conceptResults.size());
	}

	@Test
	public void testExpressionConstraintLanguageExample_Self_1() throws ServiceException {
		assertQueryMatches(
				"404684003 |clinical finding|",

				404684003
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_DescendantOf_1() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|",

				64572001, 56265001
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_DescendantOrSelfOf_1() throws ServiceException {
		assertQueryMatches(
				"<< 73211009 |diabetes mellitus|",

				73211009, 46635009, 105401000119101L
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_AncestorOf_1() throws ServiceException {
		assertQueryMatches(
				"> 40541001 |acute pulmonary edema|",

				111273006, 404684003, 138875005
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_AncestorOrSelfOf_1() throws ServiceException {
		assertQueryMatches(
				">> 40541001|acute pulmonary edema|",

				40541001, 111273006, 404684003, 138875005
		);
	}

//	@Test TODO: Does have the example problem list reference set for testing purposes?
	public void testExpressionConstraintLanguageExample_MemberOf_1() throws ServiceException {
		assertQueryMatches(
				"^ 700043003 |example problem list concepts reference set |",

				394659003, 194828000, 29857009
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_Any_1() throws ServiceException {
		assertQueryMatches(
				"*",

				138875005, 404684003, 322236009
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_Attribute_1() throws ServiceException {
		assertQueryMatches(
				"< 19829001 |disorder of lung|:  " +
						"116676008 |associated morphology| = 79654002 |edema|",

				11468004, 276637009
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_Attribute_2() throws ServiceException {
		assertQueryMatches(
				"< 19829001 |disorder of lung|:  " +
						"116676008 |associated morphology| = << 79654002 |edema|",

				233709006, 233711002
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_Attribute_3() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: " +
						"363698007 |finding site| = << 39057004 |pulmonary valve structure|,  " +
						"116676008 |associated morphology| = << 415582006 |stenosis|",

				56786000, 86299006
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_Attribute_4() throws ServiceException {
		assertQueryMatches(
				"* : 246075003 |causative agent| = 387517004 |paracetamol|",

				295124009, 292042007
		);
	}

	// TODO: support attribute grouping
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeGroup_1() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: " +
						"{ 363698007 |finding site| = << 39057004 |pulmonary valve structure|, " +
						"  116676008 |associated morphology| = << 415582006 |stenosis|}, " +
						"{ 363698007 |finding site| = << 53085002 |right ventricular structure|,  " +
						"  116676008 |associated morphology| = << 56246009 |hypertrophy|}",

				86299006, 204351007
		);
	}

	// TODO: support nested queries
	// This is a refinedExpressionConstraint as an expressionConstraintValue
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_NestedAttribute_1() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: " +
						"47429007 |associated with| = (< 404684003 |clinical finding|:  " +
						"116676008 |associated morphology| = << 55641003 |infarct|)",

				71023004
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_AttributeOperator_1() throws ServiceException {
		assertQueryMatches(
				"<< 404684003 |clinical finding|: " +
						"<< 47429007 |associated with| = << 267038008 |edema|",

				230580009
		);
	}

//	@Test TODO - example file and PDF mismatch, ask
//	public void testExpressionConstraintLanguageExample_ConcreteValues_1() throws ServiceException {
//		assertQueryMatches(
//				"< 27658006 |amoxicillin |: " +
//						"411116001 |has dose form| = << 385049006 |capsule|, " +
//						"{ 111115 |has basis of strength| = ( 111115 |amoxicillin only|: " +
//						"   111115 |strength magnitude| >= #500, " +
//						"   111115 |strength unit| = 258684004 |mg|)}",
//
//				27658006
//		);
//	}
//
//	@Test TODO - example file and PDF mismatch, ask
//	public void testExpressionConstraintLanguageExample_ConcreteValues_2() throws ServiceException {
//		assertQueryMatches(
//				"< 27658006 |amoxicillin |: " +
//						"411116001 |has dose form| = << 385049006 |capsule|, " +
//						"{ 111115 |has basis of strength| = ( 111115 |amoxicillin only|: " +
//						"   111115 |strength magnitude| >= #500,   111115 |strength magnitude| <= #800,  " +
//						"   111115 |strength unit| = 258684004 |mg|)}",
//
//				27658006
//		);
//	}

	// TODO: support string comparison operator - find test case
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_ConcreteValues_3() throws ServiceException {
		assertQueryMatches(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"111115 |trade name| = \"PANADOL\"",

				111115
		);
	}

	// TODO: support reverse of
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_ReverseOf_1() throws ServiceException {
		assertQueryMatches(
				"< 105590001 |substance|:  " +
						"R 127489000 |has active ingredient| = 111115 |TRIPHASIL tablet|",

				126109000, 126097006
		);
	}

	// TODO: support wildcard attributeName
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AnyAttributeNameValue_1() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: * = 79654002 |edema|",

				19242006, 97341000119105L
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_AnyAttributeNameValue_2() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: 116676008 |associated morphology| = *",

				19242006, 263225007
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeCardinality_1() throws ServiceException {
		assertQueryMatches(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..3] 127489000 |has active ingredient| = < 105590001 |substance|",

				322236009, 404826002
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeCardinality_2() throws ServiceException {
		assertQueryMatches(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..1] 127489000 |has active ingredient| = < 105590001 |substance|",

				370166004
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeCardinality_3() throws ServiceException {
		assertQueryMatches(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[0..1] 127489000 |has active ingredient| = < 105590001 |substance|",

				111115, 370166004
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeCardinality_4() throws ServiceException {
		assertQueryMatches(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..*] 127489000 |has active ingredient| = < 105590001 |substance|",

				7947003, 437867004
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeCardinality_5() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: " +
						"[1..1] 363698007 |finding site| = < 91723000 |anatomical structure|",

				125596004
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeCardinality_6() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|:  " +
						"[2..*] 363698007 |finding site| = < 91723000 |anatomical structure|",

				86299006
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeCardinality_7() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|:  " +
						"{ [2..*] 363698007 |finding site| = < 91723000 |anatomical structure| }"
		);
	}

	// TODO: support group cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeGroupCardinality_1() throws ServiceException {
		assertQueryMatches(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..3] { [1..*] 127489000 |has active ingredient| = < 105590001 |substance|}",

				322236009
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeGroupCardinality_2() throws ServiceException {
		assertQueryMatches(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[0..1] { 127489000 |has active ingredient| = < 105590001 |substance|}",

				111115, 370166004
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeGroupCardinality_3() throws ServiceException {
		assertQueryMatches(
				"< 373873005 |pharmaceutical / biologic product|: " +
						"[1..*] { 127489000 |has active ingredient| = < 105590001 |substance|}",

				370166004
		);
	}

//	@Test TODO: Missing from PDF - Ask
//	public void testExpressionConstraintLanguageExample_AttributeGroupCardinality_4() throws ServiceException {
//		assertQueryMatches(
//				"< 373873005 |pharmaceutical / biologic product|: " +
//						"[1..*] { [1..*] 127489000 |has active ingredient| = < 105590001 |substance|}",
//
//
//		);
//	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeGroupCardinality_5() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: " +
						"[1..1] { 363698007 |finding site| = < 91723000 |anatomical structure|}",

				125596004
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeGroupCardinality_6() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|:  " +
						"[0..0] { [2..*] 363698007 |finding site| = < 91723000 |anatomical structure|}",

				86299006
		);
	}

	// TODO: Missing from PDF - Ask for expected matches
	@Test
	public void testExpressionConstraintLanguageExample_AttributeConjunctionDisjunction_1() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: " +
						"363698007 |finding site| = << 39057004 |pulmonary valve structure| AND  " +
						"116676008 |associated morphology| = << 415582006 |stenosis|",

				56786000
		);
	}

	// TODO: Missing from PDF - Ask for expected matches
	@Test
	public void testExpressionConstraintLanguageExample_AttributeConjunctionDisjunction_2() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|:  " +
						"116676008 |associated morphology| = << 55641003 |infarct| OR  " +
						"42752001 |due to| = << 22298006 |myocardial infarction|",

				233832000
		);
	}

	// TODO: Missing from PDF - Ask for expected matches
	@Test
	public void testExpressionConstraintLanguageExample_AttributeConjunctionDisjunction_3() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: " +
						"( 363698007 |finding site| = << 39057004 |pulmonary valve structure| AND  " +
						"116676008 |associated morphology| = << 415582006 |stenosis| ) AND " +
						"42752001 |due to| = << 445238008|malignant carcinoid tumor|"


		);
	}

	// TODO: Missing from PDF - Ask for expected matches
	@Test
	public void testExpressionConstraintLanguageExample_AttributeConjunctionDisjunction_4() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding| :  " +
						"( 363698007 |finding site| = << 39057004 |pulmonary valve structure| AND  " +
						"116676008 |associated morphology| = << 415582006 |stenosis|) OR " +
						"42752001 |due to| = << 445238008|malignant carcinoid tumor|",

				56786000
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_SimpleConjunctionDisjunction_1() throws ServiceException {
		assertQueryMatches(
				"< 19829001 |disorder of lung| AND < 301867009 |edema of trunk|",

				233709006, 61233003
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_SimpleConjunctionDisjunction_2() throws ServiceException {
		assertQueryMatches(
				"< 19829001 |disorder of lung| OR < 301867009 |edema of trunk|",

				363358000, 19242006
		);
	}

//	@Test TODO: Ask for the example problem list reference set for testing purposes
//	public void testExpressionConstraintLanguageExample_SimpleConjunctionDisjunction_3() throws ServiceException {
//		assertQueryMatches(
//				"< 19829001|disorder of lung| AND ^ 700043003 |example problem list concepts reference set|",
//
//				1001000119102L
//		);
//	}

//	@Test TODO: Ask for the example problem list reference set for testing purposes
//	public void testExpressionConstraintLanguageExample_SimpleConjunctionDisjunction_4() throws ServiceException {
//		assertQueryMatches(
//				"(< 19829001 |disorder of lung| AND < 301867009 |edema of trunk|) AND  " +
//						"^ 700043003 |example problem list concepts reference set|",
//
//				91442002, 86299006
//		);
//	}

//	@Test TODO: Ask for the example problem list reference set for testing purposes
//	public void testExpressionConstraintLanguageExample_SimpleConjunctionDisjunction_5() throws ServiceException {
//		assertQueryMatches(
//				"(< 19829001 |disorder of lung| AND < 301867009 |edema of trunk|) OR  " +
//						"^ 700043003 |example problem list concepts reference set|",
//
//				45456005, 703326006
//		);
//	}

	// TODO: support attribute grouping
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_AttributeGroupConjunctionDisjunction_1() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: " +
						"{ 363698007 |finding site| = << 39057004 |pulmonary valve structure|, " +
						"   116676008 |associated morphology| = << 415582006 |stenosis|} OR " +
						"{ 363698007 |finding site| = << 53085002 |right ventricular structure|, " +
						"   116676008 |associated morphology| = << 56246009 |hypertrophy|}",

				85971001, 86299006
		);
	}

	@Test // Used alternative refset (ICD-9-CM) since original in example is not available: 450990004 |adverse drug reactions reference set for GP/FP health issue
	public void testExpressionConstraintLanguageExample_AttributeValueConjunctionDisjunction_1() throws ServiceException {
		assertQueryMatches(
				"^ 447563008 |ICD-9-CM equivalence complex map reference set|: 246075003 |causative agent| = " +
						"(< 373873005 |pharmaceutical / biologic product| OR < 105590001 |substance|)",

				294811002, 293584003, 293585002
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_AttributeValueConjunctionDisjunction_2() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: 116676008 |associated morphology| = " +
						"(<< 56208002|ulcer| AND << 50960005|hemorrhage|)",

				12847006
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_ExclusionSimpleExpressions_1() throws ServiceException {
		assertQueryMatches(
				"<< 19829001 |disorder of lung| MINUS << 301867009 |edema of trunk|",

				372146004, 413839001
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_ExclusionSimpleExpressions_2() throws ServiceException {
		assertQueryMatches(
				"<< 19829001 |disorder of lung| MINUS ^ 700043003 |example problem list concepts reference set|",

				233613009
		);
	}

	@Test
	public void testExpressionConstraintLanguageExample_ExclusionAttributeValues_1() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: 116676008 |associated morphology| = " +
						"((<< 56208002 |ulcer| AND << 50960005 |hemorrhage|) MINUS << 26036001 |obstruction|)",

				15902003
		);
	}

	// TODO: support not-equal expression comparison operator
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_NotEqualToAttributeValue_1() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|: " +
						"116676008 |associated morphology| != 26036001 |obstruction|",

				233613009, 46708007
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_NotEqualToAttributeValue_2() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|:  " +
						"[0..0] 116676008 |associated morphology| =  << 26036001 |obstruction|",

				233613009, 15902003
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_NotEqualToAttributeValue_3() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|:  " +
						"[0..0] 116676008 |associated morphology| != << 26036001 |obstruction|",

				244815007, 84906002
		);
	}

	// TODO: support cardinality
	@Test(expected = UnsupportedOperationException.class)
	public void testExpressionConstraintLanguageExample_NotEqualToAttributeValue_4() throws ServiceException {
		assertQueryMatches(
				"< 404684003 |clinical finding|:  " +
						"[0..0] 116676008 |associated morphology| !=  << 26036001 |obstruction| and " +
						"[1..*] 116676008 |associated morphology| =   << 26036001 |obstruction|",

				244815007
		);
	}

//	@Test TODO: Ask for the example problem list reference set for testing purposes
//	public void testExpressionConstraintLanguageExample_UnaryOperators_1() throws ServiceException {
//		assertQueryMatches(
//				"< ^ 700043003 |example problem list concepts reference set|",
//
//
//		);
//	}

//	@Test TODO: Ask for the example problem list reference set for testing purposes
//	public void testExpressionConstraintLanguageExample_BinaryOperators_1() throws ServiceException {
//		assertQueryMatches(
//				"(< 19829001|disorder of lung| OR ^ 700043003 |example problem list concepts reference set|)  " +
//						"MINUS ^ 450976002|disorders and diseases reference set for GP/FP reason for encounter|",
//
//
//		);
//	}

//	@Test TODO: Ask for the example problem list reference set for testing purposes
//	public void testExpressionConstraintLanguageExample_BinaryOperators_2() throws ServiceException {
//		assertQueryMatches(
//				"(< 19829001|disorder of lung| MINUS ^ 700043003 |example problem list concepts reference set|) MINUS " +
//						"^ 450976002|disorders and diseases reference set for GP/FP reason for encounter|",
//
//
//		);
//	}

//	@Test TODO: Ask for the example problem list reference set for testing purposes
//	public void testExpressionConstraintLanguageExample_BinaryOperators_3() throws ServiceException {
//		assertQueryMatches(
//				"< 19829001|disorder of lung| OR ^ 700043003 |example problem list concepts reference set| OR " +
//						"^ 450976002|disorders and diseases reference set for GP/FP reason for encounter|",
//
//
//		);
//	}

	private void assertQueryMatches(String eclQuery, long... conceptIds) throws ServiceException {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery(eclQuery, 0, -1).getItems();
		Set<ConceptResult> notFound = new HashSet<>();
		for (long conceptId : conceptIds) {
			final ConceptResult testConcept = new ConceptResult(conceptId + "");
			if (!conceptResults.contains(testConcept)) {
				notFound.add(testConcept);
			}
		}
		if (!notFound.isEmpty()) {
			Assert.fail("Results set did not match expected concept ids." + (notFound.isEmpty() ? "" : "\nConcepts not found are: " + notFound));
		}
		if (conceptIds.length == 0 && !conceptResults.isEmpty()) {
			Assert.fail("Expected no results but got " + conceptResults.size() + ": " + conceptResults);
		}
	}

	//	Used to generate test cases for all examples
//	@Test
	@SuppressWarnings("unused")
	public void generate() throws IOException {
		final File file = new File("/Users/kaikewley/code/SNOMEDCT-Languages/SnomedCTExpressionConstraintLanguage/ECL Examples");
		for (File file1 : file.listFiles()) {
			if (file1.isFile() && file1.getName().endsWith(".txt")) {
				final String[] split = file1.getName().split(" ");
				final String testName = split[5];
				final String testNumber = split[6].substring(1, 2);
				String example = new String(Files.readAllBytes(file1.toPath()));
				System.out.println("\t@Test");
				example = example.replace("\"", "\\\"");
				example = example.replaceAll("\n", " \" +\n\t\t\t\"");
				System.out.println(
						"\tpublic void testExpressionConstraintLanguageExample_" + testName + "_" + testNumber + "() throws ServiceException {\n" +
								"\t\tassertQueryMatches(\n\t\t\t\"" + example + "\",\n\n\t\t\t\"\"\n\t\t\t);\n" +
								"\t}\n"
				);
			}
		}
	}

}
