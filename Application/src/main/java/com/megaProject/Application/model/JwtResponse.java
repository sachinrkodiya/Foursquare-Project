package com.megaProject.Application.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private String username;
	private String email;
	private String dob;
	private String gender;
	private Long phone;

	public JwtResponse(String jwttoken,String username,String email,String dob,String gender,
			Long phone) {
		this.jwttoken = jwttoken;
		this.username = username;
		this.email = email;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
	}
	
	

	public String getUsername() {
		return username;
	}







	public String getEmail() {
		return email;
	}



	public String getDob() {
		return dob;
	}



	public String getGender() {
		return gender;
	}



	public Long getPhone() {
		return phone;
	}



	public String getToken() {
		return this.jwttoken;
	}
}