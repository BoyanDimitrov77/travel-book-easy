package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.book.easy.travelbookeasy.db.model.Bus;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusDto {

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

	private int maxSeats;

	public BusDto() {
		super();
	}

	public BusDto(long id, CompanyDto company, String name, LocationDto locationFrom, LocationDto locationTo,
			Date departDate, Date arriveDate, BigDecimal price, int maxSeats) {
		super();
		this.id = id;
		this.company = company;
		this.name = name;
		this.locationFrom = locationFrom;
		this.locationTo = locationTo;
		this.departDate = departDate;
		this.arriveDate = arriveDate;
		this.price = price;
		this.maxSeats = maxSeats;
	}
	
	public static BusDto of(Bus bus) {
		return TravelBookEasyApp.ofNullable(bus, b -> BusDto.builder()
				.id(b.getId())
				.company(CompanyDto.of(b.getCompany()))
				.name(b.getName())
				.locationFrom(LocationDto.of(b.getLocationFrom()))
				.locationTo(LocationDto.of(b.getLocationTo()))
				.departDate(b.getDepartDate())
				.arriveDate(b.getArriveDate())
				.price(b.getPrice())
				.maxSeats(b.getMaxSeats())
				.build());
	}
	
}
