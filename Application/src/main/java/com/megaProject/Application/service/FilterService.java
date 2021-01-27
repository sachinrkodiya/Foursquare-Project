package com.megaProject.Application.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megaProject.Application.model.Distance;
import com.megaProject.Application.model.Filter;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.model.PlaceDistance;
import com.megaProject.Application.repository.FilterRepository;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.request.FilterRequest;

@Service
public class FilterService {

	@Autowired
	FilterRepository filterRepo;

	@Autowired
	PlaceRepository placeRepo;

	public List<Place> GetbyCost(int cost, List<Place> place) {

		List<Place> placeLst = new ArrayList<Place>();
		for (Place pl : place) {
			if (pl.getCost() == cost) {
				placeLst.add(pl);

			}
		}
		return placeLst;
	}

	public List<Filter> feature(FilterRequest filReq, List<Place> placeList) {

		List<Filter> DatabaseValues = new ArrayList<Filter>();
		List<Filter> temp = new ArrayList<>();
		List<Integer> placeId = new ArrayList<>();

		for (Place pl : placeList) {
			placeId.add(pl.getId());

		}
		for (Integer i : placeId) {
			DatabaseValues.add(filterRepo.findByPlaceId(i));
		}

		if (filReq.isCreditCard()) {
			for (Filter value : DatabaseValues) {
				if (value.isCredit_card()) {
					temp.add(value);
				}

			}

			DatabaseValues.clear();
			DatabaseValues.addAll(temp);
			temp.clear();

		}

		if (filReq.isDelivery()) {
			for (Filter value : DatabaseValues) {
				if (value.isDelivery()) {
					temp.add(value);
				}

			}

			DatabaseValues.clear();
			DatabaseValues.addAll(temp);
			temp.clear();

		}

		if (filReq.isFamily_friendly()) {
			for (Filter value : DatabaseValues) {
				if (value.isFamily_friendly()) {
					temp.add(value);
				}

			}

			DatabaseValues.clear();
			DatabaseValues.addAll(temp);
			temp.clear();

		}

		if (filReq.isDog_friendly()) {
			for (Filter value : DatabaseValues) {
				if (value.isDog_friendly()) {
					temp.add(value);
				}

			}

			DatabaseValues.clear();
			DatabaseValues.addAll(temp);
			temp.clear();

		}

		if (filReq.isOutdoor()) {
			for (Filter value : DatabaseValues) {
				if (value.isOutdoor()) {
					temp.add(value);
				}

			}

			DatabaseValues.clear();
			DatabaseValues.addAll(temp);
			temp.clear();

		}

		if (filReq.isWalking()) {
			for (Filter value : DatabaseValues) {
				if (value.isWalking()) {
					temp.add(value);
				}

			}

			DatabaseValues.clear();
			DatabaseValues.addAll(temp);
			temp.clear();

		}

		if (filReq.isParking()) {
			for (Filter value : DatabaseValues) {
				if (value.isParking()) {
					temp.add(value);
				}

			}

			DatabaseValues.clear();
			DatabaseValues.addAll(temp);
			temp.clear();

		}

		if (filReq.isWifi()) {
			for (Filter value : DatabaseValues) {
				if (value.isWi_fi()) {
					temp.add(value);
				}

			}

			DatabaseValues.clear();
			DatabaseValues.addAll(temp);
			temp.clear();

		}

		return DatabaseValues;

	}

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
			int ans =  rate1 - rate2;
			return ans;
		}
	};
	
//--------------
	
	
	public static Comparator<Distance> PlaceRatings = new Comparator<Distance>() {
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
			int ans =  rate2 - rate1;
			return ans;
		}

	};
	

}
