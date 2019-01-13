package com.travel.book.easy.travelbookeasy.api.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.FlightService;

@RestController
@RequestMapping(value = "flight", produces = "application/json")
public class FlightController {

	@Autowired
	private FlightService flightService;

	@RequestMapping(method = RequestMethod.POST, value = "/create/createFlightRecord/{companyId}")
	@Transactional
	public ResponseEntity<FlightDto> createFlight(@PathVariable(name = "companyId") long companyId,
			@RequestBody FlightDto flightDto, SecurityContextHolder context) {
		FlightDto dto = flightService.createFlighRecord(flightDto, companyId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{flightId}")
	public ResponseEntity<FlightDto> getFlight(@PathVariable(name = "flightId") long flightId) {

		FlightDto dto = flightService.getFlight(flightId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ResponseEntity<List<FlightDto>> findAllFlights() {

		List<FlightDto> dto = flightService.findAllFlights();

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/searchFlights")
	public ResponseEntity<List<FlightDto>> getSearchFlight(@RequestBody SearchFilterDto searchFilterDto) {
		
		List<FlightDto> searchFlights = flightService.searchFlights(searchFilterDto);
		return new ResponseEntity<>(searchFlights, HttpStatus.OK);
				
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/update")
	@Transactional
	public ResponseEntity<FlightDto> updateFlight(@RequestBody FlightDto flightDto) {
		FlightDto updateFlight = flightService.updateFlight(flightDto);
		return new ResponseEntity<>(updateFlight, HttpStatus.OK);
	}

}
