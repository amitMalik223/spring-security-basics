package com.sample.springsecuritybasics.custom.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

	@Value("${jwt.expiration.time}")
	private Long expirationTime;

	@Value("${jwt.secret.key}")
	private String secretKey;

	@Value("${jwt.header.key}")
	private String headerKey;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug(
				"inside JWTTokenValidatorFilter : inside doFilterInternal ---------------------------------------------------- ");
		log.debug("inside JWTTokenValidatorFilter : inside doFilterInternal : headerKey : {} ", headerKey);
		boolean deviceHeaderExists = request.getHeader(headerKey) != null;
		log.debug("inside JWTTokenValidatorFilter : inside doFilterInternal : deviceHeaderExists : {} ",
				deviceHeaderExists);
		if (Objects.nonNull(request)) {
			String jwt = request.getHeader(headerKey);
			log.debug("inside JWTTokenValidatorFilter : inside doFilterInternal : jwt : {} ", jwt);
			if (null != jwt) {
				try {
					SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

					Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
					String username = String.valueOf(claims.get("username"));
					log.debug("inside JWTTokenValidatorFilter : inside doFilterInternal : username : {} ", username);
					String authorities = (String) claims.get("authorities");
					log.debug("inside JWTTokenValidatorFilter : inside doFilterInternal : authorities : {} ",
							authorities);
					Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
							AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
					SecurityContextHolder.getContext().setAuthentication(auth);
				} catch (Exception e) {
					throw new BadCredentialsException("Invalid Token received!");
				}

			}
			chain.doFilter(request, response);
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		log.debug(
				"inside JWTTokenValidatorFilter : inside shouldNotFilter : condition - request.getServletPath().equalsIgnoreCase(/api/v1/login) : {} ",
				request.getServletPath().equalsIgnoreCase("/api/v1/login"));
		return request.getServletPath().equalsIgnoreCase("/api/v1/login");
	}

}
