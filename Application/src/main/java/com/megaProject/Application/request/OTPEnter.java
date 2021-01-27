package com.megaProject.Application.request;

public class OTPEnter {
	private long otpNum;
	private String email;
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getOtpNum() {
		return otpNum;
	}

	public void setOtpNum(long otpNum) {
		this.otpNum = otpNum;
	}

	
	public OTPEnter() {
		
	}

	public OTPEnter(long otpNum, String email) {
		super();
		this.otpNum = otpNum;
		this.email = email;
	}
	
	
	
	
	

	
	
	

}
