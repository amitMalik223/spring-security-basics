package com.sample.springsecuritybasics.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
@Data
@NoArgsConstructor
public class Customers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String email;

	private String password;

	private String salt;

	@OneToMany(mappedBy = "customers", fetch = FetchType.EAGER)
	private List<CustomersRoles> customersRoles;

	public Customers(Long id, String email, String password, List<CustomersRoles> customersRoles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.customersRoles = customersRoles;
	}

}
