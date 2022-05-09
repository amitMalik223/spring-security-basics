package com.sample.springsecuritybasics.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springsecuritybasics.models.Roles;
import com.sample.springsecuritybasics.services.RolesService;

/**
 * 
 * @author Amit Malik
 * 
 *         Role Rest Controller which accepts the requests related to Roles
 *
 */
@RestController
@RequestMapping("/api/v1/roles")
//@Api(value = "rolesController", description = "Operations pertaining to roles in Spring Security Sample Project")
public class RolesController {

	/**
	 * Inject RoleService Default scope is Singleton
	 */
	@Autowired
	private RolesService rolesService;

	/**
	 * This method is responsible for creating and updating Roles in
	 * Application/Database
	 * 
	 * @param role Role Object that is to be created or updated
	 * @return Role Object
	 * @throws Exception
	 */
//	@ApiOperation(value = "Create and Updated Roles", response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity<Roles> createUpdateRoles(@RequestBody Roles role) throws Exception {
		Roles savedRole = this.rolesService.createUpdateRoles(role);
		if (Objects.isNull(savedRole)) {
			throw new Exception("Role exists with name : " + role.getName());
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
		}
	}

	/**
	 * This method is responsible for fetch/get Role from Application/Database by
	 * Role Id
	 * 
	 * @param object_id Role Id to fetch the single Role Object
	 * @return Single role Object
	 * @throws Exception
	 */
//	@ApiOperation(value = "Get Role record based on Role Id or Object Id", response = ResponseEntity.class)
	@GetMapping("/{object_id}")
	public ResponseEntity<Roles> getRoleById(@PathVariable(name = "object_id", required = true) Long object_id)
			throws Exception {
		Roles role = this.rolesService.getRoleById(object_id);
		if (Objects.isNull(role)) {
			throw new Exception("Role not found with id : " + object_id);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(role);
		}
	}

	/**
	 * This method is responsible for fetch/get list of all Roles from
	 * Application/Database
	 * 
	 * @return List of all roles
	 * @throws Exception
	 */
//	@ApiOperation(value = "Get list of all Roles", response = ResponseEntity.class)
	@GetMapping
	public ResponseEntity<List<Roles>> getRolesList() throws Exception {
		List<Roles> roleList = this.rolesService.getRolesList();
		if (Objects.isNull(roleList) || roleList.size() <= 0) {
			throw new Exception("No record found.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(roleList);
		}
	}

//	@ApiOperation(value = "Get list of all Roles", response = ResponseEntity.class)
	@GetMapping("/helloWorld")
	public ResponseEntity<String> helloWorld() {
		return ResponseEntity.status(HttpStatus.OK).body("Hello World!");
	}
}
