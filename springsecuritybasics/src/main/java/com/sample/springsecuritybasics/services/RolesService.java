package com.sample.springsecuritybasics.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.springsecuritybasics.models.Roles;
import com.sample.springsecuritybasics.repository.RolesRepository;

@Service
public class RolesService {

	@Autowired
	private RolesRepository rolesRepository;

	public Roles createUpdateRoles(Roles role) throws Exception {
		boolean duplicacyCheck = this.rolesRepository.existsByNameAndIdNot(role.getName(), role.getId());
		if (duplicacyCheck) {
			return null;
		}
		if (role.getId() <= 0) {
			return this.rolesRepository.save(role);
		} else {
			Roles existingRecord = this.rolesRepository.findById(role.getId()).get();
			if (Objects.isNull(existingRecord)) {
				throw new Exception("Role not exists with id : " + role.getId());
			}
			BeanUtils.copyProperties(role, existingRecord);
			existingRecord.setId(role.getId());
			return this.rolesRepository.save(role);
		}
	}

	public Roles getRoleById(Long object_id) {
		return this.rolesRepository.findById(object_id).get();
	}

	public List<Roles> getRolesList() {
		return this.rolesRepository.findAll();
	}

}
