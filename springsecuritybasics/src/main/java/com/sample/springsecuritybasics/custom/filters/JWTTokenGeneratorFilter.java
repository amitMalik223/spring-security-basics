package com.sample.springsecuritybasics.custom.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

	@Value("${jwt.expiration.time}")
	private Long expirationTime;

	@Value("${jwt.secret.key}")
	private String secretKey;

	@Value("${jwt.header.key}")
	private String headerKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.debug(
				"inside JWTTokenGeneratorFilter : inside doFilterInternal ---------------------------------------------------- ");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (Objects.nonNull(authentication)) {
			SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
			ZoneId defaultZoneId = ZoneId.systemDefault();
			LocalDate localDate = LocalDate.now();
			Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
			log.debug(
					"inside JWTTokenGeneratorFilter : inside doFilterInternal : Generate JWT Token for username : {} ",
					authentication.getName());
			String jwt = Jwts.builder().setIssuer("Amit Malik").setSubject("JWT Token")
					.claim("username", authentication.getName())
					.claim("authorities", populateAuthorities(authentication.getAuthorities())).setIssuedAt(date)
					.setExpiration(new Date(date.getTime() + expirationTime)).signWith(key).compact();
			log.debug("inside JWTTokenGeneratorFilter : inside doFilterInternal : JWT Token : {} ", jwt);
			response.setHeader(headerKey, jwt);

		}
		filterChain.doFilter(request, response);
	}

	/**
	 * We have override this method and implemented a logic that my filter should be
	 * executed only during the login request inside my application
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		log.debug(
				"inside JWTTokenGeneratorFilter : inside shouldNotFilter ---------------------------------------------------- ");
		return !request.getServletPath().equalsIgnoreCase("/api/v1/login");
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		log.debug(
				"inside JWTTokenGeneratorFilter : inside populateAuthorities ---------------------------------------------------- ");
		List<String> authoritiesSet = new ArrayList<>();
		for (GrantedAuthority authority : authorities) {
			log.debug("inside JWTTokenGeneratorFilter : inside populateAuthorities : authority : {} ",
					authority.getAuthority());
			authoritiesSet.add(authority.getAuthority());
		}
		return String.join(",", authoritiesSet);
	}
}
