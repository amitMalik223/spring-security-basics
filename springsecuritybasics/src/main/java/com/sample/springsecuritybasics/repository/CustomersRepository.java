package com.sample.springsecuritybasics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.springsecuritybasics.models.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {

	List<Customers> findByEmail(String email);
}
