package com.ocds.controllers.jenny;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {	

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
}
