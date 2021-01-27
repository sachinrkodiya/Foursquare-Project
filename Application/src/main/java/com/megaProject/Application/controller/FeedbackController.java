package com.megaProject.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megaProject.Application.model.DAOUser;
import com.megaProject.Application.model.Feedback;

import com.megaProject.Application.repository.FeedbackRepository;
import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.request.feedbackRequest;

import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.service.EmailService;


@RestController
@CrossOrigin
public class FeedbackController {
	
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	public EmailService emailService;
	
	@PostMapping("/feedback")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> feedbackDetails(@RequestBody feedbackRequest feedbackValues ) {
		
		Feedback values = new Feedback();
		
		DAOUser user = userDao.findByUserID(feedbackValues.getUserId());
		if(user == null) {
			return ResponseEntity.status(404).body(new ReturnResponse(404,"NOT FOUND","user dosen't exist"));
			
		}
		if(user.getEmail().isEmpty()) {
			return ResponseEntity.status(404).body(new ReturnResponse(404,"NOT FOUND","email dosen't exist"));
			
		}
		
		emailService.sendFeedback(user.getEmail(), feedbackValues.getFeedback());
		
		
		
		values.setUser_id(feedbackValues.getUserId());
		values.setFeedbacks(feedbackValues.getFeedback());
		feedbackRepository.save(values);
		
		

		
		return ResponseEntity.status(200).body(new ReturnResponse(200," ","Feedback added successfully"));
		
	}

}
