CREATE TABLE IF NOT EXISTS `flight_book_passenger_ticket`(
	`flight_book_id` INT(11) NOT NULL,
	`passenger_ticket_id` INT(11) NOT NULL,
	 PRIMARY KEY (`flight_book_id`,`passenger_ticket_id`),
     KEY `FK_flght_book_passenger_ticket_idx`(`passenger_ticket_id`),
     CONSTRAINT `FK_flight_book_passenger_ticket_flight` FOREIGN KEY (`flight_book_id`) REFERENCES `flight_book`(`id`),
     CONSTRAINT `FK_flight_book_passenger_ticket_passenger_ticket` FOREIGN KEY (`passenger_ticket_id`) REFERENCES `passenger_ticket`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;