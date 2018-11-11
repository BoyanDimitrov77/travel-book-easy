CREATE TABLE IF NOT EXISTS `travel_class`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`max_seats` INT(7) NOT NULL,
	`count_seats` INT(7) DEFAULT 0,
	`travel_class` VARCHAR(50) NOT NULL,
	`price` DECIMAL(10,2) DEFAULT 0,
	PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;;