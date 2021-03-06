package com.ocds.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

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
	public String register(HttpSession session,ModelMap model) {	
			//To check if user is logged in
			String loginuser = (String) session.getAttribute( "loginuser" );
			if (loginuser==null){				
				model.addAttribute("error", true);
				model.addAttribute("message", "Login credential not found. Kindly login.");
				return "login";
			}
			// To check if logged in user has Manager role & logged in with Manager role
			User user = userComponent.loadUserByUsername(loginuser);
			String loginrole = (String) session.getAttribute( "loginrole" );
			if (!user.hasRole("ROLE_ADMIN") || !loginrole.equalsIgnoreCase("ROLE_ADMIN") ){
				model.addAttribute("error", true);
				model.addAttribute("message", "Denied access for this operation!! Kindly login with Manager role or contact Manager for access");
				return "login";
			}			
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpSession session,
			@RequestParam(value = "firstName", required = true) String firstName, 
			@RequestParam(value = "lastName", required = true) String lastName, 
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "email", required = true) String email, 
			@RequestParam(value = "password", required = true) String password,	
			@RequestParam(value = "userroles", required = true) ArrayList<String> userroles,
			ModelMap model) {
		//To check if user is logged in
		String loginuser = (String) session.getAttribute( "loginuser" );
		if (loginuser==null){				
			model.addAttribute("error", true);
			model.addAttribute("message", "Login credential not found. Kindly login.");
			return "login";
		}
		// To check if logged in user has Manager role & logged in with Manager role
		User user = userComponent.loadUserByUsername(loginuser);
		String loginrole = (String) session.getAttribute( "loginrole" );
		if (!user.hasRole("ROLE_ADMIN") || !loginrole.equalsIgnoreCase("ROLE_ADMIN") ){
			model.addAttribute("error", true);
			model.addAttribute("message", "Denied access for this operation!! Kindly login with Manager role or contact Manager for access");
			return "login";
		}			
		password = passwordEncoder.encode(password);	
		// To check of new username already exists
		user =  User.findUserByName(userName);
        if (user != null) {        	
        	model.addAttribute("regerror", true);
        	return "register";           
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
		ArrayList<Role> userrole= new ArrayList<Role>();
 		for (int i = 0; i < roles.size(); i++)
		{
			//System.out.println(roles.get(i).getName());
			if (userroles.contains(roles.get(i).getName()))
			{
				userrole.add(roles.get(i));
			}
		} 		
		userComponent.addUser(user,userrole);
		model.addAttribute("success", true);
		
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
