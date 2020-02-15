package dev.cmlee.cosmosapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"))
//@SecurityScheme(
//		name = "bearerAuth",
//		type = SecuritySchemeType.HTTP,
//		bearerFormat = "JWT",
//		scheme = "bearer"
//)
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("cosmos API")
					.version("0.0.1")
					.description("API for cosmos project"))
				.components(new Components()
					.addSecuritySchemes("bearer-key",
						new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")));
	}
}
