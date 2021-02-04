package com.megaProject.Application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megaProject.Application.model.Distance;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.model.ReviewRating;
import com.megaProject.Application.repository.PlaceRepository;


@Service
public class SortingService {
	

	@Autowired
	ComparatorService compareService;
	
	@Autowired
	PlaceRepository placeRepo;
	
	@Autowired
	DistanceCalculator distanceCal;
	
	public List<Place> PlaceSortByRating(List<Place> places) {

		Collections.sort(places, compareService.PlaceRating);

		return places;

	}

	public List<Distance> PlaceSortByRatings(List<Distance> places) {

		Collections.sort(places, compareService.DistanceRatings);

		return places;

	}
	
	
	public List<Distance> PlaceSortByDistance(List<Distance> places) {

		Collections.sort(places, compareService.DistanceCompare);

		return places;

	}
	
    public HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<Integer, Integer> > list = 
               new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet()); 
        
     
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() { 
            public int compare(Map.Entry<Integer, Integer> o1,  
                               Map.Entry<Integer, Integer> o2) 
            { 
                return (o2.getValue()).compareTo(o1.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>(); 
        for (Map.Entry<Integer, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }

//===================================Filter========================
    
	public List<Place> sortByRating(List<Place> places) {

		Collections.sort(places, compareService.PlaceRating);

		return places;

	}

	public List<Place> sortByDistance(List<Place> places, double latitude, double longitude) {

		List<Distance> distance = new ArrayList<Distance>();
		List<Place> place = new ArrayList<Place>();

		for (Place placeValues : places) {
			double lat = placeValues.getLatitude();
			double lon = placeValues.getLongitude();
			double km = distanceCal.distance(latitude, longitude, lat, lon);

			// System.out.println(km);
			distance.add(new Distance(placeValues, km));

		}
		Collections.sort(distance, compareService.DistanceCompare);

		for (Distance i : distance) {

			Place values = (Place) i.getPlace();
			System.out.println(values.getName() + " ---  " + i.getDistance());
			place.add(values);

		}

		return place;

	}
	
	public List<Place> sortForPopular(List<Place> places, List<ReviewRating> reviewRating) {
		List<Place> FinalList = new ArrayList<>();

		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		for (Place val : places) {
			System.out.println(val.getId() + " " + val.getLandmark());
			int count = 0;
			for (ReviewRating rev : reviewRating) {
				if (val.getId() == rev.getPlace_id()) {
					count = count + 1;
				}
			}
			System.out.println(count);
			if (count != 0) {
				hm.put(val.getId(), count);

			}

		}
		Map<Integer, Integer> hm1 = sortByValue(hm);

		for (Map.Entry<Integer, Integer> en : hm1.entrySet()) {
			System.out.println("Key = " + en.getKey() + ", Value = " + en.getValue());
		}

		for (Map.Entry<Integer, Integer> en : hm1.entrySet()) {
			Place pl = placeRepo.findByPlaceId(en.getKey());
			FinalList.add(pl);
		}
		return FinalList;

	}

}
