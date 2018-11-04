CREATE TABLE IF NOT EXISTS `hotel_room`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`hotel_id` INT(11) NOT NULL,
	`type_room` VARCHAR(50) NOT NULL,
	`price` DECIMAL(10,2) DEFAULT 0,
	PRIMARY KEY(`id`),
	CONSTRAINT `FK_hotel_room_hotel` FOREIGN KEY(`hotel_id`) REFERENCES `hotel`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;