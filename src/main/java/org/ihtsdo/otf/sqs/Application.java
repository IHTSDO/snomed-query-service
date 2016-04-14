package org.ihtsdo.otf.sqs;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.sqs.service.ReleaseImportManager;
import org.ihtsdo.otf.sqs.service.ReleaseReader;
import org.ihtsdo.otf.sqs.service.TestReleaseImportManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@ComponentScan(basePackages = "org.ihtsdo.otf.sqs.rest")
@RequestMapping(produces={MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
public class Application implements CommandLineRunner {

	public static final String USEAGE = "Use one of --loadRelease 'pathOfExtractedRelease', --serve";
	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

	@Autowired
	private ConfigurableApplicationContext context;

	private LoadingProfile loadingProfile = LoadingProfile.light; // TODO Configure via config

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	private static File releaseDirectory = null;
	private static boolean loadTestData = false;
	private static boolean serve = false;

	@Bean
	public ReleaseReader getReleaseReader() throws IOException, InterruptedException {
		final ReleaseImportManager releaseImportManager = loadTestData ? new TestReleaseImportManager(false) : new ReleaseImportManager();
		if (serve) {
			return new ReleaseReader(releaseImportManager.openExistingReleaseStore());
		} else {
			return new ReleaseReader(releaseImportManager.loadReleaseZip(releaseDirectory, loadingProfile));
		}
	}

	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
				.apiInfo(new ApiInfo(
						"SNOMED-CT Query Service API",
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

	public static void main(String[] argsArray) throws IOException {
		if (argsArray.length == 0) {
			exit("Not enough arguments. " + USEAGE);
		}

		boolean meaningfulArgumentsFound = false;
		for (int i = 0; i < argsArray.length; i++) {
			final String option = argsArray[i];
			switch (option) {
				case "--loadRelease":
					if (argsArray.length > i + 1) {
						String releaseDirectoryPath = argsArray[1];
						releaseDirectory = new File(releaseDirectoryPath);
						if (!releaseDirectory.isDirectory()) {
							exit("This is not a directory: " + releaseDirectory.getAbsolutePath());
						}
						meaningfulArgumentsFound = true;
					} else {
						exit("After the --loadRelease argument please specify the path of the directory where your RF2 release archive has been extracted.");
					}
					break;
				case "--loadTestData":
					loadTestData = true;
					meaningfulArgumentsFound = true;
					break;
				case "--serve":
					serve = true;
					meaningfulArgumentsFound = true;
					break;
			}
		}
		if (!meaningfulArgumentsFound) {
			exit("Not enough arguments. " + USEAGE);
		}

		SpringApplication.run(Application.class);
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
