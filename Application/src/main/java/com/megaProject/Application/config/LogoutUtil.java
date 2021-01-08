package com.megaProject.Application.config;

import java.io.Serializable;

public class LogoutUtil implements Serializable {
	
	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	private String secret = "LOGOUT";
	
	

}
