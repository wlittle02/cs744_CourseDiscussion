package com.ocds.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocds.users.Role;
import com.ocds.users.User;
import com.ocds.users.UserComponent;


@Controller
public class RegistrationController {
	@Autowired
	UserComponent userComponent;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	public  RegistrationController() { 
		System.out.println("CREATING REGISTRATION CONTROLLER");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(
			@RequestParam(value = "firstName", required = true) String firstName, 
			@RequestParam(value = "lastName", required = true) String lastName, 
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "email", required = true) String email, 
			@RequestParam(value = "password", required = true) String password,	
			@RequestParam(value = "userroles", required = true) ArrayList<String> userroles,
			ModelMap model) {
		password = passwordEncoder.encode(password);	
		User user =  User.findUserByName(userName);
		
        
        if (user != null) {        	
        	model.addAttribute("regerror", true);
        	return "register";
            //throw new UsernameNotFoundException("Username already exists");
        }
        
		user = new User.Builder()
			.firstName(firstName)
			.lastName(lastName)
			.username(userName)
			.email(email)
			.password(password)
			.authorities(new HashSet<Role>())
			.build();
		List<Role> roles = getRoles();
		for (int i = 0; i < getRoles().size(); i++)
		{
			if (userroles.contains(roles.get(i).getName()))
			{
				user.addRole(roles.get(i));
			}
		}
		
		userComponent.addUser(user);
		model.addAttribute("message", "Registration success.");
		
		return "register";
	}
	
	private List<Role> getRoles()
	{
		List<Role> roles = new ArrayList<Role>();
		for (String rolename : Role.roleNames)
		{
			Role role = new Role(rolename);
			roles.add(role);
		}
		return roles;
	}
}
