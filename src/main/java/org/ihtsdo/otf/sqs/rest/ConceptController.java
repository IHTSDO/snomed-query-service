package org.ihtsdo.otf.sqs.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.ihtsdo.otf.sqs.service.SnomedQueryService;
import org.ihtsdo.otf.sqs.service.dto.ConceptResult;
import org.ihtsdo.otf.sqs.service.dto.ConceptResults;
import org.ihtsdo.otf.sqs.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/concepts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConceptController {

	@Autowired
	private SnomedQueryService snomedQueryService;

	@RequestMapping
	@Operation(summary = "Query concepts using Expression Constraint Language or search term", description = "SNOMED CT Expression Constraint Language queries can be used. " +
			"For list of ECL syntax supported see https://github.com/ihtsdo/snomed-query-service")
	@ResponseBody
	public ConceptResults retrieveConcepts(@RequestParam(required = false) String ecQuery,
			@RequestParam(required = false) String term,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = SnomedQueryService.DEFAULT_LIMIT + "") int limit) throws ServiceException {

		return snomedQueryService.search(ecQuery, term, offset, limit);
	}

	@RequestMapping("/{conceptId}")
	@Operation(summary = "Retrieve concept",
			description = "Retrieve a concept by its identifier.")
	@ResponseBody
	public ConceptResult retrieveConcept(@PathVariable String conceptId) throws ServiceException {
		return snomedQueryService.retrieveConcept(conceptId);
	}

	@RequestMapping("/{conceptId}/ancestors")
	@Operation(summary = "Retrieve concept ancestors",
			description = "Retrieve all ancestors of the given concept, including those from multiple parents.")
	@ResponseBody
	public ConceptResults retrieveConceptAncestors(@PathVariable String conceptId,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = SnomedQueryService.DEFAULT_LIMIT + "") int limit) throws ServiceException {
		return snomedQueryService.retrieveConceptAncestors(conceptId, offset, limit);
	}

	@RequestMapping("/{conceptId}/descendants")
	@Operation(summary = "Retrieve concept descendants", description = "Retrieve all descendants of the given concept.")
	@ResponseBody
	public ConceptResults retrieveConceptDescendants(@PathVariable String conceptId,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = SnomedQueryService.DEFAULT_LIMIT + "") int limit) throws ServiceException {
		return snomedQueryService.retrieveConceptDescendants(conceptId, offset, limit);
	}

}
