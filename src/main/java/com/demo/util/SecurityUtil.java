package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

	private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

	private SecurityUtil() {
	}

	/**
	 * SecurityContext의 Authentication객체를 이용해 userid를 리턴해준다.
	 * @return userid
	 */
	public static Optional<String> getCurrentUserid() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			logger.debug("Security Context에 인증 정보가 없습니다.");
			return Optional.empty();
		}

		String userid = null;
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
			userid = springSecurityUser.getUsername();
		} else if (authentication.getPrincipal() instanceof String) {
			userid = (String) authentication.getPrincipal();
		}

		return Optional.ofNullable(userid);
	}
}