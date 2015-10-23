package com.kaicube.snomed.srqs.rest;

import com.kaicube.snomed.srqs.service.ReleaseReader;
import com.kaicube.snomed.srqs.service.dto.ConceptResult;
import com.kaicube.snomed.srqs.service.dto.ConceptResults;
import com.kaicube.snomed.srqs.service.exception.ServiceException;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/concepts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConceptController {

	@Autowired
	private ReleaseReader releaseReader;

	@RequestMapping
	@ApiOperation(value = "Query concepts using Expression Constraint Language", notes = "Simple SNOMED CT Expression Constraint Language queries can be used. " +
			"Supported expressions for the focus concept are: (<, <<, >, >>, *, ^). A singe attribute and value can be used. Conjunction and disjunction are not yet supported.",
			response = ConceptResults.class)
	@ResponseBody
	public ConceptResults retrieveConcepts(@RequestParam String ecQuery,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = ReleaseReader.DEFAULT_LIMIT + "") int limit) throws ServiceException {
		return releaseReader.expressionConstraintQuery(ecQuery, offset, limit);
	}

	@RequestMapping("/{conceptId}")
	@ApiOperation(value = "Retrieve concept",
			notes = "Retrieve a concept by its identifier.",
			response = ConceptResult.class)
	@ResponseBody
	public ConceptResult retrieveConcept(@PathVariable String conceptId) throws ServiceException {
		return releaseReader.retrieveConcept(conceptId);
	}

	@RequestMapping("/{conceptId}/ancestors")
	@ApiOperation(value = "Retrieve concept ancestors",
			notes = "Retrieve all ancestors of the given concept, including those from multiple parents.",
			response = ConceptResults.class)
	@ResponseBody
	public ConceptResults retrieveConceptAncestors(@PathVariable String conceptId,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = ReleaseReader.DEFAULT_LIMIT + "") int limit) throws ServiceException {
		return releaseReader.retrieveConceptAncestors(conceptId, offset, limit);
	}

	@RequestMapping("/{conceptId}/descendants")
	@ApiOperation(value = "Retrieve concept descendants", notes = "Retrieve all descendants of the given concept.",
			response = ConceptResults.class)
	@ResponseBody
	public ConceptResults retrieveConceptDescendants(@PathVariable String conceptId,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = ReleaseReader.DEFAULT_LIMIT + "") int limit) throws ServiceException {
		return releaseReader.retrieveConceptDescendants(conceptId, offset, limit);
	}

}
