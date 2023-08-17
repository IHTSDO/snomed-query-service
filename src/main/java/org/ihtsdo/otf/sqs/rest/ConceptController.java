package org.ihtsdo.otf.sqs.rest;

import com.wordnik.swagger.annotations.ApiOperation;
import org.ihtsdo.otf.sqs.service.SnomedQueryService;
import org.ihtsdo.otf.sqs.service.dto.ConceptResult;
import org.ihtsdo.otf.sqs.service.dto.ConceptResults;
import org.ihtsdo.otf.sqs.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/concepts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConceptController {

	@Autowired
	private SnomedQueryService snomedQueryService;

	@RequestMapping
	@ApiOperation(value = "Query concepts using Expression Constraint Language or search term", notes = "SNOMED CT Expression Constraint Language queries can be used. " +
			"For list of ECL syntax supported see https://github.com/ihtsdo/snomed-query-service",
			response = ConceptResults.class)
	@ResponseBody
	public ConceptResults retrieveConcepts(@RequestParam(required = false) String ecQuery,
			@RequestParam(required = false) String term,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = SnomedQueryService.DEFAULT_LIMIT + "") int limit) throws ServiceException {

		return snomedQueryService.search(ecQuery, term, offset, limit);
	}

	@RequestMapping("/{conceptId}")
	@ApiOperation(value = "Retrieve concept",
			notes = "Retrieve a concept by its identifier.",
			response = ConceptResult.class)
	@ResponseBody
	public ConceptResult retrieveConcept(@PathVariable String conceptId) throws ServiceException {
		return snomedQueryService.retrieveConcept(conceptId);
	}

	@RequestMapping("/{conceptId}/ancestors")
	@ApiOperation(value = "Retrieve concept ancestors",
			notes = "Retrieve all ancestors of the given concept, including those from multiple parents.",
			response = ConceptResults.class)
	@ResponseBody
	public ConceptResults retrieveConceptAncestors(@PathVariable String conceptId,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = SnomedQueryService.DEFAULT_LIMIT + "") int limit) throws ServiceException {
		return snomedQueryService.retrieveConceptAncestors(conceptId, offset, limit);
	}

	@RequestMapping("/{conceptId}/descendants")
	@ApiOperation(value = "Retrieve concept descendants", notes = "Retrieve all descendants of the given concept.",
			response = ConceptResults.class)
	@ResponseBody
	public ConceptResults retrieveConceptDescendants(@PathVariable String conceptId,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = SnomedQueryService.DEFAULT_LIMIT + "") int limit) throws ServiceException {
		return snomedQueryService.retrieveConceptDescendants(conceptId, offset, limit);
	}

}
