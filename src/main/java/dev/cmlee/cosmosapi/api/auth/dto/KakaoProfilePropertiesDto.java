package dev.cmlee.cosmosapi.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoProfilePropertiesDto {

	@JsonProperty("nickname")
	private String nickname;

	@JsonProperty("profile_image")
	private String profileImage;

	@JsonProperty("thumbnail_image")
	private String thumbnailImage;
}
