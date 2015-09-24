package com.kaicube.snomed.srqs;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
public class DemoApplication {

	private static ReleaseStore releaseStore;
	private static ReleaseReader releaseReader;

	@RequestMapping("/")
	@ResponseBody
	Map<String, String> home() throws IOException {
		Map<String, String> map = new HashMap<>();
		map.put("conceptCount", releaseReader.getConceptCount() + "");
		return map;
	}

	@RequestMapping("/concepts")
	@ResponseBody
	List<String> retrieveConcepts(@RequestParam(defaultValue = "10") int limit) throws IOException, ParseException {
		return releaseReader.retrieveConcepts(limit);
	}

	public static void main(String[] args) throws IOException {
		releaseStore = new ReleaseImporter().loadReleaseZip("SnomedCT_RF2Release_INT_20150131.zip");
		releaseReader = new ReleaseReader(releaseStore);
		SpringApplication.run(DemoApplication.class, args);
	}

}
