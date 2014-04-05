package com.ocds.Dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ocds.Domain.CThread;
import com.ocds.Domain.Contribution;
import com.ocds.Domain.Course;
import com.ocds.users.User;
import com.ocds.users.UserComponent;

public class ReportsComponent {
	@Autowired
	CourseComponent courseComponent;
	@Autowired
	ThreadComponent threadComponent;
	
	public enum TimeType
	{
		EWeekly,
		EMonthly,
		EYearly
	}
	
	public enum RoleType
	{
		EStudent,
		ETa,
		EInstructor,
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
	public Integer getContributionTotal(TimeType pTimeType, Date pDate, Long pThreadId) throws ParseException
	{
		Date startDate = pDate;
		Date endDate = getEndDate(pTimeType, pDate);
		Integer count = 0;
		List<Contribution> contributions = threadComponent.getAllContributions(pThreadId);
		for (int i = 0; i < contributions.size(); i++)
		{
			Date contributionDate = new SimpleDateFormat("MM/dd?yyyy").parse(contributions.get(i).getDateTime());
			if (isWithinRange(contributionDate, startDate, endDate))
			{
				count++;
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
		Date startDate = pDate;
		Date endDate = getEndDate(pTimeType, pDate);
		Integer count = 0;
		List<CThread> threads = threadComponent.getAllThreads(pCourse);
		for (int i = 0; i < threads.size(); i++)
		{
			Date threadDate = new SimpleDateFormat("MM/dd/yyyy").parse(threads.get(i).getDateTime());
			if (this.isWithinRange(threadDate, startDate, endDate))
			{
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Collects and counts all threads by course
	 * @param pTimeType Enumeration that tells the algorithm what is the time frame
	 * @param pDate Starting date
	 * @param pCourse Course Id
	 * @return Number of threads found within the date range and course
	 * @throws ParseException
	 */
	public Integer getThreadByClass(TimeType pTimeType, Date pDate, Integer pCourse) throws ParseException
	{
		Date startDate = pDate;
		Date endDate = getEndDate(pTimeType, pDate);
		Integer count = 0;
		List<CThread> threads = threadComponent.getAllThreads(pCourse);
		for (int i = 0; i < threads.size(); i++)
		{
			Date threadDate = new SimpleDateFormat("MM/dd/yyyy").parse(threads.get(i).getDateTime());
			if (isWithinRange(threadDate, startDate, endDate))
			{
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Returns the number of contributions by a course
	 * @param pTimeType Enumeration that tells the algorithm what is the time frame
	 * @param pDate Starting date
	 * @param pCourse Course Id
	 * @return Number of contributions found within a course for the specified time range
	 * @throws ParseException
	 */
	public Integer getContributionsByRole(TimeType pTimeType, Date pDate, Integer pCourse, RoleType pRoleType) throws ParseException
	{
		Date startDate = pDate;
		Date endDate = getEndDate(pTimeType, pDate);
		Integer count = 0;
		List<CThread> threads = threadComponent.getAllThreads(pCourse);
		
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
		Date startDate = pDate;
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
	 * Check on the date
	 * @param pDate Date form to be checked
	 * @param pStart Lower bound date
	 * @param pEnd Upper bound date
	 * @return true if within range, false if outside of range
	 */
	private boolean isWithinRange(Date pDate, Date pStart, Date pEnd)
	{
		return (pStart.compareTo(pDate) <= 0 && pEnd.compareTo(pDate) >= 0);
	}
	
	/**
	 * Calculates the end date
	 * @param pTimeType Enumeration for the type of time range
	 * @param pDate starting date
	 * @return end date
	 */
	private Date getEndDate(TimeType pTimeType, Date pDate)
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
