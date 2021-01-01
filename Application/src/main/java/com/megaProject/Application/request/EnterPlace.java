package com.megaProject.Application.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EnterPlace {
	@NotBlank
	@Size(max = 50)
	String name;

	private Set<String> placeType;

	@NotBlank
	int cost;

	@NotBlank
	@Size(max = 200)
	String landmark;

	@NotBlank
	@Size(max = 200)
	String Address;

	@NotBlank
	@Size(max = 500)
	String Overview;

	@NotBlank
	private double longitude;

	@NotBlank
	private double latitude;
	
	@NotBlank
	private long phone;
	
	
	

	public double getLongitude() {
		return longitude;
	}


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getPlaceType() {
		return placeType;
	}

	public void setPlaceType(Set<String> placeType) {
		this.placeType = placeType;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getOverview() {
		return Overview;
	}

	public void setOverview(String overview) {
		this.Overview = overview;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
