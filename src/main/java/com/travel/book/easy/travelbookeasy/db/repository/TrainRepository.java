package com.travel.book.easy.travelbookeasy.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.book.easy.travelbookeasy.db.model.Train;

public interface TrainRepository extends JpaRepository<Train,Long>{

	@Query("SELECT tr FROM Train tr WHERE tr.departDate>=NOW() ORDER BY tr.departDate ")
	List<Train> findAllTrains();

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo AND Date(tr.departDate) = Date(:toDate) ORDER BY tr.price,tr.company.raiting")
	List<Train> findTrainsByAllRequirements(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo AND Date(tr.departDate) = Date(:toDate) ORDER BY tr.price")
	List<Train> findTrainsByLocationAndDateAndPriceWithoutRating(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo AND Date(tr.departDate) = Date(:toDate) ORDER BY tr.company.raiting")
	List<Train> findTrainsByLocationAndDateAndRatingWithoutPrice(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo ORDER BY tr.price,tr.company.raiting, tr.departDate")
	List<Train> findTrainsByLocationAndPriceAndRatingWithoutDate(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo ORDER BY tr.price,tr.departDate")
	List<Train> findTrainsByLocationAndPriceWithoutDateAndRaiting(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo ORDER BY tr.company.raiting, tr.departDate")
	List<Train> findTrainByLocationAndRaitingWithoutDateAndPrice(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT tr FROM Train tr where tr.departDate>=NOW() ORDER BY tr.price ASC")
	List<Train> findTrainsByPrice();

	@Query("SELECT tr FROM Train tr where tr.departDate>=NOW() ORDER BY tr.company.raiting DESC")
	List<Train> findTrainsByRaiting();

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name = :locationFrom AND tr.locationTo.name = :locationTo ORDER BY tr.departDate")
	List<Train> findTrainsByLocation(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name = :locationFrom AND tr.locationTo.name = :locationTo AND Date(tr.departDate) = Date(:toDate)")
	List<Train> findTrainsByLocationAndDateWithoutPriceAndRaiting(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);
}
