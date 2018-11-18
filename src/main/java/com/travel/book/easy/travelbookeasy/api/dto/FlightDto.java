package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.book.easy.travelbookeasy.db.model.Flight;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightDto {

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

	public FlightDto() {
		super();
	}

	public FlightDto(long id, CompanyDto company, String name, LocationDto locationFrom, LocationDto locationTo,
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
	
	public static FlightDto of(Flight flight) {
		return TravelBookEasyApp.ofNullable(flight, f -> FlightDto.builder()
				.id(f.getId())
				.company(CompanyDto.of(f.getCompany()))
				.name(f.getName())
				.locationFrom(LocationDto.of(f.getLocationFrom()))
				.locationTo(LocationDto.of(f.getLocationTo()))
				.departDate(f.getDepartDate())
				.arriveDate(f.getArriveDate())
				.price(f.getPrice())
				.travelClasses(TravelClassDto.of(f.getTracelClasses()))
				.build());
	}
}
