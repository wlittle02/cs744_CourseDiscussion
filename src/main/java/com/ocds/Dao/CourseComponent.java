package com.ocds.Domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		entitymanager.persist(course); 
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
	
	/*public void test(){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();  
		entitymanager.persist(new Course());  
		entitymanager.getTransaction().commit();  
		entitymanager.close();  
	}
*/
}
