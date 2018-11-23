package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;
import com.travel.book.easy.travelbookeasy.api.dto.TrainDto;
import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.db.model.Train;
import com.travel.book.easy.travelbookeasy.db.repository.CompanyRepository;
import com.travel.book.easy.travelbookeasy.db.repository.TrainRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.LocationService;
import com.travel.book.easy.travelbookeasy.services.interfaces.TrainService;
import com.travel.book.easy.travelbookeasy.services.interfaces.TravelClassService;
import com.travel.book.easy.travelbookeasy.util.SearchUtil;

@Service
public class TrainServiceImpl  implements TrainService{
	
	@Autowired
	private TrainRepository trainRepository;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private TravelClassService travelClassService;

	@Override
	public TrainDto creatTrainRecord(TrainDto dto, long companyId) {
		
		Optional<Company> company = companyRepository.findById(companyId);
		
		if(!company.isPresent()) {
			throw new ApiException("Company not found");
		}
		
		Train train = new Train();
		train.setCompany(company.get());
		train.setName(dto.getName());
		train.setLocationFrom(locationService.createLocation(dto.getLocationFrom().getName()));
		train.setLocationTo(locationService.createLocation(dto.getLocationTo().getName()));
		train.setDepartDate(dto.getDepartDate());
		train.setArriveDate(dto.getArriveDate());
		train.setPrice(dto.getPrice());
		train.setTravelClasses(travelClassService.createTravelClasses(dto.getTravelClasses()));
		
		Train savedTrain = trainRepository.saveAndFlush(train);
		
		return TrainDto.of(savedTrain);
	}

	@Override
	public TrainDto getTrain(long trainId) {
		
		Optional<Train> train = trainRepository.findById(trainId);
		
		if(!train.isPresent()) {
			throw new ApiException("Train not found");
		}
		
		return TrainDto.of(train.get());
	}

	@Override
	public List<TrainDto> findAllTrains() {

		return trainRepository.findAllTrains().stream().map(t -> TrainDto.of(t)).collect(Collectors.toList());
	}

	@Override
	public List<TrainDto> searchTrains(SearchFilterDto searchFilterDto) {

		if (SearchUtil.checkAllFilter(searchFilterDto)) {
			return trainRepository.findTrainsByAllRequirements(searchFilterDto.getLocationFrom(),
					searchFilterDto.getLocationTo(), searchFilterDto.getDate()).stream().map(tr -> TrainDto.of(tr))
					.collect(Collectors.toList());
		} else if (SearchUtil.checkFitlerWitPriceWithoutRating(searchFilterDto)) {
			return trainRepository
					.findTrainsByLocationAndDateAndPriceWithoutRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(tr -> TrainDto.of(tr)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithRatingWithoutPrice(searchFilterDto)) {
			return trainRepository
					.findTrainsByLocationAndDateAndRatingWithoutPrice(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(tr -> TrainDto.of(tr)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithPriceAndRatingWithoutDate(searchFilterDto)) {
			return trainRepository
					.findTrainsByLocationAndPriceAndRatingWithoutDate(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(tr -> TrainDto.of(tr)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithPriceWithoutDateAndRating(searchFilterDto)) {
			return trainRepository
					.findTrainsByLocationAndPriceWithoutDateAndRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(tr -> TrainDto.of(tr)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithRatingWithoutDateAndPrice(searchFilterDto)) {
			return trainRepository
					.findTrainByLocationAndRatingWithoutDateAndPrice(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(tr -> TrainDto.of(tr)).collect(Collectors.toList());
		} else if (SearchUtil.checkoFilterOnlyWithPrice(searchFilterDto)) {
			return trainRepository.findTrainsByPrice().stream().map(tr -> TrainDto.of(tr)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterOnlyRating(searchFilterDto)) {
			return trainRepository.findTrainsByRating().stream().map(tr -> TrainDto.of(tr))
					.collect(Collectors.toList());
		} else if (SearchUtil.checkFilterOnlyLocation(searchFilterDto)) {
			return trainRepository
					.findTrainsByLocation(searchFilterDto.getLocationFrom(), searchFilterDto.getLocationTo()).stream()
					.map(tr -> TrainDto.of(tr)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterLocationAndDateWithoutPriceAndRating(searchFilterDto)) {
			return trainRepository
					.findTrainsByLocationAndDateWithoutPriceAndRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(tr -> TrainDto.of(tr)).collect(Collectors.toList());
		}

		return trainRepository.findAllTrains().stream().map(tr -> TrainDto.of(tr)).collect(Collectors.toList());
	}
	
}
