package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.util.List;

import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;
import com.travel.book.easy.travelbookeasy.api.dto.TrainDto;

public interface TrainService {

	TrainDto creatTrainRecord(TrainDto dto,  long companyId);
	
	TrainDto getTrain(long trainId);

	List<TrainDto> findAllTrains();

	List<TrainDto> searchTrains(SearchFilterDto searchFilterDto);
}
