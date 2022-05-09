package com.sample.springsecuritybasics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springsecuritybasics.services.CustomersService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomersController {

	@Autowired
	private CustomersService customersService;
}
