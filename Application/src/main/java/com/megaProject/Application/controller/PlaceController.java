package com.megaProject.Application.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.megaProject.Application.model.Place;
import com.megaProject.Application.model.PlaceType;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.PlaceTypeRepository;
import com.megaProject.Application.request.EnterPlace;
import com.megaProject.Application.request.LatAndLon;
import com.megaProject.Application.service.DistanceCalculator;



@RestController
@RequestMapping("/PlaceApi")
public class PlaceController {
	@Autowired
	PlaceRepository placeRepository;

	@Autowired
	PlaceTypeRepository placeTypeRepository;

	@Autowired
	DistanceCalculator dist;

	@GetMapping("/places")
	public List<Place> getAllNotes() {
		return placeRepository.findAll();
	}

	@PostMapping("/addPlaces")
	public Place addPlace(@Valid @RequestBody EnterPlace place) {

		// Create new user's account
		Place newPlace = new Place(place.getName(), place.getLatitude(), place.getLongitude(), place.getCost(),
				place.getLandmark(), place.getAddress(), place.getOverview(), place.getPhone());

		Set<String> strTypes = place.getPlaceType();
		Set<PlaceType> placeVal = new HashSet<>();

		strTypes.forEach(placeType -> {
			switch (placeType) {
			case "italian":
				PlaceType italian = placeTypeRepository.findByName("ITALIAN_RESTAURANT")
						.orElseThrow(() -> new RuntimeException("Error: Place Type is not found."));
				placeVal.add(italian);

				break;
			case "chinese":
				PlaceType chinese = placeTypeRepository.findByName("CHINESE_RESTAURANT")
						.orElseThrow(() -> new RuntimeException("Error: Place Type is not found."));
				placeVal.add(chinese);

				break;
			case "indian":
				PlaceType indian = placeTypeRepository.findByName("INDIAN_RESTAURANT")
						.orElseThrow(() -> new RuntimeException("Error: Place Type is not found."));
				placeVal.add(indian);
			default:
				new RuntimeException("Error: Place Type is not found.");
			}
		});

		newPlace.setPlaceType(placeVal);

		return placeRepository.save(newPlace);
	}

	@GetMapping("/places/{name}")
	public Place getPlaceById(@PathVariable(value = "name") String name) {
		return placeRepository.findByName(name).orElseThrow(() -> new RuntimeException("Error: Place is not found."));
	}
	
	@GetMapping("/searchByLandmark/{landmark}")
	public List<Place> getPlaceByLandmark(@PathVariable(value = "landmark") String landmark) {
		List<Place> landmarkList = placeRepository.findByLandmark(landmark);
		return landmarkList;

	}

	@GetMapping("/nearBy")
	public List<Place> distanceValue(@RequestBody LatAndLon values) {

		List<Place> place = placeRepository.findAll();
		List<Place> distance = new ArrayList<Place>();

		for (Place placeValues : place) {
			double lat = placeValues.getLatitude();
			double lon = placeValues.getLongitude();
			double km = dist.distance(values.getLatitude(), values.getLongitude(), lat, lon);
			System.out.println(km);
			if (km < 30) {
				distance.add(placeValues);
			}

		}
		return distance;

	}

	@GetMapping("/popular/{landmark}")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Place> popular(@PathVariable String landmark) {

		List<Place> place  = placeRepository.findByPopular(landmark);

		return place;

	}

}
