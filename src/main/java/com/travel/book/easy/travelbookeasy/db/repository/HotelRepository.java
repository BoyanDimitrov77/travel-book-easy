package com.travel.book.easy.travelbookeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.book.easy.travelbookeasy.db.model.Hotel;
import com.travel.book.easy.travelbookeasy.db.model.HotelRoom;
import com.travel.book.easy.travelbookeasy.db.model.Location;

public interface HotelRepository extends JpaRepository<Hotel,Long>{

	@Query("SELECT hr FROM Hotel h JOIN h.hotelRooms hr WHERE hr.id =:hotelRoomId")
	HotelRoom getHotelRoom(@Param("hotelRoomId") long hotelRoomId);

	List<Hotel> findByLocation(Location location);
}
