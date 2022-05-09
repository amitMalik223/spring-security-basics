package com.sample.springsecuritybasics.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * We will need to create a bean of type AuditorAware and will also need to
 * enable JPA auditing by specifying @EnableJpaAuditing on one of our
 * configuration class. @EnableJpaAuditing accepts one argument auditorAwareRef
 * where we need to pass the name of the AuditorAware bean.
 * 
 * 
 * @author Amit Malik
 *
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}
}
