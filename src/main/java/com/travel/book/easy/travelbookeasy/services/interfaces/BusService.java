package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.util.List;

import com.travel.book.easy.travelbookeasy.api.dto.BusDto;
import com.travel.book.easy.travelbookeasy.api.dto.SearchFilterDto;

public interface BusService {

	BusDto createBusRecord(BusDto busDto, long companyId);
	
	BusDto getBus(long busId);

	List<BusDto> findAllBuses();

	List<BusDto> searchBuses(SearchFilterDto searchFilterDto);

	BusDto updateBus(BusDto busDto);
}
