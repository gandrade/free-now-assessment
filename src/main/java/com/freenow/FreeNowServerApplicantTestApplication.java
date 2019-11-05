package com.freenow;

import com.freenow.util.LoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@EnableSwagger2
@SpringBootApplication
@EnableWebSecurity
public class FreeNowServerApplicantTestApplication extends WebSecurityConfigurerAdapter implements WebMvcConfigurer
{

    public static void main(String[] args)
    {
        SpringApplication.run(FreeNowServerApplicantTestApplication.class, args);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }


    @Bean
    public Docket docket()
    {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(generateApiInfo()).securitySchemes(Arrays.asList(new BasicAuth("basicAuth")));
    }


    private ApiInfo generateApiInfo()
    {
        return new ApiInfo("freenow Server Applicant Test Service", "This service is to check the technology knowledge of a server applicant for freenow.", "Version 1.0 - mw",
            "urn:tos", "career@freenow.com", "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
    }


    @Bean
    public UserDetailsService userDetailsService()
    {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        manager.createUser(User.withUsername("mytaxi").password(encoder.encode("mytaxi")).roles("USER").build());
        return manager;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .mvcMatchers("/").permitAll()
            .mvcMatchers("/v1/**").authenticated()
            .and()
            .httpBasic().and().formLogin();

        allowH2Console(http);
    }


    private void allowH2Console(HttpSecurity http) throws Exception
    {
        http.csrf()
            .disable()
            .headers()
            .frameOptions().disable();
    }
}
