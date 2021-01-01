package com.megaProject.Application.request;

public class generateOtp {
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public generateOtp() {
		
	}

	public generateOtp(String email) {
		super();
		this.email = email;
	}
	

}
