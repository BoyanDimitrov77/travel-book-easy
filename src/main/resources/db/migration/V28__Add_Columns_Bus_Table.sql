ALTER TABLE `bus`
ADD COLUMN `max_seats` INT(7) NOT NULL,
ADD COLUMN `count_seats` INT(7) DEFAULT 0;