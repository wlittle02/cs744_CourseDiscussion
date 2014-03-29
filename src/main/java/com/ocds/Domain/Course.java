package com.ocds.Domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.ocds.users.User;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@Entity
@Table(name = "course")
public class Course implements java.io.Serializable {

	private static final long serialVersionUID = 3240281547213597385L;
	
	private Integer id;
	private String name;
	private String year;
	private String semester;
	private String state;
	private User instructor;
	private Set<User> students = new HashSet<User>();

	//Lately Introduced
	private String id_num;
	private Integer section_num;
	private Set<User> TAs = new HashSet<User>();
	
	public Course() {
	}

	public Course(String name,String year,String semester,
			String state,String id_num,Integer section_num,User instructor) {
		this.name = name;
		this.year = year;
		this.semester = semester;
		this.state = state;
		this.id_num = id_num;
		this.section_num = section_num;
		this.instructor = instructor;
	}

	

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 500)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "year")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Column(name = "semester")
	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "in_id")
	public User getInstructor() {
		return instructor;
	}

	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}

	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="course_has_students", joinColumns= {@JoinColumn(name="course_id")}, inverseJoinColumns={@JoinColumn(name="student_id")})
	public Set<User> getStudents() {
		return students;
	}

	public void setStudents(Set<User> students) {
		this.students = students;
	}
	
	@Column(name = "id_num")
	public String getId_num() {
		return id_num;
	}

	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
	
	@Min(value = 1)
	@Column(name = "section_num")
	public Integer getSection_num() {
		return section_num;
	}

	public void setSection_num(Integer section_num) {
		this.section_num = section_num;
	}

	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="course_has_tas", joinColumns= {@JoinColumn(name="course_id")}, inverseJoinColumns={@JoinColumn(name="student_id")})
	public Set<User> getTAs() {
		return TAs;
	}

	public void setTAs(Set<User> tAs) {
		TAs = tAs;
	}
}