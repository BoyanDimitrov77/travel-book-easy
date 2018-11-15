package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.dto.TravelClassDto;
import com.travel.book.easy.travelbookeasy.db.model.TravelClass;
import com.travel.book.easy.travelbookeasy.services.interfaces.TravelClassService;

@Service
public class TravelClassServiceImpl implements TravelClassService{

	@Override
	public List<TravelClass> createTravelClasses(List<TravelClassDto> travelClassDtos) {
		return travelClassDtos.stream().map(dto ->{
			TravelClass travelClass = new TravelClass();
			travelClass.setMaxSeats(dto.getMaxSeats());
			travelClass.setPrice(dto.getPrice());
			travelClass.setTravelClass(dto.getTravelClass());

			return travelClass;
			
		}).collect(Collectors.toList());
	}

}
