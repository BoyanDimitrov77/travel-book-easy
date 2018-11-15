package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.io.IOException;

import com.travel.book.easy.travelbookeasy.api.dto.PictureDto;
import com.travel.book.easy.travelbookeasy.db.model.Picture;
import com.travel.book.easy.travelbookeasy.util.PictureInfo;

public interface PictureService {

	PictureDto savePicure(PictureInfo image , String path) throws IOException;

	Picture getPictureById(String pictureId);
}
