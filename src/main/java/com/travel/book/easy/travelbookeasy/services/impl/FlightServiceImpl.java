package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.api.dto.TravelClassDto;
import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.db.model.Flight;
import com.travel.book.easy.travelbookeasy.db.model.TravelClass;
import com.travel.book.easy.travelbookeasy.db.repository.CompanyRepository;
import com.travel.book.easy.travelbookeasy.db.repository.FlightRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.FlightService;
import com.travel.book.easy.travelbookeasy.services.interfaces.LocationService;

@Service
public class FlightServiceImpl implements FlightService{

	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public FlightDto createFligh(FlightDto flightDto, long companyId) {
		
		Optional<Company> company = companyRepository.findById(companyId);
		
		if(!company.isPresent()) {
			throw new ApiException("Company not found");
		}
		
		Flight flight = new Flight();
		flight.setCompany(company.get());
		flight.setName(flightDto.getName());
		flight.setLocationFrom(locationService.createLocation(flightDto.getLocationFrom().getName()));
		flight.setLocationTo(locationService.createLocation(flightDto.getLocationTo().getName()));
		flight.setDepartDate(flightDto.getDepartDate());
		flight.setArriveDate(flightDto.getArriveDate());
		flight.setPrice(flightDto.getPrice());
		flight.setTracelClasses(createTravelClasses(flightDto.getTravelClassDtos()));
		
		Flight saveFlight = flightRepository.saveAndFlush(flight);
		
		return FlightDto.of(saveFlight);
	}

	private List<TravelClass> createTravelClasses(List<TravelClassDto> travelClassDtos) {
		
		 return travelClassDtos.stream().map(dto ->{
			TravelClass travelClass = new TravelClass();
			travelClass.setMaxSeats(dto.getMaxSeats());
			travelClass.setPrice(dto.getPrice());
			travelClass.setTravelClass(dto.getTravelClass());

			return travelClass;
			
		}).collect(Collectors.toList());

	}

}
