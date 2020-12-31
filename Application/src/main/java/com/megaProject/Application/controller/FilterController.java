package com.megaProject.Application.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.megaProject.Application.model.Filter;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.model.PlaceDistance;
import com.megaProject.Application.repository.FilterRepository;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.request.FilterRequest;
import com.megaProject.Application.service.DistanceCalculator;
import com.megaProject.Application.service.FilterService;

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

	@GetMapping("/FeatureFilters")
	public List<Place> filterByData(@RequestBody FilterRequest filReq) {
		List<Place> place = placeRepo.findAll();
		List<Place> placeList = new ArrayList<Place>();
		List<Place> pl = new ArrayList<Place>();

		List<Filter> featurelist = new ArrayList<Filter>();
		if (filReq.getLandmark() == null) {

			for (Place placeValues : place) {
				double lat = placeValues.getLatitude();
				double lon = placeValues.getLongitude();
				double value = distanceCal.distance(filReq.getLatitude(), filReq.getLongitude(), lat, lon);
				System.out.println(value);

				if (filReq.getRadius() == 0) {
					if (value < 30) {
						placeList.add(placeValues);
						if (filReq.getCost() != 0) {
							placeList = filterService.GetbyCost(filReq.getCost(), placeList);

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

						featurelist = filterService.feature(filReq, placeList);
						placeList = filterService.getPlaceValues(featurelist);
					}
				}
			}

		} else {
			placeList = placeRepo.findByLandmark(filReq.getLandmark());

			if (filReq.getCost() != 0) {
				placeList = filterService.GetbyCost(filReq.getCost(), placeList);

			}
			featurelist = filterService.feature(filReq, placeList);
			placeList = filterService.getPlaceValues(featurelist);

		}

		if (filReq.getSortBy().equals("rating")) {
			placeList = sortByRating(placeList);

		}
		else {
			placeList = sortByDistance(placeList,filReq.getLatitude(),filReq.getLongitude());
			
		}


		

		return placeList;

	}

//_____________________________-----==============

	public List<Place> sortByRating(List<Place> places) {

		Collections.sort(places, filterService.PlaceRating);

		return places;

	}
	
	
	public List<Place> sortByDistance(List<Place> places, double latitude, double longitude) {
		
		List<PlaceDistance> distance = new ArrayList<PlaceDistance>();
		List<Place> place = new ArrayList<Place>();
		List<PlaceDistance> temp = new ArrayList<PlaceDistance>();
		

		for (Place placeValues : places) {
			double lat = placeValues.getLatitude();
			double lon = placeValues.getLongitude();
			double km = distanceCal.distance(latitude, longitude, lat, lon);
			System.out.println(placeValues.getId()+" ---  "+ km);
			//System.out.println(km);
			distance.add(new PlaceDistance(placeValues.getId(),km));


		}
		Collections.sort(distance,filterService.DistanceCompare);
		
	   
	   		
		for (PlaceDistance i : distance) {
			 System.out.println("values======== "+ i.getDistance() +" " + i.getPlaceId());
			place.add(placeRepo.findByPlaceId(i.getPlaceId()));
			
			

		}
		
		
		return place;
		
	}

}



