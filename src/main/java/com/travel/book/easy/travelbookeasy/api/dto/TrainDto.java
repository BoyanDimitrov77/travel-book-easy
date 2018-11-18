package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.book.easy.travelbookeasy.db.model.Train;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainDto {

private long id;
	
	private CompanyDto company;
	
	private String name;
	
	private LocationDto locationFrom;
	
	private LocationDto locationTo;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
	private Date departDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
	private Date arriveDate;
	
	private BigDecimal price;
	
	private List<TravelClassDto> travelClasses;

	public TrainDto() {
		super();
	}

	public TrainDto(long id, CompanyDto company, String name, LocationDto locationFrom, LocationDto locationTo,
			Date departDate, Date arriveDate, BigDecimal price, List<TravelClassDto> travelClasses) {
		super();
		this.id = id;
		this.company = company;
		this.name = name;
		this.locationFrom = locationFrom;
		this.locationTo = locationTo;
		this.departDate = departDate;
		this.arriveDate = arriveDate;
		this.price = price;
		this.travelClasses = travelClasses;
	}
	
	public static TrainDto of(Train train) {
		return TravelBookEasyApp.ofNullable(train, t -> TrainDto.builder()
				.id(t.getId())
				.company(CompanyDto.of(t.getCompany()))
				.name(t.getName())
				.locationFrom(LocationDto.of(t.getLocationFrom()))
				.locationTo(LocationDto.of(t.getLocationTo()))
				.departDate(t.getDepartDate())
				.arriveDate(t.getArriveDate())
				.price(t.getPrice())
				.travelClasses(TravelClassDto.of(t.getTravelClasses()))
				.build());
	}
}
