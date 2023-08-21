package org.ihtsdo.otf.sqs;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.sqs.service.ReleaseImportManager;
import org.ihtsdo.otf.sqs.service.SnomedQueryService;
import org.ihtsdo.otf.sqs.service.TestReleaseImportManager;
import org.ihtsdo.otf.sqs.service.store.DiskReleaseStore;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;


@SuppressWarnings("unused")
@SpringBootApplication
public class Application implements CommandLineRunner {

	public static final String USAGE = "Use one of --loadRelease='pathOfExtractedRelease', --serve";

	@Autowired(required = false)
	private BuildProperties buildProperties;

	@Autowired
	private ConfigurableApplicationContext context;

	@Value("${loadRelease}")
	private String loadReleaseArchive;

	@Value("${loadTestData}")
	private String loadTestData;

	@Value("${serve}")
	private String serve;

	@Value("${loadInactiveConcepts}")
	private String loadInactiveConcepts;

	private ReleaseImportManager releaseImportManager;

	private LoadingProfile loadingProfile = LoadingProfile.light.withAllRefsets();

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	private File releaseDirectory = null;

	public static void main(String[] argsArray) throws IOException {
		SpringApplication.run(Application.class, argsArray);
	}

	@Bean
	public ReleaseStore getReleaseStore() throws IOException, InterruptedException {
		return new DiskReleaseStore(new File("index"));
	}

	@Bean
	public SnomedQueryService getReleaseReader() throws IOException, InterruptedException {
		releaseImportManager = isParamSet(loadTestData) ? new TestReleaseImportManager(false) : new ReleaseImportManager();
		if (isServe()) {
			return new SnomedQueryService(releaseImportManager.openExistingReleaseStore(new File("index")));
		} else {
			return null;
		}
	}

	@Bean
	public OpenAPI apiInfo() {
		final String version = buildProperties != null ? buildProperties.getVersion() : "DEV";
		return new OpenAPI()
				.info(new Info().title("snomed-query-service")
						.description("SNOMED-CT Query Service API")
						.version(version)
						.contact(new Contact().name("SNOMED International").url("https://www.snomed.org"))
						.license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0")))
				.externalDocs(new ExternalDocumentation().description("See more about snomed-query-service in GitHub").url("https://github.com/IHTSDO/snomed-query-service"));
	}

	@Bean
	public GroupedOpenApi apiDocs() {
		GroupedOpenApi.Builder apiBuilder = GroupedOpenApi.builder()
				.group("snomed-query-service")
				.packagesToScan("org.ihtsdo.otf.sqs.rest");
		// Don't show the error or root endpoints in swagger
		apiBuilder.pathsToExclude("/error", "/");
		return apiBuilder.build();
	}

	@Bean
	public GroupedOpenApi springActuatorApi() {
		return GroupedOpenApi.builder().group("actuator")
				.packagesToScan("org.springframework.boot.actuate")
				.pathsToMatch("/actuator/**")
				.build();
	}


	private static void exit(String error) {
		LOGGER.error(error);
		System.exit(1);
	}

	@Override
	public void run(String... strings) throws Exception {
		checkArguments();
		if (!isServe()) {
			// If not running in serve mode, load release files and exit
			if (isParamSet(loadInactiveConcepts)) {
				loadingProfile = loadingProfile.withInactiveConcepts();
			}
			releaseImportManager.loadReleaseFilesToDiskBasedIndex(releaseDirectory, loadingProfile, new File("index"));
			context.close();
		}
	}

	private void checkArguments() {
		boolean meaningfulArgumentsFound = false;

		if (loadReleaseArchive != null && !loadReleaseArchive.isEmpty() && isParamSet(loadReleaseArchive)) {
			releaseDirectory = new File(loadReleaseArchive);
			if (!releaseDirectory.isDirectory()) {
				exit("This is not a directory: " + releaseDirectory.getAbsolutePath());
			}
			meaningfulArgumentsFound = true;
		} else if (isParamSet(loadTestData)) {
			meaningfulArgumentsFound = true;
		} else if (isServe()) {
			meaningfulArgumentsFound = true;
		}
		if (!meaningfulArgumentsFound) {
			exit("Not enough arguments. " + USAGE);
		}
	}

	private boolean isServe() {
		return isParamSet(serve);
	}

	private boolean isParamSet(String flag) {
		return !"not_set".equals(flag);
	}

}
