CREATE TABLE IF NOT EXISTS `user_company_rating`(
	`user_id` INT(11) NOT NULL,
	`company_id` INT(11) NOT NULL,
	`rating` DECIMAL(2,1) DEFAULT 0,
	UNIQUE KEY `user_rating` (`user_id`,`company_id`),
	CONSTRAINT `FK_user_company_rating_users` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
	CONSTRAINT `FK_user_company_rating_company` FOREIGN KEY (`company_id`) REFERENCES `company`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;