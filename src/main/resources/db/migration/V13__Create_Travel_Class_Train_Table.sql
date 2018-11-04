CREATE TABLE IF NOT EXISTS `travel_class_train`(
	`train_id` INT(11) NOT NULL,
	`travel_class_id` INT(11) NOT NULL,
	 PRIMARY KEY(`train_id`,`travel_class_id`),
	 KEY `FK_travel_class_train_idx` (`travel_class_id`),
	 CONSTRAINT 	`FK_travel_class_train_train` FOREIGN KEY(`train_id`) REFERENCES `train`(`id`),
	 CONSTRAINT `FK_travel_class_train_travel_class` FOREIGN KEY (`travel_class_id`) REFERENCES `travel_class`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;