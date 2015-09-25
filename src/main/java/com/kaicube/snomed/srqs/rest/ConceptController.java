package com.kaicube.snomed.srqs.rest;

import com.kaicube.snomed.srqs.service.ReleaseReader;
import com.kaicube.snomed.srqs.exceptions.NotFoundException;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/concepts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConceptController {

	@Autowired
	private ReleaseReader releaseReader;

	@RequestMapping
	@ApiOperation(value = "List concept IDs", notes = "Minimal SNOMED CT Expression Constraint queries can be used. " +
			"Only constraint operators are supported (<, <<, >, >>). Example: < 39928001")
	@ResponseBody
	public List<String> retrieveConcepts(@RequestParam String ecQuery, @RequestParam(defaultValue = "50") int limit) throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveConcepts(ecQuery, limit);
	}

	@RequestMapping("/{conceptId}/ancestors")
	@ApiOperation(value = "List concept ancestor IDs", notes = "List the identifiers of all ancestors of the given concept, including those from multiple parents.")
	@ResponseBody
	public String[] retrieveConceptAncestors(@PathVariable String conceptId) throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveConceptAncestors(conceptId);
	}

	@RequestMapping("/{conceptId}/descendants")
	@ApiOperation(value = "List concept descendant IDs", notes = "List the identifiers of all descendants of the given concept.")
	@ResponseBody
	public List<String> retrieveConceptDescendants(@PathVariable String conceptId) throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveConceptDescendants(conceptId);
	}

}
