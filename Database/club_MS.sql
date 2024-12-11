-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: club_ms
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `activity_id` int NOT NULL AUTO_INCREMENT COMMENT '社团活动ID',
  `name` varchar(20) DEFAULT NULL COMMENT '活动名称',
  `club_id` int DEFAULT NULL COMMENT '所属社团',
  `tel` varchar(200) DEFAULT NULL COMMENT '联系电话',
  `images` varchar(200) DEFAULT NULL COMMENT '宣传图片',
  `ac_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '活动介绍',
  `ac_time` datetime DEFAULT NULL COMMENT '活动时间',
  `ac_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '活动地址',
  `user_id` int DEFAULT NULL COMMENT '主持人',
  `create_time` datetime DEFAULT NULL COMMENT '发起时间',
  `host_id` int DEFAULT NULL COMMENT '发起人',
  `status` int DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`activity_id`),
  KEY `activity_club_fk` (`club_id`),
  KEY `activity_user_fk` (`user_id`),
  KEY `activity_user_fk_1` (`host_id`),
  CONSTRAINT `activity_club_fk` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`),
  CONSTRAINT `activity_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `activity_user_fk_1` FOREIGN KEY (`host_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='社团活动表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (2,'足球比赛',1,'12345678',NULL,'足球都来了','2022-09-01 21:25:52','足球场',1,'2022-08-08 21:26:16',1,1),(4,'阅读启蒙',2,'321321','http://localhost:9999//images/f304a663-a571-4cdc-a327-4b45324df934.png','　　为丰富校园文化内容，给广大社员们一个展示自我的平台，同时也展现学生采风的成果，显现我社在美学与文学方面的成绩，通过学生作品的形式促进各方面的交流。','2022-08-09 08:00:00','2132312',4,'2022-08-08 22:01:30',1,1),(5,'今日文学',2,'321321321',NULL,'学哥学姐： 是你们亲自接我们入校，又带领我们融入了这个大家庭，三年里你们留下的回忆会继续伴我们成长，希望你们一路走好，创造自己美好的未来','2022-08-09 20:21:12','小广场',4,'2022-08-09 20:21:26',1,1),(6,'风采归来',2,'13212344321','http://localhost:9999//images/9bc85d56-4a27-4fa4-b5bf-79f7a3e4461f.png','　　乐于展示，敢于展示。','2022-08-31 08:00:00','多媒体教室',4,'2022-08-21 11:24:09',1,0);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_status`
--

DROP TABLE IF EXISTS `admin_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_status` (
  `admin_id` int NOT NULL COMMENT '管理员ID',
  `status` int DEFAULT NULL COMMENT '管理员状态',
  PRIMARY KEY (`admin_id`),
  CONSTRAINT `admin_status_user_fk` FOREIGN KEY (`admin_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_status`
--

LOCK TABLES `admin_status` WRITE;
/*!40000 ALTER TABLE `admin_status` DISABLE KEYS */;
INSERT INTO `admin_status` VALUES (1,1),(4,1),(6,1),(7,1),(8,1),(9,1),(10,1);
/*!40000 ALTER TABLE `admin_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apply_info`
--

DROP TABLE IF EXISTS `apply_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply_info` (
  `apply_info_id` int NOT NULL AUTO_INCREMENT COMMENT '入团申请ID',
  `user_id` int DEFAULT NULL COMMENT '申请人id',
  `content` varchar(200) DEFAULT NULL COMMENT '申请内容',
  `create_time` datetime DEFAULT NULL COMMENT '申请时间',
  `status` int DEFAULT NULL COMMENT '审核状态',
  `club_id` int DEFAULT NULL COMMENT '社团id',
  PRIMARY KEY (`apply_info_id`),
  KEY `apply_info_user_fk` (`user_id`),
  KEY `apply_info_club_FK` (`club_id`),
  CONSTRAINT `apply_info_club_FK` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`),
  CONSTRAINT `apply_info_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='社团入团申请记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply_info`
--

LOCK TABLES `apply_info` WRITE;
/*!40000 ALTER TABLE `apply_info` DISABLE KEYS */;
INSERT INTO `apply_info` VALUES (1,1,'我想入团','2024-08-01 20:33:32',0,1);
/*!40000 ALTER TABLE `apply_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apply_list`
--

DROP TABLE IF EXISTS `apply_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply_list` (
  `apply_list_id` int NOT NULL AUTO_INCREMENT COMMENT '申请审核ID',
  `apply_info_id` int DEFAULT NULL COMMENT '申请id',
  `content` varchar(300) DEFAULT NULL COMMENT '审核内容',
  `author_id` int DEFAULT NULL COMMENT '审核人',
  PRIMARY KEY (`apply_list_id`),
  KEY `apply_list_user_FK` (`author_id`),
  KEY `apply_list_apply_info_FK` (`apply_info_id`),
  CONSTRAINT `apply_list_apply_info_FK` FOREIGN KEY (`apply_info_id`) REFERENCES `apply_info` (`apply_info_id`),
  CONSTRAINT `apply_list_user_FK` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='申请审核记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply_list`
--

LOCK TABLES `apply_list` WRITE;
/*!40000 ALTER TABLE `apply_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `apply_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '目录ID',
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='社团分类表;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'汉服社','从名字当中我们就能看出来，汉服社跟汉服有关。汉服社主要是弘扬跟继承中国传统文化。让大学生通过穿汉服，体验古人的生活方式，了解汉服的起源与发展。'),(2,' 文学社','文学社是文化气息非常浓厚的社团，爱好文学、喜欢咬文嚼字的学生，一定要参加文学社。在文学社当中，我们不仅可以结交喜爱文学的同学，还能提高自己的文化素养'),(7,'摄影社','摄影社是爱好摄影同学的集结地。对于喜欢摄影，喜欢拍照的学生，一定要参加学校的摄影社。但是摄影社的同学一般每人一部相机，一部好的相机价值不菲，同学们要做好经济之间的平衡'),(8,'动漫社','动漫社是一个好玩又有趣的社团。动漫社会不定期地举办一些cosplay活动。通过角色扮演，大学生可以感受动漫人物的快乐'),(9,'武术社','武术社是大学当中比较吃香的社团。每个学校几乎都有武术社，对于喜欢武术的学生，可以参加学校的武术社。这样不仅可以学习武术，还能强身健体'),(10,' 轮滑社','轮滑社是大学当中比较拉风的社团。轮滑社的学生各个身怀绝技，穿着轮滑鞋，穿梭在人群中。对于喜欢轮滑，想要学习轮滑的学生，可以加入大学当中的轮滑社');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club`
--

DROP TABLE IF EXISTS `club`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club` (
  `club_id` int NOT NULL AUTO_INCREMENT COMMENT '社团ID',
  `club_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `create_by` int DEFAULT NULL COMMENT '创建人',
  `category_id` int DEFAULT NULL COMMENT '社团类型',
  `captain_id` int DEFAULT NULL COMMENT '团长',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int DEFAULT NULL COMMENT '状态',
  `images` varchar(200) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`club_id`),
  KEY `club_category_fk` (`category_id`),
  KEY `club_user_fk` (`captain_id`),
  KEY `club_user_create_FK` (`create_by`),
  CONSTRAINT `club_category_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `club_user_create_FK` FOREIGN KEY (`create_by`) REFERENCES `user` (`user_id`),
  CONSTRAINT `club_user_fk` FOREIGN KEY (`captain_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='社团信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club`
--

LOCK TABLES `club` WRITE;
/*!40000 ALTER TABLE `club` DISABLE KEYS */;
INSERT INTO `club` VALUES (1,'汕头文学社',1,2,7,'2024-07-11 15:28:03',1,'http://localhost:9999//images/wenxue.png'),(2,'汕头汉服社',1,1,4,'2024-07-12 21:34:58',1,'http://localhost:9999//images/hanfu.png'),(4,'汕头工作室',1,2,8,'2024-07-12 22:41:14',1,'http://localhost:9999//images/wenxue.png'),(5,'汕头摄影社',1,7,9,'2024-07-12 22:46:21',1,'http://localhost:9999//images/sheying.png'),(6,'汕头滑轮社',1,10,10,'2024-07-22 08:00:00',1,'http://localhost:9999//images/lunhua.png'),(7,'汕头武术社',1,9,10,'2024-07-13 22:46:52',0,'http://localhost:9999//images/wushu.png'),(11,'汕头动漫社',1,8,6,'2024-07-28 20:56:57',1,'http://localhost:9999//images/dongman.png'),(12,'汕头美好时光',1,7,9,'2024-08-31 08:00:00',0,'http://localhost:9999//images/5cdf0d1b-4ac7-46a3-9e8e-68226e32a235.png');
/*!40000 ALTER TABLE `club` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club_member`
--

DROP TABLE IF EXISTS `club_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club_member` (
  `club_member_id` int NOT NULL AUTO_INCREMENT COMMENT '社团成员记录ID',
  `user_id` int DEFAULT NULL COMMENT '社团成员ID',
  `join_time` datetime DEFAULT NULL COMMENT '入团时间',
  `club_id` int DEFAULT NULL COMMENT '所属社团',
  PRIMARY KEY (`club_member_id`),
  KEY `club_member_user_fk` (`user_id`),
  KEY `club_member_club_fk` (`club_id`),
  CONSTRAINT `club_member_club_fk` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`),
  CONSTRAINT `club_member_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='社团成员管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club_member`
--

LOCK TABLES `club_member` WRITE;
/*!40000 ALTER TABLE `club_member` DISABLE KEYS */;
INSERT INTO `club_member` VALUES (1,1,'2024-08-10 21:35:15',1);
/*!40000 ALTER TABLE `club_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cost_list`
--

DROP TABLE IF EXISTS `cost_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cost_list` (
  `cost_list_id` int NOT NULL AUTO_INCREMENT COMMENT '社团费用ID',
  `club_id` int DEFAULT NULL COMMENT '所属社团',
  `name` varchar(20) DEFAULT NULL COMMENT '费用名称',
  `price` int DEFAULT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`cost_list_id`),
  KEY `cost_list_club_fk` (`club_id`),
  CONSTRAINT `cost_list_club_fk` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='社团费用记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost_list`
--

LOCK TABLES `cost_list` WRITE;
/*!40000 ALTER TABLE `cost_list` DISABLE KEYS */;
INSERT INTO `cost_list` VALUES (1,11,'买礼物',200,'2022-08-09 21:00:18','给参加猜谜语活动的学生准备礼物'),(2,11,'测试费用1',100,'2022-08-09 21:55:53','测试成功了么1'),(3,11,'2121',212121,'2022-08-09 21:59:49','212121'),(4,2,'种子书友会',1000,'2022-08-21 11:28:19','买书分发'),(5,2,'阅读启蒙送爱心',300,'2022-08-21 11:28:44','发送小奖品');
/*!40000 ALTER TABLE `cost_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_info`
--

DROP TABLE IF EXISTS `leave_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leave_info` (
  `leave_info_id` int NOT NULL AUTO_INCREMENT COMMENT '在线留言ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `club_id` int DEFAULT NULL COMMENT '社团ID',
  `content` varchar(120) DEFAULT NULL COMMENT '留言内容',
  `create_time` datetime DEFAULT NULL COMMENT '留言时间',
  PRIMARY KEY (`leave_info_id`),
  KEY `leave_info_user_FK` (`user_id`),
  KEY `leave_info_club_FK` (`club_id`),
  CONSTRAINT `leave_info_club_FK` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`),
  CONSTRAINT `leave_info_user_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='在线留言表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_info`
--

LOCK TABLES `leave_info` WRITE;
/*!40000 ALTER TABLE `leave_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `leave_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告信息ID',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `title` varchar(200) DEFAULT NULL COMMENT '公告标题',
  `user_id` int DEFAULT NULL COMMENT '发布人',
  `create_time` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`notice_id`),
  KEY `notice_user_FK` (`user_id`),
  CONSTRAINT `notice_user_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `password` varchar(25) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(120) DEFAULT NULL COMMENT '真实姓名',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `tel` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(30) DEFAULT NULL COMMENT 'QQ',
  `wx` varchar(30) DEFAULT NULL COMMENT '微信',
  `image` varchar(255) DEFAULT NULL COMMENT '头像',
  `type` int DEFAULT NULL COMMENT '用户类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','123456','张三','女','13912345432','zhangsan@126.com','13912345432','13912345432',NULL,2,'2024-08-10 21:35:15'),(4,'kevin','123456','lisi','男','13712345678','lisi@163.com','13912345432','13912345432',NULL,1,'2024-08-10 21:35:15'),(6,'kappy','123456','占山','女','13812345432','zhangsan@126.com','13912345432','13912345432',NULL,1,'2024-08-10 21:35:15'),(7,'lily','123456','丽丽','女','13456789876','lili@163.com','13912345432','13912345432',NULL,0,'2024-08-10 21:35:15'),(8,'wangxiaoming','123456','王小明','男','13212349876','wangxiaoming@126.com','13912345432','13912345432',NULL,0,'2024-08-10 21:35:15'),(9,'xiaozhan','123456','肖战','男','13567896543','xiaozhan@126.com','13912345432','13912345432',NULL,0,'2024-08-10 21:35:15'),(10,'chengyi','123456','成毅','男','13212344321','chegyi@163.com','13912345432','13912345432',NULL,0,'2024-08-10 21:35:15');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'club_ms'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-11 22:50:16
