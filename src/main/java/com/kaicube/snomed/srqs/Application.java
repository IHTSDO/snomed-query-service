package com.kaicube.snomed.srqs;

import com.kaicube.snomed.srqs.service.ReleaseImporter;
import com.kaicube.snomed.srqs.service.ReleaseReader;
import com.kaicube.snomed.srqs.service.ReleaseStore;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
@EnableSwagger
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.kaicube.snomed.srqs.rest")
@RequestMapping(produces={MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
public class Application {

	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

	@Bean
	public ReleaseReader getReleaseReader() throws IOException {
		ReleaseStore releaseStore = new ReleaseImporter().loadReleaseZip("SnomedCT_RF2Release_INT_20150131.zip");
		return new ReleaseReader(releaseStore);
	}

	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
				.apiInfo(new ApiInfo(
						"SNOMED-CT Release Query Service API",
						"This app lets you query a snomed release.",
						null,
						null,
						"Apache 2.0",
						null
				))
				//Here we disable auto generating of responses for REST-endpoints
				.useDefaultResponseMessages(false)
				//Here we specify URI patterns which will be included in Swagger docs. Use regex for this purpose.
				.includePatterns("/stats.*", "/concepts.*");
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}

}
