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

	private BigDecimal raiting;

	private PictureDto companyLogo;

	public CompanyDto() {
		super();
	}

	public CompanyDto(long id, String name, BigDecimal raiting, PictureDto companyLogo) {
		super();
		this.id = id;
		this.name = name;
		this.raiting = raiting;
		this.companyLogo = companyLogo;
	}

	public static CompanyDto of(Company company) {
		return TravelBookEasyApp.ofNullable(company, c -> CompanyDto.builder().id(c.getId()).name(c.getName())
				.raiting(c.getRaiting()).companyLogo(PictureDto.of(c.getCompanyLogo())).build());
	}
}
