CREATE TABLE IF NOT EXISTS `user_company_comments`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL,
	`company_id` INT(11) NOT NULL,
	`comment` VARCHAR(100) NOT NULL,
	`count_likes` INT(10) DEFAULT 0,
	`count_dislike` INT(10) DEFAULT 0,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_user_company_comments_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
	CONSTRAINT `FK_user_company_comments_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;