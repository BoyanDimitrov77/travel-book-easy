package db.model;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "picture")
public class Picture {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "original_picture_resource_id")
	private Resource originalImage;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "thumbnail_picture_resource_id")
	private Resource thumbnailPicture;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "full_screen_picture_resource_id")
	private Resource fullScreenPicutre;

	@PrePersist
	private void setUUID() {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
	}

}
