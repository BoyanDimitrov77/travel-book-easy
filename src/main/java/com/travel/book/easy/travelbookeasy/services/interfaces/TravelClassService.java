package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.util.List;

import com.travel.book.easy.travelbookeasy.api.dto.TravelClassDto;
import com.travel.book.easy.travelbookeasy.db.model.TravelClass;

public interface TravelClassService {

	List<TravelClass> createTravelClasses(List<TravelClassDto> travelClassDtos);
}
