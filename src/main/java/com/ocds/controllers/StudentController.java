package com.ocds.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocds.Dao.CourseComponent;
import com.ocds.Dao.StudentComponent;
import com.ocds.Domain.Course;
import com.ocds.users.User;
// Will listen to all the requests of the JSP
import com.ocds.users.UserComponent;
@Controller
public class StudentController {
	@Autowired
	private StudentComponent studentcomponent;
	@Autowired
	CourseComponent courseComponent;
	@Autowired
	UserComponent userComponent;
	
	public StudentController()
	{
		System.out.println("Creating Student Controller");
	}
	
	@RequestMapping(value = "/student_courses")
	public String getCourses(ModelMap model, HttpSession session) {
		String loginuser = (String) session.getAttribute( "loginuser" );
		//User student = userComponent.loadUserByUsername(loginuser);
		//List<Course> courses = courseComponent.getAllCoursesforStudent(student);
		List<Course> courses = studentcomponent.getCourseListForStudent(loginuser);
	 	model.addAttribute("loginuser",loginuser);
	 	model.addAttribute("courses",courses);
	 	return "student";
	}
	
	@RequestMapping(value = "/view_thread", method = RequestMethod.GET)
	public String viewThread(@RequestParam(value = "courseId", required = true) String courseId,HttpSession session, ModelMap model)
	{
		System.out.println("Method was called. I am viewThread.");
		return "student_class";
	}
}
