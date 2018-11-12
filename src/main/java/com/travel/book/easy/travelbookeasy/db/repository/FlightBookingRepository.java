package com.travel.book.easy.travelbookeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.book.easy.travelbookeasy.db.model.FlightBook;

public interface FlightBookingRepository extends JpaRepository<FlightBook, Long>{

}
