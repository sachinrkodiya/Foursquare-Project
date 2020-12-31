package com.megaProject.Application.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LatAndLon {
    @NotBlank
    @Size(max = 50)
    private double latitude;
    
    @NotBlank
    @Size(max = 50)
    private double longitude;

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
    
    

}