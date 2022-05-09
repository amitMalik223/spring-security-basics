package com.sample.springsecuritybasics.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.sample.springsecuritybasics.repository.RoleAuthoritiesRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Amit Malik
 * 
 *         To Customize UserDetails in Spring Security then we have created this
 *         class as we have Customers class defined so we have named this class
 *         as securityCustomer.
 * 
 *         The aim behind this class is :- In Spring Security, by default spring
 *         security looks for the "username" and "password" fields for
 *         authentication but suppose if we have a scenario where username can
 *         we email or any other field then we need to customize the user
 *         details that's why we have created this SecurityCustomer POJO
 * 
 *         But in production phase this will not be recommended
 *
 */
@Slf4j
public class SecurityCustomer implements UserDetails {

	private static final long serialVersionUID = 5837728121297491081L;

	private final Customers customers;

	@Autowired
	private RoleAuthoritiesRepository roleAuthoritiesRepository;

	public SecurityCustomer(Customers customers) {
		super();
		this.customers = customers;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.debug("inside SecurityCustomer : inside getAuthorities() --------------------------------");
		List<GrantedAuthority> authorities = this.getAuthorities(customers.getCustomersRoles(), customers.getEmail());
		return authorities;
	}

	private List<GrantedAuthority> getAuthorities(List<CustomersRoles> customersRolesList, String customerEmail) {
		log.debug(
				"inside SecurityCustomer : inside getAuthorities(List<CustomersRoles> customersRolesList) : customersRolesList size : {} ",
				customersRolesList.size());
		List<GrantedAuthority> grantedAuthority = new ArrayList<>();
		List<RoleAuthorities> roleAuthoritiesList = this.roleAuthoritiesRepository.findAll();
		customersRolesList.stream().forEach(customerRoles -> {
			List<Authorities> authoritiesList = roleAuthoritiesList.stream()
					.filter(roleAuthorities -> roleAuthorities.getRoles().getId() == customerRoles.getRoles().getId())
					.map(roleAuthorities -> roleAuthorities.getAuthorities()).collect(Collectors.toList());
			log.debug(
					"inside SecurityCustomer : inside getAuthorities(List<CustomersRoles> customersRolesList) : customer email : {} : authoritiesList size : {} ",
					customerEmail, authoritiesList.size());
			authoritiesList.stream().forEach(authorities -> {
				log.debug(
						"inside SecurityCustomer : inside getAuthorities(List<CustomersRoles> customersRolesList) : customer email : {} : authorities are : {} ",
						customerEmail, authorities.getName());
				grantedAuthority.add(new SimpleGrantedAuthority(authorities.getName()));
			});
		});
		return grantedAuthority;
	}

	@Override
	public String getPassword() {
		return this.customers.getPassword();
	}

	@Override
	public String getUsername() {
		return this.customers.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
