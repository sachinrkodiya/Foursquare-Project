package com.megaProject.Application.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.megaProject.Application.model.Filter;
import com.megaProject.Application.model.Place;

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

	public List<Filter> featureFilter(Filter filReq, List<Place> placeList) {

		List<Filter> DatabaseValues = new ArrayList<Filter>();
		List<Filter> temp = new ArrayList<>();
		List<Integer> placeId = new ArrayList<>();

		for (Place pl : placeList) {
			placeId.add(pl.getId());

		}
		for (Integer i : placeId) {
			DatabaseValues.add(filterRepo.findByPlaceId(i));
		}

		if (filReq.isCredit_card()) {
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

		if (filReq.isWi_fi()) {
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
	
//=============request body===================
	
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

	

}
