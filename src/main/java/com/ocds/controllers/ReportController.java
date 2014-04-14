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

@Controller
public class ReportController {
	
	
	@Autowired
	private CourseComponent coursecomponent;
	
	@Autowired
	private ThreadComponent threadcomponent;
	
	@Autowired
	private ReportsComponent reportscomponent;
	
	public  ReportController() { 
		System.out.println("CREATING Report CONTROLLER");
		
		
	}
	
	//Begin of Course
	@RequestMapping(value = "/courses_report", method = RequestMethod.GET)
	public String courseReport(ModelMap model) {	
		
		return "course_report";
	}
	@RequestMapping(value = "/get_courses_report", method = RequestMethod.POST)
	public String displaycourseReport(ModelMap model,
									@RequestParam(value = "startdate", required = true) String date,
									@RequestParam(value = "report_type", required = true) String report_type) {	
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");			
			Date dt = formatter.parse(date);			
			List<Course> courses = coursecomponent.getAllCourses();
			model.addAttribute("courses",courses);
			HashMap<Integer,Integer> threadcountmap = new HashMap<Integer,Integer>();
			for(int i=0;i<courses.size();i++){
				int threadcount=0;
				Course course = courses.get(i);
				if (report_type.equalsIgnoreCase("Weekly"))
					threadcount=reportscomponent.getThreadTotal(TimeType.EWeekly, dt, course.getId());
				if (report_type.equalsIgnoreCase("Monthly"))
					threadcount=reportscomponent.getThreadTotal(TimeType.EMonthly, dt, course.getId());
				if (report_type.equalsIgnoreCase("Yearly"))
					threadcount=reportscomponent.getThreadTotal(TimeType.EYearly, dt, course.getId());
				//List<CThread> threads = threadcomponent.getAllThreads(course.getId());
//				if (threads.isEmpty())
//					threadcount=0;
//				else
//					threadcount=threads.size();
				threadcountmap.put(course.getId(), threadcount);			
			}
			model.addAttribute("threadcountmap",threadcountmap);
			model.addAttribute("start_date", date);
			model.addAttribute("reporttype", report_type);
		}
		catch(Exception e){
			System.out.println(e);
		}
		System.out.println("ok");
		return "course_report";
	}
	
	@RequestMapping(value = "/get_contribution_report", method = RequestMethod.GET)
	public String displayThreadReport(ModelMap model,
									@RequestParam(value = "courseId", required = true) Integer courseId,
									@RequestParam(value = "startdate", required = true) String date,
									@RequestParam(value = "report_type", required = true) String report_type)
	{
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			
			Date dt = formatter.parse(date);
			HashMap<Integer,HashMap<Integer,Integer>> contcountmap = new HashMap<Integer,HashMap<Integer,Integer>>();
			List<CThread> threads = this.threadcomponent.getAllThreads(courseId);
			for (int i = 0; i < threads.size(); i++)
			{
				Integer totalcount = 0;
				Integer instrcount = 0;
				Integer tacount = 0;
				Integer studcount = 0;
				HashMap<Integer,Integer> cont = new HashMap<Integer,Integer>();
				if (report_type.equalsIgnoreCase("Weekly"))
				{
					totalcount = reportscomponent.getContributionTotal(TimeType.EWeekly, dt, threads.get(i).getId(), RoleType.EAll);
					instrcount = reportscomponent.getContributionTotal(TimeType.EWeekly, dt, threads.get(i).getId(), RoleType.EInstructor);
					tacount = reportscomponent.getContributionTotal(TimeType.EWeekly, dt, threads.get(i).getId(), RoleType.ETa);
					studcount = reportscomponent.getContributionTotal(TimeType.EWeekly, dt, threads.get(i).getId(), RoleType.EStudent);
				}
				else if (report_type.equalsIgnoreCase("Monthly"))
				{
					totalcount = reportscomponent.getContributionTotal(TimeType.EMonthly, dt, threads.get(i).getId(), RoleType.EAll);
					instrcount = reportscomponent.getContributionTotal(TimeType.EMonthly, dt, threads.get(i).getId(), RoleType.EInstructor);
					tacount = reportscomponent.getContributionTotal(TimeType.EMonthly, dt, threads.get(i).getId(), RoleType.ETa);
					studcount = reportscomponent.getContributionTotal(TimeType.EMonthly, dt, threads.get(i).getId(), RoleType.EStudent);
				}
				else if (report_type.equalsIgnoreCase("Yearly"))
				{
					totalcount = reportscomponent.getContributionTotal(TimeType.EYearly, dt, threads.get(i).getId(), RoleType.EAll);
					instrcount = reportscomponent.getContributionTotal(TimeType.EYearly, dt, threads.get(i).getId(), RoleType.EInstructor);
					tacount = reportscomponent.getContributionTotal(TimeType.EYearly, dt, threads.get(i).getId(), RoleType.ETa);
					studcount = reportscomponent.getContributionTotal(TimeType.EYearly, dt, threads.get(i).getId(), RoleType.EStudent);
				}
				cont.put(0,totalcount);
				cont.put(1,instrcount);
				cont.put(2,tacount);
				cont.put(3,studcount);
				contcountmap.put(Integer.parseInt(threads.get(i).getId().toString()), cont);
			}
			model.addAttribute("threads", threads);
			model.addAttribute("contcountmap", contcountmap);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return "thread_report";
	}
}
