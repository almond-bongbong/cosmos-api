package dev.cmlee.cosmosapi.api.auth.controller;

import dev.cmlee.cosmosapi.common.BaseControllerTest;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends BaseControllerTest {

	@Autowired
	RestTemplate restTemplate;

	@Test
	@DisplayName("카카오 로그인 필수 파라미터 누락")
	public void authKakaoRequiredParamFail() throws Exception {
		mockMvc.perform(post("/auth/kakao")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("message").exists());
	}

	@Test
	@DisplayName("카카오 로그인 실패")
	public void authKakaoInvalidTokenFail() throws Exception {
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

	@Test
	@DisplayName("카카오 로그인")
	public void authKakao() throws Exception {
		String code = getKakaoLoginCode();
		String accessToken = getKakaoAccessTokenByCode(code);
		String body = new JSONObject()
				.put("accessToken", accessToken)
				.toString();

		mockMvc.perform(post("/auth/kakao")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("accessToken").exists())
			.andExpect(jsonPath("refreshToken").exists());
	}

	private String getKakaoLoginCode() throws Exception {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		WebDriver driver = new ChromeDriver(options);

		driver.get("https://kauth.kakao.com/oauth/authorize?client_id=c6d95eb1aec9adef110317b2b9d6ea6c&redirect_uri=http://localhost:8080/oauth/kakao&response_type=code");

		WebElement inputEmail = driver.findElement(By.id("id_email_2"));
		WebElement inputPassword = driver.findElement(By.id("id_password_3"));
		WebElement submitLogin = driver.findElement(By.className("submit"));

		assertThat(inputEmail).isNotNull();
		assertThat(inputPassword).isNotNull();
		assertThat(submitLogin).isNotNull();

		inputEmail.sendKeys("dlcndaks12@naver.com");
		inputPassword.sendKeys("dldmswk!12");
		submitLogin.click();

		Thread.sleep(500);

		if (!driver.getCurrentUrl().contains("/oauth/kakao")) {
			WebElement agreeCheckbox = driver.findElement(By.className("lab_all"));
			WebElement btnSubmit = driver.findElement(By.id("acceptButton"));

			assertThat(agreeCheckbox).isNotNull();
			assertThat(btnSubmit).isNotNull();

			agreeCheckbox.click();
			btnSubmit.click();

			Thread.sleep(500);
		}

		MultiValueMap<String, String> queryParams = UriComponentsBuilder
				.fromUriString(driver.getCurrentUrl())
				.build()
				.getQueryParams();

		driver.quit();

		return queryParams.get("code").get(0);
	}

	private String getKakaoAccessTokenByCode(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("grant_type", "authorization_code");
		requestBody.add("client_id", "c6d95eb1aec9adef110317b2b9d6ea6c");
		requestBody.add("redirect_uri", "http://localhost:8080/oauth/kakao");
		requestBody.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
		KakaoTokenResponse response = restTemplate.postForObject("https://kauth.kakao.com/oauth/token", request, KakaoTokenResponse.class);

		if (response != null) {
			return response.getAccess_token();
		} else {
			return "";
		}
	}

	private static class KakaoTokenResponse {
		private String access_token;

		public KakaoTokenResponse() {
		}

		public KakaoTokenResponse(String access_token) {
			this.access_token = access_token;
		}

		public String getAccess_token() {
			return access_token;
		}
	}
}