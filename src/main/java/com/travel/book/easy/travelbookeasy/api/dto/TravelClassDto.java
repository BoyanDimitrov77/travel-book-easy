package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.travel.book.easy.travelbookeasy.db.model.TravelClass;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelClassDto {

	private long id;
	
	private int maxSeats;
	
	private String travelClass;
	
	private BigDecimal price;

	public TravelClassDto() {
		super();
	}
	
	public TravelClassDto(long id, int maxSeats, String travelClass, BigDecimal price) {
		super();
		this.id = id;
		this.maxSeats = maxSeats;
		this.travelClass = travelClass;
		this.price = price;
	}

	public static List<TravelClassDto> of(List<TravelClass> travelClasses){
		
		return travelClasses.stream().map(travelClass -> TravelBookEasyApp.ofNullable(travelClass, tc ->TravelClassDto.builder()
				.id(tc.getId())
				.maxSeats(tc.getMaxSeats())
				.travelClass(tc.getTravelClass())
				.price(tc.getPrice())
				.build())).collect(Collectors.toList());
		
	}
	
}
