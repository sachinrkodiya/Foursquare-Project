package com.megaProject.Application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "review_photos")
public class ReviewPhotos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long photo_id;
	@Column
	private long user_id;
	
	@Column
	private int place_id;

	@Column
	private String image;
	
	@Column
	private String date;
	
	public ReviewPhotos() {
		
	}
	

	public ReviewPhotos(long user_id, int place_id, String image, String date) {
		super();
		this.user_id = user_id;
		this.place_id = place_id;
		this.image = image;
		this.date = date;
	}


	public long getPhoto_id() {
		return photo_id;
	}


	public void setPhoto_id(long photo_id) {
		this.photo_id = photo_id;
	}


	public long getUser_id() {
		return user_id;
	}


	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}


	public int getPlace_id() {
		return place_id;
	}


	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
	
	
	
	

}
