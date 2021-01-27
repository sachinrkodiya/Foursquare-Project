package com.megaProject.Application.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megaProject.Application.model.Distance;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.model.PlaceType;

import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.PlaceTypeRepository;
import com.megaProject.Application.request.EnterPlace;
import com.megaProject.Application.request.LatAndLon;
import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.service.DistanceCalculator;
import com.megaProject.Application.service.FilterService;
import com.megaProject.Application.service.Pagging;

@RestController
@RequestMapping("/PlaceApi")
public class PlaceController {
	@Autowired
	PlaceRepository placeRepository;

	@Autowired
	PlaceTypeRepository placeTypeRepository;

	@Autowired
	DistanceCalculator dist;

	@Autowired
	FilterService filterService;

	@Autowired
	Pagging pagging;

	@GetMapping("/getAllPlaces")
	public ResponseEntity<?> getAllNotes(@RequestParam int pageNo, @RequestParam int pageSize) {
		List<Place> places = placeRepository.findAll();
		return pagging.PagedValues(places, pageNo, pageSize);


	}
	
	@GetMapping("/test")
	public ResponseEntity<?> testApi(@RequestParam int Valid, @RequestParam int pageNo, @RequestParam int pageSize) {
		if(Valid != 10) {
			return ResponseEntity.status(200).body(new ReturnResponse(404, "VALID VALUE ERROR", "Request parm valid value not equals to 10"));
			
		}
		List<Place> places = placeRepository.findAll();
		return pagging.PagedValues(places, pageNo, pageSize);


	}

	@PostMapping("/addPlaces")
	public Place addPlace(@Valid @RequestBody EnterPlace place) {

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

	@GetMapping("/getPlaceByName")
	public ResponseEntity<?> getPlaceByName(@RequestParam String name) {
		List<Place> placeList = placeRepository.findByNames(name);
		if (placeList.isEmpty()) {
			return ResponseEntity.status(204).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND"));

		}
		return ResponseEntity.status(200).body(new ReturnResponse(200, " ", "Success", placeList));
	}

	@GetMapping("/placeById")
	public ResponseEntity<?> getPlaceBy(@RequestParam int placeId) {
		Optional<Place> place = placeRepository.findById(placeId);
		if (place.isEmpty()) {
			return ResponseEntity.status(404).body(new ReturnResponse(404, "NOT FOUND", "NO PLACE FOUND"));

		}
		return ResponseEntity.status(200).body(new ReturnResponse(200, " ", "Success", place));
	}

	@GetMapping("/searchByLandmark")
	public ResponseEntity<?> getPlaceByLandmark(@RequestParam(value = "landmark") String landmark,
			@RequestParam int pageNo, @RequestParam int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Place> result = placeRepository.findByLandmark(landmark, paging);
		List<Place> pagedResult = result.toList();

		if (pagedResult.isEmpty()) {
			return ResponseEntity.status(204).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND"));

		}
		if (pagedResult.size() <= pageSize) {
			boolean lastPage = true;
			return ResponseEntity.status(200)
					.body(new ReturnResponse(200, " ", "Success", pageNo, pageSize, lastPage, pagedResult));
		}
		return ResponseEntity.status(200)
				.body(new ReturnResponse(200, " ", "Success", pageNo, pageSize, false, pagedResult));
	}

	@GetMapping("/nearBy")
	public ResponseEntity<?> distanceValue(@RequestParam double latitude,@RequestParam double longitude ,@RequestParam int pageNo, @RequestParam int pageSize) {

		List<Place> place = placeRepository.findAll();
		List<Distance> distance = new ArrayList<Distance>();

		for (Place placeValues : place) {
			double lat = placeValues.getLatitude();
			double lon = placeValues.getLongitude();
			double km = dist.distance(latitude, longitude, lat, lon);
			System.out.println(km);
			if (km < 30) {
				Distance value = new Distance(placeValues, km);
				distance.add(value);
			}

		}
		if (distance.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND NEAR BY"));

		}
		return pagging.PagedValues(distance, pageNo, pageSize);

	}

	@GetMapping("/popular")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> PopularValue(@RequestParam double latitude,@RequestParam double longitude,@RequestParam int pageNo, @RequestParam int pageSize) {

		List<Place> places = placeRepository.findAll();

		// --------------

		List<Distance> distance = new ArrayList<Distance>();
		List<Distance> popularList = new ArrayList<>();
		List<Distance> FinalPopularList = new ArrayList<>();

		for (Place placeValues : places) {
			double lat = placeValues.getLatitude();
			double lon = placeValues.getLongitude();
			double km = dist.distance(latitude, longitude, lat, lon);
			System.out.println(km);
			if (km < 30) {
				Distance value = new Distance(placeValues, km);
				distance.add(value);
			}

		}

		for (Distance v : distance) {
			Place place = (Place) v.getPlace();

			if (place.getOverallRating() >= 3) {
				popularList.add(v);
			}
		}

		FinalPopularList = sortByRatings(popularList);
		if (FinalPopularList.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND NEAR BY"));

		}
		return pagging.PagedValues(FinalPopularList, pageNo, pageSize);

	}

	@GetMapping("/popularByLandmark")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> popular(@RequestParam String landmark, @RequestParam int pageNo,
			@RequestParam int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Place> pagedResult = (Page<Place>) placeRepository.findByPopular(landmark, paging);
		List<Place> result = pagedResult.toList();
		List<Place> popularList = new ArrayList<>();
		for (Place place : result) {
			if (place.getOverallRating() >= 3) {
				popularList.add(place);
			}
		}
		System.out.println(popularList.size());
		if (popularList.size() == 0) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NO CONTENT", "NO DATA TO DISPLAY"));

		}
		if (popularList.size() <= pageSize) {
			boolean lastPage = true;
			return ResponseEntity.status(200)
					.body(new ReturnResponse(200, " ", "Success", pageNo, pageSize, lastPage, popularList));
		}
		return ResponseEntity.status(200)
				.body(new ReturnResponse(200, " ", "Success", pageNo, pageSize, false, popularList));

	}

	public List<Place> sortByRating(List<Place> places) {

		Collections.sort(places, filterService.PlaceRating);

		return places;

	}

	public List<Distance> sortByRatings(List<Distance> places) {

		Collections.sort(places, filterService.PlaceRatings);

		return places;

	}

}
