package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.db.model.Location;
import com.travel.book.easy.travelbookeasy.db.repository.LocationRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public Location createLocation(String name) {

		Location location = locationRepository.findByName(name);
		Location saveLocation = null;

		if (location == null) {
			Location newLocation = new Location();
			newLocation.setName(name);

			saveLocation = locationRepository.saveAndFlush(newLocation);
		}

		return saveLocation == null ? location : saveLocation;
	}

	@Override
	public Location findById(long id) {

		Optional<Location> location = locationRepository.findById(id);

		if (!location.isPresent()) {
			throw new ApiException("Location not found");
		}

		return location.get();
	}

}
