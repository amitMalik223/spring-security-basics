package com.sample.springsecuritybasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.springsecuritybasics.models.RoleAuthorities;

@Repository
public interface RoleAuthoritiesRepository extends JpaRepository<RoleAuthorities, Long> {

}
