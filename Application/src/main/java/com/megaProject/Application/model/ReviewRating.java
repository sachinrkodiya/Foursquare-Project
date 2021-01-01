package com.megaProject.Application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "review_rating")
public class ReviewRating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long rev_id;
	@Column
	private long user_id;
	@Column
	private int place_id;
	@Column
	private String review;
	@Column
	private int rating;
	@Column
	private String date;
	
	
	public ReviewRating() {
		
	}
	
	
	public ReviewRating(long user_id, int place_id, String review, int rating, String date) {
		super();
		this.user_id = user_id;
		this.place_id = place_id;
		this.review = review;
		this.rating = rating;
		this.date = date;
	}


	public long getRev_id() {
		return rev_id;
	}


	public void setRev_id(long rev_id) {
		this.rev_id = rev_id;
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


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
}
