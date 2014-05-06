package com.ocds.Dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ocds.Domain.CThread;
import com.ocds.Domain.Contribution;
import com.ocds.Domain.Course;
import com.ocds.users.User;


@Component
public class CourseComponent {

	@Autowired
	EntityManagerFactory entitymanagerfactory;
	
	public  CourseComponent() { 
		System.out.println("CREATING Course Component");
	}
	
	public void addCourse(Course course){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		
		entitymanager.merge(course); 
		
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
	}
	
	public List<Course> getAllCourses(){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		List<Course> courses = entitymanager.createQuery("SELECT course FROM Course course", Course.class)
				.getResultList();
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		
		
		return courses;
	}
	
	public Course findCourseByID(int id) {
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  

		List<Course> list = entitymanager.createQuery("SELECT course FROM Course course where course.id = ?1", Course.class).setParameter(1, id)
				.getResultList();

		Course course =  (list == null || list.size() == 0 ? null : list.get(0));
		
		entitymanager.getTransaction().commit();  
		entitymanager.close();

		return course;
	}
	
	public List<Course> findCourseByName(String name) {
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  

		List<Course> list = entitymanager.createQuery("SELECT course FROM Course course where course.name = ?1", Course.class).setParameter(1, name)
				.getResultList();

		
		entitymanager.getTransaction().commit();  
		entitymanager.close();

		return list;
	}
	
	public List<Course> findCourseByName_Section(String id_num, int section_num){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  

		List<Course> list = entitymanager
				.createQuery("SELECT course FROM Course course where course.id_num = ?1 and course.section_num = ?2", Course.class)
				.setParameter(1, id_num)
				.setParameter(2, section_num)
				.getResultList();

		
		entitymanager.getTransaction().commit();  
		entitymanager.close();

		return list; 
	}
	
	public List<Course> findCourseByIDNum_Name(String id_num, String name){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  

		List<Course> list = entitymanager
				.createQuery("SELECT course FROM Course course where course.name = ?1 and course.section_num = ?2", Course.class)
				.setParameter(1, id_num)
				.setParameter(2, name)
				.getResultList();

		
		entitymanager.getTransaction().commit();  
		entitymanager.close();

		return list; 
	}	
	
	public void updateCourse(int course_id,String year,
			String semester,String state,int instructor_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		
		Course course = entitymanager.createQuery("SELECT course FROM Course course where course.id = ?1", Course.class).setParameter(1, course_id)
				.getResultList().get(0);
		course.setYear(year);
		course.setSemester(semester);
		course.setState(state);
		
		User instructor = entitymanager.createQuery("SELECT user FROM User user where id = ?1", User.class)
				.setParameter(1, Long.valueOf(instructor_id)).getResultList().get(0);
		
		entitymanager.refresh(instructor);
		
		course.setInstructor(instructor);
		entitymanager.merge(course);
		
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
	}
	
	public void deleteCourse(Course course){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		
		course.setInstructor(null);
		course.setStudents(null);
		course.setTAs(null);
		
		deleteThread(course.getId());
		entitymanager.remove(entitymanager.merge(course));
		
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
	}
	
	public void deleteThread(int course_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  

		List<CThread> list = entitymanager
				.createQuery("SELECT thread FROM CThread thread where thread.course.id = ?1", CThread.class)
				.setParameter(1, course_id)
				.getResultList();

		for(CThread thread : list){
			deleteContribution(thread.getId());
			thread.setCourse(null);
			entitymanager.remove(entitymanager.merge(thread));
			
		}
		
		entitymanager.getTransaction().commit();  
		entitymanager.close();
	}
	
	public void deleteContribution(Long thread_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  

		List<Contribution> list = entitymanager
				.createQuery("SELECT contribution FROM Contribution contribution where contribution.thread.id = ?1", Contribution.class)
				.setParameter(1, thread_id)
				.getResultList();

		for(Contribution contri : list){
			contri.setThread(null);
			entitymanager.remove(entitymanager.merge(contri));
			
		}
		
		entitymanager.getTransaction().commit();  
		entitymanager.close();
		
	}
	
	
	
	/*this inappropriate to be here
	It's supposed to implentment at UserComponent
	 */
	public List<User> getAllInstructor(){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		
		List<User> list = entitymanager.createQuery
				("SELECT user FROM User user JOIN user.authorities ua where ua.id = 2", User.class)
				.getResultList();
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		return list;
	}
	
	public List<User> getAllStudent(){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		
		List<User> list = entitymanager.createQuery
				("SELECT user FROM User user JOIN user.authorities ua where ua.id = 3", User.class)
				.getResultList();
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		return list;
	} 
	
	public List<User> getAllUnenrolled(int course_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin(); 
		
		List<User> all = entitymanager.createQuery
				("SELECT user FROM User user JOIN user.authorities ua where ua.id = 3", User.class)
				.getResultList();
		Set<User> enrolled = findCourseByID(course_id).getStudents();
		Set<User> TAs = findCourseByID(course_id).getTAs();

		for(int i=0; i<all.size(); i++){
			if(findCourseByID(course_id).getInstructor().getId() == all.get(i).getId()){
				all.remove(i);
				i--;
			}else{
				for(User u : enrolled){
					if(u.getId() == all.get(i).getId()){
						all.remove(i);
						i--;
						break;
					}	
				}
			}
		}
		
		//Remove student who is ta for this course
		for(int i=0; i<all.size(); i++){
			for(User u : TAs){
				if(u.getId() == all.get(i).getId()){
					all.remove(i);
					i--;
					break;
				}	
			}
		}
		
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		return all;
	}
	
	public User getUserByID(int id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		List<User> list = entitymanager.createQuery
				("SELECT user FROM User user where id = ?1", User.class)
				.setParameter(1, Long.valueOf(id)).getResultList();
		
		
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		
		if (list.size() != 1)
			return null;
		else
			return list.get(0);
	}
	
	public List<User> getUserListByID(List<Long> ids){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		List<User> list = entitymanager.createQuery
				("SELECT user FROM User user WHERE id IN ?1", User.class)
				.setParameter(1, ids).getResultList();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		
		return list;
	}
	
	public void enrollStudentToCourse(int course_id, List<Long> student_ids){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		try
		{
		List<User> students = entitymanager.createQuery
				("SELECT user FROM User user WHERE id IN ?1", User.class)
				.setParameter(1, student_ids).getResultList();
		
		Course course = entitymanager.createQuery
				("SELECT course FROM Course course where course.id = ?1", Course.class)
				.setParameter(1, course_id).getResultList().get(0);
		
		for(User u : students){
			entitymanager.refresh(u);
			course.getStudents().add(u);
		}
		
		entitymanager.merge(course);
		
		
		entitymanager.getTransaction().commit();
		}
		catch (Exception e)
		{
			// Do Nothing
		}
		entitymanager.close();
		
	}
	
	public void removeStudentFromCourse(int course_id, List<Long> student_ids){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		List<User> students = entitymanager.createQuery
				("SELECT user FROM User user WHERE id IN ?1", User.class)
				.setParameter(1, student_ids).getResultList();
		
		Course course = entitymanager.createQuery
				("SELECT course FROM Course course where course.id = ?1", Course.class)
				.setParameter(1, course_id).getResultList().get(0);
		
		for(User u : students){
			entitymanager.refresh(u);
			course.getStudents().remove(u);
		}
		
		entitymanager.merge(course);
		
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		
	}
	
	public List<Course> getAllCoursesforInstructor(User instructor){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		List<Course> courses = entitymanager.createQuery("SELECT course FROM Course course where course.instructor = ?1", Course.class)
				.setParameter(1, instructor).getResultList();
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		
		
		return courses;
	}
	public List<Course> getAllCoursesforTa(String username){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		List<Course> courses =  entitymanager.createQuery(
				"SELECT course FROM Course course JOIN course.TAs cs "
				+ "where cs.username = ?1", Course.class)
				.setParameter(1, username)
				.getResultList();
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		
		
		return courses;
	}
	
	
	public List<Course> getCourseListForStudent(String username){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin(); 
		
		List<Course> courses =  entitymanager.createQuery(
				"SELECT course FROM Course course JOIN course.students cs "
				+ "where cs.username = ?1", Course.class)
				.setParameter(1, username)
				.getResultList();
		
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		return courses;
	}
	
	public void removeAllCoursesOfStudent(String username){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();	
		
		List<Course> courses =  entitymanager.createQuery(
				"SELECT course FROM Course course JOIN course.students cs "
				+ "where cs.username = ?1", Course.class)
				.setParameter(1, username)
				.getResultList();
		
		User student = User.findUserByName(username);
		List<User> students = entitymanager.createQuery
				("SELECT user FROM User user WHERE id = ?1", User.class)
				.setParameter(1, student.getId()).getResultList();
		if (courses!=null){			
			for(Course course : courses ){
				for(User u : students){
					entitymanager.refresh(u);
					course.getStudents().remove(u);
				}				
				entitymanager.merge(course);
			}
		}		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	public void removeAllCoursesOfTa(String username){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();	
		
		List<Course> courses =  entitymanager.createQuery(
				"SELECT course FROM Course course JOIN course.TAs cs "
				+ "where cs.username = ?1", Course.class)
				.setParameter(1, username)
				.getResultList();
		
		User ta = User.findUserByName(username);
		List<User> tas = entitymanager.createQuery
				("SELECT user FROM User user WHERE id = ?1", User.class)
				.setParameter(1, ta.getId()).getResultList();
		if (courses!=null){			
			for(Course course : courses ){
				for(User u : tas){
					entitymanager.refresh(u);
					course.getTAs().remove(u);
				}				
				entitymanager.merge(course);
			}
		}		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	public void removeAllCoursesOfInstructor(String username){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();	
		User instructor = User.findUserByName(username);
		List<Course> courses =  entitymanager.createQuery(
				"SELECT course FROM Course course where course.instructor = ?1", Course.class)
				.setParameter(1, instructor).getResultList();
		
		
		List<User> instructors = entitymanager.createQuery
				("SELECT user FROM User user WHERE id = ?1", User.class)
				.setParameter(1, instructor.getId()).getResultList();
		if (courses!=null){			
			for(Course course : courses ){
				for(User u : instructors){
					course.setInstructor(null);
				}				
				entitymanager.merge(course);
			}
		}		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	
//For TAs	
	public List<User> getAllUnsignedTAs(int course_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin(); 
		
		List<User> all = entitymanager.createQuery
				("SELECT user FROM User user JOIN user.authorities ua where ua.id = 4", User.class)
				.getResultList();
		Set<User> enrolled = findCourseByID(course_id).getTAs();
		Set<User> students = findCourseByID(course_id).getStudents();

		for(int i=0; i<all.size(); i++){
			if(findCourseByID(course_id).getInstructor().getId() == all.get(i).getId()){
				all.remove(i);
				i--;
			}else{
				for(User u : enrolled){
					if(u.getId() == all.get(i).getId()){
						all.remove(i);
						i--;
						break;
					}	
				}
			}	
		}
		//Remove tas who have already enrolled to the course
		for(int i=0; i<all.size(); i++){
			for(User u : students){
				if(u.getId() == all.get(i).getId()){
					all.remove(i);
					i--;
					break;
				}	
			}
		}
		
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		return all;
	}
	
	public void signTAToCourse(int course_id, List<Long> ta_ids){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		try
		{
			List<User> tas = entitymanager.createQuery
					("SELECT user FROM User user WHERE id IN ?1", User.class)
					.setParameter(1, ta_ids).getResultList();
			
			Course course = entitymanager.createQuery
					("SELECT course FROM Course course where course.id = ?1", Course.class)
					.setParameter(1, course_id).getResultList().get(0);
			
			for(User u : tas){
				entitymanager.refresh(u);
				course.getTAs().add(u);
			}
			
			entitymanager.merge(course);
			
			
			entitymanager.getTransaction().commit();
		}
		catch (Exception e)
		{
			// Do Nothing
		}
		
		entitymanager.close();
		
	}
	
	public void resignTaFromCourse(int course_id, List<Long> ta_ids){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		List<User> tas = entitymanager.createQuery
				("SELECT user FROM User user WHERE id IN ?1", User.class)
				.setParameter(1, ta_ids).getResultList();
		
		Course course = entitymanager.createQuery
				("SELECT course FROM Course course where course.id = ?1", Course.class)
				.setParameter(1, course_id).getResultList().get(0);
		
		for(User u : tas){
			entitymanager.refresh(u);
			course.getTAs().remove(u);
		}
		
		entitymanager.merge(course);
		
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		
	}
	
	//For the summary of Thread
	public List<Contribution> getContributionForSummary(Long thread_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		List<Contribution> list_contri = entitymanager.createQuery
				("SELECT contri FROM Contribution contri WHERE "
						+ "contri.thread.id = ?1 and contri.isImportant = 1"
						+ " order by contri.summaryId", Contribution.class)
				.setParameter(1, thread_id).getResultList();
		
		Long count;
		if (list_contri.size() > 0)
		{
			if(list_contri.get(list_contri.size()-1).getSummaryId() == null){
				count = 0L;
			}else{
				count = list_contri.get(list_contri.size()-1).getSummaryId();
			}
		}
		else
		{
			count = 0L;
		}
			
		Long consistent_count = 1L;
		
		for(int i=0; i<list_contri.size(); i++){
			if(list_contri.get(i).getSummaryId() == null){
				count++;
				list_contri.get(i).setSummaryId(count);
				entitymanager.merge(list_contri.get(i));
			}else{
				if(list_contri.get(i).getSummaryId() != consistent_count ){
					list_contri.get(i).setSummaryId(consistent_count);
					entitymanager.merge(list_contri.get(i));
				}
				consistent_count ++;
			}
				
		}
		
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		list_contri = entitymanager.createQuery
				("SELECT contri FROM Contribution contri WHERE "
						+ "contri.thread.id = ?1 and contri.isImportant = 1"
						+ " order by contri.summaryId", Contribution.class)
				.setParameter(1, thread_id).getResultList();
		entitymanager.getTransaction().commit();
		
		entitymanager.close();
		return list_contri;
	}
	
	public List<Contribution> getContributionByThreadID(Long thread_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		List<Contribution> list_contri = entitymanager.createQuery
				("SELECT contri FROM Contribution contri WHERE "
						+ "contri.thread.id = ?1", Contribution.class)
				.setParameter(1, thread_id).getResultList();
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		
		
		return list_contri;
	}
	
	public Contribution getContributionByID(Long contribution_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();		
		
		List<Contribution> list = entitymanager.createQuery
				("SELECT contri FROM Contribution contri WHERE "
						+ "contri.id = ?1 ", Contribution.class)
				.setParameter(1, contribution_id).getResultList();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		
		if(list.size() == 0)
			return null;
		else
			return list.get(0);
	}
	
	public List<Contribution> getContributionLargerThan(Long contribution_id, Long thread_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		List<Contribution> list_contri = entitymanager.createQuery
				("SELECT contri FROM Contribution contri WHERE "
						+ "contri.thread.id = ?1 and contri.summaryId >?2 and contri.isImportant = 1"
						+ " order by contri.summaryId", Contribution.class)
				.setParameter(1, thread_id).setParameter(2, contribution_id).getResultList();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		return list_contri;
	}
	
	public List<Contribution> getContributionLessThan(Long contribution_id, Long thread_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		List<Contribution> list_contri = entitymanager.createQuery
				("SELECT contri FROM Contribution contri WHERE "
						+ "contri.thread.id = ?1 and contri.summaryId <?2 and contri.isImportant = 1"
						+ " order by contri.summaryId", Contribution.class)
				.setParameter(1, thread_id).setParameter(2, contribution_id).getResultList();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		return list_contri;
	}
	
	public void updateSummaryID(Long contribution_id, Long update_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		Contribution contri = entitymanager.createQuery
				("SELECT contri FROM Contribution contri WHERE "
						+ "contri.id = ?1 ", Contribution.class)
				.setParameter(1, contribution_id).getResultList().get(0);
		
		contri.setSummaryId(update_id);
		entitymanager.merge(contri);
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	
	public CThread getThreadByID(Long thread_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		CThread thread = entitymanager.createQuery
				("SELECT thread FROM CThread thread WHERE "
						+ "thread.id = ?1 ", CThread.class)
				.setParameter(1, thread_id).getResultList().get(0);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		return thread;
	}
	
	public void setThreadInactive(Long thread_id){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		CThread thread = entitymanager.createQuery
				("SELECT thread FROM CThread thread WHERE "
						+ "thread.id = ?1 ", CThread.class)
				.setParameter(1, thread_id).getResultList().get(0);
		
		thread.setIsActive(false);
		entitymanager.merge(thread);
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	public boolean checkIfTAForCourse(Course course, String username){
		List<Course> courses = getAllCoursesforTa(username);
		for(Course c: courses){
			if (c.getId()==course.getId())
				return true;
		}
		return false;
	}
	
	public List<Course> getAllCoursesforStudent(String username){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		List<Course> courses =  entitymanager.createQuery(
				"SELECT course FROM Course course JOIN course.students cs "
				+ "where cs.username = ?1", Course.class)
				.setParameter(1, username)
				.getResultList();
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		
		
		return courses;
	}
	
	public boolean checkIfStudentForCourse(Course course, String username){
		List<Course> courses = getAllCoursesforStudent(username);
		for(Course c: courses){
			if (c.getId()==course.getId())
				return true;
		}
		return false;
	}
	
	//Trival
	
}
