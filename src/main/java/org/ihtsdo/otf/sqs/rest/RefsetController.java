package org.ihtsdo.otf.sqs.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.ihtsdo.otf.sqs.service.SnomedQueryService;
import org.ihtsdo.otf.sqs.service.dto.ConceptResults;
import org.ihtsdo.otf.sqs.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/refsets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
@ConditionalOnProperty(name = "serve", havingValue = "true")
public class RefsetController {

	@Autowired
	private SnomedQueryService snomedQueryService;

	@RequestMapping
	@Operation(summary = "Retrieve reference sets", description = "Retrieve all reference sets.")
	@ResponseBody
	public ConceptResults retrieveReferenceSets(@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = SnomedQueryService.DEFAULT_LIMIT + "") int limit) throws ServiceException {
		return snomedQueryService.retrieveReferenceSets(offset, limit);
	}

}
