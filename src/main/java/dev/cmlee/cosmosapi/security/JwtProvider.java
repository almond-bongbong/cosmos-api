package dev.cmlee.cosmosapi.security;

import dev.cmlee.cosmosapi.constants.AppProperties;
import io.jsonwebtoken.*;
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

	public boolean validateToken(String jwt) {
		try {
			Jwts.parser().setSigningKey(appProperties.getJwtSecret()).parseClaimsJws(jwt);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
