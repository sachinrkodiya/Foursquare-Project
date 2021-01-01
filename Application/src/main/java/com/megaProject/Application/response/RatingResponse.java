package com.megaProject.Application.response;

public class RatingResponse {
	private float overallRating;

	public float getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(float overallRating) {
		this.overallRating = overallRating;
	}
	
	RatingResponse() {
		
	}

	public RatingResponse(float overallRating) {
		super();
		this.overallRating = overallRating;
	}
	
	

}
