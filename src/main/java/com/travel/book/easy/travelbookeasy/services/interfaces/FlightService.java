package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.util.List;

import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;

public interface FlightService {

	FlightDto createFlighRecord(FlightDto flightDto, long companyId);
	
	FlightDto getFlight(long flightId);

	List<FlightDto> findAllFlights();

	List<FlightDto> searchFlights(SearchFilterDto searchFilterDto);
}
