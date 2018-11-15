package com.travel.book.easy.travelbookeasy.api.endpoint;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travel.book.easy.travelbookeasy.api.dto.HotelDto;
import com.travel.book.easy.travelbookeasy.api.dto.PictureDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.HotelService;

@RestController
@RequestMapping(value= "hotel", produces = "application/json")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/create/hotel")
	@Transactional
	public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto, SecurityContextHolder context) {
		HotelDto dto = hotelService.createHotel(hotelDto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{hotelId}")
	public ResponseEntity<HotelDto> getHotel(@PathVariable("hotelId") long id, SecurityContextHolder context){
		
		HotelDto hotel = hotelService.getHotel(id);
		
		return new ResponseEntity<HotelDto>(hotel, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadPictures/{hotelId}")
	public ResponseEntity<List<PictureDto>> uploadProfilePhoto(@PathVariable("hotelId") long hotelId,
			@RequestParam("files") MultipartFile[] files) {

		List<PictureDto> dtos = new ArrayList<>();

		try {
			dtos = hotelService.uploadHotelPicutures(hotelId, files);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

}
