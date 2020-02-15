package dev.cmlee.cosmosapi.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfileAccountDto {

	@JsonProperty("email_needs_agreement")
	private boolean emailNeedsAgreement;

	@JsonProperty("has_email")
	private boolean hasEmail;

	@JsonProperty("is_email_valid")
	private boolean isEmailValid;

	@JsonProperty("is_email_verified")
	private boolean isEmailVerified;

	@JsonProperty("email")
	private String email;

	@JsonProperty("has_age_range")
	private boolean hasAgeRange;

	@JsonProperty("age_range")
	private String ageRange;

	@JsonProperty("has_birthday")
	private boolean hasBirthday;

	@JsonProperty("birthday")
	private String birthday;

	@JsonProperty("has_gender")
	private boolean hasGender;

	@JsonProperty("gender")
	private String gender;
}
