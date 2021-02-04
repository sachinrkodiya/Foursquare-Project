package com.megaProject.Application.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megaProject.Application.model.DAOUser;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.model.ReviewRating;

import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.ReviewRatingRepository;
import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.request.RatingRequest;
import com.megaProject.Application.request.ReviewRatingRequest;
import com.megaProject.Application.request.ReviewRequest;

import com.megaProject.Application.response.OverallratingResponse;

import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.response.ReviewResponse;
import com.megaProject.Application.service.Pagging;

@RestController
@CrossOrigin
public class ReviewRatingController {

	@Autowired
	PlaceRepository placeRepository;

	@Autowired
	private UserDao userDao;

	@Autowired
	ReviewRatingRepository revRepository;
	
	@Autowired
	Pagging pagging;

	@PostMapping("/addReview")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?>  addReview(@RequestBody ReviewRequest revReq) {
		
		Place placeCheck = placeRepository.findByPlaceId(revReq.getPlaceId());
		if(placeCheck == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NO CONTENT","Place ID not valid"));
			
		}
		DAOUser userCheck = userDao.findByUserID(revReq.getUserId());
		if(userCheck == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NO CONTENT","User ID not valid"));
			
		}

		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		System.out.println(date);

		ReviewRating values = revRepository.findTheRating(revReq.getUserId(), revReq.getPlaceId());
		if(values==null) {
			 values = new ReviewRating(revReq.getUserId(), revReq.getPlaceId(), revReq.getReview(),
					0, date);	
		}
		else {
			values.setReview(revReq.getReview());
			values.setDate(date);
			
		}

		revRepository.save(values);
		return ResponseEntity.status(200).body(new ReturnResponse(200," ","SUCCESS"));

		
	}

	@PostMapping("/addRating")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?>  addRating(@RequestBody RatingRequest ratReq) {
		Place placeCheck = placeRepository.findByPlaceId(ratReq.getPlaceId());
		if(placeCheck == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NO CONTENT","Place ID not valid"));
			
		}
		DAOUser userCheck = userDao.findByUserID(ratReq.getUserId());
		if(userCheck == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NO CONTENT","User ID not valid"));
			
		}
		
		if(ratReq.getRating()>5) {
			return ResponseEntity.status(200).body(new ReturnResponse(401,"BAD CREDENTIALS","rating is above the range"));
			
		}
		

		ReviewRating values = revRepository.findTheRating(ratReq.getUserId(), ratReq.getPlaceId());
		if(values == null) {
			values = new ReviewRating(ratReq.getUserId(), ratReq.getPlaceId(),ratReq.getRating());
			
		}
		else {
			values.setRating(ratReq.getRating());
			
		}

		revRepository.save(values);
		// DAOUser user = userDao.findByUserID(values.getUser_id());
		Place place = placeRepository.findByPlaceId(values.getPlace_id());
		List<ReviewRating> ratingLists = revRepository.ListOfRating(values.getPlace_id());
		float ans = 0;
		int count = ratingLists.size();
		for (ReviewRating rating : ratingLists) {
			if(rating.getRating() == 0) {
				count=count-1;
			}
			ans = ans + rating.getRating();
		}

		float overall = (ans / count) * 2;
		System.out.println(overall);
		place.setOverallRating(overall);
		placeRepository.save(place);
		
		return ResponseEntity.status(200).body(new ReturnResponse(200," ","SUCCESS",place.getOverallRating()));

		
	}

	@GetMapping("/reviews")
	public ResponseEntity<?> getAllreviews(@RequestParam  int PlaceId,@RequestParam  int pageNo, @RequestParam  int pageSize) {

		Place place = placeRepository.findByPlaceId(PlaceId);
		List<ReviewRating> review = revRepository.findByReview(PlaceId);
		if(place == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NO CONTENT","Place ID not valid"));
			
		}
		List<ReviewResponse> response = new ArrayList<>();

		for (ReviewRating rev : review) {

			DAOUser user = userDao.findByUserID(rev.getUser_id());
			ReviewResponse newReview = new ReviewResponse(place.getId(), place.getName(), 
					user.getImage(),user.getUsername(), rev.getReview(), rev.getDate());
			if(rev.getReview() == null) {
				continue;
			}
			response.add(newReview);

		}
		if(response.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NO CONTENT","NO REVIEW FOUND"));
			
		}
		return pagging.PagedValues(response, pageNo, pageSize);

	}

	@GetMapping("/overallrating")
	public OverallratingResponse getOverallrating(@RequestBody ReviewRatingRequest request) {
		Place place = placeRepository.findByPlaceId(request.getPlaceId());
		return new OverallratingResponse(place.getId(), place.getOverallRating());
	}

}
