package com.ocds.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocds.Dao.CourseComponent;
import com.ocds.Dao.ReportsComponent;
import com.ocds.Dao.ReportsComponent.RoleType;
import com.ocds.Dao.ReportsComponent.TimeType;
import com.ocds.Dao.ThreadComponent;
import com.ocds.Domain.CThread;
import com.ocds.Domain.Course;
import com.ocds.users.Role;
import com.ocds.users.User;
import com.ocds.users.UserComponent;

@Controller
public class ReportController {


	@Autowired
	private CourseComponent coursecomponent;

	@Autowired
	private ThreadComponent threadcomponent;

	@Autowired
	private ReportsComponent reportscomponent;

	@Autowired
	private UserComponent userComponent;

	public  ReportController() { 
		System.out.println("CREATING Report CONTROLLER");


	}

	//Begin of Course
	@RequestMapping(value = "/courses_report", method = RequestMethod.GET)
	public String courseReport(HttpSession session,ModelMap model) {
		//To check if user is logged in
		String loginuser = (String) session.getAttribute( "loginuser" );
		if (loginuser==null){				
			model.addAttribute("error", true);
			model.addAttribute("message", "Login credential not found. Kindly login.");
			return "login";
		}
		// To check if logged in user has Manager role & logged in with Manager role
		User user = userComponent.loadUserByUsername(loginuser);
		String loginrole = (String) session.getAttribute( "loginrole" );
		if (!user.hasRole("ROLE_ADMIN") || !loginrole.equalsIgnoreCase("ROLE_ADMIN") ){
			model.addAttribute("error", true);
			model.addAttribute("message", "Denied access for this operation!! Kindly login with Manager role or contact Manager for access");
			return "login";
		}		
		model.addAttribute("reportActive", false);
		return "course_report";
	}
	@RequestMapping(value = "/get_courses_report", method = RequestMethod.POST)
	public String displaycourseReport(HttpSession session,ModelMap model,
			@RequestParam(value = "startdate", required = true) String date,
			@RequestParam(value = "report_type", required = true) String report_type) {	
		//To check if user is logged in
		String loginuser = (String) session.getAttribute( "loginuser" );
		if (loginuser==null){				
			model.addAttribute("error", true);
			model.addAttribute("message", "Login credential not found. Kindly login.");
			return "login";
		}
		// To check if logged in user has Manager role & logged in with Manager role
		User user = userComponent.loadUserByUsername(loginuser);
		String loginrole = (String) session.getAttribute( "loginrole" );
		if (!user.hasRole("ROLE_ADMIN") || !loginrole.equalsIgnoreCase("ROLE_ADMIN") ){
			model.addAttribute("error", true);
			model.addAttribute("message", "Denied access for this operation!! Kindly login with Manager role or contact Manager for access");
			return "login";
		}		
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");			
			Date dt = formatter.parse(date);	
			Date enddate = null;
			String end_date = "";
			List<Course> courses = coursecomponent.getAllCourses();
			model.addAttribute("courses",courses);
			HashMap<Integer,Integer> threadcountmap = new HashMap<Integer,Integer>();
			for(int i=0;i<courses.size();i++){
				int threadcount=0;
				Course course = courses.get(i);
				if (report_type.equalsIgnoreCase("Weekly"))
				{
					threadcount=reportscomponent.getThreadTotal(TimeType.EWeekly, dt, course.getId());
					enddate = reportscomponent.getEndDate(TimeType.EWeekly, dt);
				}
				if (report_type.equalsIgnoreCase("Monthly"))
				{
					threadcount=reportscomponent.getThreadTotal(TimeType.EMonthly, dt, course.getId());
					enddate = reportscomponent.getEndDate(TimeType.EMonthly, dt);
				}
				if (report_type.equalsIgnoreCase("Yearly"))
				{
					threadcount=reportscomponent.getThreadTotal(TimeType.EYearly, dt, course.getId());
					enddate = reportscomponent.getEndDate(TimeType.EYearly, dt);
				}
				//List<CThread> threads = threadcomponent.getAllThreads(course.getId());
				//				if (threads.isEmpty())
				//					threadcount=0;
				//				else
				//					threadcount=threads.size();
				if (enddate != null)
				{
					if (enddate.getMonth() < 10)
					{
						end_date = "0" + Integer.toString(enddate.getMonth() + 1) + "/";
					}
					else
					{
						end_date = Integer.toString(enddate.getMonth() + 1) + "/";
					}
					if (enddate.getDate() < 10)
					{
						end_date = end_date + "0" + Integer.toString(enddate.getDate()) + "/";
					}
					else
					{
						end_date = end_date + Integer.toString(enddate.getDate()) + "/";
					}
					end_date = end_date + Integer.toString(enddate.getYear() + 1900);
				}
				threadcountmap.put(course.getId(), threadcount);			
			}
			model.addAttribute("threadcountmap",threadcountmap);
			model.addAttribute("start_date", date);
			model.addAttribute("end_date", end_date);
			model.addAttribute("reporttype", report_type);
			model.addAttribute("reportActive", true);
		}
		catch(Exception e){
			System.out.println(e);
		}
		System.out.println("ok");
		return "course_report";
	}

	@RequestMapping(value = "/get_contribution_report", method = RequestMethod.GET)
	public String displayThreadReport(HttpSession session,ModelMap model,
			@RequestParam(value = "courseId", required = true) Integer courseId,
			@RequestParam(value = "startdate", required = true) String date,
			@RequestParam(value = "enddate", required = true) String end_date,
			@RequestParam(value = "report_type", required = true) String report_type)
	{
		//To check if user is logged in
		String loginuser = (String) session.getAttribute( "loginuser" );
		if (loginuser==null){				
			model.addAttribute("error", true);
			model.addAttribute("message", "Login credential not found. Kindly login.");
			return "login";
		}
		// To check if logged in user has Manager role & logged in with Manager role
		User user = userComponent.loadUserByUsername(loginuser);
		String loginrole = (String) session.getAttribute( "loginrole" );
		if (!user.hasRole("ROLE_ADMIN") || !loginrole.equalsIgnoreCase("ROLE_ADMIN") ){
			model.addAttribute("error", true);
			model.addAttribute("message", "Denied access for this operation!! Kindly login with Manager role or contact Manager for access");
			return "login";
		}		
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			Date dt = formatter.parse(date);
			HashMap<Long,ArrayList<Integer>> contcountmap = new HashMap<Long,ArrayList<Integer>>();
			List<CThread> threads = this.threadcomponent.getAllThreads(courseId);
			for (int i = 0; i < threads.size(); i++)
			{
				Integer totalcount = 0;
				Integer impcount = 0;
				Integer instrcount = 0;
				Integer tacount = 0;
				Integer studcount = 0;
				ArrayList<Integer> contributionscount = new ArrayList<Integer>();
				if (report_type.equalsIgnoreCase("Weekly"))
				{
					totalcount = reportscomponent.getContributionTotal(TimeType.EWeekly, dt, threads.get(i).getId(), RoleType.EAll);
					impcount = reportscomponent.getImportantContributionTotal(TimeType.EWeekly, dt, threads.get(i).getId(), RoleType.EAll);
					instrcount = reportscomponent.getContributionTotal(TimeType.EWeekly, dt, threads.get(i).getId(), RoleType.EInstructor);
					tacount = reportscomponent.getContributionTotal(TimeType.EWeekly, dt, threads.get(i).getId(), RoleType.ETa);
					studcount = reportscomponent.getContributionTotal(TimeType.EWeekly, dt, threads.get(i).getId(), RoleType.EStudent);
				}
				else if (report_type.equalsIgnoreCase("Monthly"))
				{
					totalcount = reportscomponent.getContributionTotal(TimeType.EMonthly, dt, threads.get(i).getId(), RoleType.EAll);
					impcount = reportscomponent.getImportantContributionTotal(TimeType.EMonthly, dt, threads.get(i).getId(), RoleType.EAll);
					instrcount = reportscomponent.getContributionTotal(TimeType.EMonthly, dt, threads.get(i).getId(), RoleType.EInstructor);
					tacount = reportscomponent.getContributionTotal(TimeType.EMonthly, dt, threads.get(i).getId(), RoleType.ETa);
					studcount = reportscomponent.getContributionTotal(TimeType.EMonthly, dt, threads.get(i).getId(), RoleType.EStudent);
				}
				else if (report_type.equalsIgnoreCase("Yearly"))
				{
					totalcount = reportscomponent.getContributionTotal(TimeType.EYearly, dt, threads.get(i).getId(), RoleType.EAll);
					impcount = reportscomponent.getImportantContributionTotal(TimeType.EYearly, dt, threads.get(i).getId(), RoleType.EAll);
					instrcount = reportscomponent.getContributionTotal(TimeType.EYearly, dt, threads.get(i).getId(), RoleType.EInstructor);
					tacount = reportscomponent.getContributionTotal(TimeType.EYearly, dt, threads.get(i).getId(), RoleType.ETa);
					studcount = reportscomponent.getContributionTotal(TimeType.EYearly, dt, threads.get(i).getId(), RoleType.EStudent);
				}
				System.out.println(totalcount);
				contributionscount.add(0, totalcount);
				contributionscount.add(1,impcount);
				contributionscount.add(2,instrcount);
				contributionscount.add(3,tacount);
				contributionscount.add(4,studcount);
				
				contcountmap.put(threads.get(i).getId(), contributionscount);
			}
			model.addAttribute("threads", threads);
			model.addAttribute("contcountmap", contcountmap);
			model.addAttribute("start_date", date);
			model.addAttribute("end_date", end_date);
			model.addAttribute("reporttype", report_type);
			Course course = coursecomponent.findCourseByID(courseId);
			model.addAttribute("coursename", course.getName());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return "thread_report";
	}
}
