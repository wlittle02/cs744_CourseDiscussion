package com.ocds.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
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
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		System.out.println("ok");
		return "course_report";
	}
}
