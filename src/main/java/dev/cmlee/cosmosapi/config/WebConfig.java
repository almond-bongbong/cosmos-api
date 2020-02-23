package dev.cmlee.cosmosapi.config;

import dev.cmlee.cosmosapi.interceptor.RestTemplateLoggingRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedHeaders("authorization", "content-type");
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
				.requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
				.setConnectTimeout(Duration.ofSeconds(30))
				.setReadTimeout(Duration.ofSeconds(30))
				.additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
				.additionalInterceptors(new RestTemplateLoggingRequestInterceptor()) .build(); }
}

