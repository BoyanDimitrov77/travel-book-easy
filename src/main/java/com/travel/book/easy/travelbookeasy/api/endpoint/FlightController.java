package com.travel.book.easy.travelbookeasy.api.endpoint;

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
		FlightDto dto = flightService.createFligh(flightDto, companyId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
