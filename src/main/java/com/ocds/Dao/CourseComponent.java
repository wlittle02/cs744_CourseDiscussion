package com.ocds.Dao;

import java.util.List;

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
	
	public void updateCourse(Course course){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		entitymanager.merge(course); 
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
	}
	
	public void deleteCourse(Course course){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
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
	
	public User getInstructorByID(int id){
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
	
	/*public void test(){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		entitymanager.persist(new Course());  
		entitymanager.getTransaction().commit();  
		entitymanager.close();  
	}
*/
}
