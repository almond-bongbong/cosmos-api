package dev.cmlee.cosmosapi.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties("app")
@Getter @Setter
public class AppProperties {

	@NotEmpty
	private String jwtSecret;

	@NotEmpty
	private Long jwtExpirationInMs;
}
