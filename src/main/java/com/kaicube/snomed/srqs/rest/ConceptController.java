package com.kaicube.snomed.srqs.rest;

import com.kaicube.snomed.srqs.exceptions.NotFoundException;
import com.kaicube.snomed.srqs.service.ReleaseReader;
import com.kaicube.snomed.srqs.service.dto.ConceptResult;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/concepts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConceptController {

	@Autowired
	private ReleaseReader releaseReader;

	@RequestMapping
	@ApiOperation(value = "Query concepts using Expression Constraint Language", notes = "Simple SNOMED CT Expression Constraint Language queries can be used. " +
			"Supported expressions for the focus concept are: (<, <<, >, >>, *, ^). A singe attribute and value can be used. Conjunction and disjunction are not yet supported.",
			response = ConceptResult.class, responseContainer = "List")
	@ResponseBody
	public List<ConceptResult> retrieveConcepts(@RequestParam String ecQuery) throws IOException, ParseException, NotFoundException {
		return releaseReader.expressionConstraintQuery(ecQuery);
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
	@ApiOperation(value = "Retrieve concept ancestors",
			notes = "Retrieve all ancestors of the given concept, including those from multiple parents.",
			response = ConceptResult.class, responseContainer = "Set")
	@ResponseBody
	public Set<ConceptResult> retrieveConceptAncestors(@PathVariable String conceptId) throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveConceptAncestors(conceptId);
	}

	@RequestMapping("/{conceptId}/descendants")
	@ApiOperation(value = "Retrieve concept descendants", notes = "Retrieve all descendants of the given concept.",
			response = ConceptResult.class, responseContainer = "Set")
	@ResponseBody
	public Set<ConceptResult> retrieveConceptDescendants(@PathVariable String conceptId) throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveConceptDescendants(conceptId);
	}

}
