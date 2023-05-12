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
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `companyName` varchar(30) NOT NULL COMMENT '회사명',
  `companyNo` varchar(40) NOT NULL COMMENT '사업자 번호',
  `isPermit` int DEFAULT '0' COMMENT '승인여부. 0 : 대기, 1 : 승인, 2 : 반려',
  `confirmAt` datetime DEFAULT NULL COMMENT '승인 또는 반려 일시',
  `email` varchar(30) NOT NULL COMMENT '등록 유저 email',
  `regAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '서비스 등록 시간',
  `negativeComment` varchar(500) DEFAULT NULL COMMENT '반려 사유',
  `id` int NOT NULL AUTO_INCREMENT,
  `regUser` varchar(40) DEFAULT NULL COMMENT '승인 또는 반려한 유저',
  PRIMARY KEY (`id`),
  KEY `email` (`email`),
  CONSTRAINT `service_ibfk_1` FOREIGN KEY (`email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES ('aton','123',2,'2023-05-08 19:36:12','jeongjh122@atoncorp.com','2023-05-02 10:06:40','반려 사유.\n          ',1,'admin@admin'),('test','12',2,'2023-05-08 19:36:12','jeongjh122@atoncorp.com','2023-05-02 16:05:03','반려 사유.\n          ',2,'admin@admin'),('test','12',2,'2023-05-08 19:50:44','jeongjh122@atoncorp.com','2023-05-04 14:08:46','pdf 부적절.',3,'admin@admin'),('test','21',1,'2023-05-08 19:37:05','jeongjh122@atoncorp.com','2023-05-04 17:49:35','반려 사유.\n          ',4,'admin@admin'),('nexon','12',1,'2023-05-09 18:02:38','jeongjh122@atoncorp.com','2023-05-09 13:11:26',NULL,5,NULL),('kakao','12',1,'2023-05-09 18:06:35','jeongjh122@atoncorp.com','2023-05-09 13:12:47',NULL,6,NULL),('naver','123',1,'2023-05-09 18:03:15','jeongjh122@atoncorp.com','2023-05-09 13:14:23',NULL,7,NULL),('toss','123',1,NULL,'jeongjh122@atoncorp.com','2023-05-09 13:40:05',NULL,8,NULL),('line','324342',1,'2023-05-10 19:41:52','jeongjh122@atoncorp.com','2023-05-09 13:42:41',NULL,9,NULL),('배달의 민족','123456',0,NULL,'jeongjh122@atoncorp.com','2023-05-10 20:00:35',NULL,10,NULL),('당근마켓','456456',1,'2023-05-10 20:05:22','jeongjh122@atoncorp.com','2023-05-10 20:04:34',NULL,11,NULL);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-12 18:22:39
