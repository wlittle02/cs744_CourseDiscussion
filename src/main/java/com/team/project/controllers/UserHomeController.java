package com.team.project.controllers;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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








import com.team.project.components.UserComponent;
import com.team.project.users.*;



@Controller
public class UserHomeController {
	@Autowired
	UserComponent userComponent;
	
	
			
	
	public UserHomeController() { 
		System.out.println("CREATING USER CONTROLLER");
	}
	/** To update the model  **/
	
//	private void updateModel(Principal principal, ModelMap model,String date, boolean isAdmin) {	
//		try{
//			DietRecordUser user = DietRecordUser.findUserByName(principal.getName());
//			Date dt = new Date();			
//			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
//			if(user == null) return;		
//			if (date == null || date.isEmpty()){					
//				date = dateFormat.format(dt);				
//			}	
//			model.addAttribute("date",date);
//			dt = dateFormat.parse(date);
//			date = dateFormat.format(dt);
//			Map<String, Double> total = new HashMap<String, Double>() ;
//			if (!isAdmin) {
//				model.addAttribute("dietRecords", dietRecordsComponent.getDietRecordsByDate(user.getId(),date));
//				total = findTotal(dietRecordsComponent.getDietRecordsByDate(user.getId(),date));
//			} else {
//				//model.addAttribute("dietRecords", dietRecordsComponent.getAllDietRecords());
//				model.addAttribute("dietRecords", dietRecordsComponent.getDietRecordsByDate(user.getId(),date));
//				total = findTotal(dietRecordsComponent.getDietRecordsByDate(user.getId(),date));
//			}
//			model.addAttribute("user", user.getUsername());
//			model.addAttribute("proteinTotal", total.get("protein"));
//			model.addAttribute("phenylTotal", total.get("phenyl"));
//			model.addAttribute("date",date);
//		}
//		catch(Exception e){
//		System.out.println(e);	
//		}
//	}
//	
	@RequestMapping(value = "/home")
		public String home(String date,ModelMap model, Principal principal) {
		System.out.println("user");		
		//updateModel(principal, model,date, false);
		Set<String> roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
		System.out.println(roles);
		 if (roles.contains("ROLE_ADMIN")) {	
	            return "register";
	        }	
		 if (roles.contains("ROLE_INSTRUCTOR")) {	
	            return "instructor";
	        }
		return "student";
	}
	
	@RequestMapping(value = "/viewinstructors")
	public String viewallinstructors(String date,ModelMap model, Principal principal) {
		
		List<User> users = userComponent.getAllUsers(); 
	 	model.addAttribute("users", users);
	 	return "viewallinstructors";
	}
}
