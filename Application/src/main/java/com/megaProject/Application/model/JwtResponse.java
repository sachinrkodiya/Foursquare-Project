package com.megaProject.Application.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private String username;
	private String email;
	private String date_of_birth;
	private String gender;
	private Long phone;

	public JwtResponse(String jwttoken,String username,String email,String date_of_birth,String gender,
			Long phone) {
		this.jwttoken = jwttoken;
		this.username = username;
		this.email = email;
		this.date_of_birth = date_of_birth;
		this.gender = gender;
		this.phone = phone;
	}
	
	

	public String getUsername() {
		return username;
	}







	public String getEmail() {
		return email;
	}





	public String getDate_of_birth() {
		return date_of_birth;
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