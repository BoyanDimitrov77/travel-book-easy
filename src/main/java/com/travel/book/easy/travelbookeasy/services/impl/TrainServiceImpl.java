package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.TrainDto;
import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.db.model.Train;
import com.travel.book.easy.travelbookeasy.db.repository.CompanyRepository;
import com.travel.book.easy.travelbookeasy.db.repository.TrainRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.LocationService;
import com.travel.book.easy.travelbookeasy.services.interfaces.TrainService;
import com.travel.book.easy.travelbookeasy.services.interfaces.TravelClassService;

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
		train.setTravelClasses(travelClassService.createTravelClasses(dto.getTravelClassDtos()));
		
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

	
	
}
