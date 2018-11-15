CREATE TABLE IF NOT EXISTS `bus_book`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`bus_id` INT(11) NOT NULL,
	`booker` INT (11) NOT NULL,
	`payment_id` INT(11) NOT NULL,
	`status` VARCHAR(50) NOT NULL,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
	CONSTRAINT `FK_bus_book_bus` FOREIGN KEY(`bus_id`) REFERENCES `bus`(`id`),
	CONSTRAINT `FK_bus_book_user` FOREIGN KEY(`booker`) REFERENCES `users`(`id`),
	CONSTRAINT `FK_bus_book_payment` FOREIGN KEY(`payment_id`) REFERENCES `payment`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;