package com.kaicube.snomed.srqs.rest;

import com.kaicube.snomed.srqs.service.ReleaseReader;
import com.kaicube.snomed.srqs.exceptions.NotFoundException;
import com.kaicube.snomed.srqs.service.dto.ConceptResult;
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
	@ApiOperation(value = "Query concepts using Expression Constraint Language", notes = "Minimal SNOMED CT Expression Constraint Language queries can be used. " +
			"Only constraint operators are supported (<, <<, >, >>). Example: < 39928001",
			response = ConceptResult.class, responseContainer = "List")
	@ResponseBody
	public List<ConceptResult> retrieveConcepts(@RequestParam String ecQuery, @RequestParam(defaultValue = "50") int limit) throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveConcepts(ecQuery, limit);
	}

	@RequestMapping("/{conceptId}")
	@ApiOperation(value = "Retrieve concept",
			notes = "Retrieve a concept by its identifier.",
			response = ConceptResult.class)
	@ResponseBody
	public ConceptResult retrieveConcept(@PathVariable String conceptId) throws IOException, NotFoundException {
		return releaseReader.retrieveConcept(conceptId);
	}

	@RequestMapping("/{conceptId}/ancestors")
	@ApiOperation(value = "List concept ancestors",
			notes = "List all ancestors of the given concept, including those from multiple parents.",
			response = ConceptResult.class, responseContainer = "List")
	@ResponseBody
	public List<ConceptResult> retrieveConceptAncestors(@PathVariable String conceptId) throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveConceptAncestors(conceptId);
	}

	@RequestMapping("/{conceptId}/descendants")
	@ApiOperation(value = "List concept descendants", notes = "List all descendants of the given concept.",
			response = ConceptResult.class, responseContainer = "List")
	@ResponseBody
	public List<ConceptResult> retrieveConceptDescendants(@PathVariable String conceptId) throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveConceptDescendants(conceptId);
	}

}
