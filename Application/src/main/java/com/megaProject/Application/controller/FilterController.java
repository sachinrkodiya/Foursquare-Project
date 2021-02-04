package com.megaProject.Application.controller;

/*
 * when using the landmark near me, sortBydistance, radius is disabled.
 * when using nearme  the landmark is disabled.
 */
import java.util.ArrayList;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.megaProject.Application.model.Filter;
import com.megaProject.Application.model.Place;

import com.megaProject.Application.model.ReviewRating;
import com.megaProject.Application.repository.FilterRepository;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.ReviewRatingRepository;
import com.megaProject.Application.request.FilterRequest;
import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.service.DistanceCalculator;
import com.megaProject.Application.service.FilterService;
import com.megaProject.Application.service.Pagging;
import com.megaProject.Application.service.SortingService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FilterController {

	@Autowired
	FilterRepository filterRepo;

	@Autowired
	PlaceRepository placeRepo;

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

	@GetMapping("/search")
	public ResponseEntity<?> Search(@RequestParam(value = "name") String name, @RequestParam int pageNo,
			@RequestParam int pageSize) {
		int count = 0;

		List<Place> result = placeRepo.findByLandmark(name);
		List<Place> placeList = placeRepo.findByNames(name);

		for (Place valueOne : placeList) {
			for (Place valueTwo : result) {
				if (valueOne.getId() == valueTwo.getId()) {
					count = 0;
					break;
				} else {
					count++;
				}
			}
			if (count == result.size()) {
				result.add(valueOne);

			}

		}
		if (result.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NO CONTENT", "NO PLACE FOUND"));

		}
		return pagging.PagedValues(result, pageNo, pageSize);

	}

	@PostMapping("/filters")
	public ResponseEntity<?> filterByData(@RequestBody FilterRequest filReq, @RequestParam int pageNo,
			@RequestParam int pageSize) {

		List<Place> place = placeRepo.findAll();
		List<Place> placeList = new ArrayList<Place>();

		List<ReviewRating> reviewRating = RevRatRepository.findAll();

		List<Filter> featurelist = new ArrayList<Filter>();
		if (filReq.getLandmark() == null && (filReq.getLatitude() == 0 || filReq.getLongitude() == 0)) {
			return ResponseEntity.status(200)
					.body(new ReturnResponse(401, "BAD CREDENTIALS", "VALUES ARE NOT ORAGNIZED IN PROPER WAY"));
		}

		if (filReq.getLandmark() == null) {

			for (Place placeValues : place) {
				double lat = placeValues.getLatitude();
				double lon = placeValues.getLongitude();
				double value = distanceCal.distance(filReq.getLatitude(), filReq.getLongitude(), lat, lon);

				if (filReq.getRadius() == 0) {
					if (value < 30) {
						placeList.add(placeValues);
						if (filReq.getCost() != 0) {
							placeList = filterService.GetbyCost(filReq.getCost(), placeList);

						}
						if (filReq.getSortBy() != null) {
							if (filReq.getSortBy().equals("distance")) {

								placeList = sortingService.sortByDistance(placeList, filReq.getLatitude(), filReq.getLongitude());
							}

							if (filReq.getSortBy().equals("rating")) {
								placeList = sortingService.sortByRating(placeList);
							}
							
							if (filReq.getSortBy().equals("popular")) {
								placeList = sortingService.sortForPopular(placeList,reviewRating);

							}

						}

						featurelist = filterService.feature(filReq, placeList);
						placeList = filterService.getPlaceValues(featurelist);
					}

				} else {
					if (value <= filReq.getRadius()) {
						placeList.add(placeValues);
						if (filReq.getCost() != 0) {
							placeList = filterService.GetbyCost(filReq.getCost(), placeList);

						}
						if (filReq.getSortBy() != null) {
							if (filReq.getSortBy().equals("distance")) {

								placeList = sortingService.sortByDistance(placeList, filReq.getLatitude(), filReq.getLongitude());
							}

							if (filReq.getSortBy().equals("rating")) {
								placeList = sortingService.sortByRating(placeList);
							}
							if (filReq.getSortBy().equals("popular")) {
								placeList = sortingService.sortForPopular(placeList,reviewRating);

							}

						}

						featurelist = filterService.feature(filReq, placeList);
						placeList = filterService.getPlaceValues(featurelist);

						// ---

					}

				}
			}

		} else { // landmark---
			List<Place> landmarkValues = placeRepo.findByLandmark(filReq.getLandmark());
			placeList = landmarkValues;

//
			if (filReq.getCost() != 0) {
				placeList = filterService.GetbyCost(filReq.getCost(), placeList);

			}
			featurelist = filterService.feature(filReq, placeList);
			placeList = filterService.getPlaceValues(featurelist);

		}
		if (filReq.getSortBy() != null) {
			if (filReq.getSortBy().equals("rating")) {
				placeList = sortingService.sortByRating(placeList);
			}
			if (filReq.getSortBy().equals("popular")) {
				placeList = sortingService.sortForPopular(placeList,reviewRating);

			}

		}
		return pagging.PagedValues(placeList, pageNo, pageSize);

	}
	
	

	@RequestMapping(value = "/addFilter", method = RequestMethod.POST)
	public ResponseEntity<?> addFilter(@RequestBody Filter filter) throws Exception {
		return ResponseEntity.ok(filterRepo.save(filter));
	}
}
