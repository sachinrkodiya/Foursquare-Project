package com.megaProject.Application.response;



public class ReviewResponse {
	private int placeId;
	private String placeName;

	private String userImage;
	private String userName;

	private String review;
	private String date;

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ReviewResponse() {
		super();
// TODO Auto-generated constructor stub
	}

	public ReviewResponse(int placeId, String placeName, String userImage, String userName, String review,
			String date) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.userImage = userImage;
		this.userName = userName;
		this.review = review;
		this.date = date;
	}

}
