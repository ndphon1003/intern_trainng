package com.trainng.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api/users")
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	@GetMapping("/hello")
	public String hello(@RequestHeader("X-User-Id") String userId,
						@RequestHeader("X-User-Name") String username,
						@RequestHeader("X-User-Role") String role) {
		return "Hello " + username + "! Id=" + userId + ", Role=" + role;
	}

}
