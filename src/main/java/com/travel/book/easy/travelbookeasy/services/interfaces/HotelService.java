package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.travel.book.easy.travelbookeasy.api.dto.HotelDto;
import com.travel.book.easy.travelbookeasy.api.dto.PictureDto;

public interface HotelService {

	HotelDto createHotel(HotelDto dto);
	
	HotelDto getHotel(long hotelId);
	
	List<PictureDto> uploadHotelPicutures(long hotelId, MultipartFile[] files) throws IOException ;
	
}
