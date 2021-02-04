package com.megaProject.Application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megaProject.Application.model.DAOUser;
import com.megaProject.Application.model.Favourite;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.repository.FavouriteRepository;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.request.FavRequest;

import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.service.Pagging;






@RestController
@CrossOrigin
public class FavouriteController {
	
	
	
	@Autowired
	FavouriteRepository favouriteRepository;
	
	@Autowired
	PlaceRepository placeRepository;
	
	@Autowired
	Pagging pagging;
	
	@Autowired
	UserDao userDao;
	
	
	@PostMapping("/addFavourite")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> AddFavourite(@RequestBody FavRequest favValues ) {
		Favourite value = new Favourite();
		DAOUser user = userDao.findByUserID(favValues.getUserId());
		if(user == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NO CONTENT","user Id not found"));
			
		}
		Place place = placeRepository.findByPlaceId(favValues.getPlaceId());
		if(place == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NO CONTENT","place Id not found"));
			
		}
		
		Favourite check = favouriteRepository.findFav(favValues.getUserId(), favValues.getPlaceId());
		if(check != null) {
			return ResponseEntity.status(200).body(new ReturnResponse(402,"Bad Credentials","Favorite already exist"));
			
		}
		
		
		value.setUser_id(favValues.getUserId());
		value.setPlace_id(favValues.getPlaceId());
		favouriteRepository.save(value);
		
		
		return ResponseEntity.status(200).body(new ReturnResponse(200," ","Favorite added successfully",value));
		
	}
	
	
	
	@DeleteMapping("/deleteFavourite")
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<?> deleteFavourite(@RequestBody FavRequest values) {
		
		try {
			Favourite check = favouriteRepository.findFav(values.getUserId(), values.getPlaceId());
			if(check == null) {
				return ResponseEntity.status(200).body(new ReturnResponse(402,"Bad Credentials","Favourite  not found"));
				
			}
			else {
				favouriteRepository.deleteFavourite(values.getUserId(), values.getPlaceId());
				return ResponseEntity.status(200).body(new ReturnResponse(200," ","Favorite Deleted successfully"));	
			}

		} catch (NoSuchElementException e){
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NOT FOUND ","No data to delete"));
		}
	
		
	}
	
	
	@GetMapping("/getFavourite")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> findFavourites(@RequestParam long userId,@RequestParam  int pageNo, @RequestParam  int pageSize) {
		
		List<Favourite> result = favouriteRepository.findFavourite(userId); 
		if(result.size() == 0) {
			return ResponseEntity.status(200).body(new ReturnResponse(204,"NO CONTENT ","user id is not valid"));
			
		}
		List<Place> Response = new ArrayList<Place>();
		for(Favourite value : result) {
			int placeId = value.getPlace_id();
			Place place = placeRepository.findByPlaceId(placeId);			
			

			Response.add(place);
			
		}
		return pagging.PagedValues(Response, pageNo, pageSize);
		
	}

}
