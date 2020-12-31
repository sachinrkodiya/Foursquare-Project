package com.megaProject.Application.request;

public class DeleteFeedback {
	private long userId;
	private int placeId;
	
	
	public DeleteFeedback( ) {
		
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getPlaceId() {
		return placeId;
	}
	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	public DeleteFeedback(long userId, int placeId) {
		super();
		this.userId = userId;
		this.placeId = placeId;
	}
	
}
