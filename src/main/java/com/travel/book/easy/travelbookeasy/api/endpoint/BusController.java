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

import com.travel.book.easy.travelbookeasy.api.dto.BusDto;
import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.BusService;

@RestController
@RequestMapping(value = "bus", produces = "application/json")
public class BusController {

	@Autowired
	private BusService busService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/create/createBusRecord/{companyId}")
	@Transactional
	public ResponseEntity<BusDto> createBusRecord(@PathVariable(name = "companyId") long companyId,
			@RequestBody BusDto busDto, SecurityContextHolder context) {
		BusDto dto = busService.createBusRecord(busDto, companyId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{busId}")
	public ResponseEntity<BusDto> getBus(@PathVariable(name = "busId") long busId) {

		BusDto dto = busService.getBus(busId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ResponseEntity<List<BusDto>> findAllTrains() {

		List<BusDto> dto = busService.findAllBuses();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/searchBuses")
	public List<BusDto> getSearchBuses(@RequestBody SearchFilterDto searchFilterDto) {
		return busService.searchBuses(searchFilterDto);
	}
	
}
