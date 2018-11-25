package com.travel.book.easy.travelbookeasy.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.HotelDto;
import com.travel.book.easy.travelbookeasy.api.dto.HotelRoomDto;
import com.travel.book.easy.travelbookeasy.api.dto.PictureDto;
import com.travel.book.easy.travelbookeasy.db.model.Hotel;
import com.travel.book.easy.travelbookeasy.db.model.HotelRoom;
import com.travel.book.easy.travelbookeasy.db.model.Location;
import com.travel.book.easy.travelbookeasy.db.model.Picture;
import com.travel.book.easy.travelbookeasy.db.repository.HotelRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.HotelService;
import com.travel.book.easy.travelbookeasy.services.interfaces.LocationService;
import com.travel.book.easy.travelbookeasy.services.interfaces.PictureService;
import com.travel.book.easy.travelbookeasy.util.PictureUtil;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private PictureService pictureService;
	
	@Override
	public HotelDto createHotel(HotelDto dto) {
		
		Hotel hotel = new Hotel();
		hotel.setHotelName(dto.getName());
		hotel.setDescription(dto.getDescription());
		hotel.setLocation(locationService.createLocation(dto.getLocation().getName()));
		hotel.setHotelRooms(createHotelRoom(dto.getHotelRooms(), hotel));
		
		Hotel savedHotel = hotelRepository.saveAndFlush(hotel);
		
		return HotelDto.of(savedHotel);
	}
	
	private List<HotelRoom> createHotelRoom(List<HotelRoomDto> list, Hotel hotel) {

		return list.stream().map(hotelRoomDto -> {
			HotelRoom hotelRoom = new HotelRoom();
			hotelRoom.setHotel(hotel);
			hotelRoom.setPrice(hotelRoomDto.getPrice());
			hotelRoom.setTypeRoom(hotelRoomDto.getTypeRoom());

			return hotelRoom;
		}).collect(Collectors.toList());
	}

	@Override
	public HotelDto getHotel(long hotelId) {
		
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		
		if(!hotel.isPresent()) {
			throw new ApiException("Hotel not found");
		}
		
		return HotelDto.of(hotel.get());
	}

	@Override
	public List<PictureDto> uploadHotelPicutures(long hotelId, MultipartFile[] files) throws IOException {

		Optional<Hotel> hotel = hotelRepository.findById(hotelId);

		if(!hotel.isPresent()) {
			throw new ApiException("Hotel not found");
		}

		Hotel savedHotel = setHotelPctures(files, hotel.get(), Hotel::setHotelPictures);

		return savedHotel.getHotelPictures().stream().map(picture -> PictureDto.of(picture))
				.collect(Collectors.toList());
	}

	private Hotel setHotelPctures(MultipartFile[] files, Hotel hotel, BiConsumer<Hotel, List<Picture>> setter)
			throws IOException {

		List<PictureDto> pictureDtos = new ArrayList<>();

		for (MultipartFile file : files) {
			PictureDto savePicure = pictureService.savePicure(PictureUtil.getImageFromMultipartFile(file),
					hotel.getHotelName());
			pictureDtos.add(savePicure);
		}

		List<Picture> hotelPictures = pictureDtos.stream()
				.map(pictureDto -> pictureService.getPictureById(pictureDto.getId())).collect(Collectors.toList());
		setter.accept(hotel, hotelPictures);

		Hotel saveHotel = hotelRepository.saveAndFlush(hotel);

		return saveHotel;
	}

	@Override
	public List<HotelDto> findHotelsByCurrentDestionation(long locationId) {
		Location location = locationService.findById(locationId);

		List<Hotel> hotels = hotelRepository.findByLocation(location);

		return hotels.stream().map(hotel -> HotelDto.of(hotel)).collect(Collectors.toList());
	}

	@Override
	public List<HotelDto> findAllHotels() {

		List<Hotel> hotels = hotelRepository.findAll();

		return hotels.stream().map(h -> HotelDto.of(h)).collect(Collectors.toList());
	}

}
