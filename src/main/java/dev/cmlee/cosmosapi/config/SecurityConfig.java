package dev.cmlee.cosmosapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
			"/v3/api-docs",
			"/swagger"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
					.disable()
				.cors()
				.and()
				.authorizeRequests()
					.antMatchers(AUTH_WHITELIST).permitAll()
					.antMatchers("/auth/kakao").permitAll()
					.anyRequest().permitAll();
	}
}
