package dev.cmlee.cosmosapi.api.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@PostMapping("/")
	public ResponseEntity auth() {
		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup")
	public ResponseEntity signup() {


		return ResponseEntity.ok().build();
	}
}
