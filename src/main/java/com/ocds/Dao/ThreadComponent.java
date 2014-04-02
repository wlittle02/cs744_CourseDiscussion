package com.ocds.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.ocds.Domain.Contribution;
import com.ocds.Domain.CThread;
import com.ocds.Domain.Course;
import com.ocds.users.User;

@Component
public class ThreadComponent {
	@Autowired
	EntityManagerFactory entitymanagerfactory;
	
	
	public void createThread(CThread pThread)
		
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager(); 
		entitymanager.getTransaction().begin();
		
		entitymanager.merge(pThread);
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	
	public void deleteThread(CThread pThread)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		pThread.setCourse(null);
		entitymanager.remove(entitymanager.merge(pThread));
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	
	public List<CThread> getAllThreads(int pCourse)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		List<CThread> Threads = entitymanager.createQuery("SELECT thread FROM CThread thread WHERE thread.course.id = ?1", CThread.class).
				setParameter(1, pCourse).getResultList();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		return Threads;
	}
	
	public CThread getThread(Long pThread)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		List<CThread> Threads = entitymanager.createQuery("SELECT thread FROM CThread thread WHERE thread.id = ?1", CThread.class)
				.setParameter(1, pThread).getResultList();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		if (Threads.size()>0)
			return Threads.get(0);
		else
			return null;
	}
	
	public CThread findThreadByName(String threadName)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		List<CThread> Threads = entitymanager.createQuery("SELECT thread FROM CThread thread WHERE thread.name = ?1", CThread.class)
				.setParameter(1, threadName).getResultList();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		if (Threads.size()>0)
			return Threads.get(0);
		else
			return null;
	}
	
	public void createContribution(Contribution pContribution)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		entitymanager.merge(pContribution);
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	
	public void contributionIsImportant(Contribution pContribution, Boolean pIsImportant)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		pContribution.setIsImportant(pIsImportant);
		entitymanager.merge(pContribution);
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	
	public List<Contribution> getAllContributions(Long pThreadId)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		List<Contribution> allContributions = entitymanager.createQuery("SELECT contribution FROM Contribution contribution WHERE contribution.thread.id = ?1", Contribution.class).setParameter(1, pThreadId).getResultList();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();		
		
		return allContributions;
	}
	
	public Contribution getContribution(Long contributionId)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		List<Contribution> contributions = entitymanager.createQuery("SELECT contribution FROM Contribution contribution WHERE contribution.id = ?1", Contribution.class)
				.setParameter(1, contributionId).getResultList();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		if (contributions.size()>0)
			return contributions.get(0);
		else
			return null;
	}
	
	public void updateUserAttributes(String pUserName, String pFirstName, String pLastName)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		List<Contribution> contributions = entitymanager.createQuery("SELECT contribution FROM Contribution contribution WHERE contribution.username = ?1", Contribution.class)
				.setParameter(1,  pUserName).getResultList();
		
		// Update the first and last name of the user
		for (int i = 0; i < contributions.size(); i++)
		{
			contributions.get(i).setEnteredBy(pFirstName + " " + pLastName);
			entitymanager.merge(contributions.get(i));
		}
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
	
	public void invalidateUserName(String pUserName)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		List<Contribution> contributions = entitymanager.createQuery("SELECT contribution FROM Contribution contribution WHERE contribution.username = ?1", Contribution.class)
				.setParameter(1,  pUserName).getResultList();
		
		// Update the user name to an invalid form
		for (int i = 0; i < contributions.size(); i++)
		{
			contributions.get(i).setUsername("");
			entitymanager.merge(contributions.get(i));
		}
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
	}
}
