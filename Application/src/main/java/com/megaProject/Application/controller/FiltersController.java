package com.megaProject.Application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.megaProject.Application.model.Filter;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.model.ReviewRating;
import com.megaProject.Application.repository.FilterRepository;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.ReviewRatingRepository;

import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.service.DistanceCalculator;
import com.megaProject.Application.service.FilterService;
import com.megaProject.Application.service.Pagging;
import com.megaProject.Application.service.SortingService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FiltersController {
	@Autowired
	FilterRepository filterRepo;

	@Autowired
	PlaceRepository placeRepo;
	
//	@Autowired
//	FilterRequest filReq;

	@Autowired
	DistanceCalculator distanceCal;

	@Autowired
	FilterService filterService;
	
	@Autowired
	SortingService sortingService;

	@Autowired
	Pagging pagging;

	@Autowired
	ReviewRatingRepository RevRatRepository;
	
	
	@GetMapping("/FeatureFilters")
	public ResponseEntity<?> filterByData(@RequestParam String landmark, @RequestParam double latitude, 
			@RequestParam double longitude,@RequestParam boolean rating, @RequestParam boolean distance,
			@RequestParam boolean popular,@RequestParam float radius, @RequestParam int cost, 
			@RequestParam boolean creditCard,@RequestParam boolean delivery, @RequestParam boolean dog_friendly, 
			@RequestParam boolean family_friendly, @RequestParam boolean outdoor, @RequestParam boolean wifi,
			@RequestParam boolean walking, @RequestParam boolean parking, 
			@RequestParam int pageNo, @RequestParam int pageSize) {
		

		List<Place> place = placeRepo.findAll();
		List<Place> placeList = new ArrayList<Place>();
		List<ReviewRating> reviewRating = RevRatRepository.findAll();
		List<Filter> featurelist = new ArrayList<Filter>();
		Filter filter = new Filter(creditCard,  delivery,  dog_friendly,  outdoor,  
				family_friendly,walking, parking, wifi);
		
		if (landmark == null && (latitude == 0 || longitude == 0)) {
			return ResponseEntity.status(200)
					.body(new ReturnResponse(401, "BAD CREDENTIALS", "VALUES ARE NOT ORAGNIZED IN PROPER WAY"));
		}
		if (landmark == null) {

			for (Place placeValues : place) {
				double lat = placeValues.getLatitude();
				double lon = placeValues.getLongitude();
				double value = distanceCal.distance(latitude, longitude, lat, lon);

				if (radius == 0) {
					if (value < 30) {
						placeList.add(placeValues);
						if (cost != 0) {
							placeList = filterService.GetbyCost(cost, placeList);

						}
						if (rating || distance || popular) {
							if (distance) {

								placeList = sortingService.sortByDistance(placeList, latitude, longitude);
							}

							if (rating) {
								placeList = sortingService.sortByRating(placeList);
							}
							
							if (popular) {
								placeList = sortingService.sortForPopular(placeList,reviewRating);

							}

						}
						


						featurelist = filterService.featureFilter(filter, placeList);
						placeList = filterService.getPlaceValues(featurelist);
					}

				} else {
					if (value <= radius) {
						placeList.add(placeValues);
						if (cost != 0) {
							placeList = filterService.GetbyCost(cost, placeList);

						}
						if (rating || distance || popular) {
							if (distance) {

								placeList = sortingService.sortByDistance(placeList, latitude, longitude);
							}

							if (rating) {
								placeList = sortingService.sortByRating(placeList);
							}
							if (popular) {
								placeList = sortingService.sortForPopular(placeList,reviewRating);

							}

						}

						featurelist = filterService.featureFilter(filter, placeList);
						placeList = filterService.getPlaceValues(featurelist);

						// ---

					}

				}
			}

		} else { // landmark---
			List<Place> landmarkValues = placeRepo.findByLandmark(landmark);
			placeList = landmarkValues;

//
			if (cost != 0) {
				placeList = filterService.GetbyCost(cost, placeList);

			}
			featurelist = filterService.featureFilter(filter, placeList);
			placeList = filterService.getPlaceValues(featurelist);

		}
		if (rating || popular) {
			if (rating) {
				placeList = sortingService.sortByRating(placeList);
			}
			if (popular) {
				placeList = sortingService.sortForPopular(placeList,reviewRating);

			}

		}
		
		
		
		return pagging.PagedValues(placeList, pageNo, pageSize);
	}

}
