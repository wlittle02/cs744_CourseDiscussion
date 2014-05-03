package com.ocds.controllers;

import java.io.File;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
		//To check if user is logged in
		String loginuser = (String) session.getAttribute( "loginuser" );
		if (loginuser==null){				
			model.addAttribute("error", true);
			model.addAttribute("message", "Login credential not found. Kindly login.");
			return "login";
		}
		// To check if logged in user has student role & logged in with student role
		User user = userComponent.loadUserByUsername(loginuser);
		String loginrole = (String) session.getAttribute( "loginrole" );
		if (!user.hasRole("ROLE_STUDENT") || !loginrole.equalsIgnoreCase("ROLE_STUDENT") ){
			model.addAttribute("error", true);
			model.addAttribute("message", "Denied access for this operation!! Kindly login with Student role or contact Manager for access");
			return "login";
		}	

		List<Course> courses = studentcomponent.getCourseListForStudent(loginuser);		
		model.addAttribute("loginuser",loginuser);
		model.addAttribute("courses",courses);
		return "student_home";
	}
	@RequestMapping(value = "/view_student_threads", method = RequestMethod.GET)
	public String viewThread(@RequestParam(value = "courseId", required = true) String courseId,HttpSession session, ModelMap model)
	{
		//To check if user is logged in
		String loginuser = (String) session.getAttribute( "loginuser" );
		if (loginuser==null){				
			model.addAttribute("error", true);
			model.addAttribute("message", "Login credential not found. Kindly login.");
			return "login";
		}
		// To check if logged in user has student role & logged in with student role
		User user = userComponent.loadUserByUsername(loginuser);
		String loginrole = (String) session.getAttribute( "loginrole" );
		if (!user.hasRole("ROLE_STUDENT") || !loginrole.equalsIgnoreCase("ROLE_STUDENT") ){
			model.addAttribute("error", true);
			model.addAttribute("message", "Denied access for this operation!! Kindly login with Student role or contact Manager for access");
			return "login";
		}	
		// To check if student has access to course
		Course course = courseComponent.findCourseByID(Integer.parseInt(courseId));					
		if(!studentcomponent.checkIfStudentInCourse(course, loginuser)){
			model.addAttribute("error", true);
			model.addAttribute("message", "You have been denied access for this course!! Kindly login again");
			return "login";
		}	
		List<CThread> threads = threadComponent.getAllThreads(Integer.parseInt(courseId));
		if (threads!=null)
			model.addAttribute("threads",threads);
		model.addAttribute("courseId",courseId);
		//Course course = courseComponent.findCourseByID(Integer.parseInt(courseId));
		model.addAttribute("courseName",course.getName());
		return "student_threads";
	}

	@RequestMapping(value = "/view_student_contributions", method = RequestMethod.GET)
	public String view_contributions(@RequestParam(value = "threadId", required = true) String threadId,HttpSession session, ModelMap model)
	{
		//To check if user is logged in
		String loginuser = (String) session.getAttribute( "loginuser" );
		if (loginuser==null){				
			model.addAttribute("error", true);
			model.addAttribute("message", "Login credential not found. Kindly login.");
			return "login";
		}
		// To check if logged in user has student role & logged in with student role
		User user = userComponent.loadUserByUsername(loginuser);
		String loginrole = (String) session.getAttribute( "loginrole" );
		if (!user.hasRole("ROLE_STUDENT") || !loginrole.equalsIgnoreCase("ROLE_STUDENT") ){
			model.addAttribute("error", true);
			model.addAttribute("message", "Denied access for this operation!! Kindly login with Student role or contact Manager for access");
			return "login";
		}	
		// To check if student has access to course			
		CThread thread = threadComponent.getThread(Long.parseLong(threadId));
		Course course = courseComponent.findCourseByID(thread.getCourse().getId());	
		if(!studentcomponent.checkIfStudentInCourse(course, loginuser)){
			model.addAttribute("error", true);
			model.addAttribute("message", "You have been denied access for this course!! Kindly login again");
			return "login";
		}		
		List<Contribution> contributions = threadComponent.getAllContributions(Long.parseLong(threadId));
		if (contributions!=null)
			model.addAttribute("contributions",contributions);
		model.addAttribute("threadId",threadId);

		model.addAttribute("threadName",thread.getName());
		model.addAttribute("threadActive", thread.getIsActive());
		return "student_contributions";
	}
	@RequestMapping(value = "/create_student_Contribution", method = RequestMethod.POST)
	public String createContribution(@RequestParam(value = "threadId", required = true) String threadId,
			@RequestParam(value = "message", required = true) String message,
			@RequestParam("name") String name,
			@RequestParam("file") CommonsMultipartFile mFile,
			HttpSession session,
			ModelMap model)
	{
		//To check if user is logged in
		String loginuser = (String) session.getAttribute( "loginuser" );
		if (loginuser==null){				
			model.addAttribute("error", true);
			model.addAttribute("message", "Login credential not found. Kindly login.");
			return "login";
		}
		// To check if logged in user has student role & logged in with student role
		User user = userComponent.loadUserByUsername(loginuser);
		String loginrole = (String) session.getAttribute( "loginrole" );
		if (!user.hasRole("ROLE_STUDENT") || !loginrole.equalsIgnoreCase("ROLE_STUDENT") ){
			model.addAttribute("error", true);
			model.addAttribute("message", "Denied access for this operation!! Kindly login with Student role or contact Manager for access");
			return "login";
		}	
		// To check if student has access to course
		CThread thread = threadComponent.getThread(Long.parseLong(threadId));
		Course course = courseComponent.findCourseByID(thread.getCourse().getId());	
		if(!studentcomponent.checkIfStudentInCourse(course, loginuser)){
			model.addAttribute("error", true);
			model.addAttribute("message", "You have been denied access for this course!! Kindly login again");
			return "login";
		}		
		Date date = new Date();			
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");	
		String stringDate = dateFormat.format(date);
		//		String loginuser = (String) session.getAttribute( "loginuser" );
		//		User user = userComponent.loadUserByUsername(loginuser);
		String enteredBy = user.getFirstName() + " " + user.getLastName();

		//upload file
		String attachment = "";
		if (!mFile.isEmpty()) {

			name = name.substring(name.lastIndexOf("\\")+1);

			File file = new File("\\" + new Date().getTime() + "_" + name);
			System.out.println(file.getAbsolutePath());
			try {
				mFile.getFileItem().write(file);
				attachment = file.getAbsolutePath();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		Contribution contribution = new Contribution(message,
				attachment,
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
		//CThread thread = threadComponent.getThread(Long.parseLong(threadId));
		model.addAttribute("threadName",thread.getName());
		model.addAttribute("threadActive", thread.getIsActive());
		return "student_contributions";
	}

}
