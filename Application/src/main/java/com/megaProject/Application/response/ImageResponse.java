package com.megaProject.Application.response;

public class ImageResponse {
	private long photoId;
	private String placeName;
	private String userName;
	private String userImage;
	private String date;
	private String photoUrl;

	public ImageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageResponse(long photoId, String placeName, String userName, String userImage, String date,
			String photoUrl) {
		super();
		this.photoId = photoId;
		this.placeName = placeName;
		this.userName = userName;
		this.userImage = userImage;
		this.date = date;
		this.photoUrl = photoUrl;
	}

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
