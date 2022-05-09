package com.sample.springsecuritybasics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.springsecuritybasics.models.CustomersRoles;

@Repository
public interface CustomersRolesRepository extends JpaRepository<CustomersRoles, Long> {

	List<CustomersRoles> findByCustomersId(long id);

}
