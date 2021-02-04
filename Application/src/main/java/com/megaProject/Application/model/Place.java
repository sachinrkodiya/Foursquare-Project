package com.megaProject.Application.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Place {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@NotBlank
	@Size(max = 50)
	@Column
	String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "place_types", joinColumns = @JoinColumn(name = "place_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
	private Set<PlaceType> placeType = new HashSet<>();

	@Column
	float overallRating = 0;

	@Column
	private double latitude;

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

	@Column
	private double longitude;

	@NotBlank
	@Column
	int cost;
	
	@NotBlank
	@Column
	long phone;

	@NotBlank
	@Column
	@Size(max = 200)
	String landmark;

	@NotBlank
	@Size(max = 200)
	String address;

	@NotBlank
	@Size(max = 500)
	String overview;

	@Column
	String image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<PlaceType> getPlaceType() {
		return placeType;
	}

	public void setPlaceType(Set<PlaceType> placeType) {
		this.placeType = placeType;
	}

	public float getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(float overallRating) {
		this.overallRating = overallRating;
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
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	



	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public Place() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Place(int id, @NotBlank @Size(max = 50) String name, float overallRating,
			double latitude, double longitude, @NotBlank int cost, @NotBlank long phone,
			@NotBlank @Size(max = 200) String landmark, @NotBlank @Size(max = 200) String address,
			@NotBlank @Size(max = 500) String overview, String image) {
		super();
		this.id = id;
		this.name = name;
		this.overallRating = overallRating;
		this.latitude = latitude;
		this.longitude = longitude;
		this.cost = cost;
		this.phone = phone;
		this.landmark = landmark;
		this.address = address;
		this.overview = overview;
		this.image = image;
	}
	
	public Place(@NotBlank @Size(max = 50) String name, double latitude, double longitude, @NotBlank int cost,
			@NotBlank @Size(max = 200) String landmark, @NotBlank @Size(max = 200) String address,
			@NotBlank @Size(max = 500) String overview,  @NotBlank long phone) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.cost = cost;
		this.landmark = landmark;
		this.address = address;
		this.overview = overview;
		this.phone = phone;
	}
	
	


	
	
	

}
