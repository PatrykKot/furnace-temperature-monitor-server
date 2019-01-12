package com.kotlarz.configuration;

import com.kotlarz.configuration.logger.RestApiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfiguration
                implements WebMvcConfigurer
{
    @Autowired
    private RestApiLogger restApiLogger;

    @Override
    public void addInterceptors( InterceptorRegistry registry )
    {
        registry.addInterceptor( restApiLogger );
    }
}
