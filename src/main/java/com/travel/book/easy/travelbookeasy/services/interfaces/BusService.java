package com.travel.book.easy.travelbookeasy.services.interfaces;

import com.travel.book.easy.travelbookeasy.api.dto.BusDto;

public interface BusService {

	BusDto createBusRecord(BusDto busDto, long companyId);
	
	BusDto getBus(long busId);
}
