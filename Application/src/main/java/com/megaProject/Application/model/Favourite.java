package com.megaProject.Application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_favourite")
public class Favourite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long fav_id;
	@Column
	private long user_id;
	@Column
	private int place_id;
	
	
	
	public Favourite() {
		
	}
	
	public Favourite(long fav_id, long user_id, int place_id) {
		super();
		this.fav_id = fav_id;
		this.user_id = user_id;
		this.place_id = place_id;
	}

	public long getFav_id() {
		return fav_id;
	}

	public void setFav_id(long fav_id) {
		this.fav_id = fav_id;
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
	
	
	
	
	

}
