package com.sample.springsecuritybasics.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.springsecuritybasics.jpa.config.Auditable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Roles extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 9162679045456361108L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	@JsonIgnore
	@OneToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private List<RoleAuthorities> roles;

}
