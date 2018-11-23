package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
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

	private BigDecimal rating;

	private PictureDto companyLogo;

	private Boolean isCurrentUserVoted;

	private List<UserCompanyCommentDto> comments;

	public CompanyDto() {
		super();
	}

	public CompanyDto(long id, String name, BigDecimal rating, PictureDto companyLogo, Boolean isCurrentUserVoted, List<UserCompanyCommentDto> comments) {
		super();
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.companyLogo = companyLogo;
		this.isCurrentUserVoted = isCurrentUserVoted;
		this.comments = comments;
	}

	public static CompanyDto of(Company company) {
		return TravelBookEasyApp.ofNullable(company, c -> CompanyDto.builder().id(c.getId()).name(c.getName())
				.rating(c.getRating()).companyLogo(PictureDto.of(c.getCompanyLogo()))
				.comments(company.getUserCompanyComments() != null ? company.getUserCompanyComments().stream().map(ucc-> UserCompanyCommentDto.of(ucc)).collect(Collectors.toList()): new ArrayList<>())
				.build());
	}

	public static CompanyDto of(Company company, Boolean isCurrentUserVoted) {
		return TravelBookEasyApp.ofNullable(company, c -> CompanyDto.builder().id(c.getId()).name(c.getName())
				.rating(c.getRating()).companyLogo(PictureDto.of(c.getCompanyLogo()))
				.isCurrentUserVoted(isCurrentUserVoted)
				.comments(company.getUserCompanyComments() != null ? company.getUserCompanyComments().stream().map(ucc-> UserCompanyCommentDto.of(ucc)).collect(Collectors.toList()): new ArrayList<>())
				.build());
	}
}
