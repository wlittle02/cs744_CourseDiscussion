package com.ocds.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ocds.Domain.Contribution;
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
	
	@RequestMapping(value = "/view_instructor_threads", method = RequestMethod.GET)
	public String viewThread(@RequestParam(value = "courseId", required = true) String courseId,HttpSession session, ModelMap model)
	{
		List<CThread> threads = threadComponent.getAllThreads(Integer.parseInt(courseId));
		if (threads!=null)
		model.addAttribute("threads",threads);
		model.addAttribute("courseId",courseId);
		Course course = courseComponent.findCourseByID(Integer.parseInt(courseId));
		model.addAttribute("courseName",course.getName());
		return "instructor_threads";
	}
	
	/**
	 * User to create new threads by the instructor. This action is attached to the submit button
	 * @param courseId
	 * @param threadName
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create_instructor_thread", method = RequestMethod.POST)
	public String createThread(@RequestParam(value = "courseId", required = true) String courseId,
							   @RequestParam(value = "threadName", required = true) String threadName,
			                   HttpSession session,
			                   ModelMap model)
	{
		Date date = new Date();			
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");	
		String stringDate = dateFormat.format(date);				
		if (threadComponent.findThreadByName(threadName)!=null){
			model.addAttribute("error", true);
			List<CThread> threads = threadComponent.getAllThreads(Integer.parseInt(courseId));
			model.addAttribute("threads",threads);
			Course course = courseComponent.findCourseByID(Integer.parseInt(courseId));
			model.addAttribute("courseName",course.getName());
			return "instructor_threads";
		}
		CThread newThread = new CThread(threadName,
									    this.courseComponent.findCourseByID(Integer.parseInt(courseId)),
									    stringDate,
									    "",
									    true);
		this.threadComponent.createThread(newThread);
		List<CThread> threads = threadComponent.getAllThreads(Integer.parseInt(courseId));
		model.addAttribute("threads",threads);
		Course course = courseComponent.findCourseByID(Integer.parseInt(courseId));
		model.addAttribute("courseName",course.getName());
		model.addAttribute("courseId", course.getId());
		return "instructor_threads";
	}
	
	@RequestMapping(value = "/view_instructor_contributions", method = RequestMethod.GET)
	public String view_contributions(@RequestParam(value = "threadId", required = true) String threadId,HttpSession session, ModelMap model)
	{
		List<Contribution> contributions = threadComponent.getAllContributions(Long.parseLong(threadId));
		if (contributions!=null)
		model.addAttribute("contributions",contributions);
		model.addAttribute("threadId",threadId);
		CThread thread = threadComponent.getThread(Long.parseLong(threadId));
		model.addAttribute("threadName",thread.getName());
		return "instructor_contributions";
	}
	@RequestMapping(value = "/create_instructor_Contribution", method = RequestMethod.POST)
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
		return "instructor_contributions";
	}
	@RequestMapping(value = "/set_instructor_Contribution", method = RequestMethod.GET)
	public String setContribution(@RequestParam(value = "isImportant", required = true) Boolean isImportant,
							   @RequestParam(value = "contributionId", required = true) Long contributionId,
			                   HttpSession session,
			                   ModelMap model)
	{
		
		Contribution contribution = threadComponent.getContribution(contributionId);
		
		threadComponent.contributionIsImportant(contribution, isImportant);
		List<Contribution> contributions = threadComponent.getAllContributions(contribution.getThread().getId());
		if (contributions!=null)
		model.addAttribute("contributions",contributions);
		String threadId = contribution.getThread().getId().toString();
		model.addAttribute("threadId",threadId);
		CThread thread = threadComponent.getThread(Long.parseLong(threadId));
		model.addAttribute("threadName",thread.getName());
		return "instructor_contributions";
	}
}
