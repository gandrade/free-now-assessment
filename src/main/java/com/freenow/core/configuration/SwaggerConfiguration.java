package com.freenow.core.configuration;

import com.freenow.FreeNowServerApplicantTestApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Configures Swagger2 configuration, including security also.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration
{

    @Bean
    public Docket docket(@Value("${security.roles}") String[] roles)
    {
        String packageName = FreeNowServerApplicantTestApplication.class.getPackage().getName();
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(packageName))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(generateApiInfo())
            .securityContexts(Arrays.asList(generateSecurityContext(roles)))
            .securitySchemes(Arrays.asList(generateBasicAuth()));
    }


    private BasicAuth generateBasicAuth()
    {
        return new BasicAuth("basicAuth");
    }


    private SecurityContext generateSecurityContext(String[] roles)
    {
        return SecurityContext.builder()
            .securityReferences(Arrays.asList(generateSecurityReference(roles)))
            .forPaths(PathSelectors.ant("/v1/**"))
            .build();
    }


    private SecurityReference generateSecurityReference(String[] roles)
    {
        AuthorizationScope[] authorizationScopes = Stream.of(roles)
            .map(role -> new AuthorizationScope(role, role))
            .toArray(AuthorizationScope[]::new);
        return new SecurityReference("basicAuth", authorizationScopes);
    }


    private ApiInfo generateApiInfo()
    {
        return new ApiInfo(
            "freenow Server Applicant Test Service",
            "This service is to check the technology knowledge of a server applicant for freenow.",
            "Version 1.0 - mw",
            "urn:tos",
            new Contact(null, null, "career@freenow.com"),
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            Collections.emptyList());
    }
}
