CREATE TABLE IF NOT EXISTS `train_book`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`train_id` INT(11) NOT NULL,
	`booker` INT (11) NOT NULL,
	`payment_id` INT(11) NOT NULL,
	`status` VARCHAR(50) NOT NULL,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
	CONSTRAINT `FK_train_book_train` FOREIGN KEY(`train_id`) REFERENCES `train`(`id`),
	CONSTRAINT `FK_train_book_user` FOREIGN KEY(`booker`) REFERENCES `users`(`id`),
	CONSTRAINT `FK_train_book_payment` FOREIGN KEY(`payment_id`) REFERENCES `payment`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;