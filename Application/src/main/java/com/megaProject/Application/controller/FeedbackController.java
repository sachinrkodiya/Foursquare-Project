package com.megaProject.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.megaProject.Application.response.MessageResponse;
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
	public MessageResponse feedbackDetails(@RequestBody feedbackRequest feedbackValues ) {
		
		Feedback values = new Feedback();
		
		DAOUser user = userDao.findByUserID(feedbackValues.getUserId());
		if(user.getEmail().isEmpty()) {
			return new MessageResponse("account doesn't exist");
			
		}
		
		emailService.sendFeedback(user.getEmail(), feedbackValues.getText());
		
		
		
		values.setUser_id(feedbackValues.getUserId());
		values.setFeedbacks(feedbackValues.getText());
		feedbackRepository.save(values);
		
		

		
		return new MessageResponse("feedback Added ");
		
	}

}
