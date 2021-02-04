package com.megaProject.Application.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megaProject.Application.model.Distance;
import com.megaProject.Application.model.Filter;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.repository.PlaceRepository;


@Service
public class ComparatorService {
	
	@Autowired
	PlaceRepository placeRepo;
	
	public List<Place> getPlaceValues(List<Filter> filter) {
		List<Integer> placeId = new ArrayList<>();
		List<Place> place = new ArrayList<>();
		for (Filter values : filter) {
			placeId.add(values.getPlace_id());
		}

		for (Integer i : placeId) {
			place.add(placeRepo.findByPlaceId(i));

		}
		return place;
	}


	public static Comparator<Place> PlaceRating = new Comparator<Place>() {

		@Override
		public int compare(Place o1, Place o2) {
			// TODO Auto-generated method stub

			float r1 =  o1.getOverallRating();
			float r2 =  o2.getOverallRating();
			int rate1 = (int) (o1.getOverallRating());
			int rate2 = (int) (o2.getOverallRating());
			if(r1 % rate1 != 0) {
				rate1 = rate1+1;
				
			}
			if(r2 % rate2 != 0) {
				rate2 = rate2+1;
				
			}
			if(r2 > r1) {
				rate2 = rate2+1;
			}
			else {
				rate1 = rate1+1;
				
			}
			int ans =  rate2 - rate1;
			return ans;
		}
	};
	
	public static Comparator<Distance> DistanceCompare = new Comparator<Distance>() {

		@Override
		public int compare(Distance o1, Distance o2) {
			double r1 =  o1.getDistance();
			double r2 =  o2.getDistance();
			int rate1 = (int) (o1.getDistance());
			int rate2 = (int) (o2.getDistance());
			if(r1 % rate1 != 0) {
				rate1 = rate1+1;
				
			}
			if(r2 % rate2 != 0) {
				rate2 = rate2+1;
				
			}
			
			if(r2 > r1) {
				rate2 = rate2+1;
			}
			else {
				rate1 = rate1+1;
				
			}
			int ans =  rate1 - rate2;
			return ans;
		}
	};
	
//--------------
	
	
	public static Comparator<Distance> DistanceRatings = new Comparator<Distance>() {
		@Override
		public int compare(Distance o1, Distance o2) {
		    Place v1 = (Place) o1.getPlace();
		    Place v2 = (Place) o2.getPlace();
			float r1 =  v1.getOverallRating();
			float r2 =  v2.getOverallRating();
			int rate1 = (int) (v1.getOverallRating());
			int rate2 = (int) (v2.getOverallRating());
			if(r1 % rate1 != 0) {
				rate1 = rate1+1;
				
			}
			if(r2 % rate2 != 0) {
				rate2 = rate2+1;
				
			}
			if(r2 > r1) {
				rate2 = rate2+1;
			}
			else {
				rate1 = rate1+1;
				
			}
			int ans =  rate2 - rate1;
			return ans;
		}

	};
	


}
