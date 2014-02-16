package com.ocds.controllers.jenny;

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
		String password="aaa";
		String encodedPassword = passwordEncoder.encode(password);
		User user1 = new User.Builder()
			.username("aaa")
			.firstName("aaa")
			.lastName("aaa")
			.email("aaa@aaa")
			.password(encodedPassword)						
			.authorities(new HashSet<Role>())			
			.build();					
		user1.persist();
		System.out.println("created user(" + user1 + ")");
		
		password="bbb";
		encodedPassword = passwordEncoder.encode(password);
		User user2 = new User.Builder()
		.username("bbb")
		.firstName("bbb")
		.lastName("bbb")
		.email("bbb@bbb")
		.password(encodedPassword)
		.authorities(new HashSet<Role>())		
		.build();					
		user2.persist();
		System.out.println("created user(" + user2 + ")");
		
		password="ccc";
		encodedPassword = passwordEncoder.encode(password);
		User user3 = new User.Builder()
		.username("ccc")
		.firstName("ccc")
		.lastName("ccc")
		.email("ccc@ccc")
		.password(encodedPassword)
		.authorities(new HashSet<Role>())		
		.build();					
		user3.persist();
		System.out.println("created user(" + user3 + ")");
		
		user1.addRole(roles.get(0));
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
}
