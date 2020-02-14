package dev.cmlee.cosmosapi.security;

import dev.cmlee.cosmosapi.common.AppProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


@Component
@RequiredArgsConstructor
public class JwtProvider {

	private final AppProperties appProperties;

	public String generateToken() {
		Date now = new Date();
		Calendar timeout = Calendar.getInstance();
		timeout.setTimeInMillis(now.getTime() + appProperties.getJwtExpirationInMs());

		HashMap<String, Object> payload = new HashMap<>();
		payload.put("uid", "1");
		payload.put("plt", "cosmos");
		payload.put("rol", "USER");

		return Jwts.builder()
				.setSubject("cosmos")
				.setIssuer("maxx")
				.setIssuedAt(new Date())
				.setExpiration(new Date(timeout.getTimeInMillis()))
				.addClaims(payload)
				.signWith(SignatureAlgorithm.HS512, appProperties.getJwtSecret())
				.compact();
	}
}
