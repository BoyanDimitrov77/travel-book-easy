package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;

import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDto {

	private long id;

	private String name;

	private BigDecimal rating;

	private PictureDto companyLogo;

	public CompanyDto() {
		super();
	}

	public CompanyDto(long id, String name, BigDecimal rating, PictureDto companyLogo) {
		super();
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.companyLogo = companyLogo;
	}

	public static CompanyDto of(Company company) {
		return TravelBookEasyApp.ofNullable(company, c -> CompanyDto.builder().id(c.getId()).name(c.getName())
				.rating(c.getRating()).companyLogo(PictureDto.of(c.getCompanyLogo())).build());
	}
}
