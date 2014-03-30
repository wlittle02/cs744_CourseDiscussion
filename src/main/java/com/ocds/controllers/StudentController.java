package com.ocds.controllers;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ocds.Domain.*;

import java.util.Date;
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
import com.ocds.Dao.ThreadComponent;
import com.ocds.Domain.CThread;
import com.ocds.Domain.Contribution;
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
	@Autowired
	ThreadComponent threadComponent;
	
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
		// Will need a getCourseListForTA(loginuser)
	 	model.addAttribute("loginuser",loginuser);
	 	model.addAttribute("courses",courses);
	 	return "student_home";
	}
	@RequestMapping(value = "/view_student_threads", method = RequestMethod.GET)
	public String viewThread(@RequestParam(value = "courseId", required = true) String courseId,HttpSession session, ModelMap model)
	{
		List<CThread> threads = threadComponent.getAllThreads(Integer.parseInt(courseId));
		if (threads!=null)
		model.addAttribute("threads",threads);
		model.addAttribute("courseId",courseId);
		Course course = courseComponent.findCourseByID(Integer.parseInt(courseId));
		model.addAttribute("courseName",course.getName());
		return "student_threads";
	}
	
		@RequestMapping(value = "/view_student_contributions", method = RequestMethod.GET)
	public String view_contributions(@RequestParam(value = "threadId", required = true) String threadId,HttpSession session, ModelMap model)
	{
		List<Contribution> contributions = threadComponent.getAllContributions(Long.parseLong(threadId));
		if (contributions!=null)
		model.addAttribute("contributions",contributions);
		model.addAttribute("threadId",threadId);
		CThread thread = threadComponent.getThread(Long.parseLong(threadId));
		model.addAttribute("threadName",thread.getName());
		return "student_contributions";
	}
	@RequestMapping(value = "/create_student_Contribution", method = RequestMethod.POST)
	public String createContribution(@RequestParam(value = "threadId", required = true) String threadId,
							   @RequestParam(value = "message", required = true) String message,
			                   HttpSession session,
			                   ModelMap model)
	{
		Date date = new Date();			
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");	
		String stringDate = dateFormat.format(date);
		String loginuser = (String) session.getAttribute( "loginuser" );
		User user = userComponent.loadUserByUsername(loginuser);
		String enteredBy = user.getFirstName() + " " + user.getLastName();
		Contribution contribution = new Contribution(message,
									    "",
									    false,
									    enteredBy,
									    loginuser,
									    threadComponent.getThread(Long.parseLong(threadId)),
									    null,									    
									    stringDate);
		
		this.threadComponent.createContribution(contribution);
		List<Contribution> contributions = threadComponent.getAllContributions(Long.parseLong(threadId));
		if (contributions!=null)
		model.addAttribute("contributions",contributions);
		model.addAttribute("threadId",threadId);
		CThread thread = threadComponent.getThread(Long.parseLong(threadId));
		model.addAttribute("threadName",thread.getName());
		return "student_contributions";
	}
	
	}
