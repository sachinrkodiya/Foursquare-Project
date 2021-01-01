package com.megaProject.Application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(	name = "filter")
public class Filter {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer filter_id;
	
	
	
	@Column(length = 20)
	private int place_id;
	
	
	@Column(length = 20)
	private boolean credit_card;
	
	

	@Column(length = 20)
	private boolean delivery;

	@Column(length = 20)
	private boolean dog_friendly;
	
	@Column(length = 20)
	private boolean outdoor;
	
	
	
	@Column(length = 20)
	private boolean family_friendly;
	
	
	@Column(length = 20)
	private boolean walking;
	
	@Column(length = 20)
	private boolean parking;

	@Column(length = 20)
	private boolean wi_fi;
	
	
	public Filter() {
		
	}


	public Filter(int place_id, boolean credit_card, boolean delivery, boolean dog_friendly, boolean outdoor,
			boolean family_friendly, boolean walking, boolean parking, boolean wi_fi) {
		super();
		this.place_id = place_id;
		this.credit_card = credit_card;
		this.delivery = delivery;
		this.dog_friendly = dog_friendly;
		this.outdoor = outdoor;
		this.family_friendly = family_friendly;
		this.walking = walking;
		this.parking = parking;
		this.wi_fi = wi_fi;
	}


	public Integer getFilter_id() {
		return filter_id;
	}


	public void setFilter_id(Integer filter_id) {
		this.filter_id = filter_id;
	}


	public int getPlace_id() {
		return place_id;
	}


	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}


	public boolean isCredit_card() {
		return credit_card;
	}


	public void setCredit_card(boolean credit_card) {
		this.credit_card = credit_card;
	}


	public boolean isDelivery() {
		return delivery;
	}


	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}


	public boolean isDog_friendly() {
		return dog_friendly;
	}


	public void setDog_friendly(boolean dog_friendly) {
		this.dog_friendly = dog_friendly;
	}


	public boolean isOutdoor() {
		return outdoor;
	}


	public void setOutdoor(boolean outdoor) {
		this.outdoor = outdoor;
	}


	public boolean isFamily_friendly() {
		return family_friendly;
	}


	public void setFamily_friendly(boolean family_friendly) {
		this.family_friendly = family_friendly;
	}


	public boolean isWalking() {
		return walking;
	}


	public void setWalking(boolean walking) {
		this.walking = walking;
	}


	public boolean isParking() {
		return parking;
	}


	public void setParking(boolean parking) {
		this.parking = parking;
	}


	public boolean isWi_fi() {
		return wi_fi;
	}


	public void setWi_fi(boolean wi_fi) {
		this.wi_fi = wi_fi;
	}
	
	


	
	
	
	
	
	
	
	
	

}
