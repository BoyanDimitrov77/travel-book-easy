CREATE TABLE IF NOT EXISTS `hotel_picture`(
	`hotel_id` INT(11) NOT NULL,
	`picture_id` VARCHAR(36) NOT NULL,
	PRIMARY KEY (`hotel_id`,`picture_id`),
	KEY `FK_hotel_picture_idx` (`picture_id`),
	CONSTRAINT `FK_hotel_picture_hotel` FOREIGN KEY (`hotel_id`) REFERENCES `hotel`(`id`),
	CONSTRAINT `FK_hotel_picture_picture` FOREIGN KEY (`picture_id`) REFERENCES `picture`(`id`)
	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;