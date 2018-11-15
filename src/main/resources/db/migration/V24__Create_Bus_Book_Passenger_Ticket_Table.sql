CREATE TABLE IF NOT EXISTS `bus_book_passenger_ticket`(
	`bus_book_id` INT(11) NOT NULL,
	`passenger_ticket_id` INT(11) NOT NULL,
	 PRIMARY KEY (`bus_book_id`,`passenger_ticket_id`),
     KEY `FK_bus_book_passenger_ticket_idx`(`passenger_ticket_id`),
     CONSTRAINT `FK_bus_book_passenger_ticket_bus` FOREIGN KEY (`bus_book_id`) REFERENCES `bus_book`(`id`),
     CONSTRAINT `FK_bus_book_passenger_ticket_passenger_ticket` FOREIGN KEY (`passenger_ticket_id`) REFERENCES `passenger_ticket`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;