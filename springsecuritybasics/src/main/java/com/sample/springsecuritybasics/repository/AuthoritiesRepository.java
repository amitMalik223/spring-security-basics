package com.sample.springsecuritybasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.springsecuritybasics.models.Authorities;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {

}
