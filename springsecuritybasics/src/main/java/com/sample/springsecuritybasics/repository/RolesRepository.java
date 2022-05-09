package com.sample.springsecuritybasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.springsecuritybasics.models.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

	boolean existsByNameAndIdNot(String name, Long id);

}
