package com.sample.springsecuritybasics.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "authorities", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NoArgsConstructor
public class Authorities implements Serializable {

	private static final long serialVersionUID = -8200562703665358346L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	public Authorities(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
}
