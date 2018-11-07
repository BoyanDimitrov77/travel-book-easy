package com.travel.book.easy.travelbookeasy.services.interfaces;

import com.travel.book.easy.travelbookeasy.api.dto.TrainDto;

public interface TrainService {

	TrainDto creatTrainRecord(TrainDto dto,  long companyId);
	
	TrainDto getTrain(long trainId);
}
