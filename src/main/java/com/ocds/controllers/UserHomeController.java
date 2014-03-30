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

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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

import com.ocds.Dao.CourseComponent;
import com.ocds.users.*;



@Controller
public class UserHomeController {
	@Autowired
	UserComponent userComponent;
	@Autowired
	CourseComponent courseComponent;
	
	
			
	
	public UserHomeController() { 
		System.out.println("CREATING USER CONTROLLER");
	}
	/** To update the model  **/
	
	// Removed as the login screen was changed to have only one page
	/*@RequestMapping(value = "/home")
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
	}*/
	
	@RequestMapping(value = "/viewusers")
	public String viewallusers(ModelMap model, HttpSession session) {
			
		List<User> users = userComponent.getAllUsers(); 	
	 	model.addAttribute("users", users);	 	
	 	String loginuser = (String) session.getAttribute( "loginuser" );
	 	model.addAttribute("loginuser",loginuser);
	 	return "viewallusers";
	}
	
	@RequestMapping(value = "/modify")
	@Secured(value = { "ROLE_ADMIN" })
	public String deleteUser(@RequestParam(value = "username", required = true) String username,HttpSession session, ModelMap model) {
			
		User user = userComponent.loadUserByUsername(username);
		model.addAttribute("user", user);
		String loginuser = (String) session.getAttribute( "loginuser" );
	 	model.addAttribute("loginuser",loginuser);
		return "modifyuser";
	}
	
	@RequestMapping(value = "/delete")
	@Secured(value = { "ROLE_ADMIN" })
	public String modifyUser(@RequestParam(value = "username", required = true) String username, HttpSession session, ModelMap model) {	
		String loginuser = (String) session.getAttribute( "loginuser" );		
		courseComponent.removeAllCoursesOfStudent(username);
		courseComponent.removeAllCoursesOfInstructor(username);
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
			ModelMap model,HttpSession session) {			
		System.out.println("updating user");
		String loginuser = (String) session.getAttribute( "loginuser" );	
		if (userName.equalsIgnoreCase(loginuser))
				userroles.add("ROLE_ADMIN");
		for(int i=0;i<userroles.size();i++)
			System.out.println(userroles.get(i));
		User user =  User.findUserByName(userName);
        
        if (user == null) {        	
        	model.addAttribute("updateerror", true);
        	return "redirect:/viewusers";
            
        }        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);   	        
        user.setAuthorities(null);   	
    	userComponent.updateUser(user,userroles);
	    model.addAttribute("message", "Update success.");
	
	return "redirect:/viewusers";

}
	 
}
