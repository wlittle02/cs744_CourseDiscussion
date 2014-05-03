package com.ocds.Dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ocds.Domain.CThread;
import com.ocds.Domain.Contribution;
import com.ocds.Domain.Course;
import com.ocds.users.Role;
import com.ocds.users.User;
import com.ocds.users.UserComponent;

@Component
public class ReportsComponent {
	@Autowired
	CourseComponent courseComponent;
	@Autowired
	ThreadComponent threadComponent;
	@Autowired
	UserComponent userComponent;
	
	public enum TimeType
	{
		EWeekly,
		EMonthly,
		EYearly
	}
	
	
	public enum RoleType
	{   // Must be updated if Role.roleNames changes.
		EManager,
		EInstructor,
		EStudent,
		ETa,
		EAll
	}
	
	/**
	 * Gets the total number of contributions for a thread for a particular time range
	 * @param pTimeType
	 * @param pDate
	 * @param pThreadId
	 * @return Returns the total number of contributions for a thread
	 * @throws ParseException
	 */
	public Integer getContributionTotal(TimeType pTimeType, Date pDate, Long pThreadId, RoleType pRoleType) throws ParseException
	{
		Date startDate = formatStartDate(pDate);
		Date endDate = getEndDate(pTimeType, pDate);
		Integer count = 0;
		List<Contribution> contributions = threadComponent.getAllContributions(pThreadId);
		for (int i = 0; i < contributions.size(); i++)
		{
			String username = contributions.get(i).getUsername();
			if (hasRole(username, pRoleType))
			{
				Date contributionDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(contributions.get(i).getDateTime());
				if (isWithinRange(contributionDate, startDate, endDate))
				{
					count++;
				}
			}
		}
		return count;
	}
	/**
	 * Gets the total number of contributions for a thread for a particular time range
	 * @param pTimeType
	 * @param pDate
	 * @param pThreadId
	 * @return Returns the total number of contributions for a thread
	 * @throws ParseException
	 */
	public Integer getImportantContributionTotal(TimeType pTimeType, Date pDate, Long pThreadId, RoleType pRoleType) throws ParseException
	{
		Date startDate = formatStartDate(pDate);
		Date endDate = getEndDate(pTimeType, pDate);
		Integer count = 0;
		List<Contribution> contributions = threadComponent.getAllImportantContributions(pThreadId);
		for (int i = 0; i < contributions.size(); i++)
		{
			String username = contributions.get(i).getUsername();
			if (hasRole(username, pRoleType))
			{
				Date contributionDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(contributions.get(i).getDateTime());
				if (isWithinRange(contributionDate, startDate, endDate))
				{
					count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * 
	 * @param pTimeType Enumeration that tells the algorithm what is the time frame
	 * @param pDate Starting date
	 * @param pCourse Course Id
	 * @return Returns the total number of threads for a course
	 * @throws ParseException
	 */
	public Integer getThreadTotal(TimeType pTimeType, Date pDate, Integer pCourse) throws ParseException
	{
		Date startDate = formatStartDate(pDate);
		Date endDate = getEndDate(pTimeType, pDate);
		Integer count = 0;
		List<CThread> threads = threadComponent.getAllThreads(pCourse);
		for (int i = 0; i < threads.size(); i++)
		{
			Date threadDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(threads.get(i).getDateTime());
			if (this.isWithinRange(threadDate, startDate, endDate))
			{
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Returns the threads created by the user
	 * @param pTimeType Enumeration that tells the algorithm what is the time frame
	 * @param pDate Starting date
	 * @param pUserId User object for finding threads it made
	 * @return count of threads created by user
	 * @throws ParseException
	 */
	public Integer getThreadsByUser(TimeType pTimeType, Date pDate, User pUserId) throws ParseException
	{
		Integer count = 0;
		Date startDate = formatStartDate(pDate);
		Date endDate = getEndDate(pTimeType, pDate);
		List<Course> courses = this.courseComponent.getAllCoursesforInstructor(pUserId);
		for (int i = 0; i < courses.size(); i++)
		{
			List<CThread> threads = this.threadComponent.getAllThreads(courses.get(i).getId());
			for (int j = 0; j < threads.size(); j++)
			{
				Date threadDate = new SimpleDateFormat("MM/dd/yyyy").parse(threads.get(i).getDateTime());
				if (this.isWithinRange(threadDate, startDate, endDate))
				{
					count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * Checks if the user has a particular role type
	 * @param pUserName User name found within the User component
	 * @param pRoleType Role Type to be searched
	 * @return Boolean result if the user has the targeted role type
	 */
	private Boolean hasRole(String pUserName, RoleType pRoleType)
	{
		Boolean result = false;
		User user = userComponent.loadUserByUsername(pUserName);
		if (user == null)
			return result;
		switch(pRoleType)
		{
		case EStudent:
			result = user.hasRole(Role.roleNames[2]);
			break;
		case ETa:
			result = user.hasRole(Role.roleNames[3]);
			break;
		case EInstructor:
			result = user.hasRole(Role.roleNames[1]);
			break;
		default:
			result = true;
			break;
		}
		return result;
	}
	
	/**
	 * Check on the date
	 * @param pDate Date form to be checked
	 * @param pStart Lower bound date
	 * @param pEnd Upper bound date
	 * @return true if within range, false if outside of range
	 */
	private boolean isWithinRange(Date pDate, Date pStart, Date pEnd)
	{
		Boolean isWithinRange = false;
		int debug1 = pStart.compareTo(pDate);
		int debug2 = pEnd.compareTo(pDate);
		if (pStart.compareTo(pDate) <= 0)
		{
			isWithinRange = true;
		}
		if (pEnd.compareTo(pDate) >= 0)
		{
			isWithinRange = true;
		}
		else
		{
			isWithinRange = false;
		}
		return (pStart.compareTo(pDate) <= 0 && pEnd.compareTo(pDate) >= 0);
	}
	
	private Date formatStartDate(Date pDate)
	{
		pDate.setHours(0);
		pDate.setMinutes(0);
		pDate.setSeconds(0);
		return pDate;
	}
	
	/**
	 * Calculates the end date
	 * @param pTimeType Enumeration for the type of time range
	 * @param pDate starting date
	 * @return end date
	 */
	public Date getEndDate(TimeType pTimeType, Date pDate)
	{
		pDate.setHours(0);
		pDate.setMinutes(0);
		pDate.setSeconds(0);
		Date result = pDate;
		Calendar cal = Calendar.getInstance();
		switch(pTimeType)
		{
		case EWeekly:
			cal.setTime(pDate);
			cal.add(Calendar.DATE, 7);
			break;
		case EMonthly:
			cal.setTime(pDate);
			cal.add(Calendar.DATE, 30);
			break;
		case EYearly:
			cal.setTime(pDate);
			cal.add(Calendar.DATE, 365);
			break;
		}
		result = cal.getTime();
		return result;
	}
}
