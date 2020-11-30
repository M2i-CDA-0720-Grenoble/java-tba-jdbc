CREATE DATABASE IF NOT EXISTS `java_tba`;

USE `java_tba`;

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
    `id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255),
    `description` VARCHAR(255)
);

DROP TABLE IF EXISTS `direction`;
CREATE TABLE `direction` (
    `id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255),
    `command` VARCHAR(100)
);

DROP TABLE IF EXISTS `room_transition`;
CREATE TABLE `room_transition` (
    `id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `from_room_id` INT(11) NOT NULL,
    `to_room_id` INT(11) NOT NULL,
    `direction_id` INT(11) NOT NULL,

    FOREIGN KEY (`from_room_id`) REFERENCES `room`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`to_room_id`) REFERENCES `room`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`direction_id`) REFERENCES `direction`(`id`) ON DELETE CASCADE
);
