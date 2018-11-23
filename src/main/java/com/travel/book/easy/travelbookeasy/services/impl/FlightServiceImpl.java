package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;
import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.db.model.Flight;
import com.travel.book.easy.travelbookeasy.db.repository.CompanyRepository;
import com.travel.book.easy.travelbookeasy.db.repository.FlightRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.FlightService;
import com.travel.book.easy.travelbookeasy.services.interfaces.LocationService;
import com.travel.book.easy.travelbookeasy.services.interfaces.TravelClassService;
import com.travel.book.easy.travelbookeasy.util.SearchUtil;

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
		flight.setTracelClasses(travelClassService.createTravelClasses(flightDto.getTravelClasses()));
		
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

	@Override
	public List<FlightDto> findAllFlights() {

		return flightRepository.findAllFlights().stream().map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
	}

	@Override
	public List<FlightDto> searchFlights(SearchFilterDto searchFilterDto) {
		if (SearchUtil.checkAllFilter(searchFilterDto)) {
			return flightRepository
					.findFlightsByAllRequirements(searchFilterDto.getLocationFrom(), searchFilterDto.getLocationTo(),
							searchFilterDto.getDate())
					.stream().map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
		} else if (SearchUtil.checkFitlerWitPriceWithoutRating(searchFilterDto)) {
			return flightRepository
					.findFlightsByLocationAndDateAndPriceWithoutRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithRatingWithoutPrice(searchFilterDto)) {
			return flightRepository
					.findFlightsByLocationAndDateAndRatingWithoutPrice(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithPriceAndRatingWithoutDate(searchFilterDto)) {
			return flightRepository
					.findFlightsByLocationAndPriceAndRatingWithoutDate(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithPriceWithoutDateAndRating(searchFilterDto)) {
			return flightRepository
					.findFlightsByLocationAndPriceWithoutDateAndRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithRatingWithoutDateAndPrice(searchFilterDto)) {
			return flightRepository
					.findTrainByLocationAndRatingWithoutDateAndPrice(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
		} else if (SearchUtil.checkoFilterOnlyWithPrice(searchFilterDto)) {
			return flightRepository.findFlightsByPrice().stream().map(fl -> FlightDto.of(fl))
					.collect(Collectors.toList());
		} else if (SearchUtil.checkFilterOnlyRating(searchFilterDto)) {
			return flightRepository.findFlightsByRating().stream().map(fl -> FlightDto.of(fl))
					.collect(Collectors.toList());
		} else if (SearchUtil.checkFilterOnlyLocation(searchFilterDto)) {
			return flightRepository
					.findFlightsByLocation(searchFilterDto.getLocationFrom(), searchFilterDto.getLocationTo()).stream()
					.map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterLocationAndDateWithoutPriceAndRating(searchFilterDto)) {
			return flightRepository
					.findFlightsByLocationAndDateWithoutPriceAndRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
		}

		return flightRepository.findAllFlights().stream().map(fl -> FlightDto.of(fl)).collect(Collectors.toList());
	}

}
