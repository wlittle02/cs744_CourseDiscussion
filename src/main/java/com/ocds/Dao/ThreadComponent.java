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
	
	private ThreadContribution contribution;
	
	
	public class ThreadContribution
	{
		public ThreadContribution(Contribution contribution) {
			setContribution(contribution);
		}

		private String userName;
		private Contribution contribution;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public Contribution getContribution() {
			return contribution;
		}

		public void setContribution(Contribution contribution) {
			this.contribution = contribution;
		}
	}
	
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
		return Threads.get(0);
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
	
	public List<ThreadContribution> getAllContributions(Long pThreadId)
	{
		EntityManager entitymanager = entitymanagerfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		
		List<Contribution> getAllContributions = entitymanager.createQuery("SELECT contribution FROM Contribution contribution WHERE contribution.thread.id = ?1", Contribution.class).setParameter(1, pThreadId).getResultList();
		List<ThreadContribution> allContributions = new ArrayList<ThreadContribution>();
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		
		for (int i = 0; i < getAllContributions.size(); i++)
		{
			allContributions.add(new ThreadContribution(getAllContributions.get(i)));
		}
		
		return allContributions;
	}
}
