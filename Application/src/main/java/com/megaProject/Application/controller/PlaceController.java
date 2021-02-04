package com.megaProject.Application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.megaProject.Application.model.ReviewRating;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.PlaceTypeRepository;
import com.megaProject.Application.repository.ReviewRatingRepository;
import com.megaProject.Application.request.EnterPlace;

import com.megaProject.Application.response.ReturnResponse;
import com.megaProject.Application.service.DistanceCalculator;
import com.megaProject.Application.service.FilterService;
import com.megaProject.Application.service.Pagging;
import com.megaProject.Application.service.SortingService;

@RestController
@RequestMapping("/PlaceApi")
public class PlaceController {
	@Autowired
	PlaceRepository placeRepository;

	@Autowired
	PlaceTypeRepository placeTypeRepository;
	
	@Autowired
	ReviewRatingRepository RevRatRepository;

	@Autowired
	DistanceCalculator dist;

	@Autowired
	FilterService filterService;
	
	@Autowired
	SortingService sortingService;

	@Autowired
	Pagging pagging;



	@GetMapping("/nearBy")
	public ResponseEntity<?> distanceValue(@RequestParam double latitude,@RequestParam double longitude ,@RequestParam int pageNo, @RequestParam int pageSize) {

		List<Place> place = placeRepository.findAll();
		List<Distance> distance = new ArrayList<Distance>();
		List<Distance> FinalList = new ArrayList<>();

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
		FinalList = sortingService.PlaceSortByDistance(distance);
		
		if (FinalList.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND NEAR BY"));

		}
		return pagging.PagedValues(FinalList, pageNo, pageSize);

	}

	@GetMapping("/topPick")
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

		FinalPopularList = sortingService.PlaceSortByRatings(popularList);
		if (FinalPopularList.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND NEAR BY"));

		}
		return pagging.PagedValues(FinalPopularList, pageNo, pageSize);

	}
	
	
	@GetMapping("/popular")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> TopPickValue(@RequestParam double latitude,@RequestParam double longitude,@RequestParam int pageNo, @RequestParam int pageSize) {

		List<Place> places = placeRepository.findAll();

		List<ReviewRating> reviewRating = RevRatRepository.findAll();
		List<Place> FinalList = new ArrayList<>();
		List<Place> place = new ArrayList<>();
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>(); 
	     
	

		for (Place placeValues : places) {
			double lat = placeValues.getLatitude();
			double lon = placeValues.getLongitude();
			double km = dist.distance(latitude, longitude, lat, lon);
			System.out.println(km);
			if (km < 30) {
				place.add(placeValues);
			}

		}
		if (place.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO VALUE FOUND NEAR BY"));

		}
		
		
		for(Place val:place) {
			System.out.println(val.getId()+" "+val.getLandmark());
			int count = 0;
			for(ReviewRating rev: reviewRating ) {
				if(val.getId() == rev.getPlace_id()) {
					count = count+1;
				}	
			}
			System.out.println(count);
			if(count != 0) {
		        hm.put(val.getId(), count);
				
			}

		}
		
		if(hm.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO POPULAR VALUE FOUND NEAR BY"));
			
		}
		Map<Integer, Integer> hm1 = sortingService.sortByValue(hm);
		
        for (Map.Entry<Integer, Integer> en : hm1.entrySet()) { 
            System.out.println("Key = " + en.getKey() +  
                          ", Value = " + en.getValue()); 
        } 
        
        for (Map.Entry<Integer, Integer> en : hm1.entrySet()) { 
        	Place pl = placeRepository.findByPlaceId(en.getKey());
        	FinalList.add(pl);
        } 
		if (FinalList.isEmpty()) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND"));

		}
		
		return pagging.PagedValues(FinalList, pageNo, pageSize);
	}
	
	
	@GetMapping("/placeById")
	public ResponseEntity<?> getPlaceBy(@RequestParam int placeId) {
		Place place = placeRepository.findByPlaceId(placeId);
		if (place == null) {
			return ResponseEntity.status(200).body(new ReturnResponse(204, "NOT FOUND", "NO PLACE FOUND"));

		}
		return ResponseEntity.status(200).body(new ReturnResponse(200, " ", "Success", place));
	}
	
	
	//========================================================================================

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


}
