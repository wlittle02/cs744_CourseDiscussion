package com.ocds.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocds.users.User;
import com.ocds.users.UserComponent;
import com.ocds.Dao.CourseComponent;
import com.ocds.Domain.Course;


@Controller
public class InstructorController {
	@Autowired
	CourseComponent courseComponent;
	@Autowired
	UserComponent userComponent;
	
	
	@RequestMapping(value = "/instructor_courses")
	public String getCoursesForInstructor(ModelMap model, HttpSession session) {
		String loginuser = (String) session.getAttribute( "loginuser" );
		User instructor = userComponent.loadUserByUsername(loginuser);
		List<Course> courses = courseComponent.getAllCoursesforInstructor(instructor);	 	
	 	
	 	model.addAttribute("loginuser",loginuser);
	 	model.addAttribute("courses",courses);
	 	return "instructor";
	}
	
	@RequestMapping(value = "/student_courses")
	public String getCoursesForStudent(ModelMap model, HttpSession session) {
		String loginuser = (String) session.getAttribute( "loginuser" );
		User student = userComponent.loadUserByUsername(loginuser);
		List<Course> courses = courseComponent.getAllCoursesforStudent(student);	 		 	
	 	model.addAttribute("loginuser",loginuser);
	 	model.addAttribute("courses",courses);
	 	return "student";
	}

}
