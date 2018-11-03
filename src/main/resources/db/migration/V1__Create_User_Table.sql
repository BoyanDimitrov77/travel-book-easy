CREATE TABLE IF NOT EXISTS `users`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_name` VARCHAR(50) NOT NULL,
	`email` VARCHAR(64) UNIQUE NOT NULL,
	`password` VARCHAR(64) NOT NULL,
	`enabled` BOOLEAN NOT NULL,
	`full_name`VARCHAR(100) NOT NULL,
    `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`))
	ENGINE=InnoDB DEFAULT CHARSET=utf8;