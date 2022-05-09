package com.sample.springsecuritybasics.config;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.sample.springsecuritybasics.custom.filters.AuthoritiesLoggingAfterFilter;
import com.sample.springsecuritybasics.custom.filters.AuthoritiesLoggingAtFilter;
import com.sample.springsecuritybasics.custom.filters.JWTTokenGeneratorFilter;
import com.sample.springsecuritybasics.custom.filters.JWTTokenValidatorFilter;
import com.sample.springsecuritybasics.custom.filters.RequestValidationBeforeFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * To be configured first or initially
 * 
 * @author Amit Malik
 * 
 *         WebSecurityConfigurerAdapter - this is the base class for all the
 *         spring security configuration
 *
 */
@Configuration
@Slf4j
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Suppose we have already defined one user in application.properties and we
	 * have only user then this config() method can work properly
	 * 
	 * 
	 * Here are some APIs that need to be secured and some APIs need no security
	 * e.g. /myAccount - Secured /myBalance - Secured /myLoan - Secured /myCards -
	 * Secured /notices - Not Secured /contact - Not Secured
	 */
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		log.debug(
				"Inside ProjectSecurityConfig : inside first configure method ================================================");
		/**
		 * Default configuration of the configure() method - it means accept any request
		 * but you must authenticate the request
		 */
		/*
		 * httpSecurity.authorizeRequests((requests) ->
		 * requests.anyRequest().authenticated()); httpSecurity.formLogin();
		 * httpSecurity.httpBasic();
		 */
//-------------------------------------------------------------------------------------------------------------------------------------------------------

		/**
		 * The APIs that are marked as authenticated() are secured and we need login
		 * credentials to access them. But, The APIs that are marked as permitAll() are
		 * not secured so they can be invoked without any login credentials
		 */

		/*
		 * httpSecurity.authorizeRequests().antMatchers("/api/v1/myAccount").
		 * authenticated()
		 * .antMatchers("/api/v1/myBalance").authenticated().antMatchers(
		 * "/api/v1/myLoan").authenticated()
		 * .antMatchers("/api/v1/myCards").authenticated().antMatchers("/api/v1/notices"
		 * ).permitAll()
		 * .antMatchers("/api/v1/contact").permitAll().and().formLogin().and().httpBasic
		 * ();
		 */
//--------------------------------------------------------------------------------------------------------------------------------------------------------		

		/**
		 * Configuration to deny all the requests
		 * 
		 * Suppose there is a requirement that my server should not accept any request
		 * even if they have proper credentials associated to them
		 */
		/*
		 * httpSecurity.authorizeRequests((requests) ->
		 * requests.anyRequest().denyAll()); httpSecurity.formLogin();
		 * httpSecurity.httpBasic();
		 */
//--------------------------------------------------------------------------------------------------------------------------------------------------------

		/**
		 * Configuration to permit all the requests
		 * 
		 * Suppose there is a requirement that my server should accept any/all request
		 * even if they have/haven't proper credentials associated to them
		 */

		/*
		 * httpSecurity.authorizeRequests((requests) ->
		 * requests.anyRequest().permitAll()); httpSecurity.formLogin();
		 * httpSecurity.httpBasic();
		 */
//-----------------------------------------------------------------------------------------------------------------------------------------------------------		

		/**
		 * CORS issue configuration
		 * 
		 * To resolve the issue of CORS we have to apply the following configurations.
		 * 
		 * CORS :- Cross Origin Resource Sharing :- Means when the UI is deployed on
		 * other host/port and server is deployed on other host/port then the browser
		 * will not allow to access the server and it will show the CORS issue for
		 * security purpose only. So this issue can be resolved with at UI/UX side also
		 * and can also be resolved from server/back-end side. It is prefer to resolve
		 * from back-end side because we can do some more configurations at back-end
		 * side like 1. We can define the host/port which can access our server. 2. We
		 * can configure that which HTTP methods are allowed etc. Please follow below
		 * code for resolving and configuring CORS issue.
		 */
		/*
		 * httpSecurity.cors().configurationSource(new CorsConfigurationSource() {
		 * 
		 * @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
		 * request) { CorsConfiguration config = new CorsConfiguration();
		 * config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		 * config.setAllowedMethods(Collections.singletonList("*"));
		 * config.setAllowCredentials(true);
		 * config.setAllowedHeaders(Collections.singletonList("*"));
		 * config.setMaxAge(3600L); return config; }
		 * }).and().authorizeRequests().antMatchers("/api/v1/myAccount").authenticated()
		 * .antMatchers("/api/v1/myBalance")
		 * .authenticated().antMatchers("/api/v1/myLoan").authenticated().antMatchers(
		 * "/api/v1/myCards")
		 * .authenticated().antMatchers("/api/v1/notices").permitAll().antMatchers(
		 * "/api/v1/contact").permitAll() .and().formLogin().and().httpBasic();
		 */
//----------------------------------------------------------------------------------------------------------------------------------------------------------

		/**
		 * CSRFs issue configuration
		 * 
		 * To resolve the issue of CSRFs we have to apply the following configurations.
		 * 
		 * CSRFs :- Cross Site Request Forgery :- Cross-Site Request Forgery (CSRF) is
		 * an attack that forces authenticated users to submit a request to a Web
		 * application against which they are currently authenticated. CSRF attacks
		 * exploit the trust a Web application has in an authenticated user.
		 * 
		 * To resolve this issues we have 2 ways :- 1. By disabling the CSRFs i.e.
		 * csrf().disable()
		 * 
		 * httpSecurity.cors().configurationSource(new CorsConfigurationSource() {
		 * 
		 * @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
		 *           request) { CorsConfiguration config = new CorsConfiguration();
		 *           config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		 *           config.setAllowedMethods(Collections.singletonList("*"));
		 *           config.setAllowCredentials(true);
		 *           config.setAllowedHeaders(Collections.singletonList("*"));
		 *           config.setMaxAge(3600L); return config; } }).and().csrf().disable()
		 *           .authorizeRequests().antMatchers("/api/v1/myAccount").authenticated()
		 *           .antMatchers("/api/v1/myBalance")
		 *           .authenticated().antMatchers("/api/v1/myLoan").authenticated().antMatchers(
		 *           "/api/v1/myCards")
		 *           .authenticated().antMatchers("/api/v1/notices").permitAll().antMatchers(
		 *           "/api/v1/contact").permitAll()
		 *           .and().formLogin().and().httpBasic();
		 * 
		 *           Note :- But 1st way is not recommended in the production phase when
		 *           your application is open for public use so the 2nd way is given
		 *           below.
		 * 
		 *           2. In this way we will handle CSRFs issue by maintaining a CSRF
		 *           token as shown in the below code.
		 * 
		 *           Note :- This CSRF token will be put in the UI so that all the
		 *           operations like PST, PUT, DELETE (state changing operations) will
		 *           be successful as long as the tokens are same which are being
		 *           maintained by both UI and back-end.
		 * 
		 *           httpSecurity.cors().configurationSource(new
		 *           CorsConfigurationSource() {
		 * 
		 * @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
		 *           request) { CorsConfiguration config = new CorsConfiguration();
		 *           config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		 *           config.setAllowedMethods(Collections.singletonList("*"));
		 *           config.setAllowCredentials(true);
		 *           config.setAllowedHeaders(Collections.singletonList("*"));
		 *           config.setMaxAge(3600L); return config; }
		 *           }).and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().authorizeRequests()
		 *           .antMatchers("/api/v1/myAccount").authenticated().antMatchers("/api/v1/myBalance").authenticated()
		 *           .antMatchers("/api/v1/myLoan").authenticated().antMatchers("/api/v1/myCards").authenticated()
		 *           .antMatchers("/api/v1/notices").permitAll().antMatchers("/api/v1/contact").permitAll().and().formLogin()
		 *           .and().httpBasic();
		 * 
		 *           Note :- There are some APIs which need not be authenticated like
		 *           contact us etc but CSRF will not allow these API as we are passing
		 *           CSRF token in every request headers so to access those APIs we have
		 *           to do some changes which are given below i.e.
		 *           ignoringAntMatchers("/api/v1/contacts") will ignore the CSRF
		 *           enforcement on the contact but for other APIs CSRFs is applied.
		 * 
		 *           Note :- When you are using JWT then there is no need to CSRFs Token
		 *           so just remove that if JWT Authentication and Authorization is
		 *           implemented.
		 */
		/*
		 * httpSecurity.cors().configurationSource(new CorsConfigurationSource() {
		 * 
		 * @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
		 * request) { CorsConfiguration config = new CorsConfiguration();
		 * config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		 * config.setAllowedMethods(Collections.singletonList("*"));
		 * config.setAllowCredentials(true);
		 * config.setAllowedHeaders(Collections.singletonList("*"));
		 * config.setMaxAge(3600L); return config; }
		 * }).and().csrf().ignoringAntMatchers("/api/v1/contact")
		 * .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().
		 * authorizeRequests()
		 * .antMatchers("/api/v1/myAccount").authenticated().antMatchers(
		 * "/api/v1/myBalance").authenticated()
		 * .antMatchers("/api/v1/myLoan").authenticated().antMatchers("/api/v1/myCards")
		 * .authenticated()
		 * .antMatchers("/api/v1/notices").permitAll().antMatchers("/api/v1/contact").
		 * permitAll().and().formLogin() .and().httpBasic();
		 */
//---------------------------------------------------------------------------------------------------------------------------------------------------------

		/**
		 * 
		 * Custom Filters
		 * 
		 * Now lets configure filters. But why we need filters in Spring Security?
		 * 
		 * A filter is a component which receives requests, process its logic and
		 * hand-over to the next filter in the chain
		 * 
		 * Spring security uses the filter chain to execute most of the security
		 * features. In this, we will look at the Spring security filters chain. In a
		 * web application, we drive Spring security through the servlet filters.
		 * Servlet filters works by intercepting the request before it reaches to the
		 * actual resource (e.g. Spring controller).
		 * 
		 * Some use cases of filters like why we need custom filters as Spring Security
		 * already provides some filters.
		 * 
		 * Lot of time, we will have situations where we need to perform some house
		 * keeping activities during the authentication and authorization flow. Few such
		 * examples are :- (1) Input validation like suppose our username or email some
		 * text like test/demo then we have to filter such credential and not allowed
		 * user to login. (2) Tracing, Auditing and reporting. (3) Logging of input like
		 * IP address etc (4) Encryption and decryption (5) Multi-factor authentication
		 * 
		 * All such requirements can be handled using HTTP filters inside spring
		 * security.
		 * 
		 * We already saw some in-built filters of spring security framework like
		 * Authentication Filter, Authorization Filter, CSRF Filter, CORS Filter above.
		 * 
		 * To see/check filters registered in the filter chain in the
		 * console/logs/terminal then we have to do some configuration which is as
		 * follows :- (1) Annotate the main class with "@EnableWebSecurity(debug=true)"
		 * (2) "logging.level.org.springframework.security.web.FilterChainProxy=DEBUG"
		 * in application.properties. After that you can start the server and try to
		 * login the application and check console/logs/terminal, there is the filter
		 * chain mentioned.
		 * 
		 * Now lets create some custom filters.
		 * 
		 * Note :- All the filters are mentioned in the configure method as shown below.
		 * 
		 * Note :- We can define the order of custom filters like (1) Adding custom
		 * filters using addFilterBefore() method provided by Spring Security (2) Adding
		 * custom filters using addFilterAfter() method provided by Spring Security
		 * (3)Adding custom filters using addFilterAt() method provided by Spring
		 * Security
		 */
		/*
		 * httpSecurity.cors().configurationSource(new CorsConfigurationSource() {
		 * 
		 * @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
		 * request) { CorsConfiguration config = new CorsConfiguration();
		 * config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		 * config.setAllowedMethods(Collections.singletonList("*"));
		 * config.setAllowCredentials(true);
		 * config.setAllowedHeaders(Collections.singletonList("*"));
		 * config.setMaxAge(3600L); return config; }
		 * }).and().csrf().ignoringAntMatchers("/api/v1/contact")
		 * .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
		 * .addFilterBefore(new RequestValidationBeforeFilter(),
		 * BasicAuthenticationFilter.class) .addFilterAfter(new
		 * AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
		 * .addFilterAt(new AuthoritiesLoggingAtFilter(),
		 * BasicAuthenticationFilter.class).authorizeRequests()
		 * .antMatchers("/api/v1/myAccount").authenticated().antMatchers(
		 * "/api/v1/myBalance").authenticated()
		 * .antMatchers("/api/v1/myLoan").authenticated().antMatchers("/api/v1/myCards")
		 * .authenticated()
		 * .antMatchers("/api/v1/notices").permitAll().antMatchers("/api/v1/contact").
		 * permitAll().and().formLogin() .and().httpBasic();
		 */
//--------------------------------------------------------------------------------------------------------------------------------------------------------

		/**
		 * JWT Token configuration
		 * 
		 * When you are using JWT authentication/authorization then there is no need of
		 * CSRFs token or CSRF applied so you can disable CSRF because it will become
		 * the complex task for your application to manage 2 tokens
		 * 
		 * Note :- The line
		 * "sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)"
		 * tells spring security that do not generate any type of token I will manage
		 * that by my own
		 * 
		 * Note :- Also we have to add a new line inside our CORS configuration which
		 * indicates that I want to expose my headers from my back-end
		 * services/application to outside front-end applications or any application
		 * that is consuming my back-end services with the name "Authorization" or
		 * whatever name we will give
		 * 
		 * So we have done total 3 changes
		 */
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors()
				.configurationSource(new CorsConfigurationSource() {

					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setExposedHeaders(Collections.singletonList("Authorization"));
						config.setMaxAge(3600L);
						return config;
					}
				}).and().csrf().disable()
				.addFilterBefore(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class).authorizeRequests()
				.antMatchers("/api/v1/myAccount").authenticated().antMatchers("/api/v1/myBalance").authenticated()
				.antMatchers("/api/v1/myLoan").authenticated().antMatchers("/api/v1/myCards").authenticated()
				.antMatchers("/api/v1/roles/**").permitAll().antMatchers("/api/v1/notices").permitAll()
				.antMatchers("/swagger-ui.html/**").permitAll().antMatchers("/api/v1/contact").permitAll().and()
				.formLogin().and().httpBasic();
	}

	/**
	 * Suppose we have many user whose credentials are already known and who can
	 * access our application then this config() method can work properly
	 * 
	 * @throws Exception
	 */
//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		log.debug(
//				"Inside ProjectSecurityConfig : inside second configure method =========================================");
	// Lets try to configure multiple users whose credentials are already known
	/*
	 * Note :- When using this method, user credentials that are defined in the
	 * application properties will not work because we have used in memory
	 * authentication which means credentials defined below are saved in spring
	 * container for In Memory Authentication
	 * 
	 * Note :- If we don't provide password encoder then it will throw the exception
	 * as "No PasswordEncoder mapped"
	 */
//		authenticationManagerBuilder.inMemoryAuthentication().withUser("admin").password("Admin@123")
//				.authorities("admin").and().withUser("user1").password("User1@123").authorities("read").and()
//				.withUser("user2").password("User2@123").authorities("read").and()
//				.passwordEncoder(NoOpPasswordEncoder.getInstance());
//	}

	/**
	 * Suppose we have many user whose credentials are already known and who can
	 * access our application then this config() method can work properly
	 * 
	 * Configuring users using InMemoryUserDetailsManager class for saving user
	 * details or credentials
	 * 
	 * @throws Exception
	 */
//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		log.debug(
//				"Inside ProjectSecurityConfig : inside third configure method =========================================");
	// Lets try to configure multiple users whose credentials are already known
	// using InMemoryUserDetailsManager class
	/*
	 * Note :- When using this method, user credentials that are defined in the
	 * application properties will not work because we have used in memory
	 * authentication which means credentials defined below are saved in spring
	 * container for In Memory Authentication
	 * 
	 * Note :- If we don't provide password encoder then it will throw the exception
	 * as "No PasswordEncoder mapped" so either provider the password encoder or
	 * create a @Bean method which will return password encoder
	 * (methodName-passwordEncoder() )
	 * 
	 * Note :- User :- it is the default implementation provided by the spring
	 * security which adheres(जुड़ जाना) to the UserDetails interface schema
	 * 
	 * Note :- If you don't want to create your own custom user class then you can
	 * go with predefined User class because it have all the basic required fields
	 * as shown below.
	 * 
	 * Note :- UserDetails if the schema/contract/blueprint which is maintain by the
	 * spring security framework to represent actual user who is trying to access
	 * your application. If you want to customize the user details then you can
	 * implement UserDetails interface to customize the Users according to your
	 * requirement.
	 * 
	 * NOTE :- UserDetailsService interface :- This interface contains only one
	 * method loadByUserName(String username) which will load the user details by
	 * username from the persistent layer like DB/LDAP. But if you have a scenario
	 * where your application also has to handle creating, updating or deleting the
	 * user details then UserDetailsService will not be enough in all such scenarios
	 * you should go ahead and implements UserDetailsManager interface which
	 * internally extends UserDetailsService as well.
	 * 
	 * Note :- InMemoryUserDetailsManager we can say that it is a UserDetailsService
	 * below it internally implement UserDetailsManager and UserDetailsManager
	 * internally extends UserDetailsService. This uses Map to save/create, update
	 * or delete etc the user details.
	 * 
	 * Note :- We also have JDBCUserDetailsManager which save the user details in
	 * database and it have some advanced features also. Note :- We also have
	 * LDAPUserDetailsManager
	 * 
	 * You can also create your own Custom UserDetailsManager
	 * 
	 */
	/**
	 * InMemoryUserDetailsManager inMemoryUserDetailsManager = new
	 * InMemoryUserDetailsManager(); UserDetails adminDetail =
	 * User.withUsername("admin").password("Admin@123").authorities("admin").build();
	 * UserDetails user1Detail =
	 * User.withUsername("user1").password("User1@123").authorities("read").build();
	 * UserDetails user2Detail =
	 * User.withUsername("user2").password("user2@123").authorities("read").build();
	 * inMemoryUserDetailsManager.createUser(adminDetail);
	 * inMemoryUserDetailsManager.createUser(user1Detail);
	 * inMemoryUserDetailsManager.createUser(user2Detail);
	 * authenticationManagerBuilder.userDetailsService(inMemoryUserDetailsManager);
	 */
	// }

	/**
	 * 
	 * @return PasswordEncoder instance
	 * 
	 *         Note :- NoOpPasswordEncoder is deprecated so we can use this only in
	 *         the development phase not in the production or staging phase
	 */
	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return
	 * NoOpPasswordEncoder.getInstance(); }
	 */

	/**
	 * 
	 * @return PasswordEncoder instance
	 * 
	 *         Note :- BCryptPasswordEncoder is very powerful as compared to
	 *         NoOpPasswordEncoder, StandardPasswordEncoder and
	 *         Pbkdf2PasswordEncoder so we can use this in the development as well
	 *         as in the production or staging phase
	 * 
	 *         Note :- If you save raw password in database and apply/use
	 *         BCryptPasswordEncoder then at the time of login when Spring Security
	 *         try to match both the passwords(user password and database saved
	 *         password) then it will give the warning in logs as Saved Encoded
	 *         password does not look like BCrypt and return false;
	 * 
	 *         Note :- to check both the passwords(user entered password and
	 *         database saved encrypted password) you can check on the following
	 *         link "https://bcrypt-generator.com"
	 * 
	 *         Note :- If you want to create your custom PasswordEncoders then you
	 *         can by implementing the PasswordEncoder interface
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
