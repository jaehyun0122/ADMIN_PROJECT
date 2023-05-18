-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: final_project
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `email` varchar(30) NOT NULL,
  `phoneNo` varchar(15) NOT NULL,
  `password` varchar(100) NOT NULL,
  `isPermit` tinyint(1) DEFAULT '0',
  `roles` varchar(30) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `passwordCount` int DEFAULT '0',
  `isLock` tinyint DEFAULT '0',
  `isPause` tinyint DEFAULT '0',
  `isQuit` tinyint DEFAULT '0',
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `passwordChangeDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastLoginDate` datetime DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('12@12','29829','$2a$10$/u9aV0ro0Ohn3SJLMVLw3eDuK1MRI7kao6Mi9zFZPG52cT2.rM9E.',1,'ROLE_USER','test',0,0,1,0,'2023-05-04 10:30:16','2023-05-04 10:30:16',NULL),('admin@atoncorp.com','123456','$2a$10$/u9aV0ro0Ohn3SJLMVLw3eDuK1MRI7kao6Mi9zFZPG52cT2.rM9E.',1,'ROLE_ADMIN,ROLE_SUPER','나는 관리자',1,0,0,0,'2023-05-02 17:25:12','2022-05-02 17:25:12','2023-05-17 17:48:36'),('email@email.com','01012341234','$2a$10$S0iqTBYVgYQ5BY8JUh.98OCmOX5TpRpoAGVPrSA28AEkNLMSQ108C',0,'ROLE_USER','회원가입테스트',1,0,0,0,'2023-05-16 15:09:28','2023-05-16 15:09:28',NULL),('jeongjh122@atoncorp.com','01093689836','$2a$10$WIAO3KDnQK38MjI8gQ5FreUrTzpUNViocEIWFyJKvgPEJn/yJqXwe',1,'ROLE_USER','safdasd',1,0,0,0,'2023-04-27 13:13:14','2022-05-16 10:44:02','2023-05-17 17:47:33'),('jungjh122@gmail.com','123','$2a$10$Xz2163rsEkqYXE40kJJ0B.0.N7z/ic5fRCX0cU/bbg/4lbFb..I3y',0,'ROLE_USER','김구글',3,0,0,0,'2023-05-11 10:10:25','2023-05-11 10:10:25',NULL),('jungjh122@naver.com','010456456','$2a$10$FBUNkKOO3Frk8sX.4dnRh.l5AQyF6QphkM2RRtL.l9OykgRdkOU.2',1,'ROLE_USER',NULL,0,0,0,0,'2023-05-11 09:33:41','2023-05-17 10:41:43','2023-05-17 10:41:17'),('sdfs@fdsf','213213','$2a$10$fCW.YuioI85gl6goxAE5DuNfyj320oEtkeB1imt9iL.sgDyReG2.6',1,'ROLE_ADMIN','관리자 등록 테스트 (비밀번호 : 1)',0,0,0,0,'2023-05-03 17:56:29','2023-05-03 17:56:29',NULL),('test@test','01093689836','$2a$10$XhmHYCQfveMpm/v1RRj4OONb4ozHrPaGJT/FRve2FMkrS/7wK8zAi',1,'ROLE_USER','정재현',1,0,0,0,'2023-04-27 13:13:14','2023-04-27 13:13:14','2023-05-17 10:47:44'),('test@test.com','01012341234','$2a$10$PTHvE1eHZuPGzIk9LZjT5.N8PL9FXseFhXzHufgYdcxq6GszTeZ6W',0,'ROLE_USER','회원가입테스트',1,0,0,0,'2023-05-16 15:07:36','2023-05-16 15:07:36',NULL),('zxc-02@nate.com','123','$2a$10$grtggjjawJ2I4dB135R4keFm/tHykp9uBa8xX00eO8gFwYSVn/8Fa',1,'ROLE_USER','회원가입 테스트',0,0,0,0,'2023-05-11 19:58:24','2023-05-11 19:58:24','2023-05-16 19:44:57');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-17 17:49:24
