package com.travel.book.easy.travelbookeasy.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.book.easy.travelbookeasy.db.model.Flight;

public interface FlightRepository extends JpaRepository<Flight,Long>{
	
	@Query("SELECT fl FROM Flight fl WHERE fl.departDate>=NOW() ORDER BY fl.departDate")
	List<Flight> findAllFlights();

	@Query("SELECT fl FROM Flight fl where fl.locationFrom.name =:locationFrom AND fl.locationTo.name =:locationTo AND Date(fl.departDate) = Date(:toDate) ORDER BY fl.price,fl.company.rating")
	List<Flight> findFlightsByAllRequirements(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT fl FROM Flight fl where fl.locationFrom.name =:locationFrom AND fl.locationTo.name =:locationTo AND Date(fl.departDate) = Date(:toDate) ORDER BY fl.price")
	List<Flight> findFlightsByLocationAndDateAndPriceWithoutRating(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT fl FROM Flight fl where fl.locationFrom.name =:locationFrom AND fl.locationTo.name =:locationTo AND Date(fl.departDate) = Date(:toDate) ORDER BY fl.company.rating")
	List<Flight> findFlightsByLocationAndDateAndRatingWithoutPrice(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT fl FROM Flight fl where fl.locationFrom.name =:locationFrom AND fl.locationTo.name =:locationTo ORDER BY fl.price,fl.company.rating, fl.departDate")
	List<Flight> findFlightsByLocationAndPriceAndRatingWithoutDate(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT fl FROM Flight fl where fl.locationFrom.name =:locationFrom AND fl.locationTo.name =:locationTo ORDER BY fl.price,fl.departDate")
	List<Flight> findFlightsByLocationAndPriceWithoutDateAndRating(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT fl FROM Flight fl where fl.locationFrom.name =:locationFrom AND fl.locationTo.name =:locationTo ORDER BY fl.company.rating, fl.departDate")
	List<Flight> findTrainByLocationAndRatingWithoutDateAndPrice(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT fl FROM Flight fl where fl.departDate>=NOW() ORDER BY fl.price ASC")
	List<Flight> findFlightsByPrice();

	@Query("SELECT fl FROM Flight fl where fl.departDate>=NOW() ORDER BY fl.company.rating DESC")
	List<Flight> findFlightsByRating();

	@Query("SELECT fl FROM Flight fl where fl.locationFrom.name = :locationFrom AND fl.locationTo.name = :locationTo ORDER BY fl.departDate")
	List<Flight> findFlightsByLocation(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT fl FROM Flight fl where fl.locationFrom.name = :locationFrom AND fl.locationTo.name = :locationTo AND Date(fl.departDate) = Date(:toDate)")
	List<Flight> findFlightsByLocationAndDateWithoutPriceAndRating(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

}
