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
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "role_authorities", uniqueConstraints = @UniqueConstraint(columnNames = { "roles_id", "authorities_id" }))
@NoArgsConstructor
public class RoleAuthorities implements Serializable {

	private static final long serialVersionUID = 5762025489928070231L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "roles_id")
	private Roles roles;

	@ManyToOne
	@JoinColumn(name = "authorities_id")
	private Authorities authorities;
}
