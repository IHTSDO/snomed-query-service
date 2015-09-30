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

@RestController
@RequestMapping(value = "/refsets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class RefsetController {

	@Autowired
	private ReleaseReader releaseReader;

	@RequestMapping
	@ApiOperation(value = "List reference sets", notes = "List all reference sets.",
			response = ConceptResult.class, responseContainer = "List")
	@ResponseBody
	public List<ConceptResult> retrieveReferenceSets() throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveReferenceSets();
	}

}
