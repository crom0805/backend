package com.demo.user.controller;

import com.demo.user.dto.UserDto;
import com.demo.user.entity.User;
import com.demo.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.signup(userDto));
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")		// ROLE_USER와 ROLE_ADMIN 모두 접근가능
	public ResponseEntity<User> getMyUserInfo(HttpServletRequest request) {
		return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')")			// ROLE_ADMIN만 접근가능
	public ResponseEntity<User> getUserInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
	}
}