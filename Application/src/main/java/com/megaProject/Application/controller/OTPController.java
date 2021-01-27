package com.megaProject.Application.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.megaProject.Application.model.DAOUser;

import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.request.OTPEnter;
import com.megaProject.Application.request.generateOtp;

import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.service.EmailService;
import com.megaProject.Application.service.OTPService;



@Controller
public class OTPController {
	@Autowired
	public OTPService otpService;

	@Autowired
	public EmailService emailService;
	
	@Autowired
	public UserDao userDao;
	
	@RequestMapping(value = "/generateOtp", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody generateOtp OtpRequest) throws Exception{
		String email = OtpRequest.getEmail();
		if(!(userDao.existsByEmail(email))) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NOT FOUND","EMAIL NOT FOUND PLEASE CREATE ACCOUNT"));
			}

		DAOUser value = userDao.findByEmail(email);
		int otp = otpService.generateOTP(email);
		emailService.sendEmail(value.getEmail(), "Your requested OTP", "otp = "+otp);
		return ResponseEntity.status(200).body(new ReturnResponse(200," ","OTP sent successfully"));
	}
	
	
	@PutMapping("/validateOtp")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> validateOTP(@RequestBody OTPEnter enterOtp ) {
		
		

		String email = enterOtp.getEmail();
		if(!(userDao.existsByEmail(email))) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NOT FOUND","EMAIL NOT FOUND PLEASE CREATE ACCOUNT"));
		}
		long otpnum = enterOtp.getOtpNum();
		if(otpnum >=100000){
			
			  long serverOtp = otpService.getOtp(email);
			    if(serverOtp > 100000){
			      if(otpnum == serverOtp){
			          otpService.clearOTP(email);			
			      	return ResponseEntity.status(200).body(new ReturnResponse(200,"","OTP VALIDATED SUCCESSFULLY"));
	                } 
			        else {
			        	return ResponseEntity.status(401).body(new ReturnResponse(500,"BAD CREDENTIALS","OTP IS INVALID"));
	                   }
	               }else {
	            	   return ResponseEntity.status(401).body(new ReturnResponse(401,"BAD CREDENTIALS","TIME EXPIRED OR OTP IS ALREADY USED "));
	               }
	             }else {
	            	 return ResponseEntity.status(401).body(new ReturnResponse(401,"BAD CREDENTIALS","ENTERED VALID NUMBER OF OTP DIGITS"));
	         }		
		
	}
	
	
		

}
