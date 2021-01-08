package com.megaProject.Application.request;

public class FilterRequest {

	private String landmark;
	private double latitude;
	private double longitude;
	private float radius;
	private int cost;

	private boolean creditCard;
	private boolean delivery;
	private boolean dog_friendly;
	private boolean family_friendly;
	private boolean outdoor;
	private boolean wifi;
	private boolean walking;
	private boolean parking;

	private String sortBy;

	public FilterRequest() {

	}

	public FilterRequest(String landmark, double latitude, double longitude, float radius, int cost, boolean creditCard,
			boolean delivery, boolean dog_friendly, boolean family_friendly, boolean outdoor, boolean wifi,
			boolean walking, boolean parking, String sortBy) {
		super();
		this.landmark = landmark;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.cost = cost;
		this.creditCard = creditCard;
		this.delivery = delivery;
		this.dog_friendly = dog_friendly;
		this.family_friendly = family_friendly;
		this.outdoor = outdoor;
		this.wifi = wifi;
		this.walking = walking;
		this.parking = parking;
		this.sortBy = sortBy;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public boolean isCreditCard() {
		return creditCard;
	}

	public void setCreditCard(boolean creditCard) {
		this.creditCard = creditCard;
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

	public boolean isFamily_friendly() {
		return family_friendly;
	}

	public void setFamily_friendly(boolean family_friendly) {
		this.family_friendly = family_friendly;
	}

	public boolean isOutdoor() {
		return outdoor;
	}

	public void setOutdoor(boolean outdoor) {
		this.outdoor = outdoor;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
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

}
