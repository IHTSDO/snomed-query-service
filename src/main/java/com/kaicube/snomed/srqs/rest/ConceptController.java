package com.kaicube.snomed.srqs.rest;

import com.kaicube.snomed.srqs.ReleaseReader;
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
	@ApiOperation(value = "List concept IDs", notes = "List concept IDs")
	@ResponseBody
	public List<String> retrieveConcepts(@RequestParam(defaultValue = "50") int limit) throws IOException, ParseException {
		return releaseReader.retrieveConcepts(limit);
	}

	@RequestMapping("/{conceptId}/ancestors")
	@ApiOperation(value = "List concept ancestor IDs", notes = "List all concept ancestor IDs including those from multiple parents.")
	@ResponseBody
	public String[] retrieveConceptAncestors(@PathVariable String conceptId) throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveConceptAncestors(conceptId);
	}

}
