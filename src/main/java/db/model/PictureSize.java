package db.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PictureSize {

	private int height;

	private int width;

	public PictureSize(int height, int width) {
		this.height = height;
		this.width = width;
	}
}
