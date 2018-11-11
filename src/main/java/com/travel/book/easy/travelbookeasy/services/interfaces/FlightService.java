package com.travel.book.easy.travelbookeasy.services.interfaces;

import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;

public interface FlightService {

	FlightDto createFlighRecord(FlightDto flightDto, long companyId);
	
	FlightDto getFlight(long flightId);
}
