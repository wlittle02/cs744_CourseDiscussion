package com.ocds.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ocds.users.Role;
import com.ocds.users.User;

@Controller
public class SetupMockDataController {
			
	private List<Role> createRoles() {		
		List<Role> roles = new ArrayList<Role>();
		for(String roleName : Role.roleNames) {
			Role role = new Role(roleName);
			role.persist();
			roles.add(role);
			System.out.println("created role(" + role + ")");
		}
		return roles;
	}
	
	private void createUsers(List<Role> roles) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password="chen123";
		String encodedPassword = passwordEncoder.encode(password);
		User user1 = new User.Builder()
			.username("chen.gong")
			.firstName("Gong")
			.lastName("Chen")
			.email("gong.chen@uwlax.edu")
			.password(encodedPassword)						
			.authorities(new HashSet<Role>())			
			.build();					
		user1.persist();
		System.out.println("created user(" + user1 + ")");
		
		password="ryan123";
		encodedPassword = passwordEncoder.encode(password);
		User user2 = new User.Builder()
		.username("litt.ryan")
		.firstName("Little")
		.lastName("Ryan")
		.email("litt.ryan@uwlax.edu")
		.password(encodedPassword)
		.authorities(new HashSet<Role>())		
		.build();					
		user2.persist();
		System.out.println("created user(" + user2 + ")");
		
		password="jenny123";
		encodedPassword = passwordEncoder.encode(password);
		User user3 = new User.Builder()
		.username("gijo.jenn")
		.firstName("Jenny")
		.lastName("Gijo")
		.email("gijo.jenn@uwlax.edu")
		.password(encodedPassword)
		.authorities(new HashSet<Role>())		
		.build();					
		user3.persist();
		System.out.println("created user(" + user3 + ")");
		
		user1.addRole(roles.get(0));
		user1.addRole(roles.get(1));
		user1.merge();
		System.out.println("updated user(" + user1 + ")");
		
		user2.addRole(roles.get(1));		
		user2.merge();		
		System.out.println("updated user(" + user2 + ")");	
		
		user3.addRole(roles.get(2));		
		user3.merge();		
		System.out.println("updated user(" + user3 + ")");		
	}
	
	@RequestMapping(value="/setup")
	public String setup() {
		System.out.println("setting up mock data");
		createUsers(createRoles());
		System.out.println("completed setup of mock data");
		
		return "redirect:login";
	}	
	@RequestMapping(value="/test")
	public String test() {
		return "test";
	}
}
