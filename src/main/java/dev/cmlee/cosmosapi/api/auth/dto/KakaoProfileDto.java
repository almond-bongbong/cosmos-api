package dev.cmlee.cosmosapi.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfileDto {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("properties")
	private KakaoProfilePropertiesDto properties;

	@JsonProperty("kakao_account")
	private KakaoProfileAccountDto kakaoAccount;
}

