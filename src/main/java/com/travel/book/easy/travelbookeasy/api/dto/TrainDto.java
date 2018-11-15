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
	
	@JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm", timezone = "UTC")
	private Date departDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm", timezone = "UTC")
	private Date arriveDate;
	
	private BigDecimal price;
	
	private List<TravelClassDto> travelClassDtos;

	public TrainDto() {
		super();
	}

	public TrainDto(long id, CompanyDto company, String name, LocationDto locationFrom, LocationDto locationTo,
			Date departDate, Date arriveDate, BigDecimal price, List<TravelClassDto> travelClassDtos) {
		super();
		this.id = id;
		this.company = company;
		this.name = name;
		this.locationFrom = locationFrom;
		this.locationTo = locationTo;
		this.departDate = departDate;
		this.arriveDate = arriveDate;
		this.price = price;
		this.travelClassDtos = travelClassDtos;
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
				.travelClassDtos(TravelClassDto.of(t.getTravelClasses()))
				.build());
	}
}
