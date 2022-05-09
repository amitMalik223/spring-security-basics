package com.sample.springsecuritybasics.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sample.springsecuritybasics.models.Authorities;
import com.sample.springsecuritybasics.models.Customers;
import com.sample.springsecuritybasics.models.CustomersRoles;
import com.sample.springsecuritybasics.models.RoleAuthorities;
import com.sample.springsecuritybasics.repository.CustomersRepository;
import com.sample.springsecuritybasics.repository.RoleAuthoritiesRepository;

/**
 * 
 * @author Amit Malik
 * 
 *         This class responsible for the Custom AuthenticationProvider which
 *         implements the interface AuthenticationProvider.
 * 
 *         AuthenticationProvider interface have 2 abstract methods
 * 
 * @Note :- The UserDetails will be converted in Authentication with the help of
 *       Default DaoAuthenticationProvider. ProviderManager and
 *       DaoAuthenticationProvider is the default implementation of
 *       AuthenticationManager and AthenticationProvider inside Spring Security
 *
 */
@Component
public class CustomersAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomersRepository customersRepository;

	@Autowired
	private RoleAuthoritiesRepository roleAuthoritiesRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		List<Customers> customer = this.customersRepository.findByEmail(username);
		if (Objects.isNull(customer) || customer.size() <= 0) {
			throw new BadCredentialsException("No user registered with these details");
		} else {
			if (this.passwordEncoder.matches(password, customer.get(0).getPassword())) {
				List<GrantedAuthority> authorities = this.getAuthorities(customer.get(0).getCustomersRoles());
				return new UsernamePasswordAuthenticationToken(username, password, authorities);
			} else {
				throw new BadCredentialsException("Invalid password");
			}
		}
	}

	private List<GrantedAuthority> getAuthorities(List<CustomersRoles> customersRolesList) {

		List<GrantedAuthority> grantedAuthority = new ArrayList<>();
		List<RoleAuthorities> roleAuthoritiesList = this.roleAuthoritiesRepository.findAll();
		customersRolesList.stream().forEach(customerRoles -> {
			List<Authorities> authoritiesList = roleAuthoritiesList.stream()
					.filter(roleAuthorities -> roleAuthorities.getRoles().getId() == customerRoles.getRoles().getId())
					.map(roleAuthorities -> roleAuthorities.getAuthorities()).collect(Collectors.toList());
			authoritiesList.stream().forEach(authorities -> {
				grantedAuthority.add(new SimpleGrantedAuthority(authorities.getName()));
			});
		});
		return grantedAuthority;
	}

	/**
	 * This supports method is used to mention the Authentication provider for Face
	 * Scan or Finger Print etc. Because Sometimes we have a scenario in which the
	 * authentication if performed based on Face Scan/Finger Print then we should
	 * use/provide the details or the custom defined Authentication provider inside
	 * supports method
	 * 
	 * e.g. FingerPrintAuthenticationToken.java, FaceUnlockAuthenticationToken.java
	 * etc.
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
