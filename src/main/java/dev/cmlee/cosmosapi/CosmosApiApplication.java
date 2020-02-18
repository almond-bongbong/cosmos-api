package dev.cmlee.cosmosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CosmosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosmosApiApplication.class, args);
	}
}
