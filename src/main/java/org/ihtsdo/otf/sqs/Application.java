package org.ihtsdo.otf.sqs;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.sqs.service.ReleaseImportManager;
import org.ihtsdo.otf.sqs.service.SnomedQueryService;
import org.ihtsdo.otf.sqs.service.TestReleaseImportManager;
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
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Controller
@EnableSwagger
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.ihtsdo.otf.sqs.rest")
@RequestMapping(produces={MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
public class Application implements CommandLineRunner {

	public static final String USAGE = "Use one of --loadRelease='pathOfExtractedRelease', --serve";

	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

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

	private LoadingProfile loadingProfile;

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	private static File releaseDirectory = null;

	@Bean
	public SnomedQueryService getReleaseReader() throws IOException, InterruptedException {
		releaseImportManager = isParamSet(loadTestData) ? new TestReleaseImportManager(false) : new ReleaseImportManager();
		if (isServe()) {
			return new SnomedQueryService(releaseImportManager.openExistingReleaseStore());
		} else {
			return null;
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
		SpringApplication.run(Application.class, argsArray);
	}

	private static void exit(String error) {
		LOGGER.error(error);
		System.exit(1);
	}

	@Override
	public void run(String... strings) throws Exception {
		configure();
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
	
	private void configure(){
		Yaml yaml = new Yaml();  	
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream is = loader.getResourceAsStream("loadingProfile.yaml");
		
        LoadingProfileConfig config = yaml.loadAs( is, LoadingProfileConfig.class );

        loadingProfile = config.createLoadingProfile();
	}

}
