package org.ihtsdo.otf.sqs.rest;

import org.ihtsdo.otf.sqs.service.SnomedQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
@ConditionalOnProperty(name = "serve", havingValue = "true")
public class StatsController {

	@Autowired
	private SnomedQueryService snomedQueryService;

	@RequestMapping
	@ResponseBody
	public Map<String, String> home() throws IOException {
		Map<String, String> map = new HashMap<>();
		map.put("conceptCount", snomedQueryService.getConceptCount() + "");
		return map;
	}

}
