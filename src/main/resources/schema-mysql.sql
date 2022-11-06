CREATE TABLE IF NOT EXISTS `lesson` (
  `lesson_id` int NOT NULL,
  `lesson_name` varchar(20) DEFAULT NULL,
  `day` varchar(20) DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `credits` int DEFAULT NULL,
  PRIMARY KEY (`lesson_id`)
);


CREATE TABLE IF NOT EXISTS `student` (
  `student_id` int NOT NULL,
  `student_name` varchar(20) DEFAULT NULL,
  `lesson_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
);