package com.megaProject.Application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megaProject.Application.model.Favourite;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.repository.FavouriteRepository;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.request.DeleteFeedback;
import com.megaProject.Application.request.FavRequest;
import com.megaProject.Application.request.UserIdRequest;
import com.megaProject.Application.response.MessageResponse;



@RestController
@CrossOrigin
public class FavouriteController {
	
	
	
	@Autowired
	FavouriteRepository favouriteRepository;
	
	@Autowired
	PlaceRepository placeRepository;
	
	
	@PostMapping("/addFavourite")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse AddFavourite(@RequestBody FavRequest favValues ) {
		Favourite value = new Favourite();
		value.setUser_id(favValues.getUserId());
		value.setPlace_id(favValues.getPlaceId());
		favouriteRepository.save(value);
		
		
		return new MessageResponse(" Favourite Added Succefully");
		
	}
	
	
	
	@DeleteMapping("/deleteFavourite")
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public MessageResponse addAdmin(@RequestBody DeleteFeedback values) {
		
		try {
			favouriteRepository.deleteFavourite(values.getUserId(), values.getPlaceId());
			return new MessageResponse(" Favourite Deleted Succefully");
		} catch (NoSuchElementException e){
			 return new MessageResponse("No Data Found To Delete");
		}
	
		
	}
	
	
	@GetMapping("/getFavourite")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Place> findFavourites(@RequestBody UserIdRequest userId) {


		List<Favourite> fav = favouriteRepository.findFavourite(userId.getUserId()); 
		List<Place> Response = new ArrayList<Place>();
		for(Favourite value : fav) {
			int placeId = value.getPlace_id();
			Place place = placeRepository.findByPlaceId(placeId);			
			

			Response.add(place);
			
		}
		return Response;
		
	}

}
