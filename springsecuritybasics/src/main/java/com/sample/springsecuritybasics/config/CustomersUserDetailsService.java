package com.sample.springsecuritybasics.config;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sample.springsecuritybasics.models.Customers;
import com.sample.springsecuritybasics.models.SecurityCustomer;
import com.sample.springsecuritybasics.repository.CustomersRepository;

/**
 * 
 * @author Amit Malik
 * 
 *         Here we have used Spring Security default Authentication provider.
 *         But I don't want to follow this UserDetailsService and I don't to
 *         tightly couple with this Spring Security mechanism of
 *         UserDetailsService and UserDetails. 
 *         
 *         I just want to have my own implementation ignoring this default provided interfaces, User contract/schema etc.
 *         
 *         So to implement custom Authentication Provider we have to create a CustomersAuthenticationProvider
 *         
 *         So please follow CustomersAuthenticationProvider.java class for further instructions
 *
 */
@Service
public class CustomersUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomersRepository customersRepository;

	/**
	 * We have used default authentication provider which is
	 * DaoAuthenticationProvider by implementing UserDetailsService so we have
	 * predefined method "loadUserByUsername(String username)" which we have
	 * overridden/leverages and check the Authentication and after that it will
	 * return the UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Customers> customer = this.customersRepository.findByEmail(username);
		if (Objects.isNull(customer) || customer.size() <= 0) {
			throw new UsernameNotFoundException("User details not found for the user : " + username);
		}
		return new SecurityCustomer(customer.get(0));
	}
}
