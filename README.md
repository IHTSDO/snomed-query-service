# SNOMED CT Query Service

An implementation of the SNOMED CT Expression Constraint Language.

## Expression Constraint Language feature support
Feature | Example | Supported
------- | --------- | -------
Self | 404684003 \|clinical finding\| | Yes
Descendant Of | < 404684003 \|clinical finding\| | Yes
Descendant Or Self Of | << 73211009 \|diabetes mellitus\| | Yes
Ancestor Of | > 40541001 \|acute pulmonary edema\| | Yes
Ancestor Or Self Of | >> 40541001\|acute pulmonary edema\| | Yes
MemberOf | ^ 700043003 \|example problem list concepts reference set\| | Yes
Any | * | Yes
Attribute | * : 246075003 \|causative agent\| = 387517004 \|paracetamol\| | Yes
Focus Concept Range, Attribute | < 19829001 \|disorder of lung\|: 116676008 \|associated morphology\| = 79654002 \|edema\| | Yes
Focus Concept Range, Attribute Value Range | < 404684003 \|clinical finding\|: 363698007 \|finding site\| = << 39057004 \|pulmonary valve structure\| | Yes
Focus Concept Range, Attributes with Value Ranges | < 404684003 \|clinical finding\|: 363698007 \|finding site\| = << 39057004 \|pulmonary valve structure\|, 116676008 \|associated morphology\| = << 415582006 \|stenosis\| | Yes
Attribute Grouping | < 404684003 \|clinical finding\|: { 363698007 \|finding site\| = << 39057004 \|pulmonary valve structure\|, 116676008 \|associated morphology\| = << 415582006 \|stenosis\|}, { 363698007 \|finding site\| = << 53085002 \|right ventricular structure\|, 116676008 \|associated morphology\| = << 56246009 \|hypertrophy\|} | Yes
Nested Attribute | < 404684003 \|clinical finding\|: 47429007 \|associated with\| = (< 404684003 \|clinical finding\|: 116676008 \|associated morphology\| = << 55641003 \|infarct\|) | Yes
Attribute Operator | << 404684003 \|clinical finding\|: << 47429007 \|associated with\| = << 267038008 \|edema\| | Yes
Concrete Values | < 373873005 \|pharmaceutical / biologic product\|: 111115 \|trade name\| = "PANADOL" | Yes
Concrete Values | < 27658006 \|amoxicillin\|: 411116001 \|has dose form\| = << 385049006 \|capsule\|, { 111115 \|has basis of strength\| = ( 111115 \|amoxicillin only\|: 111115 \|strength magnitude\| >= #500, 111115 \|strength unit\| = 258684004 \|mg\|)} | Yes
Reverse Of | < 105590001 \|substance\|: R 127489000 \|has active ingredient\| = 111115 \|TRIPHASIL tablet\| | No
Any Attribute Type | < 404684003 \|clinical finding\|: * = 79654002 \|edema\| | No
Any Attribute Value | < 404684003 \|clinical finding\|: 116676008 \|associated morphology\| = * | Yes
Attribute Cardinality | < 373873005 \|pharmaceutical / biologic product\|: [1..3] 127489000 \|has active ingredient\| = < 105590001 \|substance\| | Yes
Attribute Group Cardinality | < 373873005 \|pharmaceutical / biologic product\|: [1..3] { [1..*] 127489000 \|has active ingredient\| = < 105590001 \|substance\|} | Yes
Attribute Conjunction | < 404684003 \|clinical finding\|: 363698007 \|finding site\| = << 39057004 \|pulmonary valve structure\| AND 116676008 \|associated morphology\| = << 415582006 \|stenosis\| | Yes
Attribute Disjunction | < 404684003 \|clinical finding\|: 116676008 \|associated morphology\| = << 55641003 \|infarct\| OR 42752001 \|due to\| = << 22298006 \|myocardial infarction\| | Yes
Simple Conjunction | < 19829001 \|disorder of lung\| AND < 301867009 \|edema of trunk\| | Yes
Simple Disjunction | < 19829001 \|disorder of lung\| OR < 301867009 \|edema of trunk\| | Yes
Simple Conjunction With Refset | < 19829001 \|disorder of lung\| AND ^ 700043003 \|example problem list concepts reference set\| | Yes
Attribute Group Conjunction Disjunction | < 404684003 \|clinical finding\|: { 363698007 \|finding site\| = << 39057004 \|pulmonary valve structure\|, 116676008 \|associated morphology\| = << 415582006 \|stenosis\|} OR { 363698007 \|finding site\| = << 53085002 \|right ventricular structure\|, 116676008 \|associated morphology\| = << 56246009 \|hypertrophy\|} | Yes
Attribute Value Disjunction | ^ 447563008 \|ICD-9-CM equivalence complex map reference set\|: 246075003 \|causative agent\| = (< 373873005 \|pharmaceutical / biologic product\| OR < 105590001 \|substance\|) | Yes
Attribute Value Conjunction | < 404684003 \|clinical finding\|: 116676008 \|associated morphology\| = (<< 56208002 \|ulcer\| AND << 50960005 \|hemorrhage\|) | Yes
Exclusion Simple Expressions | << 19829001 \|disorder of lung\| MINUS << 301867009 \|edema of trunk\| | Yes
Exclusion Simple Expressions | << 19829001 \|disorder of lung\| MINUS ^ 700043003 \|example problem list concepts reference set\| | Yes
Exclusion Attribute Values | < 404684003 \|clinical finding\|: 116676008 \|associated morphology\| = ((<< 56208002 \|ulcer\| AND << 50960005 \|hemorrhage\|) MINUS << 26036001 \|obstruction\|) | Yes
Not Equal To Attribute Value | < 404684003 \|clinical finding\|: 116676008 \|associated morphology\| != 26036001 \|obstruction\| | Yes


## Quick Start
- Java version 17 is required
- Download the latest application Jar from the [releases page](https://github.com/IHTSDO/snomed-query-service/releases)
- Use the commands listed below which:
  - Unzip your SNOMED RF2 archive
  - Load your SNOMED content and then serve
```
cd my-documents
unzip SnomedCT_InternationalRF2_PRODUCTION_20220131T120000Z.zip
java -Xms3g -jar snomed-query-service-4.0.0-exec.jar --loadRelease="/my-documents/SnomedCT_InternationalRF2_PRODUCTION_20220131T120000Z"
java -jar snomed-query-service-4.0.0-exec.jar --serve
```
This will create an "index" directory. This directory must be removed before running the load process again.
Once the service is running in serve mode Swagger API documentation can be found at: http://localhost:8080/swagger-ui/index.html

## Example Expression Constraint Language queries:
Follow the quick start to use these examples:
* Descendant of "Administrative statuses (finding)" [http://localhost:8080/concepts?ecQuery=<<307824009](http://localhost:8080/concepts?ecQuery=<307824009)
* Ancestor or self of "Fitting of contact lens (procedure)" having attribute "Method (attribute)" [http://localhost:8080/concepts?ecQuery=>>448642000:260686004=*](http://localhost:8080/concepts?ecQuery=>>448642000:260686004=*)
* Concepts having attribute "Direct device (attribute)" with value "Plaster cast, device (physical object)" [http://localhost:8080/concepts?ecQuery=*:363699004=34164001](http://localhost:8080/concepts?ecQuery=*:363699004=34164001)
* Concepts in the ICD-O simple map reference set having attribute "Procedure site (attribute)" [http://localhost:8080/concepts?ecQuery=^446608001:363704007=*](http://localhost:8080/concepts?ecQuery=^446608001:363704007=*)


### Run on another port
This is useful for running multiple instances of the tool to serve more releases.
```
java -jar target/*-exec.jar --serve --server.port=8081
```

### Building the software yourself
If you would like to build this open source software yourself it's simple with maven:
```
git clone https://github.com/ihtsdo/snomed-query-service.git
cd snomed-query-service
mvn clean package
```
Find the binaries in the target directory.

### Licence
 Apache 2.0 Open Source Licence

### Thanks
 [kaicode](https://github.com/kaicode) for the code donation 
