package com.megaProject.Application.request;

public class ReviewRequest {
	
	private long userId;
	private int placeId;
	private String date;
	private String review;
	private int rating=0;
	
	
	public ReviewRequest() {
		
	}
	public ReviewRequest(long userId, int placeId, String date, String review, int rating) {
		super();
		this.userId = userId;
		this.placeId = placeId;
		this.date = date;
		this.review = review;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	
	
	
	
	
	

}
