package com.ocds.Domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

//@RooJavaBean
//@RooToString
//@RooJpaActiveRecord
@Entity
@Table(name = "thread")
public class CThread implements java.io.Serializable {

	// TODO need to add a list of thread contributions
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1486656464353755097L;
	
	
	private Long id;
	private String name;
	private Course course;
	private String dateTime;
	private String summary;
	private Boolean isActive;

	public CThread()
	{
		// Do Nothing
	}
	
	public CThread(String pName, Course pCourse, String pDateTime, String pSummary, Boolean pIsActive)
	{
		name = pName;
		course = pCourse;
		dateTime = pDateTime;
		summary = pSummary;
		isActive = pIsActive;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", length = 500)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "course") // TODO Get the name
	//@Column(name = "course")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Column(name = "datetime")
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	@Column(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "isActive")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
