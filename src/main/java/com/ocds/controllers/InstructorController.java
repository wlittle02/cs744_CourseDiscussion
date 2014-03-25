package com.ocds.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocds.users.User;
import com.ocds.users.UserComponent;
import com.ocds.Dao.CourseComponent;
import com.ocds.Dao.ThreadComponent;
import com.ocds.Domain.CThread;
import com.ocds.Domain.Course;


@Controller
public class InstructorController {
	@Autowired
	CourseComponent courseComponent;
	@Autowired
	UserComponent userComponent;
	@Autowired
	ThreadComponent threadComponent;
	
	@RequestMapping(value = "/instructor_courses")
	public String getCoursesForInstructor(ModelMap model, HttpSession session) {
		String loginuser = (String) session.getAttribute( "loginuser" );
		User instructor = userComponent.loadUserByUsername(loginuser);
		List<Course> courses = courseComponent.getAllCoursesforInstructor(instructor);	 	
	 	
	 	model.addAttribute("loginuser",loginuser);
	 	model.addAttribute("courses",courses);
	 	return "instructor_home";
	}
	
	@RequestMapping(value = "/view_instructor_course", method = RequestMethod.GET)
	public String viewThread(@RequestParam(value = "courseId", required = true) String courseId,HttpSession session, ModelMap model)
	{
		
		return "instructor_course";
	}
	
	@RequestMapping(value = "/createThread", method = RequestMethod.POST)
	public String createThread(@RequestParam(value = "courseId", required = true) String courseId,
			                   HttpSession session,
			                   ModelMap modelmap)
	{
		
		return "instructor_createThread";
	}
	
	@RequestMapping(value = "/newThread", method = RequestMethod.POST)
	public String newThread(@RequestParam(value = "userName", required = true) String userName,
							@RequestParam(value = "threadName", required = true) String threadName,
					        @RequestParam(value = "courseId", required = true) String courseId,
						    HttpSession session,
							ModelMap modelmap)
	{
		CThread newThread = new CThread(threadName,
									    this.courseComponent.findCourseByID(Integer.parseInt(courseId)),
									    "",
									    "",
									    true);
		this.threadComponent.createThread(newThread);
		return "instructor_course";
	}
	
}
