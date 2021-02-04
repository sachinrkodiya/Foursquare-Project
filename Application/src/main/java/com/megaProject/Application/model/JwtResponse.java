package com.megaProject.Application.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private Object UserData;
	


	public JwtResponse(String jwttoken, Object userData) {
		super();
		this.jwttoken = jwttoken;
		UserData = userData;
	}


	public Object getUserData() {
		return UserData;
	}


	public void setUserData(Object userData) {
		UserData = userData;
	}













	public String getToken() {
		return this.jwttoken;
	}
}