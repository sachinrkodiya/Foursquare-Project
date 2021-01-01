package com.megaProject.Application.model;

public class PlaceDistance {
	
	private int placeId;
	private double distance;
	
	
	public PlaceDistance() {
		
	}
	
	
	
	public PlaceDistance(int placeId, double distance) {
		super();
		this.placeId = placeId;
		this.distance = distance;
	}
	
	public int getPlaceId() {
		return placeId;
	}
	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
	

}
