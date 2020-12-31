package com.megaProject.Application.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private String username;
	private String name;
	private String dob;
	private String gender;
	private Long phone;

	public JwtResponse(String jwttoken,String username,String name,String dob,String gender,
			Long phone) {
		this.jwttoken = jwttoken;
		this.username = username;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
	}
	
	

	public String getUsername() {
		return username;
	}



	public String getName() {
		return name;
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