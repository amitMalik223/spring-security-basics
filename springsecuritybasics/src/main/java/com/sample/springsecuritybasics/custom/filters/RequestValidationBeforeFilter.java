package com.sample.springsecuritybasics.custom.filters;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

public class RequestValidationBeforeFilter {
//implements Filter {
//
//	public static final String AUTHENTICATION_BASIC_SCHEME = "Basic";
//	private Charset credentialsCharset = StandardCharsets.UTF_8;
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//
//		String header = req.getHeader(HttpHeaders.AUTHORIZATION);
//		if (Objects.nonNull(header)) {
//			header = header.trim();
//			if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_BASIC_SCHEME)) {
//				byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
//				byte[] decoded = null;
//				try {
//					decoded = Base64.getDecoder().decode(base64Token);
//					String token = new String(decoded, getCredentialsCharset(req));
//					int delim = token.indexOf(":");
//					if (delim == -1) {
//						throw new BadCredentialsException("Invalid basic authentication token");
//					}
//					String email = token.substring(0, delim);
//					if (email.toLowerCase().contains("test")) {
//						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//						return;
//					}
//				} catch (IllegalArgumentException e) {
//					throw new BadCredentialsException("Failed to decode basic authentication token");
//				}
//			}
//		}
//		chain.doFilter(request, response);
//	}
//
//	protected Charset getCredentialsCharset(HttpServletRequest request) {
//		return getCredentialsCharset();
//	}
//
//	public Charset getCredentialsCharset() {
//		return this.credentialsCharset;
//	}

}
