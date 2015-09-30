# SNOMED CT Release Query Service
A Spring Boot webapp which loads snomed from the distribution zip into a Lucene index and allows basic queries.
This is a pet project and is not supported by IHTSDO.

Demo installation here: http://srqs.kaicube.com/

Support for simple SNOMED CT Expression Constraint Language queries

Example EC queries:
* Descendant of "Administrative statuses (finding)" [http://srqs.kaicube.com/concepts?ecQuery=<<307824009](http://srqs.kaicube.com/concepts?ecQuery=<307824009)
* Ancestor or self of "Fitting of contact lens (procedure)" having attribute "Method (attribute)" [http://srqs.kaicube.com/concepts?ecQuery=>>448642000:260686004](http://srqs.kaicube.com/concepts?ecQuery=>>448642000:260686004)
* All concepts having attribute "Direct device (attribute)" with value "Plaster cast, device (physical object)" [http://srqs.kaicube.com/concepts?ecQuery=*:363699004=34164001](http://srqs.kaicube.com/concepts?ecQuery=*:363699004=34164001)

Trello board here: https://trello.com/b/fZ0jvg0e/srqs

[![Build Status](https://travis-ci.org/kaicode/srqs.svg)](https://travis-ci.org/kaicube/srqs)

Apache 2.0 License
