package dev.cmlee.cosmosapi.api.auth.service;

import dev.cmlee.cosmosapi.api.auth.dto.KakaoProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoService {

	private final RestTemplateBuilder restBuilder;

	public Optional<KakaoProfileDto> getProfile(String accessToken) {
		RestTemplate template = restBuilder
				.setConnectTimeout(Duration.ofSeconds(3))
				.build();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");
		HttpEntity<Void> request = new HttpEntity<>(headers);

		System.out.println("request : " + request);

		try {
			KakaoProfileDto kakaoProfileDto = template.postForObject("https://kapi.kakao.com/v2/user/me", request, KakaoProfileDto.class);
			return Optional.ofNullable(kakaoProfileDto);
		} catch (HttpClientErrorException e) {
			return Optional.empty();
		}
	}
}
