package com.ocds.Domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.ocds.users.User;

//@RooJavaBean
//@RooToString
//@RooJpaActiveRecord
@Entity
@Table(name = "contribution")
public class Contribution implements java.io.Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5992979958745720368L;
	
	
	private Long id;
	private String message;
	private String attachment;
	private Boolean isImportant;
	private String enteredBy;
	private CThread thread;
	private Long summaryId;
	private String datetime;
	
	public Contribution()
	{
		// Do nothing
	}
	
	public Contribution(String pMessage,
						String pAttachment,
						Boolean pIsImportant,
						String pEnteredBy,
						CThread pThread,
						Long pSummaryId,
						String pDateTime)
	{
		setMessage(pMessage);
		setAttachment(pAttachment);
		setIsImportant(pIsImportant);
		setEnteredBy(pEnteredBy);
		setThread(pThread);
		setSummaryId(pSummaryId);
		setDateTime(pDateTime);
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
	
	@Column(name = "message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column(name = "attachment")
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	@Column(name = "isImportant")
	public Boolean getIsImportant() {
		return isImportant;
	}
	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}
	
	@Column(name = "EnteredBy")
	public String getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "thread")
	public CThread getThread() {
		return thread;
	}
	public void setThread(CThread thread) {
		this.thread = thread;
	}
	
	@Column(name = "summaryId")
	public Long getSummaryId()
	{
		return this.summaryId;
	}
	public void setSummaryId(Long summaryId)
	{
		this.summaryId = summaryId;
	}
	
	@Column(name="datetime")
	public String getDateTime()
	{
		return this.datetime;
	}
	public void setDateTime(String datetime)
	{
		this.datetime = datetime;
	}
}
