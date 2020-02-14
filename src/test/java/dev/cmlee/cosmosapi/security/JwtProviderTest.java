package dev.cmlee.cosmosapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

	@Autowired
	JwtProvider jwtProvider;

	@Test
	public void jwtCreateTest() {
		String token = jwtProvider.generateToken();
		assertNotNull(token);
	}
}