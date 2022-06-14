/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.31 : Database - Parking
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`Parking` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `Parking`;

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `target` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `content` longtext,
  `title` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `notice` */

insert  into `notice`(`id`,`target`,`create_time`,`content`,`title`) values 
(1,'parklot','2021-06-10 13:05:41','如果停车场不遵守规则，将会给与相应处罚！','停车场'),
(2,'parklot','2021-06-10 13:09:31','停车场的信息可以查看出入记录！','重要通知'),
(3,'all','2021-06-10 13:10:07','欢迎使用PsrkingSys。期待大家的反馈！','欢迎'),
(4,'parklot','2021-06-10 13:11:35','新增停车场！','重要通知'),
(5,'client','2021-06-10 14:11:08','用户可以举报违规停车场！','重要通知'),
(6,'client','2021-06-10 14:11:59','用户注意遵守规定！','重要通知'),
(7,'all','2021-06-10 14:13:00','请各位遵守相关规定！','通知'),
(8,'parklot','2021-06-15 11:45:57','要注意疫情防护，一定要检测体温。','停车场注意'),
(9,'parklot','2021-06-15 15:35:01','每个停车场都需设置临时隔离点。','注意'),
(10,'all','2021-06-16 10:19:59','今天有暴雨','公告'),
(11,'parklot','2021-06-23 09:05:39','今天注意高温','停车场注意'),
(12,'all','2021-06-23 10:48:46','   在数据库中，事务在并发调度过程中，会产生多种结果，什么样的调度是正确的？只有串行调度才是正确的结果。并发过程的结果只有与串行调度结果一样的才是正确的。这种并发调度被称为可串行化调度。\n\n    可串行化是并发事务正确调度的基本准则。对于一个并发调度，当且仅当它是可串行化的时候，才被认为是正确调度。\n\n    本文主要讲解判断可串行化调度的充要条件。','关于6月22日的补偿通知'),
(13,'client','2021-06-24 13:05:52','注意停车安全。','所有客户注意');

/*Table structure for table `parklot` */

DROP TABLE IF EXISTS `parklot`;

CREATE TABLE `parklot` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `park_name` varchar(50) DEFAULT NULL,
  `is_black` int(11) DEFAULT '0',
  `token` varchar(50) DEFAULT NULL,
  `price` int(20) DEFAULT NULL,
  `total_num` int(20) DEFAULT NULL,
  `occupy_num` int(20) DEFAULT '0',
  `manager_id` int(20) DEFAULT '0',
  `park_credit` int(20) DEFAULT '100',
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `parklot` */

insert  into `parklot`(`id`,`park_name`,`is_black`,`token`,`price`,`total_num`,`occupy_num`,`manager_id`,`park_credit`,`longitude`,`latitude`) values 
(3,'江安停车场',0,'13465714',300,300,7,13,100,100,50),
(4,'text',0,'1234',123,23,0,0,100,101,51),
(5,'tex1t',0,'12341',123,23,0,0,100,102,52),
(6,'tex21t',0,'123412',123,23,0,0,100,103,53),
(7,'41',0,'1234',123,1234,0,0,100,104,54),
(8,'test3',0,'d21211',111,1111,0,0,100,105,55),
(9,'tex3',0,'d2111',213,1234,0,9,100,106,56),
(10,'江安停车场5',1,'d1234',123,1234,0,14,100,107,57),
(11,'21313',0,'d1',12,12,0,0,100,99,58),
(12,'江安停车场6',0,'123',2311,12,0,0,100,98,59),
(13,'test4',0,'8889',123,123,0,0,100,97,60),
(14,'望江停车场12',0,'121',123,1234,0,0,100,96,61),
(15,'望江停车场3',0,'1234',123,1234,0,0,100,95,62),
(16,'望江停车场9',0,'22123',3,1,0,0,100,94.578,63.124),
(19,'tylone_test',0,'123456',100,500,0,0,100,104,30.5),
(20,'体育馆停车场',0,'111111',20,200,1,0,100,104.012988,30.564634),
(21,'江安停车场1',0,'1111',12,223,1,16,100,123,456),
(22,'sgh停车场',0,'1111',12,223,0,0,100,13,11),
(23,'ghs停车场',0,'1111',12,223,0,0,100,13,11),
(24,'西南交大停车场',0,'998',112,111,0,0,100,104.03399529962529,30.719665476653596),
(25,'电科停车场',0,'996',113,332,1,0,100,103.94031550951723,30.752703995202232),
(26,'文星停车场',0,'122112',12,200,0,21,100,104.08382430626376,30.63012663946242),
(27,'1',0,'1',1,1,0,0,100,104.082225322456,30.631016150065886);

/*Table structure for table `record` */

DROP TABLE IF EXISTS `record`;

CREATE TABLE `record` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `client_name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `parklot_name` varchar(50) DEFAULT NULL,
  `place_no` varchar(50) DEFAULT NULL,
  `arrive_time` datetime DEFAULT NULL,
  `leave_time` datetime DEFAULT NULL,
  `status` int(20) DEFAULT NULL,
  `reserve_token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

/*Data for the table `record` */

insert  into `record`(`id`,`client_name`,`create_time`,`parklot_name`,`place_no`,`arrive_time`,`leave_time`,`status`,`reserve_token`) values 
(1,'tylone','2021-06-06 12:41:44','江安停车场','100','2021-06-06 19:30:57','2021-06-06 20:42:30',2,'jalsdjlasfjla'),
(2,'pure','2021-06-07 21:02:33','江安停车场','12323','2021-06-07 13:37:22','2021-06-09 13:37:22',3,'63f2168a-b48b-4c33-95d7-897a2bf3c425'),
(4,'pure','2021-06-07 21:02:59','江安停车场','12323','2021-06-05 13:37:22','2021-06-06 13:37:22',3,'5d68870f-1d11-451c-8500-33d3ebc47bdd'),
(5,'pure','2021-06-07 21:03:04','江安停车场','12323','2021-06-04 13:37:22','2021-06-06 13:37:22',3,'0dafdbe9-90ed-47a1-a49e-db85cec79750'),
(10,'pure','2021-06-07 21:23:19','江安停车场','12323','2021-06-08 13:37:22','2021-06-15 13:37:22',3,'0de4377c-6e32-4da5-89a7-7e12fe9c1587'),
(12,'man','2021-06-08 18:43:45','体育馆停车场','川B 87676','2021-06-08 18:47:30','2021-06-08 18:48:43',2,'2619eb9a-ca3f-4d3e-ba30-a16e044f4d34'),
(13,'man','2021-06-08 21:49:35','体育馆停车场','川B 87676','2021-06-09 18:00:00','2021-06-09 20:00:00',3,'d5ff11e4-3490-4675-a0c4-1850bcaab069'),
(14,'man','2021-06-08 21:50:05','体育馆停车场','川A H7676','2021-06-09 19:00:00','2021-06-09 20:00:00',3,'f0d28469-d9ac-455f-965c-a2c978e6bdbc'),
(15,'man','2021-06-08 21:55:58','体育馆停车场','川B36273','2021-06-09 19:00:00','2021-06-09 20:00:00',3,'852524d7-32bd-431a-bf8d-66b3043315ef'),
(16,'man','2021-06-09 08:48:00','体育馆停车场','川B36273','2021-06-09 19:00:00','2021-06-09 20:00:00',3,'3fd7ecaf-e97c-461e-a5b8-20d1e9b4fc0b'),
(17,'man','2021-06-09 08:52:19','体育馆停车场','川A BH768','2021-06-09 19:00:00','2021-06-09 20:00:00',3,'d81b0653-6c6a-4027-8935-2d28b32e3c59'),
(18,'man','2021-06-09 08:54:00','体育馆停车场','川A HE172','2021-06-09 09:25:22','2021-06-09 20:00:00',2,'34067ab2-6ec9-40a0-be8b-b30656d501b7'),
(19,'man','2021-06-09 09:00:36','体育馆停车场','川A 28181','2021-06-09 09:13:24','2021-06-09 20:00:00',2,'c502cc79-f483-4681-99e2-e6fad6ae54db'),
(20,'man','2021-06-10 17:13:34','体育馆停车场','川A 98786','2021-06-09 19:00:00','2021-06-10 17:14:39',2,'6272bbed-2dab-4c5c-b28a-7ea007e8974e'),
(21,'man','2021-06-10 17:22:22','体育馆停车场','川A EH167','2021-06-09 19:00:00','2021-06-09 20:00:00',3,'25f280ec-0e9c-4151-928d-5c3fc9d240fb'),
(22,'man','2021-06-10 17:35:22','体育馆停车场','川A 128U8','2021-06-09 19:00:00','2021-06-09 20:00:00',3,'554be5d0-2de4-4c38-8214-7f5df102f782'),
(23,'man','2021-06-10 17:39:36','体育馆停车场','川A 17283','2021-06-10 21:59:00','2021-06-10 23:59:00',3,'92acbb1b-a0a8-4355-acad-7a6783589b45'),
(24,'man','2021-06-10 17:40:26','体育馆停车场','川A 12878','2021-06-10 17:40:37','2021-06-10 17:40:46',2,'604feb8e-2723-4e6a-b82d-a0179fdb20b1'),
(25,'pure','2021-06-14 15:45:06','江安停车场','12323','2021-06-12 13:37:22','2021-06-17 13:37:22',3,'0e4e38de-0cdf-4ab8-8a6b-7e5f60075c8c'),
(26,'pure','2021-06-14 15:45:18','江安停车场','452','2021-06-12 13:37:22','2021-06-17 13:37:22',3,'d09dc5e4-875c-4e96-9a3f-f540a6c4d656'),
(28,'pure','2021-06-14 15:46:24','江安停车场','225','2021-06-17 12:37:22','2021-06-19 13:20:20',3,'04339ff1-18fe-4f1c-899f-86abf3f4f4db'),
(29,'pure','2021-06-14 15:47:36','体育馆停车场','198','2021-06-17 12:37:22','2021-06-19 13:20:20',3,'47b978fb-04b0-4707-92a2-03ec9af3d897'),
(32,'man','2021-06-15 14:11:23','体育馆停车场','川A 23721','2021-06-15 14:00:00','2021-06-15 18:00:00',3,'c37665b0-3e06-47ae-bfee-9ee34db954b8'),
(33,'man','2021-06-15 14:21:51','体育馆停车场','川A 12761','2021-06-15 14:37:44','2021-06-15 14:38:27',2,'eea8568b-eaeb-416a-a19c-aa1587193ccb'),
(35,'man','2021-06-16 10:05:38','体育馆停车场','川A 65654','2021-06-16 00:59:00','2021-06-16 23:59:00',3,'f1c0a623-a4b6-44a4-bb84-248540b8d17f'),
(36,'man','2021-06-16 10:07:10','体育馆停车场','川A 76T56','2021-06-16 00:59:00','2021-06-16 19:59:00',3,'630b017b-054e-424e-9fae-0d8bfb7d6e93'),
(37,'man','2021-06-16 10:09:59','体育馆停车场','川A 21321','2021-06-16 00:59:00','2021-06-16 15:59:00',3,'ee41b3d6-0e44-4097-a055-cb5aca3f4435'),
(38,'man','2021-06-16 10:12:48','体育馆停车场','川A TR534','2021-06-16 10:13:17','2021-06-16 10:13:27',2,'01523e4a-5d2e-422d-851f-aef29fb0db91'),
(39,'man','2021-06-16 10:15:11','体育馆停车场','川A EH652','2021-06-16 11:59:00','2021-06-16 18:59:00',3,'4ad668ad-7829-4c95-bdc6-9d0ed7b9c283'),
(40,'man','2021-06-17 10:32:28','体育馆停车场','川A 76564','2021-06-17 10:33:27','2021-06-17 23:59:00',2,'e1d0480d-9a21-4e94-93ab-1998778e39d3'),
(41,'man','2021-06-23 09:02:42','体育馆停车场','川A 65123','2021-06-23 09:03:56','2021-06-23 09:04:08',2,'4d44d954-f73c-4c47-8c9e-c66345c45226'),
(42,'man','2021-06-23 09:08:07','体育馆停车场','川A 16251','2021-06-23 09:09:27','2021-06-23 14:59:00',2,'ca2fcc3c-20dc-4750-90e8-f6f5c0ad31e3'),
(43,'man','2021-06-23 10:25:56','体育馆停车场','川C 1029Q','2021-06-23 01:59:00','2021-06-23 04:59:00',3,'43c5a3ce-4c70-4c70-a582-6741abf7ac76'),
(44,'man','2021-06-23 10:27:27','体育馆停车场','川S 88111','2021-06-23 10:33:26','2021-06-23 10:33:35',2,'a29c24b6-23c7-4b9c-9abc-1f867c81b81b'),
(45,'man','2021-06-23 10:32:45','体育馆停车场','川D QWER1','2021-06-23 21:59:00','2021-06-23 22:59:00',3,'ad534adc-a6c9-41e7-b2be-00daaec34472'),
(46,'man','2021-06-23 10:35:35','体育馆停车场','川E 12482','2021-06-23 20:00:00','2021-06-23 23:00:00',3,'5062d9d0-a295-45d9-a499-e6ebbc62a02d'),
(47,'man','2021-06-23 10:36:48','体育馆停车场','川A 1029Q','2021-06-23 07:00:00','2021-06-23 10:38:44',2,'e4d0c0cf-c34b-4c5b-bd5e-8debe73b1c4f'),
(48,'man','2021-06-23 10:39:53','体育馆停车场','川U 12Q98','2021-06-23 10:40:10','2021-06-23 10:40:29',2,'902db88b-16f9-45cc-9496-a9f0bd825365'),
(49,'man','2021-06-23 10:41:36','体育馆停车场','鲁D 1029Q','2021-06-23 07:01:00','2021-06-23 09:00:00',3,'1d30c286-0863-4526-99f6-d3bb6b3b8f31'),
(50,'man','2021-06-23 11:48:21','体育馆停车场','川A 76612','2021-06-23 11:00:00','2021-06-23 21:59:00',3,'b16b227e-63d9-4cb5-bc58-50915616830a'),
(51,'man','2021-06-24 13:08:21','体育馆停车场','川A RH126','2021-06-24 13:01:00','2021-06-24 16:01:00',3,'5f918065-8594-4e17-9d93-92bb93d4e7a0'),
(52,'man','2021-06-24 13:09:23','体育馆停车场','川A 12122','2021-06-24 13:10:30','2021-06-24 13:11:02',2,'aeccb4eb-5928-4b42-8f32-6cdc6565996e');

/*Table structure for table `response` */

DROP TABLE IF EXISTS `response`;

CREATE TABLE `response` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `content` longtext,
  `parklot_name` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `stars` int(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `response` */

insert  into `response`(`id`,`user_name`,`create_time`,`content`,`parklot_name`,`role`,`stars`) values 
(1,'pure1','2021-06-06 13:57:18','23','望江停车场3','complaint',0),
(2,'pure1','2021-06-06 13:58:15','12','望江停车场3','complaint',0),
(3,'pure1','2021-06-06 14:03:33','1123','望江停车场3','complaint',0),
(4,'pure1','2021-06-06 14:04:03','1123','望江停车场3','complaint',0),
(5,'pure1','2021-06-06 14:05:16','1','望江停车场3','complaint',0),
(6,'pure1','2021-06-06 14:10:02','121','望江停车场3','feedback',3),
(7,'pure','2021-06-09 16:37:57','太好了','江安停车场','feedback',5),
(8,'pure','2021-06-09 16:38:14','太好了太好了','江安停车场','feedback',5),
(9,'pure','2021-06-09 16:38:15','太好了太好了太好了','江安停车场','feedback',5),
(10,'pure','2021-06-09 16:38:43','太好太好太好了','江安停车场','feedback',1),
(11,'pure','2021-06-09 16:38:55','太太太好了','江安停车场','feedback',4),
(12,'pure1','2021-06-10 01:21:27','hao','电科停车场','complaint',0),
(13,'pure1','2021-06-10 01:24:36','1111','电科停车场','complaint',0),
(14,'pure1','2021-06-10 01:26:00','1111','电科停车场','complaint',0),
(15,'pure1','2021-06-10 01:29:45','111','电科停车场','feedback',4),
(16,'pure1','2021-06-10 12:43:53','1111','望江停车场3','complaint',0),
(17,'pure1','2021-06-10 12:44:48','1111','望江停车场3','feedback',4),
(18,'pure1','2021-06-10 12:51:33','111','望江停车场3','feedback',1),
(19,'Noah','2021-06-10 14:09:12','1111','电科停车场','complaint',0),
(20,'man','2021-06-15 11:11:17','什么垃圾停车场','text','complaint',0),
(21,'man','2021-06-15 11:18:53','挺好的吧。','体育馆停车场','feedback',4),
(22,'man','2021-06-15 11:18:53','挺好的吧。','体育馆停车场','feedback',4),
(23,'man','2021-06-15 11:39:37','五星好评','体育馆停车场','feedback',5),
(24,'man','2021-06-15 14:43:52','什么垃圾停车场，坑钱','text','complaint',0),
(25,'man','2021-06-15 14:46:30','江安停车场太好啦，','江安停车场','feedback',5),
(26,'man','2021-06-16 10:22:44','太棒了，就在江安里面','体育馆停车场','feedback',5),
(27,'man','2021-06-23 09:04:56','太好了','体育馆停车场','feedback',5),
(28,'man','2021-06-23 10:23:13','车位虚报','体育馆停车场','feedback',3),
(29,'man','2021-06-24 13:11:52','太好了','体育馆停车场','feedback',5);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT 'client',
  `email` varchar(50) DEFAULT NULL,
  `tel_no` varchar(50) DEFAULT NULL,
  `park_no` int(20) DEFAULT '0',
  `password` varchar(100) DEFAULT NULL,
  `vip_fee` int(20) DEFAULT '0',
  `credit` int(20) DEFAULT '100',
  `created` datetime DEFAULT NULL,
  `is_black` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`role`,`email`,`tel_no`,`park_no`,`password`,`vip_fee`,`credit`,`created`,`is_black`) values 
(1,'tylone','client','test@qq.com','1324657891',0,'$2a$10$lJH6f8JoHVzJikleoEOSdONqPhI9bGryWWAywTeB50Nqsf3Se36l2',0,100,'2021-06-06 11:13:36',0),
(2,'9090','client','888@qq.com','1111',0,'$2a$10$c/LZoPgy2F5mN914YlFDUObxlpiIligqgbQjkxVM5AO9eRSbcirRy',0,100,'2021-06-06 11:14:58',0),
(3,'90910','client','888@qq.com','1111',0,'$2a$10$xHlUeEQju8GGx1C.bTi68OW1mC/MIfndpVggIwCL3sR86SMzTzZRa',0,100,'2021-06-06 11:23:33',0),
(4,'90910','client','888@qq.com','1111',0,'$2a$10$gytwX0UJ/ibnVnFF3zj/SudGqLaxFZSOWIyHJmDcuoo5tAJoGm482',0,100,'2021-06-06 11:23:33',0),
(5,'909101','client','888@qq.com','1111',0,'$2a$10$koKQgRDSp7hPnjyjN8YJb.5Z5vmuWZfZzTX/pN.3XT5F6Bej1fsyG',0,100,'2021-06-06 12:20:08',0),
(6,'pure','client','299999@qq.com','123411',0,'$2a$10$OI7OqYGXsrMUnUmXlsCZo.Fn8gVGIJsn3TeBToygiwHfYKwj.5BQO',0,100,'2021-06-06 13:37:22',0),
(7,'pure1','client','11@qq.com','1111',0,'$2a$10$gf0RYU5VtqnkbVDVuNbHY.F1n6KDiolL.MyLpvPK.WrFQ5zfN/zTC',0,100,'2021-06-06 13:42:37',0),
(8,'aaa','client','2121@qq.com','12333',0,'$2a$10$iRCiZnepihvi/4Ygf/Bw0O8s6ApxKXwR8rchIVF7f9HmyztBoQIXi',0,100,'2021-06-06 16:38:40',0),
(9,'lucifer','manager','test@qq.com','123456',9,'$2a$10$2iKFrC/pwyBn/rcH/WTdQ.lIG5noGzU5BGsN5Y4113PLxp3GULh7K',0,100,'2021-06-07 08:53:38',0),
(10,'man','manager','uuy@163.com','1278372631',18,'$2a$10$tVMeNeBLiiFKZVLG5.bzye4rwIlTPieT6wCgfGtrHjQt9igsFx0Xi',0,100,'2021-06-07 16:35:38',0),
(11,'oooo','client','8ooo882@qq.com','asdg',0,'$2a$10$4jm1U/RvjBDohSXEA7itDOZZNQQudB35Wb4Ay7bdrAVqkQfdVfdy6',0,100,'2021-06-07 16:38:44',1),
(12,'2121333','client','2121@qq.com','15608190193',0,'$2a$10$hmaej9sqOsAadv2IKbwqWeJYZ.oyopvRaIDzydDgYUh2UT9QwMhTW',0,100,'2021-06-09 08:29:11',0),
(13,'last','manager','eqwerwr@qq.com','123456789',3,'$2a$10$dMfJtAADVzUs8J0uc1xYcO4vNT9oHVTfcQic1gswSd2mVHPEtF/CS',0,100,'2021-06-09 20:54:29',0),
(14,'qnjj','manager','12312@qq.com','15608190193',10,'$2a$10$DSbkFfTCrol0uFovolyfkupzi3SYiUvl9k8SJJIHkeSaiClte0NCi',0,100,'2021-06-09 23:39:29',0),
(15,'Noah','client','yfu2669@gmail.com','15608190193',0,'$2a$10$bh9F0GqlLS2rOwEjze08GeT6TQwI9rsl10kyeunO5Y8yBTb.mLUkm',0,100,'2021-06-10 00:47:31',0),
(16,'gly','manager','12312@qq.com','15608190193',21,'$2a$10$koRagzMhtUOqrECTQFuUQuMoBnW.e4YYc07o21wxhSDWAY.idk8Km',0,100,'2021-06-10 00:48:07',0),
(17,'pure4','client','23113@qq.com','222',0,'$2a$10$G0IXFWH1EoLRZsS2NG9DS.u3rwIiOqGIBTotBIgb8f0q86b39X4fW',0,100,'2021-06-10 13:27:11',0),
(18,'pure4','client','23113@qq.com','222',0,'$2a$10$W6vdoh/LFIHu4.E8KRi6RuY3HpEu5h46JmHQc4IiC6BPgQVWHf7mm',0,100,'2021-06-10 13:27:15',0),
(19,'pure4','client','23113@qq.com','222',0,'$2a$10$Ni2IZv5zfVjWtVzu/ySwWeRIgXpcR0BnB3ihjG20Ik8.V2FClfjj6',0,100,'2021-06-10 13:27:15',0),
(20,'pure.','client','920889180@qq.com','17883680628',0,'$2a$10$Am7njFJWcxGLwUF0WWc14OvbZNmuL4Q35CRiXXm9TF7t0Bd1GwZTG',0,100,'2021-06-15 13:47:51',0),
(21,'man.','manager','Shanshan7479@163.com','13281822772',26,'$2a$10$yJIvHV7pkCAqhiXES6c4FODemqIFkAHQ9Wi7gni4341pkwgN.juE2',0,100,'2021-06-15 15:24:40',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
