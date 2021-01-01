package com.megaProject.Application.request;

public class UserIdRequest {
	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserIdRequest() {
		
	}
	public UserIdRequest(long userId) {
		super();
		this.userId = userId;
	}
	
	

}
