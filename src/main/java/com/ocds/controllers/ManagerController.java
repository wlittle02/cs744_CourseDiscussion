package com.ocds.controllers;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

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
	
	private static int[] YEARS;
	private static int[] SECTIONS;
	private static String[] STATUS;
	private static String[] SEMESTERS;
	
	@Autowired
	private CourseComponent coursecomponent;
	
	public  ManagerController() { 
		System.out.println("CREATING Manager CONTROLLER");
		
		Calendar c = Calendar.getInstance();
		int current_year = c.get(Calendar.YEAR);
		YEARS = new int[3];
		YEARS[0] = current_year;
		YEARS[1] = current_year + 1;
		YEARS[2] = current_year + 2;
		
		SECTIONS = new int[5];
		for(int i=0; i<5; i++){
			SECTIONS[i] = i+1;
		}
		
		STATUS = new String[2];
		STATUS[0] = "Active";
		STATUS[1] = "Incctive";
		
		SEMESTERS = new String[4];
		SEMESTERS[0] = "Spring";
		SEMESTERS[1] = "Summer";
		SEMESTERS[2] = "Fall";
		SEMESTERS[3] = "Winter";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultpage() {
		return "redirect:/login";
	}
	
	
			
	/*@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		System.out.println(coursecomponent.getCourseListForStudent("chen.gong"));
		return "course_enrollment";
	}*/
	
	//Begin of Course
	@RequestMapping(value = "/register_course", method = RequestMethod.GET)
	public String courseRegister(ModelMap model) {
		
		model.addAttribute("years",YEARS);
		model.addAttribute("sections",SECTIONS);
		model.addAttribute("status",STATUS);
		model.addAttribute("semesters",SEMESTERS);
		
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
		User instrutor = coursecomponent.getUserByID(instructor_id);
		Course course = new Course(name,year,semester,state,id_num,section_num,instrutor);
		if (coursecomponent.findCourseByName_Section(id_num,section_num).size() != 0){
			model.addAttribute("years",YEARS);
			model.addAttribute("sections",SECTIONS);
			model.addAttribute("status",STATUS);
			model.addAttribute("semesters",SEMESTERS);	
			model.addAttribute("instructors",coursecomponent.getAllInstructor());
			model.addAttribute("error", true);
			return "course_register";
		}else{
			coursecomponent.addCourse(course);
			return "redirect:/enrollstudent_course?id="
					+coursecomponent.findCourseByName_Section(
							course.getId_num(),course.getSection_num()).get(0).getId();
		}
	}
	
	@RequestMapping(value = "/view_course")
	public String viewallusers(String date,ModelMap model, Principal principal) {
			
		List<Course> courses = coursecomponent.getAllCourses();
	 	model.addAttribute("courses", courses);
	 	return "course_view";
	}
	
	@RequestMapping(value = "/modify_courses", method = RequestMethod.GET)
	public String courseModify(
			@RequestParam(value = "id", required = true) int id, 
			HttpSession session, ModelMap model) {
			
		Course course = coursecomponent.findCourseByID(id);
		List<User> instructors = coursecomponent.getAllInstructor();
		Set<User> students = course.getStudents(); 
		
		for(User stu : students){
			for(User ins : instructors){
				if(ins.getId() == stu.getId()){
					instructors.remove(ins);
					break;
				}
			}
		}
		
		model.addAttribute("years",YEARS);
		model.addAttribute("status",STATUS);
		model.addAttribute("semesters",SEMESTERS);
		
		model.addAttribute("instructors",instructors);
		model.addAttribute("course", course);
		
		//String loginuser = (String)session.getAttribute("loginuser");
		return "course_modify";
	}
	
	@RequestMapping(value = "/modify_courses", method = RequestMethod.POST)
	public String courseModify(
			@RequestParam(value = "id", required = true) int id, 
			//@RequestParam(value = "name", required = true) String name, 
			@RequestParam(value = "year", required = true) String year,
			@RequestParam(value = "semester", required = true) String semester,
			@RequestParam(value = "state", required = true) String state,
			//@RequestParam(value = "id_num", required = true) String id_num,
			//@RequestParam(value = "section_num", required = true) int section_num,
			@RequestParam(value = "instructor_id", required = true) int instructor_id,
			HttpSession session, ModelMap model) {
		
		coursecomponent.updateCourse(id, year,
				semester,state, instructor_id);
		
		
		return "redirect:/view_course";
	}
	
	@RequestMapping(value = "/delete_course")
	public String deleteCourse(
			@RequestParam(value = "id", required = true) int id, 
			HttpSession session, ModelMap model) {
			
		Course course = coursecomponent.findCourseByID(id);
		
		coursecomponent.deleteCourse(course);
		
		String loginuser = (String) session.getAttribute( "loginuser" );
	 	model.addAttribute("loginuser",loginuser);
		return "redirect:/view_course";
	}
	
	@RequestMapping(value = "/enrollstudent_course", method = RequestMethod.GET)
	public String enrollStudent(
			@RequestParam(value = "id", required = true) int id, 
			HttpSession session, ModelMap model) {
			
		Course course = coursecomponent.findCourseByID(id);
		List<User> unenrolled = coursecomponent.getAllUnenrolled(id);
		model.addAttribute("course", course);
		model.addAttribute("not_enrolled",unenrolled);
		return "course_enrollment";
	}
	
	@RequestMapping(value = "/enrollstudent_course", method = RequestMethod.POST)
	public String enrollStuden(
			@RequestParam(value = "id", required = true) int id, 
			@RequestParam(value = "to_enroll", required = true) List<Long> student_ids, 
			HttpSession session, ModelMap model) {
			

		coursecomponent.enrollStudentToCourse(id, student_ids);

		return "redirect:/enrollstudent_course?id=" + id;
	}
	
	@RequestMapping(value = "/removestudent_course", method = RequestMethod.POST)
	public String removeStudent(
			@RequestParam(value = "id", required = true) int id, 
			@RequestParam(value = "to_remove", required = true) List<Long> student_ids, 
			HttpSession session, ModelMap model) {
			
		coursecomponent.removeStudentFromCourse(id, student_ids);
		
		return "redirect:/enrollstudent_course?id=" + id;
	}
	
	//End of Course
}
