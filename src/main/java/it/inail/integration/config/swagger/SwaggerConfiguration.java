package it.inail.integration.config.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.Api;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ListVendorExtension;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfiguration.
 */
@Configuration
@EnableSwagger2
@ComponentScan("it.inail")
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfiguration {
	
	private static final String HEADER_VALUE = "header";

	private static final String BASE_PACKAGE = "it.inail";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfiguration.class);

	/** The Constant DEAFULT_CONTACT_EMAIL. */
	private static final String DEAFULT_CONTACT_EMAIL = "";
	
	/** The Constant DEAFULT_CONTACT_URL. */
	private static final String DEAFULT_CONTACT_URL = "http://www.inail.it";
	
	/** The Constant DEAFULT_CONTACT_NAME. */
	private static final String DEAFULT_CONTACT_NAME = "INAIL";

	/** The Constant DEAFULT_DESCRIZIONE. */
	private static final String DEAFULT_DESCRIZIONE = "Descrizione API";
	
	/** The Constant DEAFULT_VALUE. */
	private static final String DEAFULT_VALUE = "1.0";
	
	/** The Constant DEFAULT_TITOLO. */
	private static final String DEFAULT_TITOLO = "Servizio API";

	/** The Constant INAIL_API_GATEWAY_JWT. */
	private static final String INAIL_API_GATEWAY_JWT = "INAIL_API_GATEWAY_JWT";
	
	/** The Constant X_DOMAIN_INFO. */
	public static final String X_DOMAIN_INFO = "x-domain-info";
	
	public static final String X_INAIL_APICATALOG = "x-inail-apicatalog";

	/** The build properties. */
	@Autowired
	private BuildProperties buildProperties;
	
	/** The application context. */
	@Autowired
	private ApplicationContext applicationContext;
	
	/** The servlet context. */
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private TypeResolver typeResolver;
		
	/** The configurable bean factory. */
	@Autowired
	private ConfigurableBeanFactory configurableBeanFactory;
	
	/**
	 * Instantiates a new swagger configuration.
	 */
	public SwaggerConfiguration() {
		super();
	}

	/**
	 * Api.
	 *
	 * @return Docket
	 */
	@Bean
	public Docket api() {
		addControllers();
//		List<? extends SecurityScheme> securitySchemes = Arrays.asList(apiKey());
//		List<SecurityContext> securityContexts = Arrays.asList(securityContext());
		Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors
				.withClassAnnotation(RestController.class))
				.apis(RequestHandlerSelectors.basePackage(SwaggerConfiguration.BASE_PACKAGE))
				.paths(PathSelectors.any()).build()
				.pathMapping("/").apiInfo(apiInfo(null))
				.forCodeGeneration(true)
//				.securitySchemes(securitySchemes)
				.protocols(getProtocols())
//				.securityContexts(securityContexts)
				.useDefaultResponseMessages(false)
				.directModelSubstitute(Date.class, Integer.class)
				.directModelSubstitute(JsonNode.class, String.class);
//		addDefaultModels(docket);
//		addDefaultGlobalResponses(docket);
		return docket;
	}

	/**
	 * Api info.
	 *
	 * @return ApiInfo
	 */
	protected ApiInfo apiInfo(String versione) {

		final ApiInfoBuilder builder = new ApiInfoBuilder();

		builder.title(getTitoloApi()).version(getVersioneApi(versione)).description(getDescrizioneApi()).contact(getContact());
		addExtensions(builder, "default");
		return builder.build();
	}

	protected ApiInfo apiInfoController(String versione, String descrizione, String titolo, String groupName) {
		
		final ApiInfoBuilder builder = new ApiInfoBuilder();
		builder.title(titolo).version(versione).description(descrizione).contact(getContact());
		addExtensions(builder, groupName);
		return builder.build();
	}

	/**
	 * Api key.
	 *
	 * @return SecurityScheme
	 */
//	private SecurityScheme apiKey() {
//		return new ApiKey(SwaggerConfiguration.INAIL_API_GATEWAY_JWT, "Authorization", SwaggerConfiguration.HEADER_VALUE);
//	}

	/**
	 * Security context.
	 *
	 * @return SecurityContext
	 */
//	private SecurityContext securityContext() {
//		AuthorizationScope[] scopes = new AuthorizationScope[0];
//		return SecurityContext.builder()
//				.securityReferences(Arrays.asList(new SecurityReference(SwaggerConfiguration.INAIL_API_GATEWAY_JWT, scopes))).build();
//	}

	/**
	 * Adds the controllers.
	 */
	private void addControllers() {

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

		scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(RequestMapping.class));

		for (BeanDefinition bd : scanner.findCandidateComponents(SwaggerConfiguration.BASE_PACKAGE)) {
			try {
				String[] names = applicationContext.getBeanNamesForType(((ScannedGenericBeanDefinition)bd).resolveBeanClass(scanner.getClass().getClassLoader()));
				RequestMapping ann = applicationContext.findAnnotationOnBean(names[0], RequestMapping.class);
				Api apiAnnotation = applicationContext.findAnnotationOnBean(names[0], Api.class);
				
				for (String path : ann.value()) {
					generateDocketForController(apiAnnotation, path);
				}
			} catch (Exception e) {
				LOGGER.error("SwaggerConfiguration: errore nel recupero path da controller", e);
			}

		}
		
	}

	/**
	 * @param apiAnnotation
	 * @param path
	 */
	private void generateDocketForController(Api apiAnnotation, String path) {
		String finalPath = (path.startsWith("/") ? "" : "/") + path ;
		String[] raw = StringUtils.delimitedListToStringArray(finalPath, "/");
		List<String> rawAsList = Arrays.asList(raw);
		String versione = rawAsList.get(1);
		String nameWithVersion = StringUtils.collectionToDelimitedString(rawAsList.subList(1, rawAsList.size()), "/");
		String groupNameNoVersion = StringUtils.collectionToDelimitedString(rawAsList.subList(2, rawAsList.size()), "-");
		String groupName = groupNameNoVersion.concat("-v").concat(versione);
		String pathRegex = ".*"+path+".*";
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("path {} split in {} gruppo {} con regex {}", finalPath, rawAsList, groupName, pathRegex);
		}
//		List<? extends SecurityScheme> securitySchemes = Arrays.asList(apiKey());
//		List<SecurityContext> securityContexts = Arrays.asList(securityContext());					
		Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors
				.withClassAnnotation(RestController.class))
				.apis(RequestHandlerSelectors.basePackage(SwaggerConfiguration.BASE_PACKAGE))
				.paths(PathSelectors.regex(pathRegex)).build().groupName(groupName)
				.pathProvider(new RelativePathProvider(servletContext) {
			        @Override
			        public String getApplicationBasePath() {
			            return servletContext.getContextPath() + "/" + nameWithVersion;
			        }

					@Override
					public String getOperationPath(String operationPath) {
						return operationPath.substring(( "/" + nameWithVersion).length());
					}
			        
			        
			    })
				.pathMapping("/").apiInfo(apiInfoController(versione, apiAnnotation!= null ? apiAnnotation.value() : "", groupNameNoVersion, groupName))
				.forCodeGeneration(true)
//				.securitySchemes(securitySchemes)
				.protocols(getProtocols())
//				.securityContexts(securityContexts)
				.useDefaultResponseMessages(false);
//		addDefaultModels(docket);
//		addDefaultGlobalResponses(docket);
		configurableBeanFactory.registerSingleton(docket.getGroupName(), docket);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("gruppo {} creato per regex {}", groupName, pathRegex);
		}
	}

	/**
	 * Gets the protocols.
	 *
	 * @return the protocols
	 */
	private Set<String> getProtocols() {
		return Collections.unmodifiableSet(
				new HashSet<>(Arrays.asList("http","https")));
	}
	
	private Docket addDefaultGlobalResponses(Docket current) {
		return current.globalResponseMessage(RequestMethod.GET, globalResponses(false))
			    .globalResponseMessage(RequestMethod.POST, globalResponses(true))
			    .globalResponseMessage(RequestMethod.PUT, globalResponses(true))
			    .globalResponseMessage(RequestMethod.DELETE, globalResponses(false));
	}
	
	private List<ResponseMessage> globalResponses(boolean includeValidation) {
		List<ResponseMessage> messaggi = new ArrayList<>();	
		messaggi.add(new ResponseMessageBuilder()
		    .code(500)
		    .message("errore generico")
		    .responseModel(new ModelRef("Errore-API"))
		    .build());
		messaggi.add(new ResponseMessageBuilder()
	        .code(403)
	        .message("Non sei autorizzato ad accedere alla risorsa richiesta")
	        .responseModel(new ModelRef("Errore-API"))
	        .build());
		if(includeValidation) {
			messaggi.add(new ResponseMessageBuilder()
		        .code(400)
		        .message("errore validazione campi")
		        .responseModel(new ModelRef("Errore-Validazione"))
		        .build());
		}
		return messaggi;
	}
	
//	private Docket addDefaultModels(Docket current) {
//		return current.additionalModels(typeResolver.resolve(ApiError.class))
//        .additionalModels(typeResolver.resolve(ValidationMessage.class))
//        .additionalModels(typeResolver.resolve(ValidationError.class));
//	}
	
	private void addExtensions(ApiInfoBuilder apiInfoBuilder, String groupName) {
		List<Map<String, String>> domainInfoMap = new ArrayList<>();
		Map<String, String> buildInfo = new HashMap<>();
		buildProperties.forEach(item -> buildInfo.put(item.getKey(), item.getValue()));
		domainInfoMap.add(buildInfo);
		ListVendorExtension<Map<String, String>> domainInfo = new ListVendorExtension<>(X_DOMAIN_INFO,
				domainInfoMap);
		
		
		List<Map<String, String>> apiCatalogMap = new ArrayList<>();
		Map<String, String> apiCatalogValues = new HashMap<>();
		
//		CatalogDefinition computed = apiCatalogProperties.getDefinitions().get(groupName);
		
//		if(computed != null) {
			apiCatalogValues.put("CodiceApp", "POC-InGW");
			apiCatalogValues.put("Nome", "POC Inner Gateway");
			apiCatalogValues.put("JWTName", "JWTSGPS");
			apiCatalogMap.add(apiCatalogValues);
//		} else {
//			LOGGER.error("definizione non trovata per {}", groupName);
//		}
		
		ListVendorExtension<Map<String, String>> apiCatalogInfo = new ListVendorExtension<>(X_INAIL_APICATALOG,	apiCatalogMap);
		
		apiInfoBuilder.extensions(Collections.singletonList(domainInfo)).extensions(Collections.singletonList(apiCatalogInfo));
	}
	
	@Bean
	public UiConfiguration uiConfig() {
	    return UiConfigurationBuilder.builder()
	        .deepLinking(true)
	        .displayOperationId(true)
	        .defaultModelsExpandDepth(1)
	        .defaultModelExpandDepth(1)
	        .defaultModelRendering(ModelRendering.EXAMPLE)
	        .displayRequestDuration(false)
	        .docExpansion(DocExpansion.NONE)
	        .filter(false)
	        .maxDisplayedTags(null)
	        .operationsSorter(OperationsSorter.ALPHA)
	        .showExtensions(false)
	        .tagsSorter(TagsSorter.ALPHA)
	        .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
	        .validatorUrl(null)
	        .build();
	  }

	/**
	 * Gets the titolo api.
	 *
	 * @return String, il titolo delle API
	 */
	protected String getTitoloApi() {
		return SwaggerConfiguration.DEFAULT_TITOLO;
	}

	/**
	 * Gets the versione api.
	 * @param versione 
	 *
	 * @return String, la versione delle API
	 */
	protected String getVersioneApi(String versione) {
		return SwaggerConfiguration.DEAFULT_VALUE;
	}

	/**
	 * Gets the descrizione api.
	 *
	 * @return String, la descrizione delle API
	 */
	protected String getDescrizioneApi() {
		return SwaggerConfiguration.DEAFULT_DESCRIZIONE;
	}

	/**
	 * Gets the contact.
	 *
	 * @return il contatto
	 */
	private Contact getContact() {
		return new springfox.documentation.service.Contact(getContactName(), getContactUrl(), getContactEmail());
	}
	
	/**
	 * Gets the contact name.
	 *
	 * @return String, il nome del contatto
	 */
	protected String getContactName() {
		return SwaggerConfiguration.DEAFULT_CONTACT_NAME;
	}
	
	/**
	 * Gets the contact url.
	 *
	 * @return String, la URL del contatto
	 */
	protected String getContactUrl() {
		return SwaggerConfiguration.DEAFULT_CONTACT_URL;
	}

	/**
	 * Gets the contact email.
	 *
	 * @return String, la mail del contatto
	 */
	protected String getContactEmail() {
		return SwaggerConfiguration.DEAFULT_CONTACT_EMAIL;
	}
}
