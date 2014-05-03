package com.ocds.controllers;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import com.ocds.users.Role;
import com.ocds.users.User;
import com.ocds.users.UserComponent;


@Controller
public class LoginController {	
	@Autowired
	UserComponent userComponent;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Autowired
//	private	BCryptPasswordEncoder pe;
	public LoginController() {
		System.out.println("CREATING LOGIN CONTROLLER");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(
			@RequestParam(value="error", required=false) Boolean isError,
			 
			ModelMap model) {
		
		if(isError != null && isError) {
			model.addAttribute("error", isError);
		}
		
		return "login";
	}	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginvalidation(
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password,
			@RequestParam(value="role", required=true) String role,			 
			ModelMap model,HttpSession session) {
		//Getting the user
		User user = userComponent.loadUserByUsername(username);
		if (user==null){
			model.addAttribute("error", true);
			model.addAttribute("message", "User Name does not exist");
			return "login";
		}
		//Checking password match
		boolean passwordmatch = BCrypt.checkpw(password,user.getPassword());		
		if (!passwordmatch){
			model.addAttribute("error", true);
			model.addAttribute("message", "Invalid Username/Password");
			return "login";
		}
		// To check for valid role
		Iterator it =user.getAuthorities().iterator();
		boolean rolevalid = false;
		while(it.hasNext()){
			Role r = (Role)it.next();
			if (r.getName().equalsIgnoreCase(role))
				rolevalid = true;
		}
		if (!rolevalid){
			model.addAttribute("error", true);
			model.addAttribute("message", "Role not valid for Username");
			return "login";
		}
		session.setAttribute("loginuser" , username);

		session.setAttribute("loginUsersName", user.getFirstName() + " " + user.getLastName());
		session.setAttribute("loginrole" , role);
	
		if (role.equalsIgnoreCase("ROLE_ADMIN")) {	
            return "manager_home";
        }	
	 if (role.equalsIgnoreCase("ROLE_INSTRUCTOR")) {	
            return "redirect:/instructor_courses";
        }
	 if (role.equalsIgnoreCase("ROLE_TA")) {	
         return "redirect:/ta_courses";
     }
	return "redirect:/student_courses";
		
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String login(ModelMap model,HttpSession session) {
		
		session.setAttribute("loginuser" , null);
		session.setAttribute("loginUsersName" , null);
		session.setAttribute("loginrole" , null);
		
		return "login";
	}	
}
