
CREATE TABLE IF NOT EXISTS `account` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `username` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
    ) ;


CREATE TABLE IF NOT EXISTS `class` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `code` varchar(255) NOT NULL,
    `term` int DEFAULT NULL,
    `season` varchar(255) DEFAULT NULL,
    `subject_id` int NOT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
    ) ;


CREATE TABLE IF NOT EXISTS `point` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `class_id` int NOT NULL,
                                       `student_id` int NOT NULL,
                                       `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                       `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                       `cc` double DEFAULT NULL,
                                       `bt` double DEFAULT NULL,
                                       `th` double DEFAULT NULL,
                                       `kt` double DEFAULT NULL,
                                       `ck` double DEFAULT NULL,
                                       PRIMARY KEY (`id`)
    ) ;


CREATE TABLE IF NOT EXISTS `student` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `email` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `code` varchar(255) NOT NULL,
    `address` varchar(255) DEFAULT NULL,
    `gender` tinyint(1) DEFAULT '1',
    `phone` varchar(10) DEFAULT NULL,
    `dob` date DEFAULT NULL,
    `role` enum('STUDENT') DEFAULT 'STUDENT',
    `account_id` int NOT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
    ) ;


CREATE TABLE IF NOT EXISTS `student_class` (
                                               `id` int NOT NULL AUTO_INCREMENT,
                                               `student_id` int NOT NULL,
                                               `class_id` int NOT NULL,
                                               `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                               `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                               PRIMARY KEY (`id`)
    );


CREATE TABLE IF NOT EXISTS `subject` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `code` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `credit` int NOT NULL,
    `precentcc` int DEFAULT '0',
    `precentbt` int DEFAULT '0',
    `precentth` int DEFAULT '0',
    `precentkt` int DEFAULT '0',
    `precentck` int DEFAULT '0',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `teacher` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `name` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `code` varchar(255) NOT NULL,
    `address` varchar(255) DEFAULT NULL,
    `gender` tinyint(1) NOT NULL DEFAULT '1',
    `phone` varchar(10) DEFAULT NULL,
    `dob` date DEFAULT NULL,
    `role` enum('ADMIN','SUBJECT_TEACHER')  DEFAULT 'SUBJECT_TEACHER',
    `account_id` int NOT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
    ) ;


CREATE TABLE IF NOT EXISTS `teacher_class` (
                                               `id` int NOT NULL AUTO_INCREMENT,
                                               `class_id` int NOT NULL,
                                               `teacher_id` int NOT NULL,
                                               `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                               `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                               PRIMARY KEY (`id`)
    ) ;


ALTER TABLE `teacher` ADD CONSTRAINT FKTeacher353803 FOREIGN KEY (account_id) REFERENCES `account` (id);
ALTER TABLE `student` ADD CONSTRAINT FKStudent784556 FOREIGN KEY (account_id) REFERENCES `account` (id);

ALTER TABLE `student_class` ADD CONSTRAINT FKStudentSub889881 FOREIGN KEY (student_id) REFERENCES `student` (id);
ALTER TABLE `student_class` ADD CONSTRAINT FKStudentSub123620 FOREIGN KEY (class_id) REFERENCES `class` (id);

ALTER TABLE `teacher_class` ADD CONSTRAINT FKSubjectTea535992 FOREIGN KEY (class_id) REFERENCES `class` (id);
ALTER TABLE `teacher_class` ADD CONSTRAINT FKSubjectTea977187 FOREIGN KEY (teacher_id) REFERENCES `teacher` (id);

ALTER TABLE `point` ADD CONSTRAINT FKPoint208268 FOREIGN KEY (class_id) REFERENCES `class` (id);
ALTER TABLE `point` ADD CONSTRAINT FKPoint194766 FOREIGN KEY (student_id) REFERENCES `student` (id);

ALTER TABLE `class` ADD CONSTRAINT FKClass522226 FOREIGN KEY (subject_id) REFERENCES `subject` (id);

