package com.demo.user.repository;

import com.demo.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	// findOneWithAuthoritiesByUserid : userid를 기준으로 User정보를 가져올때 권한정보도 같이 가져옴
	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByUserid(String userid);
}
