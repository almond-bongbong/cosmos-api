package dev.cmlee.cosmosapi.api.common.domain;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class BaseEntity extends BaseTimeEntity {

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "deleted_by")
	private Long deletedBy;
}
