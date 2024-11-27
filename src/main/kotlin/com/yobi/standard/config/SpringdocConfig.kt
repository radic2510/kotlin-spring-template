package com.yobi.standard.config

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocConfig {

    @Bean
    fun openApi(): OpenAPI {
        val info = Info()
            .title("Standard Template API")
            .version("v0.0.1")
            .description("Standard Template API 명세서입니다.")

        return OpenAPI()
            .components(Components())
            .info(info)
    }
}