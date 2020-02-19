package dev.cmlee.cosmosapi.api.account.domain;

import dev.cmlee.cosmosapi.api.common.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Account extends BaseTimeEntity {

	@Id @GeneratedValue
	@Column(name = "account_id")
	private Long id;

	@Column(name = "email")
	private String email;
}
