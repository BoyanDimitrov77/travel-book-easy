package com.travel.book.easy.travelbookeasy.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.book.easy.travelbookeasy.db.model.Bus;

public interface BusRepository extends JpaRepository<Bus, Long>{

	@Query("SELECT b FROM Bus b WHERE b.departDate>=NOW() ORDER BY b.departDate ")
	List<Bus> findAllBuses();

	@Query("SELECT b FROM Bus b where b.locationFrom.name =:locationFrom AND b.locationTo.name =:locationTo AND Date(b.departDate) = Date(:toDate) ORDER BY b.price,b.company.raiting")
	List<Bus> findBusesByAllRequirements(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT b FROM Bus b where b.locationFrom.name =:locationFrom AND b.locationTo.name =:locationTo AND Date(b.departDate) = Date(:toDate) ORDER BY b.price")
	List<Bus> findBusesByLocationAndDateAndPriceWithoutRating(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT b FROM Bus b where b.locationFrom.name =:locationFrom AND b.locationTo.name =:locationTo AND Date(b.departDate) = Date(:toDate) ORDER BY b.company.raiting")
	List<Bus> findBusesByLocationAndDateAndRatingWithoutPrice(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT b FROM Bus b where b.locationFrom.name =:locationFrom AND b.locationTo.name =:locationTo ORDER BY b.price,b.company.raiting, b.departDate")
	List<Bus> findBusesByLocationAndPriceAndRatingWithoutDate(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT b FROM Bus b where b.locationFrom.name =:locationFrom AND b.locationTo.name =:locationTo ORDER BY b.price,b.departDate")
	List<Bus> findBusesByLocationAndPriceWithoutDateAndRaiting(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT b FROM Bus b where b.locationFrom.name =:locationFrom AND b.locationTo.name =:locationTo ORDER BY b.company.raiting, b.departDate")
	List<Bus> findTrainByLocationAndRaitingWithoutDateAndPrice(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT b FROM Bus b where b.departDate>=NOW() ORDER BY b.price ASC")
	List<Bus> findBusesByPrice();

	@Query("SELECT b FROM Bus b where b.departDate>=NOW() ORDER BY b.company.raiting DESC")
	List<Bus> findBusesByRaiting();

	@Query("SELECT b FROM Bus b where b.locationFrom.name = :locationFrom AND b.locationTo.name = :locationTo ORDER BY b.departDate")
	List<Bus> findBusesByLocation(@Param("locationFrom") String locationFrom, @Param("locationTo") String locationTo);

	@Query("SELECT b FROM Bus b where b.locationFrom.name = :locationFrom AND b.locationTo.name = :locationTo AND Date(b.departDate) = Date(:toDate)")
	List<Bus> findBusesByLocationAndDateWithoutPriceAndRaiting(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);
}
