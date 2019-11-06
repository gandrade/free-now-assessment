package com.freenow.core.configuration;

import com.freenow.util.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures application interceptor.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer
{
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LoggingInterceptor())
            .addPathPatterns("/**");
    }
}
