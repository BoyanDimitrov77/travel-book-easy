package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.TravelClassDto;
import com.travel.book.easy.travelbookeasy.db.model.TravelClass;
import com.travel.book.easy.travelbookeasy.db.repository.TravelClassRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.TravelClassService;

@Service
public class TravelClassServiceImpl implements TravelClassService{

	@Autowired
	private TravelClassRepository travelClassRepository;

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

	@Override
	public TravelClass updateTravelClass(TravelClassDto travelClassDto) {
		Optional<TravelClass> travelClass = travelClassRepository.findById(travelClassDto.getId());

		if (!travelClass.isPresent()) {
			throw new ApiException("Travel class not found");
		}

		if (travelClassDto.getMaxSeats() != 0) {
			travelClass.get().setMaxSeats(travelClassDto.getMaxSeats());
		}
		if (travelClassDto.getTravelClass() != null) {
			travelClass.get().setTravelClass(travelClassDto.getTravelClass());
		}
		if (travelClassDto.getPrice() != null) {
			travelClass.get().setPrice(travelClassDto.getPrice());
		}

		return travelClass.get();
	}

}
