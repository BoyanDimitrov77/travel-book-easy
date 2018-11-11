package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.db.model.Flight;
import com.travel.book.easy.travelbookeasy.db.repository.CompanyRepository;
import com.travel.book.easy.travelbookeasy.db.repository.FlightRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.FlightService;
import com.travel.book.easy.travelbookeasy.services.interfaces.LocationService;
import com.travel.book.easy.travelbookeasy.services.interfaces.TravelClassService;

@Service
public class FlightServiceImpl implements FlightService{

	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private TravelClassService travelClassService;
	
	@Override
	public FlightDto createFlighRecord(FlightDto flightDto, long companyId) {
		
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
		flight.setTracelClasses(travelClassService.createTravelClasses(flightDto.getTravelClassDtos()));
		
		Flight savedFlight = flightRepository.saveAndFlush(flight);
		
		return FlightDto.of(savedFlight);
	}

	@Override
	public FlightDto getFlight(long flightId) {
		
		Optional<Flight> flight = flightRepository.findById(flightId);
		
		if(!flight.isPresent()) {
			throw new ApiException("Flight not found");
		}
		
		return FlightDto.of(flight.get());
	}

}
