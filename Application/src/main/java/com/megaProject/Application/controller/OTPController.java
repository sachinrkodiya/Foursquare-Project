package com.megaProject.Application.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.megaProject.Application.model.DAOUser;

import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.request.OTPEnter;
import com.megaProject.Application.request.generateOtp;
import com.megaProject.Application.response.MessageResponse;
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
			return ResponseEntity.ok(new MessageResponse("email not found please create account"));
			}

		DAOUser value = userDao.findByEmail(email);
		int otp = otpService.generateOTP();
		emailService.sendEmail(value.getEmail(), "hii", "otp = "+otp);
		return ResponseEntity.ok(new MessageResponse("OTP sent successfully!"));
	}
	
	
	@PostMapping("/validateOtp")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<MessageResponse> validateOTP(@RequestBody OTPEnter enterOtp ) {
		
		
		final String SUCCESS = "Entered Otp is valid";
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName();
		long otpnum = enterOtp.getOtpNum();
		if(otpnum >= 0){
			
			  long serverOtp = otpService.getOtp("OTPNUMBER");
			    if(serverOtp > 0){
			      if(otpnum == serverOtp){
			          otpService.clearOTP(username);			
			      	  return ResponseEntity.ok(new MessageResponse(SUCCESS));
	                } 
			        else {
			        	return ResponseEntity.ok(new MessageResponse("Fail 1"+FAIL));
	                   }
	               }else {
	            	   return ResponseEntity.ok(new MessageResponse("Fail2"+FAIL));
	               }
	             }else {
	            	 return ResponseEntity.ok(new MessageResponse("Fail 3 "+FAIL));
	         }		
		
	}
	
	
		

}
