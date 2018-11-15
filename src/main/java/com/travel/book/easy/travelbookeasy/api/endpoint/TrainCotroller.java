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

import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;
import com.travel.book.easy.travelbookeasy.api.dto.TrainDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.TrainService;

@RestController
@RequestMapping(value = "train", produces = "application/json")
public class TrainCotroller {

	@Autowired
	private TrainService trainService;

	@RequestMapping(method = RequestMethod.POST, value = "/create/createTrainRecord/{companyId}")
	@Transactional
	public ResponseEntity<TrainDto> createTrainRecord(@PathVariable(name = "companyId") long companyId,
			@RequestBody TrainDto trainDto, SecurityContextHolder context) {
		TrainDto dto = trainService.creatTrainRecord(trainDto, companyId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{trainId}")
	public ResponseEntity<TrainDto> getTrain(@PathVariable(name = "trainId") long trainId) {

		TrainDto dto = trainService.getTrain(trainId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ResponseEntity<List<TrainDto>> findAllTrains() {

		List<TrainDto> dto = trainService.findAllTrains();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/searchTrains")
	public List<TrainDto> getSearchTrains(@RequestBody SearchFilterDto searchFilterDto) {
		return trainService.searchTrains(searchFilterDto);
	}
}
