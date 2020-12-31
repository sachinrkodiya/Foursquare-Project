package com.megaProject.Application.request;

public class RatingRequest {
	private long userId;
	private int placeId;
	private int rating;
	
	
	
	public RatingRequest() {
		
	}
	
	public RatingRequest(long userId, int placeId, int rating) {
		super();
		this.userId = userId;
		this.placeId = placeId;
		this.rating = rating;
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
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	
	

}
