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

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo AND Date(tr.departDate) = Date(:toDate) ORDER BY tr.price,tr.company.rating")
	List<Train> findTrainsByAllRequirements(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo AND Date(tr.departDate) = Date(:toDate) ORDER BY tr.price")
	List<Train> findTrainsByLocationAndDateAndPriceWithoutRating(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo AND Date(tr.departDate) = Date(:toDate) ORDER BY tr.company.rating")
	List<Train> findTrainsByLocationAndDateAndRatingWithoutPrice(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo ORDER BY tr.price,tr.company.rating, tr.departDate")
	List<Train> findTrainsByLocationAndPriceAndRatingWithoutDate(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo ORDER BY tr.price,tr.departDate")
	List<Train> findTrainsByLocationAndPriceWithoutDateAndRating(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name =:locationFrom AND tr.locationTo.name =:locationTo ORDER BY tr.company.rating, tr.departDate")
	List<Train> findTrainByLocationAndRatingWithoutDateAndPrice(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT tr FROM Train tr where tr.departDate>=NOW() ORDER BY tr.price ASC")
	List<Train> findTrainsByPrice();

	@Query("SELECT tr FROM Train tr where tr.departDate>=NOW() ORDER BY tr.company.rating DESC")
	List<Train> findTrainsByRating();

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name = :locationFrom AND tr.locationTo.name = :locationTo ORDER BY tr.departDate")
	List<Train> findTrainsByLocation(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo);

	@Query("SELECT tr FROM Train tr where tr.locationFrom.name = :locationFrom AND tr.locationTo.name = :locationTo AND Date(tr.departDate) = Date(:toDate)")
	List<Train> findTrainsByLocationAndDateWithoutPriceAndRating(@Param("locationFrom") String locationFrom,
			@Param("locationTo") String locationTo, @Param("toDate") Date toDate);
}
