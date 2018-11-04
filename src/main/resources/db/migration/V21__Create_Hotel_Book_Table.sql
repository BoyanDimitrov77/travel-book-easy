CREATE TABLE IF NOT EXISTS `hotel_book`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`hotel_room_id` INT(11) NOT NULL,
	`user_id` INT(11) NOT NULL,
	`payment_id` INT(11) NOT NULL,
	`status` VARCHAR(50) NOT NULL,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
	CONSTRAINT `FK_hotel_book_hotel_room` FOREIGN KEY(`hotel_room_id`) REFERENCES `hotel_room`(`id`),
	CONSTRAINT `FK_hotel_book_users` FOREIGN KEY(`user_id`) REFERENCES `users`(`id`),
	CONSTRAINT `FK_hotel_book_payment` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;;