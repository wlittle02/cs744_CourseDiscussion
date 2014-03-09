package com.ocds.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		entitymanager.remove(entitymanager.merge(course));
		
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
	
	public List<Course> getAllCoursesforStudent(User student){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		List<Course> courses = entitymanager.createQuery("SELECT course FROM Course course JOIN course.students where ?1 in course.students", Course.class)
				.setParameter(1, student).getResultList();
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
	/*public void test(){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		entitymanager.persist(new Course());  
		entitymanager.getTransaction().commit();  
		entitymanager.close();  
	}
*/
}
