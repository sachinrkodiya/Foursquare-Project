package com.megaProject.Application.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megaProject.Application.model.DAOUser;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.model.ReviewRating;
import com.megaProject.Application.model.UserDTO;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.ReviewRatingRepository;
import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.request.RatingRequest;
import com.megaProject.Application.request.ReviewRatingRequest;
import com.megaProject.Application.request.ReviewRequest;
import com.megaProject.Application.response.MessageResponse;
import com.megaProject.Application.response.OverallratingResponse;
import com.megaProject.Application.response.ReviewResponse;

@RestController
@CrossOrigin
public class ReviewRatingController {

	@Autowired
	PlaceRepository placeRepository;

	@Autowired
	private UserDao userDao;

	@Autowired
	ReviewRatingRepository revRepository;

	@PostMapping("/addReview")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse addReview(@RequestBody ReviewRequest revReq) {

		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		System.out.println(date);
		ReviewRating newReview = new ReviewRating(revReq.getUserId(), revReq.getPlaceId(), revReq.getReview(),
				revReq.getRating(), date);
		revRepository.save(newReview);

		return new MessageResponse(revReq.getReview());
	}

	@PostMapping("/addRating")
	@ResponseStatus(HttpStatus.CREATED)
	public float addRating(@RequestBody RatingRequest ratReq) {

		ReviewRating values = revRepository.findTheRating(ratReq.getUserId(), ratReq.getPlaceId());
		values.setRating(ratReq.getRating());
		revRepository.save(values);
		// DAOUser user = userDao.findByUserID(values.getUser_id());
		Place place = placeRepository.findByPlaceId(values.getPlace_id());
		List<ReviewRating> ratingLists = revRepository.ListOfRating(values.getPlace_id());
		float ans = 0;
		int count = ratingLists.size();
		for (ReviewRating rating : ratingLists) {
			ans = ans + rating.getRating();
		}

		float overall = (ans / count) * 2;
		System.out.println(overall);
		place.setOverallRating(overall);
		placeRepository.save(place);

		return place.getOverallRating();
	}

	@GetMapping("/reviews")
	public List<ReviewResponse> getAllreviews(@RequestBody ReviewRequest request) {
		Place place = placeRepository.findByPlaceId(request.getPlaceId());
		// DAOUser user=userDao.findByUserID(request.getUserId());
		List<ReviewRating> review = revRepository.findByReview(request.getPlaceId());
		List<ReviewResponse> response = new ArrayList<>();

		for (ReviewRating rev : review) {

			DAOUser user = userDao.findByUserID(rev.getUser_id());
			ReviewResponse newReview = new ReviewResponse(place.getId(), place.getName(), user.getUsername(),
					user.getImage(), rev.getReview(), rev.getDate());
			response.add(newReview);

		}
		return response;

	}

	@GetMapping("/overallrating")
	public OverallratingResponse getOverallrating(@RequestBody ReviewRatingRequest request) {
		Place place = placeRepository.findByPlaceId(request.getPlaceId());
		return new OverallratingResponse(place.getId(), place.getOverallRating());
	}

}
