package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

	private Boolean isCurrentUserVoted;

	private List<UserRaitingDto> userRaitings;

	public CompanyDto() {
		super();
	}

	public CompanyDto(long id, String name, BigDecimal raiting, PictureDto companyLogo, Boolean isCurrentUserVoted, List<UserRaitingDto> userRaitings) {
		super();
		this.id = id;
		this.name = name;
		this.raiting = raiting;
		this.companyLogo = companyLogo;
		this.isCurrentUserVoted = isCurrentUserVoted;
		this.userRaitings = userRaitings;
	}

	public static CompanyDto of(Company company) {
		return TravelBookEasyApp.ofNullable(company, c -> CompanyDto.builder().id(c.getId()).name(c.getName())
				.raiting(c.getRaiting()).companyLogo(PictureDto.of(c.getCompanyLogo()))
				.userRaitings(company.getUserCompanyRaitings().stream().map(ur -> UserRaitingDto.of(ur)).collect(Collectors.toList()))
				.build());
	}

	public static CompanyDto of(Company company, Boolean isCurrentUserVoted) {
		return TravelBookEasyApp.ofNullable(company, c -> CompanyDto.builder().id(c.getId()).name(c.getName())
				.raiting(c.getRaiting()).companyLogo(PictureDto.of(c.getCompanyLogo()))
				.userRaitings(company.getUserCompanyRaitings().stream().map(ur -> UserRaitingDto.of(ur)).collect(Collectors.toList()))
				.isCurrentUserVoted(isCurrentUserVoted)
				.build());
	}
}
