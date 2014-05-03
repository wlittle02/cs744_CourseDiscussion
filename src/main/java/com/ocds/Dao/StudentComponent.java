package com.ocds.Dao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ocds.Domain.Course;
import com.ocds.users.User;

@Component
public class StudentComponent {

	@Autowired
	EntityManagerFactory entitymanagerfactory;
	
	public StudentComponent()
	{
		System.out.println("CREATING Student Component");
	}
	
	/*public User getUserById(Long Id)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		List<User> list = entitymanager.createQuery("SELECT user FROM User user where id = ?1", User.class).setParameter(1, Id).getResultList();
		entitymanager.getTransaction().commit();
		entitymanager.close();
		return list.get(0);
	}

	public User getUserByUserName(String username) {
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		List<User> list = entitymanager.createQuery("SELECT user FROM User user where user.username = ?1", User.class).setParameter(1, username).getResultList();
		entitymanager.getTransaction().commit();
		entitymanager.close();
		return list.get(0);
	}*/
	
	public List<Course> getCourseListForStudent(String username){
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin(); 
		
		List<Course> courses =  entitymanager.createQuery(
				"SELECT course FROM Course course JOIN course.students cs "
				+ "where cs.username = ?1 AND course.state = 'Active'", Course.class)
				.setParameter(1, username)
				.getResultList();
		
		entitymanager.getTransaction().commit();  
		entitymanager.close(); 
		return courses;
	}
	public boolean checkIfStudentInCourse(Course course, String username){
		List<Course> courses = getCourseListForStudent(username);
		for(Course c: courses){
			if (c.getId()==course.getId())
				return true;
		}
		return false;
	}

}
