package com.kaicube.snomed.srqs;

import com.kaicube.snomed.srqs.service.LoadingMode;
import com.kaicube.snomed.srqs.service.ReleaseImporter;
import com.kaicube.snomed.srqs.service.ReleaseReader;
import com.kaicube.snomed.srqs.service.TestReleaseImporter;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;

@Controller
@EnableSwagger
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.kaicube.snomed.srqs.rest")
@RequestMapping(produces={MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
public class Application implements CommandLineRunner {

	public static final String USEAGE = "Use one of --loadRelease 'path', --serve";
	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

	@Autowired
	private ConfigurableApplicationContext context;

	@Value("${load.mode:light}")
	private LoadingMode loadingMode;

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	private static boolean loadRelease = false;
	private static String releasePath = null;
	private static boolean loadTestData = false;
	private static boolean serve = false;

	@Bean
	public ReleaseReader getReleaseReader() throws IOException {
		final ReleaseImporter releaseImporter = loadTestData ? new TestReleaseImporter(false) : new ReleaseImporter();
		if (serve) {
			return new ReleaseReader(releaseImporter.openExistingReleaseStore());
		} else {
			return new ReleaseReader(releaseImporter.loadReleaseZip(releasePath, loadingMode));
		}
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
				// Disable auto generating of responses for REST-endpoints
				.useDefaultResponseMessages(false)
				// Specify URI patterns which will be included in Swagger docs using regex
				.includePatterns("/stats.*", "/concepts.*", "/refsets.*");
	}

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			final String option = args[0];
			switch (option) {
				case "--loadRelease":
					maxArgs(2, args);
					if (args.length > 1) {
						loadRelease = true;
						releasePath = args[1];
						final File file = new File(releasePath);
						if (!file.isFile()) {
							exit("Is not a file: " + file.getAbsolutePath());
						}
					} else {
						exit("Please specify RF2 release archive path after the --loadRelease argument.");
					}
					break;
				case "--loadTestData":
					maxArgs(1, args);
					loadTestData = true;
					break;
				case "--serve":
					maxArgs(1, args);
					serve = true;
					break;
				default:
					exit("Unrecognised option: " + option);
					break;
			}
		} else {
			exit("Not enough arguments. " + USEAGE);
		}

		SpringApplication.run(Application.class, args);
	}

	private static void maxArgs(int max, String[] args) {
		if (args.length > max) {
			exit("Too many arguments. " + USEAGE);
		}
	}

	private static void exit(String error) {
		LOGGER.error(error);
		System.exit(1);
	}

	@Override
	public void run(String... strings) throws Exception {
		if (!serve) {
			context.close();
		}
	}

}
