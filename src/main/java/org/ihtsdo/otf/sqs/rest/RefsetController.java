package org.ihtsdo.otf.sqs.rest;

import org.ihtsdo.otf.sqs.service.ReleaseReader;
import org.ihtsdo.otf.sqs.service.dto.ConceptResults;
import org.ihtsdo.otf.sqs.service.exception.ServiceException;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/refsets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class RefsetController {

	@Autowired
	private ReleaseReader releaseReader;

	@RequestMapping
	@ApiOperation(value = "Retrieve reference sets", notes = "Retrieve all reference sets.",
			response = ConceptResults.class)
	@ResponseBody
	public ConceptResults retrieveReferenceSets(@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = ReleaseReader.DEFAULT_LIMIT + "") int limit) throws ServiceException {
		return releaseReader.retrieveReferenceSets(offset, limit);
	}

}
