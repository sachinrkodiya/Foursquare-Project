package com.megaProject.Application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long feedback_id;
	@Column
	private long user_id;
	@Column
	private String feedbacks;
	
	public Feedback() {
		
	}
	public Feedback(long feedback_id, long user_id, String feedbacks) {
		super();
		this.feedback_id = feedback_id;
		this.user_id = user_id;
		this.feedbacks = feedbacks;
	}
	public long getFeedback_id() {
		return feedback_id;
	}
	public void setFeedback_id(long feedback_id) {
		this.feedback_id = feedback_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getFeedbacks() {
		return feedbacks;
	}
	public void setFeedbacks(String feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	
	
	
	
	

}
