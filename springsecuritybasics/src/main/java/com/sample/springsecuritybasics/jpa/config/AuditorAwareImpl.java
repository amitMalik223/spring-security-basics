package com.sample.springsecuritybasics.jpa.config;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
//		return Optional.of("Naresh");
		// Can use Spring Security to return currently logged in user
		return Optional.of(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName());
		// Can use Spring Security to return currently logged in user
	}

}
