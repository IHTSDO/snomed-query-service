package com.kaicube.snomed.srqs.rest;

import com.kaicube.snomed.srqs.exceptions.NotFoundException;
import com.kaicube.snomed.srqs.service.ReleaseReader;
import com.kaicube.snomed.srqs.service.dto.ConceptResult;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping(value = "/refsets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class RefsetController {

	@Autowired
	private ReleaseReader releaseReader;

	@RequestMapping
	@ApiOperation(value = "Retrieve reference sets", notes = "Retrieve all reference sets.",
			response = ConceptResult.class, responseContainer = "Set")
	@ResponseBody
	public Set<ConceptResult> retrieveReferenceSets() throws IOException, ParseException, NotFoundException {
		return releaseReader.retrieveReferenceSets();
	}

}