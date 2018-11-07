package com.travel.book.easy.travelbookeasy.api.dto;

import com.travel.book.easy.travelbookeasy.db.model.Resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceDto {

	
	private String id;

	private String value;

	public ResourceDto() {
		super();
	}

	public ResourceDto(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public static final ResourceDto of(Resource resource) {
		return ResourceDto.builder().id(resource.getId()).value(resource.getValue())
				.build();
	}
}
