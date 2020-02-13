package dev.cmlee.cosmosapi.security;

import dev.cmlee.cosmosapi.common.AppProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


@Component
@RequiredArgsConstructor
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	private final AppProperties appProperties;

	public String generateToken() {
		Date now = new Date();
		Calendar timeout = Calendar.getInstance();
		timeout.setTimeInMillis(now.getTime() + appProperties.getJwtExpirationInMs());

		HashMap<String, Object> payload = new HashMap<>();
		payload.put("uid", "1");

		return "";
	}
}
