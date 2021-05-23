package dev.jamesoliver.dwpassessmentjava.configuration;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }

    static class StringToEnumConverter implements Converter<String, Cities> {

        @Override
        public Cities convert(String path) {
            try {
                return Cities.valueOf(path.toUpperCase());
            } catch (IllegalArgumentException exception) {

                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City Not Found", exception);
            }
        }
    }
}
