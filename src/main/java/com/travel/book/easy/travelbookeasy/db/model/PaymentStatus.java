package com.travel.book.easy.travelbookeasy.db.model;

public enum PaymentStatus {

	PENDING("PENDING"), CONFIRMED("CONFIRMED");
	
	private final String status;
	
	PaymentStatus(String status){
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status;
	}
}
