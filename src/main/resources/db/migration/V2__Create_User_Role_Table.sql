CREATE TABLE IF NOT EXISTS `user_role`(
	`user_id` INT(11) NOT NULL,
	`role` VARCHAR(50) NOT NULL,
	UNIQUE KEY `auth_user` (`user_id`,`role`),
	CONSTRAINT `FK_User_User_Role` FOREIGN KEY (`user_id`) REFERENCES `users`(id)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;