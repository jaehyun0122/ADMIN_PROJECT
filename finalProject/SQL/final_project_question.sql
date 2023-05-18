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
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `email` varchar(30) DEFAULT NULL COMMENT '문의 등록 유저 email',
  `title` varchar(30) NOT NULL COMMENT '문의 제목',
  `content` varchar(500) NOT NULL COMMENT '문의 내용',
  `id` int NOT NULL AUTO_INCREMENT,
  `isAnswer` tinyint DEFAULT '0' COMMENT '질문 답변여부. 답변 : 1, 미답변 : 0',
  `regAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `question_ibfk_1` (`email`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES ('jeongjh122@atoncorp.com','등록테스트','등록테스트 입니다.',1,0,'2023-05-02 11:30:35'),('jeongjh122@atoncorp.com','테스트','테스트 입니다.',2,1,'2023-05-02 11:31:42'),('jeongjh122@atoncorp.com','','',3,0,'2023-05-17 10:33:37'),('jeongjh122@atoncorp.com','','',4,0,'2023-05-17 10:33:51'),('jeongjh122@atoncorp.com','','',5,0,'2023-05-17 13:46:36'),('jeongjh122@atoncorp.com','d','d',6,0,'2023-05-17 13:47:58'),('jeongjh122@atoncorp.com','tes','tes',7,0,'2023-05-17 13:51:10'),('jeongjh122@atoncorp.com','tes','test',8,0,'2023-05-17 13:51:47'),('test@test.com','test','test',9,0,'2023-05-17 17:44:25');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
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
