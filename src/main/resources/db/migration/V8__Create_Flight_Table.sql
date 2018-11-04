CREATE TABLE IF NOT EXISTS `flight`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`company_id` INT(11) NOT NULL,
	`name` VARCHAR(100) NOT NULL,
	`location_from_id` INT(11) NOT NULL,
	`location_to_id` INT(11) NOT NULL,
	`depart_date`DATETIME NOT NULL,
	`arrive_date` DATETIME NOT NULL,
	`price` DECIMAL(10,2) DEFAULT 0,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_flight_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
	CONSTRAINT `FK_flight_location_from` FOREIGN KEY (`location_from_id`) REFERENCES `location` (`id`),
	CONSTRAINT `FK_flight_location_to` FOREIGN KEY (`location_to_id`) REFERENCES `location` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;