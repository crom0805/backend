package com.demo.user.controller;

import com.demo.user.dto.LoginDto;
import com.demo.user.dto.TokenDto;
import com.demo.jwt.JwtFilter;
import com.demo.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

		// loginDto의 username과 password를 이용해 UsernamePasswordAuthenticationToken 객체생성
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

		// authenticate 메소드가 실행될때 CustomUserDetailsService.loadUserByUsername 메소드가 실행됨
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// authentication 객체를 SecurityContext에 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// JWT Token 생성
		String jwt = tokenProvider.createToken(authentication);

		HttpHeaders httpHeaders = new HttpHeaders();
		// "Bearer" : 헤더의 Authorization 필드에 JWT 혹은 OAuth 토큰을 사용한다는 인증타입을 의미함. 수정하지 말것.
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

		return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
	}
}