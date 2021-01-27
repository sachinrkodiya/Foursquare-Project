package com.megaProject.Application.request;

public class feedbackRequest {
	private long userId;
	private String feedback;
	
	public feedbackRequest() {
		
	}

	public feedbackRequest(long userId, String feedback) {
		super();
		this.userId = userId;
		this.feedback = feedback;
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	
	
	
	

}
