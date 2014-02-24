package com.ocds.controllers;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ocds.users.*;



@Controller
public class UserHomeController {
	@Autowired
	UserComponent userComponent;
	
	
			
	
	public UserHomeController() { 
		System.out.println("CREATING USER CONTROLLER");
	}
	/** To update the model  **/
	
	
	@RequestMapping(value = "/home")
		public String home(@RequestParam(value = "role", required = true) String role,ModelMap model, Principal principal) {
		System.out.println(principal.getName());		
		//updateModel(principal, model,date, false);
		Set<String> roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
		System.out.println(roles);
		 if (role.equalsIgnoreCase("ROLE_ADMIN")) {	
	            return "base";
	        }	
		 if (role.equalsIgnoreCase("ROLE_INSTRUCTOR")) {	
	            return "instructor";
	        }
		return "student";
	}
	
	@RequestMapping(value = "/viewusers")
	public String viewallusers(String date,ModelMap model, Principal principal) {
			
		List<User> users = userComponent.getAllUsers(); 	
	 	model.addAttribute("users", users);
	 	String loginuser = principal.getName();	
	 	model.addAttribute("loginuser",loginuser);
	 	return "viewallusers";
	}
	
	@RequestMapping(value = "/modify")
	@Secured(value = { "ROLE_ADMIN" })
	public String deleteUser(@RequestParam(value = "username", required = true) String username, Principal principal, ModelMap model) {
			
		User user = userComponent.loadUserByUsername(username);
		model.addAttribute("user", user);
		String loginuser = principal.getName();	
	 	model.addAttribute("loginuser",loginuser);
		return "modifyuser";
	}
	
	@RequestMapping(value = "/delete")
	@Secured(value = { "ROLE_ADMIN" })
	public String modifyUser(@RequestParam(value = "username", required = true) String username, Principal principal, ModelMap model) {			
		userComponent.deleteUser(username);
		return "redirect:/viewusers";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(
			@RequestParam(value = "firstName", required = true) String firstName, 
			@RequestParam(value = "lastName", required = true) String lastName, 
			@RequestParam(value = "username", required = false) String userName,
			@RequestParam(value = "email", required = true) String email, 
			@RequestParam(value = "userroles", required = true) ArrayList<String> userroles, 
			ModelMap model) {			
		System.out.println("updating user");
		for(int i=0;i<userroles.size();i++)
			System.out.println(userroles.get(i));
		User user =  User.findUserByName(userName);
        
        if (user == null) {        	
        	model.addAttribute("updateerror", true);
        	return "redirect:/viewusers";
            
        }
        List<Role> roles = getRoles();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);   	
        System.out.println(roles.get(0).getName());
    	for(int i=0;i<roles.size();i++){
	    	if (userroles.contains(roles.get(i).getName())
	    			&& !(user.getAuthorities().contains(roles.get(i).getName()))
	    			){
	    	user.addRole(roles.get(i));
	    	System.out.println(roles.get(i));
	    	}
    	}
    	user.merge();
    	//userComponent.addUser(user);
	model.addAttribute("message", "Update success.");
	
	return "redirect:/viewusers";

}
	  private List<Role> getRoles() {		
  		List<Role> roles = new ArrayList<Role>();
  		for(String roleName : Role.roleNames) {
  			Role role = new Role(roleName);  			
  			roles.add(role);    			
  		}
  		return roles;
  	}
}
