package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;

import com.travel.book.easy.travelbookeasy.db.model.Payment;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDto {

	
	private long id;
	
	private UserDto user;
	
	private BigDecimal amount;
	
	private String status;

	public PaymentDto() {
		super();
	}
	
	public PaymentDto(long id, UserDto user, BigDecimal amount, String status) {
		super();
		this.id = id;
		this.user = user;
		this.amount = amount;
		this.status = status;
	}

	public static PaymentDto of(Payment payment) {
		return TravelBookEasyApp.ofNullable(payment, p->PaymentDto.builder()
				.id(p.getId())
				.user(UserDto.of(p.getUser()))
				.amount(p.getAmount())
				.status(p.getStatus())
				.build());
	}
}
