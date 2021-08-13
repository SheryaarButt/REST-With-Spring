package com.baeldung.um.spring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.lang.NonNullApi;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@Configuration
@ComponentScan({ "com.baeldung.um.web" })
@EnableWebMvc
public class UmWebConfig implements WebMvcConfigurer {

    public UmWebConfig() {
        super();
    }

    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        Optional<HttpMessageConverter<?>> converterFound =
                converters.stream().filter(c -> c instanceof AbstractJackson2HttpMessageConverter).findFirst();
        if (converterFound.isPresent()) {
            final AbstractJackson2HttpMessageConverter jsonConverter = (AbstractJackson2HttpMessageConverter) converterFound.get();
            jsonConverter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            jsonConverter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }

    // configuration
}
