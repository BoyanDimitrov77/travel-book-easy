package com.travel.book.easy.travelbookeasy.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

public class PictureUtil {

	private static final Detector DETECTOR = new DefaultDetector(MimeTypes.getDefaultMimeTypes());

	public static PictureInfo getImageFromMultipartFile(MultipartFile file) throws IOException {

		String type = null;
		try (TikaInputStream tikaIS = TikaInputStream.get(file.getInputStream())) {
			final Metadata metadata = new Metadata();
			type = DETECTOR.detect(tikaIS, metadata).toString().replaceAll("image/", "");
		}

		BufferedImage image = ImageIO.read(file.getInputStream());
		return PictureInfo.builder().image(image).type(type).build();
	}
	
	public static InputStream convertBufferedImageToInputStream(BufferedImage image,String imageFormat,String imageName) throws IOException{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, imageFormat, os);
		InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
		
		return inputStream;
	}
}
