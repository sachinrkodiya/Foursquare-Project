package com.megaProject.Application.response;

public class OverallratingResponse {

	private int placeId;
	private float overallRating;

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public float getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(float overallRating) {
		this.overallRating = overallRating;
	}

	public OverallratingResponse() {
		super();
	}

	public OverallratingResponse(int placeId, float overallRating) {
		super();
		this.placeId = placeId;
		this.overallRating = overallRating;
	}

}
