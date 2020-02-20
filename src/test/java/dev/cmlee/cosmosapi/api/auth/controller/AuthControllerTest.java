package dev.cmlee.cosmosapi.api.auth.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

	private WebDriver driver;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		driver = new ChromeDriver();
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

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
	public void getKakaoCode() throws Exception {
		String code = getKakaoLoginCode();

		assertThat(code).isNotEmpty();
	}

	private String getKakaoLoginCode() throws Exception {
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

		return queryParams.get("code").get(0);
	}
}