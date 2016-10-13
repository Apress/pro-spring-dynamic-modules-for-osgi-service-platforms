CREATE TABLE `HelloWorld` (
  `id` bigint(20) NOT NULL,
  `language` varchar(255) default NULL,
  `message` varchar(255) default NULL,
  `transdate` date default NULL,
  `translator_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `I_HLLWRLD_TRANSLATOR` (`translator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `HelloWorld` VALUES (51,'Italian','Ciao Monde!','2008-05-27',2),(52,'Spanish','Hola Mundo!','2008-05-27',1),(53,'English','Hello World!','2008-05-27',5),(54,'French','Bonjour Monde!','2008-05-27',3),(55,'German','Hallo Welt!','2008-05-27',4);

CREATE TABLE `OPENJPA_SEQUENCE_TABLE` (
  `ID` tinyint(4) NOT NULL,
  `SEQUENCE_VALUE` bigint(20) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `OPENJPA_SEQUENCE_TABLE` VALUES (0,101);

CREATE TABLE `Person` (
  `id` bigint(20) NOT NULL,
  `FNAME` varchar(255) default NULL,
  `hourlyRate` double default NULL,
  `LNAME` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `Person` VALUES (1,'Carlos',40,'Perez'),(2,'Dino',45,'Casiraghi'),(3,'Pierre',40,'LeClair'),(4,'Franz',45,'Becker'),(5,'John',45,'Smith');
