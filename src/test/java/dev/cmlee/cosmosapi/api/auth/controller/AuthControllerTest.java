package dev.cmlee.cosmosapi.api.auth.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void authKakaoFail() throws Exception {
		String body = new JSONObject()
				.put("accessToken", "invalidToken")
				.toString();

		mockMvc.perform(post("/auth/kakao")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("message").exists());
	}
}