package dev.cmlee.cosmosapi.api.file;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class File {

	@Id @GeneratedValue
	@Column(name = "file_id")
	private Long id;
}
