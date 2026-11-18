package com.projeto.transferencias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;

import tools.jackson.databind.cfg.CoercionAction;
import tools.jackson.databind.cfg.CoercionInputShape;

@Configuration
public class JacksonConfig {

    @Bean
    public JsonMapperBuilderCustomizer jsonMapperCustomizer() {
        return builder -> builder.withCoercionConfig(
                Integer.class,
                config -> config.setCoercion(
                        CoercionInputShape.String,
                        CoercionAction.Fail));
    }
}
