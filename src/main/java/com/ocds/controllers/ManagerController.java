package com.ocds.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocds.Dao.CourseComponent;
import com.ocds.Domain.Course;
import com.ocds.users.User;

@Controller
public class ManagerController {
	
	@Autowired
	private CourseComponent coursecomponent;
	
	public  ManagerController() { 
		System.out.println("CREATING Manager CONTROLLER");
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "course_enrollment";
	}
	
	//Begin of Course
	@RequestMapping(value = "/register_course", method = RequestMethod.GET)
	public String courseRegister(ModelMap model) {
		model.addAttribute("instructors",coursecomponent.getAllInstructor());
		return "course_register";
	}
	
	@RequestMapping(value = "/register_course", method = RequestMethod.POST)
	public String courseRegister(
			@RequestParam(value = "name", required = true) String name, 
			@RequestParam(value = "year", required = true) String year,
			@RequestParam(value = "semester", required = true) String semester,
			@RequestParam(value = "state", required = true) String state,
			@RequestParam(value = "id_num", required = true) String id_num,
			@RequestParam(value = "section_num", required = true) int section_num,
			@RequestParam(value = "instructor_id", required = true) int instructor_id,
			ModelMap model) {
		User instrutor = coursecomponent.getInstructorByID(instructor_id);
		Course course = new Course(name,year,semester,state,id_num,section_num,instrutor);
		if (coursecomponent.findCourseByName_Section(id_num,section_num).size() != 0){
			model.addAttribute("error", true);
			return "course_register";
		}else{
			coursecomponent.addCourse(course);
			return "redirect:/view_course";
		}
	}
	
	@RequestMapping(value = "/view_course")
	public String viewallusers(String date,ModelMap model, Principal principal) {
			
		List<Course> courses = coursecomponent.getAllCourses();
	 	model.addAttribute("courses", courses);
	 	return "course_view";
	}
	
	@RequestMapping(value = "/modify_courses", method = RequestMethod.GET)
	@Secured(value = { "ROLE_ADMIN" })
	public String courseModify(
			@RequestParam(value = "id", required = true) int id, 
			Principal principal, ModelMap model) {
			
		Course course = coursecomponent.findCourseByID(id);
		
		model.addAttribute("course", course);
		String loginuser = principal.getName();	
	 	model.addAttribute("loginuser",loginuser);
		return "course_modify";
	}
	
	@RequestMapping(value = "/modify_courses", method = RequestMethod.POST)
	@Secured(value = { "ROLE_ADMIN" })
	public String courseModify(
			@RequestParam(value = "id", required = true) int id, 
			@RequestParam(value = "name", required = true) String name, 
			@RequestParam(value = "year", required = true) String year,
			@RequestParam(value = "semester", required = true) String semester,
			@RequestParam(value = "state", required = true) String state, 
			Principal principal, ModelMap model) {
			
		Course course = coursecomponent.findCourseByID(id);
		
		course.setName(name);
		course.setYear(year);
		course.setSemester(semester);
		course.setState(state);
		
		coursecomponent.updateCourse(course);
		
		return "redirect:/view_course";
	}
	
	@RequestMapping(value = "/delete_course")
	@Secured(value = { "ROLE_ADMIN" })
	public String deleteCourse(
			@RequestParam(value = "id", required = true) int id, 
			Principal principal, ModelMap model) {
			
		Course course = coursecomponent.findCourseByID(id);
		
		coursecomponent.deleteCourse(course);
		
		String loginuser = principal.getName();	
	 	model.addAttribute("loginuser",loginuser);
		return "redirect:/view_course";
	}
	//End of Course
}
