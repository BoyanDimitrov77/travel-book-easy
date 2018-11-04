CREATE TABLE IF NOT EXISTS `train_book_passenger_ticket`(
	`train_book_id` INT(11) NOT NULL,
	`passenger_ticket_id` INT(11) NOT NULL,
	 PRIMARY KEY (`train_book_id`,`passenger_ticket_id`),
     KEY `FK_train_book_passenger_ticket_idx`(`passenger_ticket_id`),
     CONSTRAINT `FK_train_book_passenger_ticket_train` FOREIGN KEY (`train_book_id`) REFERENCES `train_book`(`id`),
     CONSTRAINT `FK_train_book_passenger_ticket_passenger_ticket` FOREIGN KEY (`passenger_ticket_id`) REFERENCES `passenger_ticket`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;