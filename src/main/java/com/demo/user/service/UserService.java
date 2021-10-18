package com.demo.user.service;

import com.demo.user.dto.UserDto;
import com.demo.user.entity.Authority;
import com.demo.user.entity.User;
import com.demo.exception.DuplicateMemberException;
import com.demo.user.repository.UserRepository;
import com.demo.util.SecurityUtil;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * 회원가입
	 * @param userDto
	 * @return User
	 */
	@Transactional
	public User signup(UserDto userDto) {
		// username이 DB에 존재하지 않으면 Authority와 User 정보를 생성해서 userRepository.save 메소드를 통해 DB에 저장
		if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
			throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
		}

		Authority authority = Authority.builder()
			.authorityName("ROLE_USER")
			.build();

		User user = User.builder()
			.username(userDto.getUsername())
			.password(passwordEncoder.encode(userDto.getPassword()))
			.nickname(userDto.getNickname())
			.authorities(Collections.singleton(authority))
			.activated(true)
			.build();

		return userRepository.save(user);
	}

	/**
	 * 파라미터로 받은 username을 기준으로 정보를 가져옴
	 * @param username
	 * @return
	 */
	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities(String username) {
		return userRepository.findOneWithAuthoritiesByUsername(username);
	}

	/**
	 * SecurityContext에 저장된 username의 정보만 가져옴
	 * @return
	 */
	@Transactional(readOnly = true)
	public Optional<User> getMyUserWithAuthorities() {
		return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
	}
}