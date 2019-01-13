package com.travel.book.easy.travelbookeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.book.easy.travelbookeasy.db.model.FlightBook;
import com.travel.book.easy.travelbookeasy.db.model.User;

public interface FlightBookingRepository extends JpaRepository<FlightBook, Long> {

	List<FlightBook> findByBooker(User booker);

}
