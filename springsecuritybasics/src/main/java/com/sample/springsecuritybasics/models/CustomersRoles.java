package com.sample.springsecuritybasics.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "customers_roles_mapping", uniqueConstraints = @UniqueConstraint(columnNames = { "customer_id",
		"role_id" }))
public class CustomersRoles implements Serializable {

	private static final long serialVersionUID = -5741573299757128998L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customers customers;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Roles roles;
}
