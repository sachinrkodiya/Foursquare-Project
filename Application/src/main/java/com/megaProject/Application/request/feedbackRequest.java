package com.megaProject.Application.request;

public class feedbackRequest {
	private long userId;
	private String text;
	
	public feedbackRequest() {
		
	}
	public feedbackRequest(long userId, String text) {
		super();
		this.userId = userId;
		this.text = text;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
	

}
