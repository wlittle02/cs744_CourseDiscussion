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
	/*
	@RequestMapping(value = "/view_thread", method = RequestMethod.GET)
	public String viewThread(@RequestParam(value = "courseId", required = true) String courseId,HttpSession session, ModelMap model)
	{
		List<CThread> threadList = threadComponent.getAllThreads(Integer.parseInt(courseId));
		model.addAttribute("threads",threadList);
		model.addAttribute("courseId", courseId);
		return "student_class";
	}
	
	// @RequestMapping(value = "/createThread", method = RequestMethod.GET)
	public String createThread(@RequestParam(value = "courseId", required = true) String courseId,
							   @RequestParam(value = "name", required = true) String name,
							   ModelMap modelmap)
   {
	   DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	   Date date = new Date();
	   CThread thread = new CThread(name, courseComponent.findCourseByID(Integer.parseInt(courseId)), dateFormat.format(date), "", true);
	   threadComponent.createThread(thread);
	   List<CThread> threads = threadComponent.getAllThreads(Integer.parseInt(courseId));
	   modelmap.addAttribute("threads",threads);
	   modelmap.addAttribute("courseId",courseId);
	   return "student_class";
   }
	*/
	
	/*
	@RequestMapping(value = "/deleteThread")
	public String deleteThread(@RequestParam(value = "threadId", required = true) String threadId, ModelMap model)
	{
		CThread thread = this.threadComponent.getThread(Long.parseLong(threadId));
		int courseId = thread.getCourse().getId();
		threadComponent.deleteThread(thread);
		List<CThread> threads = threadComponent.getAllThreads(courseId);
		model.addAttribute("threads", threads);
		return "redirect:/student_class";
	}*/
	
	/*@RequestMapping(value = "/createContribution")
	public String createContribution(@RequestParam(value = "threadId", required = true) String threadId,
									 @RequestParam(value = "message", required = true) String message,
									 @RequestParam(value = "attachment", required = true) String attachment,
									 ModelMap modelmap, HttpSession session)
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		CThread thread = this.threadComponent.getThread(Long.parseLong(threadId));
		Contribution contribute = new Contribution(message,
												   attachment,
												   false,
												   session.getAttribute("loginuser").toString(),
												   thread,
												   null,
												   dateFormat.format(date));
		this.threadComponent.createContribution(contribute);
		return "redirect:/student_contribution";
	}/*
	/*
	//@RequestMapping(value = "/createContribution", method = RequestMethod.GET)
	public String createContribution(@RequestParam(value = "threadId", required = true) String threadId, ModelMap modelmap, HttpSession session)
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		Contribution newContribution = new Contribution("This is a test contribution",
														"",
														false,
														session.getAttribute("loginuser").toString(),
														this.threadComponent.getThread(Long.parseLong(threadId)),
														0L,
														dateFormat.format(date));
		this.threadComponent.createContribution(newContribution);
		
		List<Contribution> allContributions = this.threadComponent.getAllContributions(Long.parseLong(threadId));
		
		for (int i = 0; i < allContributions.size(); i++)
		{
			User user = this.userComponent.loadUserByUsername(allContributions.get(i).getContribution().getEnteredBy());
			allContributions.get(i).setUserName(user.getFirstName() + " " + user.getLastName());
		}
		
		modelmap.addAttribute("contributions",allContributions);
		modelmap.addAttribute("threadId", threadId);
		
		return "redirect:/student_contribution";
	}
	
	@RequestMapping(value = "/getAllContributions", method = RequestMethod.GET)
	public String getAllContributions(@RequestParam(value = "threadId", required = true) String threadId, ModelMap modelmap)
	{
		List<Contribution> allContributions = this.threadComponent.getAllContributions(Long.parseLong(threadId));
		
		for (int i = 0; i < allContributions.size(); i++)
		{
			User user = this.userComponent.loadUserByUsername(allContributions.get(i).getContribution().getEnteredBy());
			allContributions.get(i).setUserName(user.getFirstName() + " " + user.getLastName());
		}
		
		modelmap.addAttribute("contributions",allContributions);
		modelmap.addAttribute("threadId", threadId);
		return "/student_contribution";
	}*/
}
