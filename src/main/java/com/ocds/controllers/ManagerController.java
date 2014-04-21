package com.ocds.controllers;

//Test github 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocds.Dao.CourseComponent;
import com.ocds.Domain.Contribution;
import com.ocds.Domain.Course;
import com.ocds.users.User;

import flexjson.JSON;

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
	public String defaultPage() {
		
		//coursecomponent.updateSummaryID(2L, 6L);
		//List<Contribution> list = coursecomponent.getContributionForSummary(1L);
		List<Contribution> list = coursecomponent.getContributionLessThan(1L, 1L);
		
		for(Contribution c : list){
			System.out.println(c.getId());
			System.out.println(c.getSummaryId());
			System.out.println(c.getMessage());
			System.out.println("------");
		}	
			
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
	public String viewAllCourses(String date,ModelMap model, Principal principal) {
			
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
	public String enrollStudents(
			@RequestParam(value = "id", required = true) int id, 
			HttpSession session, ModelMap model) {
			
		Course course = coursecomponent.findCourseByID(id);
		List<User> unenrolled = coursecomponent.getAllUnenrolled(id);
		model.addAttribute("course", course);
		model.addAttribute("not_enrolled",unenrolled);
		return "course_enrollment";
	}
	
	@RequestMapping(value = "/enrollstudent_course", method = RequestMethod.POST)
	public String enrollStudents(
			@RequestParam(value = "id", required = true) int id, 
			@RequestParam(value = "to_enroll", required = false) List<Long> student_ids, 
			HttpSession session, ModelMap model) {
			
		if(student_ids != null){
			coursecomponent.enrollStudentToCourse(id, student_ids);
		}
		
		return "redirect:/enrollstudent_course?id=" + id;
	}
	
	@RequestMapping(value = "/removestudent_course", method = RequestMethod.POST)
	public String removeStudent(
			@RequestParam(value = "id", required = true) int id, 
			@RequestParam(value = "to_remove", required = false) List<Long> student_ids, 
			HttpSession session, ModelMap model) {
			
		if(student_ids != null){
			coursecomponent.removeStudentFromCourse(id, student_ids);
		}
		
		
		return "redirect:/enrollstudent_course?id=" + id;
	}

	@RequestMapping(value = "/signta_course", method = RequestMethod.GET)
	public String signTAs(
			@RequestParam(value = "id", required = true) int id, 
			HttpSession session, ModelMap model) {
			
		Course course = coursecomponent.findCourseByID(id);
		
		List<User> not_signed = coursecomponent.getAllUnsignedTAs(id);
		
		model.addAttribute("course", course);
		model.addAttribute("not_signed",not_signed);
		return "course_signtas";
	}
	
	@RequestMapping(value = "/signtas_course", method = RequestMethod.POST)
	public String signTAs(
			@RequestParam(value = "id", required = true) int id, 
			@RequestParam(value = "to_sign",required = false) List<Long> ta_ids, 
			HttpSession session, ModelMap model) {
			
		if(ta_ids != null){
			coursecomponent.signTAToCourse(id, ta_ids);
		}
		
		return "redirect:/signta_course?id=" + id;
	}
	
	@RequestMapping(value = "/resigntas_course", method = RequestMethod.POST)
	public String resignTAs(
			@RequestParam(value = "id", required = true) int id, 
			@RequestParam(value = "to_resign", required = false) List<Long> ta_ids, 
			HttpSession session, ModelMap model) {
			
		
		if(ta_ids != null){
			coursecomponent.resignTaFromCourse(id, ta_ids);
		}
		
		return "redirect:/signta_course?id=" + id;
	}
	
	@RequestMapping(value = "/summarize_thread", method = RequestMethod.GET)
	public String summarizeThread(
			@RequestParam(value = "thread_id", required = true) Long thread_id, 
			HttpSession session, ModelMap model) {
			
		model.addAttribute("thread", coursecomponent.getThreadByID(thread_id));
		model.addAttribute("contributions",coursecomponent.getContributionForSummary(thread_id));
		return "instructor_summary";
	}
	
	@RequestMapping(value = "/reorder_contribution", method = RequestMethod.GET)
	public String reorderContribution(
			@RequestParam(value = "thread_id", required = true) Long thread_id,
			@RequestParam(value = "contribution_id", required = true) Long contribution_id,
			@RequestParam(value = "move", required = true) String move,
			HttpSession session, ModelMap model){
		
		if(move.equals("up")){
			Long temp = coursecomponent.getContributionByID(contribution_id).getSummaryId();
			List<Contribution> list = coursecomponent
					.getContributionLessThan(temp, thread_id);
			if(list.size() != 0){
				coursecomponent.updateSummaryID(contribution_id, list.get(list.size()-1).getSummaryId());
				coursecomponent.updateSummaryID(list.get(list.size()-1).getId(), temp);
			}
		}else if(move.equals("down")){
			Long temp = coursecomponent.getContributionByID(contribution_id).getSummaryId();
			List<Contribution> list = coursecomponent
					.getContributionLargerThan(temp, thread_id);
			if(list.size() != 0){
				coursecomponent.updateSummaryID(contribution_id, list.get(0).getSummaryId());
				coursecomponent.updateSummaryID(list.get(0).getId(), temp);
			}
		}else{
			System.out.println("error(reorderContribution)!");
		}
		return "redirect:/summarize_thread?thread_id=" + thread_id;
	}
	
	@RequestMapping(value = "/shutdown_thread", method = RequestMethod.GET)
	public String shutDownThread(
			@RequestParam(value = "thread_id", required = true) Long thread_id, 
			HttpSession session, ModelMap model) {
		
		coursecomponent.setThreadInactive(thread_id);
		return "redirect:/view_instructor_threads?courseId=" + coursecomponent.getThreadByID(thread_id).getCourse().getId();
	}
		
	/*@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String handleFormUpload(HttpServletRequest req) {
		
		return "upload";
	}
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("name") String name,
			@RequestParam("file") CommonsMultipartFile mFile,
			HttpServletRequest req) {
		if (!mFile.isEmpty()) {
			
			name = name.substring(name.lastIndexOf("\\")+1);
			
			File file = new File("\\" + new Date().getTime() + "_" + name);
			System.out.println(file.getAbsolutePath());
			try {
				mFile.getFileItem().write(file);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "redirect:/";
		} else {
			return "redirect:/";
		}
	}*/
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)    
    public void downloadFile(@RequestParam String fileName,
    		HttpServletResponse response,HttpServletRequest req){    
        response.setCharacterEncoding("utf-8");    
        response.setContentType("multipart/form-data");
    
        response.setHeader("Content-Disposition", "attachment;fileName="+fileName.substring(fileName.lastIndexOf("\\")+1));    
        try {    
            File file=new File(fileName);    
            System.out.println(file.getAbsolutePath());    
            InputStream inputStream=new FileInputStream(file);    
            OutputStream os=response.getOutputStream();    
            byte[] b=new byte[1024];    
            int length;    
            while((length=inputStream.read(b))>0){    
                os.write(b,0,length);    
            }    
            inputStream.close();    
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
    }   
	
	@RequestMapping(value = "/ajax_contribution", method = RequestMethod.GET)    
    public void ajaxContribution (
    		HttpServletRequest request, HttpServletResponse response){    
           
		response.setCharacterEncoding("UTF-8");
		
		try{
			
			//response.getWriter().write(String.valueOf(coursecomponent.getContributionByThreadID(1L).size()));
			response.getWriter().write(new Integer(coursecomponent.getContributionByThreadID(1L).size()).toString());
			
		}catch(Exception e){
			
		}
		
		
		//model.addAttribute("size", coursecomponent.getContributionByThreadID(threadId).size());
    }
	
	/*@RequestMapping(value = "/ajax_contribution", method = RequestMethod.GET) 
	public void ajaxContribution(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		System.out.println("sdfadsfadsfadsf");
		StringBuffer sb = new StringBuffer();

		sb.append("adfasdf");
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
		out.close();
	}*/
	
	/*@RequestMapping(value = "/ajax_contribution", method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		StringBuffer sb = new StringBuffer();
		
		System.out.println(request.getParameter("threadId"));
		
		sb.append(coursecomponent.getContributionByThreadID(Long.parseLong(request.getParameter("threadId"))).size());
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
		out.close();
	}*/
	
	
	/*@RequestMapping(value = "/summarize_thread", method = RequestMethod.POST)
	public String summarize_thread(
			@RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "new_ids", required = true) List<Long> new_ids,
			@RequestParam(value = "old_ids", required = true) List<Long> old_ids,
			HttpSession session, ModelMap model) {
			
		
		return "redirect:/signta_course?id=" + id;
	}*/
}
