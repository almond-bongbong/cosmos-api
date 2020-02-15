package dev.cmlee.cosmosapi.api.auth.controller;

import dev.cmlee.cosmosapi.api.auth.dto.KakaoProfileDto;
import dev.cmlee.cosmosapi.exception.UnAuthorizedException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final RestTemplateBuilder builder;

	@PostMapping
	public ResponseEntity<Void> auth() {
		return ResponseEntity.ok().build();
	}

	@PostMapping("/kakao")
	@RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<KakaoProfileDto> signKakao(@RequestParam("accessToken") String accessToken) {
		RestTemplate template = builder
				.setConnectTimeout(Duration.ofSeconds(3))
				.build();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");
		HttpEntity<Void> request = new HttpEntity<>(headers);
		KakaoProfileDto kakaoProfileDto = template.postForObject("https://kapi.kakao.com/v2/user/me", request, KakaoProfileDto.class);
		Optional<KakaoProfileDto> optionalKakaoProfileDto = Optional.ofNullable(kakaoProfileDto);

		return ResponseEntity.ok(optionalKakaoProfileDto.orElseThrow(UnAuthorizedException::new));
	}
}
