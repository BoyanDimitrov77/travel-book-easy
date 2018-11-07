package com.travel.book.easy.travelbookeasy.api.dto;

import com.travel.book.easy.travelbookeasy.db.model.Picture;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PictureDto {

	private String id;

	private ResourceDto orignalPicture;

	private ResourceDto thumbnailPicture;

	private ResourceDto fullScreenPicture;

	public PictureDto() {
		super();
	}

	public PictureDto(String id, ResourceDto orignalPicture, ResourceDto thumbnailPicture,
			ResourceDto fullScreenPicture) {
		super();
		this.id = id;
		this.orignalPicture = orignalPicture;
		this.thumbnailPicture = thumbnailPicture;
		this.fullScreenPicture = fullScreenPicture;
	}

	public static final PictureDto of(Picture picture) {
		return TravelBookEasyApp.ofNullable(picture,
				pic -> PictureDto.builder().id(pic.getId()).orignalPicture(ResourceDto.of(pic.getOriginalImage()))
						.thumbnailPicture(ResourceDto.of(pic.getThumbnailPicture()))
						.fullScreenPicture(ResourceDto.of(pic.getFullScreenPicutre())).build());

	}
	
}
