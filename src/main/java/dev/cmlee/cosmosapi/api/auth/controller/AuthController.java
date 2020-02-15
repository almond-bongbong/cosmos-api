package dev.cmlee.cosmosapi.api.auth.controller;

import dev.cmlee.cosmosapi.api.auth.dto.KakaoProfileDto;
import dev.cmlee.cosmosapi.api.auth.service.KakaoService;
import dev.cmlee.cosmosapi.exception.UnAuthorizedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final KakaoService kakaoService;

	@PostMapping
	public ResponseEntity<Void> auth() {
		return ResponseEntity.ok().build();
	}

	@PostMapping("/kakao")
	public ResponseEntity<KakaoProfileDto> signKakao(@RequestBody @Valid KakaoSignRequest request) {
		String accessToken = request.getAccessToken();
		return ResponseEntity.ok(kakaoService.getProfile(accessToken).orElseThrow(UnAuthorizedException::new));
	}

	@Getter
	private static class KakaoSignRequest {

		@NotEmpty
		private String accessToken;
	}
}
