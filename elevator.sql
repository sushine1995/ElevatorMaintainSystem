CREATE DATABASE  IF NOT EXISTS `elevatormaintain` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `elevatormaintain`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: elevatormaintain
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_elevator_record`
--

DROP TABLE IF EXISTS `t_elevator_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_elevator_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `building_number` varchar(20) DEFAULT NULL,
  `last_maintain_time` datetime DEFAULT NULL,
  `manufacturing_date` datetime DEFAULT NULL,
  `max_weight` int(11) DEFAULT NULL,
  `model_number` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `speed` float DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `unit` varchar(100) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `maintainPlan_id` bigint(20) DEFAULT NULL,
  `no` varchar(30) DEFAULT NULL,
  `elevator_number` varchar(20) DEFAULT NULL,
  `inspection_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b1mukgfh31kbcwu6wywuk0ou1` (`group_id`),
  KEY `FK_gvrpaboq6qe49shose6xejbrf` (`maintainPlan_id`),
  CONSTRAINT `FK_b1mukgfh31kbcwu6wywuk0ou1` FOREIGN KEY (`group_id`) REFERENCES `t_group` (`id`),
  CONSTRAINT `FK_gvrpaboq6qe49shose6xejbrf` FOREIGN KEY (`maintainPlan_id`) REFERENCES `t_maintain_plan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_elevator_record`
--

LOCK TABLES `t_elevator_record` WRITE;
/*!40000 ALTER TABLE `t_elevator_record` DISABLE KEYS */;
INSERT INTO `t_elevator_record` VALUES (6,'2016-04-23 16:48:27',100,'admin','福建省厦门市思明区厦门大学海韵园科研2-306',NULL,NULL,NULL,NULL,'','125888888525',NULL,'','厦大',1,7,'MSX10032893',NULL,'2015-06-25 00:00:00'),(7,'2016-05-17 12:57:10',100,'admin','福建省厦门市思明区厦大学生公寓','2','2016-06-14 21:19:23','2016-05-04 00:00:00',2000,'A001','18649612864',10,'扶梯','厦大学生公寓',1,7,'MSX0000001','1','2015-05-24 00:00:00');
/*!40000 ALTER TABLE `t_elevator_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_employee`
--

DROP TABLE IF EXISTS `t_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `username` varchar(30) NOT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `cid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_i1kshvf9pq8u0jwdgkgjx0gkl` (`group_id`),
  CONSTRAINT `FK_i1kshvf9pq8u0jwdgkgjx0gkl` FOREIGN KEY (`group_id`) REFERENCES `t_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_employee`
--

LOCK TABLES `t_employee` WRITE;
/*!40000 ALTER TABLE `t_employee` DISABLE KEYS */;
INSERT INTO `t_employee` VALUES (1,'2016-03-14 15:40:52',NULL,'admin','张三z','84f63b2adbbf7ec6cd54aab43ebdee87','18649612864','employee1',3,'\0','4f69b4149c34af1e7a5b81efc80de0d9',NULL),(2,'2016-03-14 19:58:28',NULL,'admin','lisi','0abf55c5d2f32558ba1b87bee71e58c5','111111111112','employee2',1,'\0','e7df707c25d147c8c8304c7211ccc95d',NULL),(3,'2016-04-07 18:44:39',NULL,'admin','王五','5bb1316083fc114ea9646ebcc510de2b','123456789541','sa',3,'\0','0cf96ffe1e21201b9eeded51581f2cda','8315630a319e86de049df15d7fcb7248'),(4,'2016-04-21 10:08:16',NULL,'admin','王志鹏','938ecfbfb00f1f259bf4a8b27b72fc12','125888888525','wzp',3,'\0','d8977e142e3833ed5cc97af8f3f42e53','87fa3f15b905bdd05bde04e5ff4a1436');
/*!40000 ALTER TABLE `t_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_employee_notice`
--

DROP TABLE IF EXISTS `t_employee_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_employee_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `content` varchar(400) DEFAULT NULL,
  `isRead` bit(1) DEFAULT NULL,
  `title` varchar(30) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9mdg1tn20asjl0xd4xly7o18d` (`receiver_id`),
  CONSTRAINT `FK_9mdg1tn20asjl0xd4xly7o18d` FOREIGN KEY (`receiver_id`) REFERENCES `t_employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_employee_notice`
--

LOCK TABLES `t_employee_notice` WRITE;
/*!40000 ALTER TABLE `t_employee_notice` DISABLE KEYS */;
INSERT INTO `t_employee_notice` VALUES (1,'2016-05-10 20:20:39',NULL,NULL,'contetnt','\0','故障工单',NULL,1),(2,'2016-05-10 20:20:40',NULL,NULL,'contetnt','\0','故障工单',NULL,3),(3,'2016-05-10 20:20:40',NULL,NULL,'contetnt','\0','故障工单',NULL,4),(4,'2016-05-10 20:23:35',NULL,NULL,'contetnt','\0','故障工单',NULL,1),(5,'2016-05-10 20:23:35',NULL,NULL,'contetnt','\0','故障工单',NULL,3),(6,'2016-05-10 20:23:35',NULL,NULL,'contetnt','\0','故障工单',NULL,4),(7,'2016-05-10 20:24:49',NULL,NULL,'contetnt','\0','故障工单',NULL,1),(8,'2016-05-10 20:24:49',NULL,NULL,'contetnt','\0','故障工单',NULL,3),(9,'2016-05-10 20:24:49',NULL,NULL,'contetnt','\0','故障工单',NULL,4),(10,'2016-05-10 20:25:42',NULL,NULL,'contetnt','\0','故障工单',NULL,1),(11,'2016-05-10 20:25:42',NULL,NULL,'contetnt','\0','故障工单',NULL,3),(12,'2016-05-10 20:25:43',NULL,NULL,'contetnt','\0','故障工单',NULL,4),(13,'2016-05-10 20:30:43',NULL,NULL,'contetnt','\0','故障工单',NULL,1),(14,'2016-05-10 20:30:43',NULL,NULL,'contetnt','\0','故障工单',NULL,3),(15,'2016-05-10 20:30:44',NULL,NULL,'contetnt','\0','故障工单',NULL,4),(16,'2016-05-10 20:36:49',NULL,NULL,'contetnt','\0','故障工单',NULL,1),(17,'2016-05-10 20:36:49',NULL,NULL,'contetnt','\0','故障工单',NULL,3),(18,'2016-05-10 20:36:49',NULL,NULL,'contetnt','\0','故障工单',NULL,4),(19,'2016-05-10 21:02:53',NULL,NULL,'contetnt','\0','故障工单',NULL,1),(20,'2016-05-10 21:02:53',NULL,NULL,'contetnt','\0','故障工单',NULL,3),(21,'2016-05-10 21:02:54',NULL,NULL,'contetnt','\0','故障工单',NULL,4),(22,'2016-05-10 21:07:26',NULL,NULL,'请尽快处理','\0','您有新的故障工单',NULL,1),(23,'2016-05-10 21:07:26',NULL,NULL,'请尽快处理','\0','您有新的故障工单',NULL,3),(24,'2016-05-10 21:07:27',NULL,NULL,'请尽快处理','\0','您有新的故障工单',NULL,4),(25,'2016-05-10 21:08:18',NULL,NULL,'请尽快处理','\0','您有新的故障工单',NULL,1),(26,'2016-05-10 21:08:18',NULL,NULL,'请尽快处理','\0','您有新的故障工单',NULL,3),(27,'2016-05-10 21:08:19',NULL,NULL,'请尽快处理','\0','您有新的故障工单',NULL,4),(28,'2016-05-10 21:36:32',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(29,'2016-05-10 21:36:32',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(30,'2016-05-10 21:36:32',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(31,'2016-05-10 22:05:43',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(32,'2016-05-10 22:05:43',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(33,'2016-05-10 22:05:44',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(34,'2016-05-10 22:14:38',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(35,'2016-05-10 22:14:38',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(36,'2016-05-10 22:14:38',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(37,'2016-05-10 22:29:07',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(38,'2016-05-10 22:29:07',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(39,'2016-05-10 22:29:08',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(40,'2016-05-11 13:47:51',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(41,'2016-05-11 13:47:51',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(42,'2016-05-11 13:47:52',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(43,'2016-05-11 14:35:12',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(44,'2016-05-11 14:35:12',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(45,'2016-05-11 14:35:12',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(46,'2016-05-11 14:36:39',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(47,'2016-05-11 14:36:39',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(48,'2016-05-11 14:36:39',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(49,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(50,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(51,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(52,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(53,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(54,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(55,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(56,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(57,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(58,'2016-05-12 16:59:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(59,'2016-05-12 16:59:23',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(60,'2016-05-12 16:59:23',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(61,'2016-05-12 16:59:23',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(62,'2016-05-12 16:59:23',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(63,'2016-05-12 16:59:23',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(64,'2016-05-17 15:00:00',NULL,NULL,'您还有1个保养工单未完成，请在截止日期前尽快处理','\0','',NULL,1),(65,'2016-05-17 15:00:00',NULL,NULL,'您还有1个保养工单未完成，请在截止日期前尽快处理','\0','',NULL,3),(66,'2016-05-17 15:00:01',NULL,NULL,'您还有1个保养工单未完成，请在截止日期前尽快处理','\0','',NULL,4),(67,'2016-05-17 15:07:00',NULL,NULL,'您还有1个保养工单未完成，请在截止日期前尽快处理','\0','',NULL,1),(68,'2016-05-17 15:07:00',NULL,NULL,'您还有1个保养工单未完成，请在截止日期前尽快处理','\0','',NULL,3),(69,'2016-05-17 15:07:01',NULL,NULL,'您还有1个保养工单未完成，请在截止日期前尽快处理','\0','',NULL,4),(70,'2016-05-17 15:10:00',NULL,NULL,'您还有4个保养工单未完成，请在截止日期前尽快处理','\0','',NULL,1),(71,'2016-05-17 15:10:00',NULL,NULL,'您还有4个保养工单未完成，请在截止日期前尽快处理','\0','',NULL,3),(72,'2016-05-17 15:10:01',NULL,NULL,'您还有4个保养工单未完成，请在截止日期前尽快处理','\0','',NULL,4),(73,'2016-05-18 21:57:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(74,'2016-05-18 21:57:22',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(75,'2016-05-18 21:57:23',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(76,'2016-05-18 22:20:34',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(77,'2016-05-18 22:20:34',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(78,'2016-05-18 22:20:34',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(79,'2016-05-18 22:37:49',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(80,'2016-05-18 22:37:49',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(81,'2016-05-18 22:38:02',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(82,'2016-05-19 14:46:50',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(83,'2016-05-19 14:46:50',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(84,'2016-05-19 14:46:51',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(85,'2016-05-19 15:03:44',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(86,'2016-05-19 15:03:44',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(87,'2016-05-19 15:03:44',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(88,'2016-05-19 15:09:29',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(89,'2016-05-19 15:09:29',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(90,'2016-05-19 15:09:29',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(91,'2016-05-19 15:21:14',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(92,'2016-05-19 15:21:14',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(93,'2016-05-19 15:21:15',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(104,'2016-05-19 20:17:15',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(105,'2016-05-19 20:17:15',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(106,'2016-05-19 20:17:16',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(107,'2016-05-19 20:17:23',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(108,'2016-05-19 20:17:23',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(109,'2016-05-19 20:17:23',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(110,'2016-05-19 20:17:25',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(111,'2016-05-19 20:17:25',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(112,'2016-05-19 20:17:25',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(113,'2016-05-19 20:19:36',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(114,'2016-05-19 20:19:36',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(115,'2016-05-19 20:19:37',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(116,'2016-05-19 20:19:51',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(117,'2016-05-19 20:19:51',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(118,'2016-05-19 20:19:51',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(119,'2016-05-19 20:19:58',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(120,'2016-05-19 20:19:58',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(121,'2016-05-19 20:19:58',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(122,'2016-05-19 20:21:11',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(123,'2016-05-19 20:21:11',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(124,'2016-05-19 20:21:11',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(125,'2016-05-19 20:39:05',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(126,'2016-05-19 20:39:05',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(127,'2016-05-19 20:39:05',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(128,'2016-05-21 14:25:08',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(129,'2016-05-21 14:25:08',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(130,'2016-05-21 14:25:08',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(131,'2016-05-21 17:35:49',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(132,'2016-05-21 17:35:49',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(133,'2016-05-21 17:35:51',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(134,'2016-05-23 22:32:10',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(135,'2016-05-23 22:32:10',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(136,'2016-05-23 22:32:11',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(137,'2016-05-25 19:55:14',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,1),(138,'2016-05-25 19:55:14',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,3),(139,'2016-05-25 19:55:14',NULL,NULL,'您有新的故障工单,请尽快处理','\0','故障工单',NULL,4),(140,'2016-06-02 16:36:56',NULL,NULL,'sfsfsfs','\0','ss',NULL,2),(141,'2016-06-02 16:36:56',NULL,NULL,'sfsfsfs','\0','ss',NULL,1),(142,'2016-06-02 16:36:56',NULL,NULL,'sfsfsfs','\0','ss',NULL,3),(143,'2016-06-02 16:36:57',NULL,NULL,'sfsfsfs','\0','ss',NULL,4);
/*!40000 ALTER TABLE `t_employee_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_fault_order`
--

DROP TABLE IF EXISTS `t_fault_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_fault_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `fixed` bit(1) DEFAULT NULL,
  `occured_time` datetime DEFAULT NULL,
  `reason` varchar(300) DEFAULT NULL,
  `receiving_time` datetime DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `sign_in_address` varchar(100) DEFAULT NULL,
  `sign_in_time` datetime DEFAULT NULL,
  `sign_out_address` varchar(100) DEFAULT NULL,
  `sign_out_time` datetime DEFAULT NULL,
  `elevatorRecord_id` bigint(20) DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `no` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7uiua6hfggq8kg632ogo3pm8i` (`elevatorRecord_id`),
  KEY `FK_84sijviumm50jlglsif0wrlqe` (`employee_id`),
  KEY `FK_3jh4460otl6kg11ef56d4p0j0` (`group_id`),
  CONSTRAINT `FK_3jh4460otl6kg11ef56d4p0j0` FOREIGN KEY (`group_id`) REFERENCES `t_group` (`id`),
  CONSTRAINT `FK_7uiua6hfggq8kg632ogo3pm8i` FOREIGN KEY (`elevatorRecord_id`) REFERENCES `t_elevator_record` (`id`),
  CONSTRAINT `FK_84sijviumm50jlglsif0wrlqe` FOREIGN KEY (`employee_id`) REFERENCES `t_employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_fault_order`
--

LOCK TABLES `t_fault_order` WRITE;
/*!40000 ALTER TABLE `t_fault_order` DISABLE KEYS */;
INSERT INTO `t_fault_order` VALUES (8,'2016-04-23 16:49:21',100,'admin','测试','\0','2016-04-23 16:49:21',NULL,'2016-05-23 15:46:43',NULL,'福建省厦门市思明区曾厝垵路125号','2016-05-23 15:50:50',NULL,NULL,6,3,NULL,'FO2016042360560725'),(14,'2016-05-05 20:21:21',100,'admin','122','','2016-05-05 20:21:21','缺少润滑油','2016-05-19 14:44:23','','福建省厦门市思明区曾厝垵路125号','2016-05-24 20:11:35','福建省厦门市思明区曾厝垵路125号','2016-05-24 20:12:06',6,4,NULL,'FO2016050573281017'),(15,'2016-05-05 20:31:27',100,'admin','ttt','\0','2016-05-05 20:31:27',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050573886576'),(16,'2016-05-09 13:35:59',100,'admin','jmeter??4444444','\0','2016-05-09 13:35:59',NULL,'2016-05-13 21:11:57','',NULL,NULL,NULL,NULL,6,3,NULL,'FO2016050948959086'),(17,'2016-05-09 13:49:15',100,'admin','test','\0','2016-05-09 13:49:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050949755162'),(19,'2016-05-09 15:40:50',100,'admin','fff','\0','2016-05-09 15:40:50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050956450463'),(20,'2016-05-09 16:20:06',100,'admin','ffffff','\0','2016-05-09 16:20:06','jjd','2016-05-19 14:44:32','','福建省厦门市思明区曾厝垵路125号','2016-06-01 20:30:02','福建省厦门市思明区环岛南路','2016-06-03 17:13:03',6,4,NULL,'FO2016050958805831'),(21,'2016-05-09 16:20:19',100,'admin','ffffff','\0','2016-05-09 16:20:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050958819235'),(22,'2016-05-09 16:20:22',100,'admin','ffffff','','2016-05-09 16:20:22','开门\n经销商\n淋着雨','2016-05-19 14:44:54','','福建省厦门市思明区曾厝垵路123号','2016-05-19 14:45:08','福建省厦门市思明区曾厝垵路125号','2016-05-25 14:58:03',6,4,NULL,'FO2016050958821796'),(23,'2016-05-09 16:20:23',100,'admin','ffffff','\0','2016-05-09 16:20:23',NULL,'2016-05-17 13:00:17',NULL,NULL,NULL,NULL,NULL,6,3,NULL,'FO2016050958822504'),(24,'2016-05-09 16:20:23',100,'admin','ffffff','\0','2016-05-09 16:20:23',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050958823132'),(25,'2016-05-09 16:33:54',100,'admin','ffffff','\0','2016-05-09 16:33:54',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050959633949'),(26,'2016-05-09 16:57:40',100,'admin','dddddd','\0','2016-05-09 16:57:40',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050961059887'),(34,'2016-05-09 20:47:20',100,'admin','ddddddddd','\0','2016-05-09 20:47:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050974839695'),(35,'2016-05-09 20:52:39',100,'admin','ddddddddd','\0','2016-05-09 20:52:39',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050975159264'),(36,'2016-05-09 20:59:06',100,'admin','test','\0','2016-05-09 20:59:06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016050975545568'),(39,'2016-05-10 20:20:39',100,'admin','dsfsfds','\0','2016-05-10 20:20:39',NULL,'2016-05-13 20:30:59',NULL,'福建省厦门市思明区曾厝垵路125号','2016-05-23 16:07:27',NULL,NULL,6,3,NULL,'FO2016051073239271'),(40,'2016-05-10 20:23:35',100,'admin','sfsdfsdfsd','\0','2016-05-10 20:23:35',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051073415036'),(41,'2016-05-10 20:24:49',100,'admin','sfsdfsdfsd','\0','2016-05-10 20:24:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051073488854'),(42,'2016-05-10 20:25:42',100,'admin','ffsdfdsfds','\0','2016-05-10 20:25:42',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051073541549'),(43,'2016-05-10 20:30:43',100,'admin','ffsdfdsfds','\0','2016-05-10 20:30:43',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051073843316'),(44,'2016-05-10 20:36:49',100,'admin','ffsdfdsfds','\0','2016-05-10 20:36:49',NULL,'2016-05-11 16:05:45',NULL,'福建省厦门市思明区曾厝垵路123号','2016-05-11 16:06:00',NULL,NULL,6,3,NULL,'FO2016051074208918'),(45,'2016-05-10 21:02:53',100,'admin','sdddddddddd','\0','2016-05-10 21:02:53',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051075772885'),(46,'2016-05-10 21:07:26',100,'admin','sdddddddddd','\0','2016-05-10 21:07:26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051076045975'),(47,'2016-05-10 21:08:18',100,'admin','sdddddddddd','','2016-05-10 21:08:18','呵呵','2016-05-10 22:44:18','','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:44:32','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:44:53',6,3,NULL,'FO2016051076098155'),(48,'2016-05-10 21:36:32',100,'admin','sdddddddddd','','2016-05-10 21:36:32','。。。','2016-05-10 22:10:11','','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:11:58','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:12:34',6,3,NULL,'FO2016051077791694'),(49,'2016-05-10 22:05:43',100,'admin','轿门开不起来','','2016-05-10 22:05:43','不知道','2016-05-10 22:10:07','','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:11:53','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:12:24',6,3,NULL,'FO2016051079542801'),(50,'2016-05-10 22:14:38',100,'admin','dfdsfsfds','','2016-05-10 22:14:38','回家\n','2016-05-10 22:16:41','','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:17:40','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:18:04',6,3,NULL,'FO2016051080077503'),(51,'2016-05-10 22:29:07',100,'admin','555555','','2016-05-10 22:29:07','呵呵','2016-05-10 22:44:14','','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:44:27','福建省厦门市思明区曾厝垵路123号','2016-05-10 22:44:45',6,3,NULL,'FO2016051080947018'),(52,'2016-05-11 13:47:51',100,'admin','fffffffffffffffffff','\0','2016-05-11 13:47:51',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051149671389'),(53,'2016-05-11 14:35:12',100,'admin','dfsdf','\0','2016-05-11 14:35:12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051152512281'),(54,'2016-05-11 14:36:39',100,'admin','dfsdf','\0','2016-05-11 14:36:39',NULL,'2016-05-11 16:05:34',NULL,'福建省厦门市思明区曾厝垵路125号','2016-05-12 16:20:42',NULL,NULL,6,3,NULL,'FO2016051152599191'),(55,'2016-05-12 16:59:22',100,'admin','ffffff','\0','2016-05-12 16:59:22',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051261161722'),(56,'2016-05-12 16:59:22',100,'admin','ffffff','\0','2016-05-12 16:59:22',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051261161720'),(57,'2016-05-12 16:59:22',100,'admin','ffffff','\0','2016-05-12 16:59:22',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051261161724'),(58,'2016-05-12 16:59:22',100,'admin','ffffff','\0','2016-05-12 16:59:22',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051261161727'),(59,'2016-05-12 16:59:22',100,'admin','ffffff','','2016-05-12 16:59:22','不太懂',NULL,'啊哈哈',NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051261161812'),(60,'2016-05-18 21:57:22',100,'admin','gfdgfd','','2016-05-18 21:57:22','fffffff',NULL,'fff',NULL,NULL,NULL,NULL,6,3,NULL,'FO2016051879042238'),(61,'2016-05-18 22:20:34',100,'admin','hahaha','\0','2016-05-18 22:20:34',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051880433749'),(62,'2016-05-18 22:37:49',100,'admin','dffs','\0','2016-05-18 22:37:49',NULL,'2016-05-23 15:51:19',NULL,NULL,NULL,NULL,NULL,7,3,NULL,'FO2016051881468574'),(63,'2016-05-19 14:46:50',100,'admin','电梯异响','\0','2016-05-19 14:46:50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051953209973'),(64,'2016-05-19 15:03:44',100,'admin','123','\0','2016-05-19 15:03:44',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051954223767'),(65,'2016-05-19 15:09:29',100,'admin','6545441','\0','2016-05-19 15:09:29',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051954569194'),(66,'2016-05-19 15:21:14',100,'admin','阿萨德刚','\0','2016-05-19 15:21:14',NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL,NULL,'FO2016051955274398'),(72,'2016-05-19 20:17:15',100,'admin','e','\0','2016-05-19 20:17:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051973034777'),(73,'2016-05-19 20:17:23',100,'admin','电梯不启动','','2016-05-19 20:17:23','电源电压过低',NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051973043431'),(74,'2016-05-19 20:17:25',100,'admin','按钮失效','\0','2016-05-19 20:17:25',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051973045184'),(75,'2016-05-19 20:19:36',100,'admin','电梯不开门','\0','2016-05-19 20:19:36',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051973176309'),(76,'2016-05-19 20:19:51',100,'admin','开门时冲击很大','\0','2016-05-19 20:19:51',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051973190901'),(78,'2016-05-19 20:21:11',100,'admin','继电器短路','\0','2016-05-19 20:21:11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051973270834'),(79,'2016-05-19 20:39:05',100,'admin','润滑不良','\0','2016-05-19 20:39:05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016051974344837'),(80,'2016-05-21 14:25:08',100,'admin','轿门损坏','\0','2016-05-21 14:25:08',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016052151907794'),(81,'2016-05-21 17:35:49',100,'admin','楼层不显示','\0','2016-05-21 17:35:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016052163348749'),(82,'2016-05-23 22:32:10',100,'admin','屏幕损坏','\0','2016-05-23 22:32:10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016052381130184'),(83,'2016-05-25 19:55:14',100,'admin','轿门损坏','\0','2016-05-25 19:55:14',NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,'FO2016052571713880');
/*!40000 ALTER TABLE `t_fault_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_group`
--

DROP TABLE IF EXISTS `t_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_group`
--

LOCK TABLES `t_group` WRITE;
/*!40000 ALTER TABLE `t_group` DISABLE KEYS */;
INSERT INTO `t_group` VALUES (1,NULL,NULL,NULL,'小组1','测试小组'),(3,'2016-03-14 19:57:51',NULL,'admin','xiaozu3','dddd');
/*!40000 ALTER TABLE `t_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_maintain_item`
--

DROP TABLE IF EXISTS `t_maintain_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_maintain_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `maintainType_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6dvdkcjvfiqmugwfgrwqqu2lp` (`maintainType_id`),
  CONSTRAINT `FK_6dvdkcjvfiqmugwfgrwqqu2lp` FOREIGN KEY (`maintainType_id`) REFERENCES `t_maintain_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_maintain_item`
--

LOCK TABLES `t_maintain_item` WRITE;
/*!40000 ALTER TABLE `t_maintain_item` DISABLE KEYS */;
INSERT INTO `t_maintain_item` VALUES (20,'2016-05-17 13:59:31',NULL,'admin','项目200',6),(21,'2016-05-17 13:59:35',NULL,'admin','项目201',6),(22,'2016-05-17 13:59:38',NULL,'admin','项目202',6),(23,'2016-05-17 13:59:42',NULL,'admin','项目203',6),(24,'2016-05-17 13:59:47',NULL,'admin','项目204',5),(25,'2016-05-17 13:59:51',NULL,'admin','项目404',5),(26,'2016-05-17 13:59:55',NULL,'admin','项目400',5);
/*!40000 ALTER TABLE `t_maintain_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_maintain_order`
--

DROP TABLE IF EXISTS `t_maintain_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_maintain_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `final_Time` datetime DEFAULT NULL,
  `finished` bit(1) DEFAULT NULL,
  `receiving_time` datetime DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `sign_in_address` varchar(100) DEFAULT NULL,
  `sign_in_time` datetime DEFAULT NULL,
  `sign_out_address` varchar(100) DEFAULT NULL,
  `sign_out_time` datetime DEFAULT NULL,
  `elevatorRecord_id` bigint(20) DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `maintainType_id` bigint(20) DEFAULT NULL,
  `is_expire` bit(1) DEFAULT NULL,
  `no` varchar(30) DEFAULT NULL,
  `finished_items` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cll3n3jraj0ugb5gy2a2lbg01` (`elevatorRecord_id`),
  KEY `FK_9grdtm9uixi0sxer6ut1rtcpq` (`employee_id`),
  KEY `FK_nxpba6u6lq5yv41cen62mhqcl` (`group_id`),
  KEY `FK_6j4i66y5yhxmxysf3idaknswl` (`maintainType_id`),
  CONSTRAINT `FK_6j4i66y5yhxmxysf3idaknswl` FOREIGN KEY (`maintainType_id`) REFERENCES `t_maintain_type` (`id`),
  CONSTRAINT `FK_9grdtm9uixi0sxer6ut1rtcpq` FOREIGN KEY (`employee_id`) REFERENCES `t_employee` (`id`),
  CONSTRAINT `FK_cll3n3jraj0ugb5gy2a2lbg01` FOREIGN KEY (`elevatorRecord_id`) REFERENCES `t_elevator_record` (`id`),
  CONSTRAINT `FK_nxpba6u6lq5yv41cen62mhqcl` FOREIGN KEY (`group_id`) REFERENCES `t_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_maintain_order`
--

LOCK TABLES `t_maintain_order` WRITE;
/*!40000 ALTER TABLE `t_maintain_order` DISABLE KEYS */;
INSERT INTO `t_maintain_order` VALUES (58,'2016-05-17 14:01:45',100,'admin','2016-05-31 00:00:00','\0',NULL,NULL,NULL,NULL,NULL,NULL,7,NULL,NULL,6,'\0','MO2016051750505266',NULL),(59,'2016-05-17 14:01:58',100,'admin','2016-05-31 00:00:00','',NULL,'',NULL,NULL,NULL,'2016-06-14 21:19:23',7,1,NULL,5,'\0','MO2016051750517577',NULL),(60,'2016-05-17 14:54:09',100,'admin','2016-05-18 00:00:00','\0','2016-06-03 16:42:19','','福建省厦门市思明区环岛南路','2016-06-03 16:42:44','福建省厦门市思明区曾厝垵路125号','2016-06-03 16:44:25',6,4,NULL,6,'\0','MO2016051753649392','20;21;'),(62,'2016-05-17 14:54:40',100,'admin','2016-05-26 00:00:00','',NULL,'',NULL,NULL,NULL,NULL,7,1,NULL,6,'\0','MO2016051753679939',NULL),(63,'2016-05-17 14:54:49',100,'admin','2016-05-27 00:00:00','',NULL,'fff',NULL,NULL,NULL,NULL,7,4,NULL,6,'\0','MO2016051753689168',NULL);
/*!40000 ALTER TABLE `t_maintain_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_maintain_plan`
--

DROP TABLE IF EXISTS `t_maintain_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_maintain_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `isEffectiveImmediate` bit(1) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `no` varchar(30) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `is_expire` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_15ik03n2af47xo4aklrdrd0hj` (`group_id`),
  CONSTRAINT `FK_15ik03n2af47xo4aklrdrd0hj` FOREIGN KEY (`group_id`) REFERENCES `t_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_maintain_plan`
--

LOCK TABLES `t_maintain_plan` WRITE;
/*!40000 ALTER TABLE `t_maintain_plan` DISABLE KEYS */;
INSERT INTO `t_maintain_plan` VALUES (2,'2016-04-15 15:58:41',NULL,'admin','2016-04-29 00:00:00','','合同12','100000002',1,NULL),(3,'2016-04-16 15:23:58',NULL,'admin','2016-04-30 00:00:00','\0','合同122','100000001',1,NULL),(4,'2016-04-21 16:42:14',NULL,'admin','2016-04-30 00:00:00',NULL,'合同5','342167897',3,'\0'),(5,'2016-06-14 21:22:58',NULL,'admin','2016-12-24 00:00:00',NULL,'海韵小区','100091199',1,'\0'),(7,'2016-06-24 17:43:20',NULL,'admin','2016-12-22 00:00:00',NULL,'海韵小区2','343335322',1,'\0');
/*!40000 ALTER TABLE `t_maintain_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_maintain_type`
--

DROP TABLE IF EXISTS `t_maintain_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_maintain_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_maintain_type`
--

LOCK TABLES `t_maintain_type` WRITE;
/*!40000 ALTER TABLE `t_maintain_type` DISABLE KEYS */;
INSERT INTO `t_maintain_type` VALUES (5,'2016-05-17 13:58:24',NULL,'admin','年检'),(6,'2016-05-17 13:58:37',NULL,'admin','日常');
/*!40000 ALTER TABLE `t_maintain_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_organization`
--

DROP TABLE IF EXISTS `t_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` time DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_organization`
--

LOCK TABLES `t_organization` WRITE;
/*!40000 ALTER TABLE `t_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_resource`
--

DROP TABLE IF EXISTS `t_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` time DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `permission` varchar(100) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_resource`
--

LOCK TABLES `t_resource` WRITE;
/*!40000 ALTER TABLE `t_resource` DISABLE KEYS */;
INSERT INTO `t_resource` VALUES (1,NULL,NULL,'','用户管理',0,'user:menu',1,'menu','user/all',NULL),(2,NULL,NULL,'','创建用户',1,'user:create',1,'button','',NULL),(3,NULL,NULL,'','删除用户',1,'user:delete',1,'button',NULL,NULL),(4,NULL,NULL,'','编辑用户',1,'user:update',1,'button',NULL,NULL),(5,NULL,NULL,'','查询用户',1,'user:view',1,'button',NULL,NULL),(6,NULL,NULL,'','角色管理',0,'role:menu',2,'menu','role/all',NULL),(7,NULL,NULL,'\0','创建角色',6,'role:create',1,'button','',NULL),(8,NULL,NULL,'','删除角色',6,'role:delete',1,'button','2',NULL),(9,NULL,NULL,'','编辑角色',6,'role:update',1,'button','',NULL),(10,NULL,NULL,'','查询角色',6,'role:view',1,'button','',NULL),(11,'14:15:19',NULL,'','资源管理',0,'resource:menu',3,'menu','resource/all','admin'),(12,'15:22:35',NULL,'','创建资源',11,'resource:create',1,'button','','admin'),(13,'15:22:52',NULL,'','删除资源',11,'resource:delete',1,'button','','admin'),(14,'15:23:23',NULL,'','查询资源',11,'resource:view',1,'button','','admin'),(15,'15:23:47',NULL,'','编辑资源',11,'resource:update',1,'button','','admin'),(18,'19:27:09',NULL,'','绑定角色',1,'user:bind',1,'button','','admin'),(19,'19:34:06',NULL,'','绑定资源',6,'role:bind',1,'button','','admin'),(30,'14:12:57',NULL,'','上传用户',1,'user:upload',1,'button','','admin'),(31,'14:13:13',NULL,'','下载用户',1,'user:download',1,'button','','admin'),(38,'23:32:16',NULL,'','监控系统',0,'monitor:view',4,'menu','druid','admin'),(40,'14:55:56',NULL,'','员工管理',0,'employee:menu',5,'menu','employee/all','admin'),(41,'14:56:55',NULL,'','创建员工',40,'employee:create',1,'button','','admin'),(42,'14:57:33',NULL,'','删除员工',40,'employee:delete',1,'button','','admin'),(43,'14:58:07',NULL,'','编辑员工',40,'employee:update',1,'button','','admin'),(44,'14:59:08',NULL,'','查询员工',40,'employee:view',1,'button','','admin'),(45,'14:59:34',NULL,'','上传员工',40,'employee:upload',1,'button','','admin'),(46,'15:00:01',NULL,'','下载员工',40,'employee:download',1,'button','','admin'),(47,'18:28:50',NULL,'','绑定小组',40,'employee:bind',1,'button','','admin'),(48,'18:29:33',NULL,'','小组管理',0,'group:menu',6,'menu','group/all','admin'),(49,'18:33:01',NULL,'','创建小组',48,'group:create',1,'button','','admin'),(50,'18:33:16',NULL,'','删除小组',48,'group:delete',1,'button','','admin'),(51,'18:33:29',NULL,'','编辑小组',48,'group:update',1,'button','','admin'),(52,'18:33:39',NULL,'','查询小组',48,'group:view',1,'button','','admin'),(53,'22:34:51',NULL,'','电梯档案管理',0,'elevatorRecord:menu',7,'menu','elevatorRecord/all','admin'),(54,'22:35:22',NULL,'','创建电梯档案',53,'elevatorRecord:create',1,'button','','admin'),(55,'22:35:33',NULL,'','删除电梯档案',53,'elevatorRecord:delete',1,'button','','admin'),(56,'22:35:45',NULL,'','编辑电梯档案',53,'elevatorRecord:update',1,'button','','admin'),(57,'22:35:55',NULL,'','查询电梯档案',53,'elevatorRecord:view',1,'button','','admin'),(58,'22:36:09',NULL,'','上传查询电梯档案',53,'elevatorRecord:upload',1,'button','','admin'),(59,'22:36:19',NULL,'','下载查询电梯档案',53,'elevatorRecord:download',1,'button','','admin'),(61,'22:40:04',NULL,'','电梯档案绑定小组',53,'elevatorRecord:bind',1,'button','','admin'),(62,'20:14:24',NULL,'','维保项目管理',0,'maintainItem:menu',8,'menu','maintainItem/all','admin'),(63,'20:15:10',NULL,'','创建维保项目',62,'maintainItem:create',1,'button','','admin'),(64,'20:15:23',NULL,'','删除维保项目',62,'maintainItem:delete',1,'button','','admin'),(65,'20:15:37',NULL,'','编辑维保项目',62,'maintainItem:update',1,'button','','admin'),(66,'20:15:48',NULL,'','查询维保项目',62,'maintainItem:view',1,'button','','admin'),(67,'20:16:06',NULL,'','上传维保项目',62,'maintainItem:upload',1,'button','','admin'),(68,'20:16:18',NULL,'','下载维保项目',62,'maintainItem:download',1,'button','','admin'),(69,'20:17:27',NULL,'','维保类型管理',0,'maintainType:menu',9,'menu','maintainType/all','admin'),(70,'20:18:04',NULL,'','创建维保类型',69,'maintainType:create',1,'button','','admin'),(71,'20:18:16',NULL,'','删除维保类型',69,'maintainType:delete',1,'button','','admin'),(72,'20:18:27',NULL,'','编辑维保类型',69,'maintainType:update',1,'button','','admin'),(73,'20:18:40',NULL,'','查询维保类型',69,'maintainType:view',1,'button','','admin'),(74,'20:18:52',NULL,'','上传维保类型',69,'maintainType:upload',1,'button','','admin'),(75,'20:19:02',NULL,'','下载维保类型',69,'maintainType:download',1,'button','','admin'),(76,'14:40:21',NULL,'','故障工单管理',0,'faultOrder:menu',10,'menu','faultOrder/all','admin'),(77,'14:40:47',NULL,'','保养工单管理',0,'maintainOrder:menu',11,'menu','maintainOrder/all','admin'),(78,'14:41:21',NULL,'','创建故障工单',76,'faultOrder:create',1,'button','','admin'),(79,'14:41:33',NULL,'','删除故障工单',76,'faultOrder:delete',1,'button','','admin'),(80,'14:42:04',NULL,'','编辑故障工单',76,'faultOrder:update',1,'button','','admin'),(81,'14:42:21',NULL,'','查询故障工单',76,'faultOrder:view',1,'button','','admin'),(82,'15:05:16',NULL,'','创建保养工单',77,'maintainOrder:create',1,'button','','admin'),(83,'15:05:28',NULL,'','删除保养工单',77,'maintainOrder:delete',1,'button','','admin'),(84,'15:05:47',NULL,'','编辑保养工单',77,'maintainOrder:update',1,'button','','admin'),(85,'15:06:01',NULL,'','查询保养工单',77,'maintainOrder:view',1,'button','','admin'),(86,'20:39:55',NULL,'','保养计划管理',0,'maintainPlan:menu',12,'menu','maintainPlan/all','admin'),(87,'20:40:52',NULL,'','创建保养计划',86,'maintainPlan:create',1,'button','','admin'),(88,'20:41:03',NULL,'','删除保养计划',86,'maintainPlan:delete',1,'button','','admin'),(89,'20:41:12',NULL,'','编辑保养计划',86,'maintainPlan:update',1,'button','','admin'),(90,'20:41:23',NULL,'','查询保养计划',86,'maintainPlan:view',1,'button','','admin'),(91,'20:42:33',NULL,'','保养计划绑定小组',86,'maintainPlan:bindGroup',1,'button','','admin'),(92,'20:42:45',NULL,'','保养计划绑定小电梯',86,'maintainPlan:bindElevator',1,'button','','admin'),(93,'16:02:51',NULL,'','电梯档案绑定保养计划',53,'elevatorRecord:bindMaintainPlan',1,'button','','admin'),(94,'16:04:13',NULL,'','电梯档案发布故障工单',53,'elevatorRecord:addFaultOrder',1,'button','','admin'),(95,'16:51:58',NULL,'','电梯档案发布保养工单',53,'elevatorRecord:addMaintainOrder',1,'button','','admin'),(96,'20:57:38',NULL,'','故障工单反馈',76,'faultOrder:feedback',1,'button','','admin'),(97,'20:58:07',NULL,'','保养工单反馈',77,'maintainOrder:feedback',1,'button','','admin');
/*!40000 ALTER TABLE `t_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `mark` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'2015-10-19 22:18:02',NULL,'','超管',NULL,'admin','guest'),(21,'2015-10-18 13:36:34',NULL,'','fg',NULL,'admin2','admin'),(25,'2015-10-28 14:36:22',NULL,'','来宾',NULL,'guest','admin');
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_resource`
--

DROP TABLE IF EXISTS `t_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_role_resource` (
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `FK_hvd3v1xf4men57mo38osb4ih1` (`resource_id`),
  CONSTRAINT `FK_hvd3v1xf4men57mo38osb4ih1` FOREIGN KEY (`resource_id`) REFERENCES `t_resource` (`id`),
  CONSTRAINT `FK_qie95ky0tnqn6cl36rd606yxo` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_resource`
--

LOCK TABLES `t_role_resource` WRITE;
/*!40000 ALTER TABLE `t_role_resource` DISABLE KEYS */;
INSERT INTO `t_role_resource` VALUES (1,1),(21,1),(25,1),(1,2),(21,2),(1,3),(21,3),(1,4),(21,4),(1,5),(21,5),(25,5),(1,6),(21,6),(25,6),(1,7),(21,7),(1,8),(21,8),(1,9),(21,9),(1,10),(21,10),(25,10),(1,11),(21,11),(25,11),(1,12),(21,12),(1,13),(21,13),(1,14),(21,14),(25,14),(1,15),(21,15),(1,18),(21,18),(1,19),(21,19),(1,30),(1,31),(1,38),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(1,46),(1,47),(1,48),(1,49),(1,50),(1,51),(1,52),(1,53),(1,54),(1,55),(1,56),(1,57),(1,58),(1,59),(1,61),(1,62),(1,63),(1,64),(1,65),(1,66),(1,67),(1,68),(1,69),(1,70),(1,71),(1,72),(1,73),(1,74),(1,75),(1,76),(1,77),(1,78),(1,79),(1,80),(1,81),(1,82),(1,83),(1,84),(1,85),(1,86),(1,87),(1,88),(1,89),(1,90),(1,91),(1,92),(1,93),(1,94),(1,95),(1,96),(1,97);
/*!40000 ALTER TABLE `t_role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `user_alias` varchar(50) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (98,'2015-10-18 13:37:12',NULL,'2016-05-10 19:26:35','\0','2016-05-10 19:27:10','bf97b99d87c941ec977cd4dc6ec945f0','9aff3745b94a4d705464bdd0e8dfb4ca','访客','guest','admin'),(100,'2015-10-19 22:27:00',NULL,'2017-05-17 20:35:59','\0','2017-05-17 20:37:44','e5e5730c57233c3a56c2b6fe9c77f3ec','d71e5418430e88094ec30d71c9a6ad04','超级管理员','admin','guest');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_notice`
--

DROP TABLE IF EXISTS `t_user_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `content` varchar(400) DEFAULT NULL,
  `isRead` bit(1) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_notice`
--

LOCK TABLES `t_user_notice` WRITE;
/*!40000 ALTER TABLE `t_user_notice` DISABLE KEYS */;
INSERT INTO `t_user_notice` VALUES (1,'2016-03-28 14:03:08',NULL,'admin','这是第0则通知','','orderExpire'),(2,'2016-03-28 14:03:08',NULL,'admin','这是第1则通知','','orderExpire'),(3,'2016-03-28 14:03:08',NULL,'admin','这是第2则通知','','orderExpire'),(4,'2016-03-28 14:03:08',NULL,'admin','这是第3则通知','','orderExpire'),(5,'2016-03-28 14:03:08',NULL,'admin','这是第4则通知','','orderExpire'),(6,'2016-03-28 14:03:08',NULL,'admin','这是第5则通知','','orderExpire'),(7,'2016-03-28 14:03:08',NULL,'admin','这是第6则通知','','orderExpire'),(8,'2016-03-28 14:03:08',NULL,'admin','这是第7则通知','','orderExpire'),(9,'2016-03-28 14:03:08',NULL,'admin','这是第8则通知','','orderExpire'),(10,'2016-03-28 14:03:08',NULL,'admin','这是第9则通知','','orderExpire'),(11,'2016-03-28 14:04:41',NULL,'admin','这是第0则通知','','orderExpire'),(12,'2016-03-28 14:04:42',NULL,'admin','这是第1则通知','','orderExpire'),(13,'2016-03-28 14:04:42',NULL,'admin','这是第2则通知','','orderExpire'),(14,'2016-03-28 14:04:42',NULL,'admin','这是第3则通知','','orderExpire'),(15,'2016-03-28 14:04:42',NULL,'admin','这是第4则通知','','orderExpire'),(16,'2016-03-29 15:50:24',NULL,'admin','这是第0则通知','','orderExpire'),(17,'2016-05-10 22:12:24',NULL,NULL,'编号为FO2016051079542801的故障工单已完成','',NULL),(18,'2016-05-10 22:12:34',NULL,NULL,'编号为FO2016051077791694的故障工单已完成','',NULL),(19,'2016-05-10 22:18:04',NULL,NULL,'编号为FO2016051080077503的故障工单已完成','',NULL),(20,'2016-05-10 22:44:45',NULL,NULL,'编号为FO2016051080947018的故障工单已完成','',NULL),(21,'2016-05-10 22:44:53',NULL,NULL,'编号为FO2016051076098155的故障工单已完成','',NULL),(22,'2016-05-11 16:39:43',NULL,NULL,'编号为null的故障工单已完成','','故障工单'),(23,'2016-05-18 21:36:35',NULL,NULL,'编号为FO2016051261161812的故障工单已完成','','故障工单'),(24,'2016-05-24 20:12:06',NULL,NULL,'编号为null的故障工单已完成','','故障工单'),(25,'2016-05-25 14:58:03',NULL,NULL,'编号为null的故障工单已完成','','故障工单');
/*!40000 ALTER TABLE `t_user_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_role`
--

DROP TABLE IF EXISTS `t_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_4uvv76e86ms8ru0kk9s01d3s2` (`role_id`),
  CONSTRAINT `FK_4uvv76e86ms8ru0kk9s01d3s2` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FK_kefwen29p9h9ilvry31mgyc94` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_role`
--

LOCK TABLES `t_user_role` WRITE;
/*!40000 ALTER TABLE `t_user_role` DISABLE KEYS */;
INSERT INTO `t_user_role` VALUES (98,1),(100,1);
/*!40000 ALTER TABLE `t_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-20 11:54:13
