package com.megaProject.Application.model;

public class Distance {
	private Object place;
	private double distance;
	
	public Distance() {
		
		
	}
	
	public Distance(Object place, double distance) {
		super();
		this.place = place;
		this.distance = distance;
	}

	public Object getPlace() {
		return place;
	}

	public void setPlace(Object place) {
		this.place = place;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	
	
	
	
	

}
