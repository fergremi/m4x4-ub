-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tfg
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.8-MariaDB

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
-- Table structure for table `alumno`
--

DROP TABLE IF EXISTS `alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alumno` (
  `nombre` varchar(16) NOT NULL,
  `apellidos` varchar(25) NOT NULL,
  `edad` int(11) NOT NULL,
  `curso actual` datetime NOT NULL,
  `curso futuro` datetime DEFAULT NULL,
  `subgrupo` varchar(10) NOT NULL,
  `optativas` varchar(45) NOT NULL,
  `socio_DNI` varchar(9) NOT NULL,
  PRIMARY KEY (`socio_DNI`),
  CONSTRAINT `fk_alumno_socio1` FOREIGN KEY (`socio_DNI`) REFERENCES `socio` (`DNI`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumno`
--

LOCK TABLES `alumno` WRITE;
/*!40000 ALTER TABLE `alumno` DISABLE KEYS */;
INSERT INTO `alumno` VALUES ('Arcadi','Aranda',10,'0000-00-00 00:00:00','0000-00-00 00:00:00','a','si','20034177G');
/*!40000 ALTER TABLE `alumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libro` (
  `ISBN` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `asignatura` varchar(45) NOT NULL,
  `curso` int(11) NOT NULL,
  `editorial` varchar(45) NOT NULL,
  `estado` int(11) NOT NULL,
  `año edicion` datetime NOT NULL,
  `año compra` datetime NOT NULL,
  PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lote`
--

DROP TABLE IF EXISTS `lote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lote` (
  `prestamo_id prestamo` int(11) NOT NULL,
  `prestamo_alumno_socio_DNI` varchar(9) NOT NULL,
  `libro_ISBN` int(11) NOT NULL,
  `estado` varchar(45) NOT NULL,
  PRIMARY KEY (`prestamo_id prestamo`,`prestamo_alumno_socio_DNI`,`libro_ISBN`),
  KEY `fk_prestamo_has_libro_libro1_idx` (`libro_ISBN`),
  KEY `fk_prestamo_has_libro_prestamo1_idx` (`prestamo_id prestamo`,`prestamo_alumno_socio_DNI`),
  CONSTRAINT `fk_prestamo_has_libro_libro1` FOREIGN KEY (`libro_ISBN`) REFERENCES `libro` (`ISBN`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_prestamo_has_libro_prestamo1` FOREIGN KEY (`prestamo_id prestamo`, `prestamo_alumno_socio_DNI`) REFERENCES `prestamo` (`id prestamo`, `alumno_socio_DNI`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lote`
--

LOCK TABLES `lote` WRITE;
/*!40000 ALTER TABLE `lote` DISABLE KEYS */;
/*!40000 ALTER TABLE `lote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prestamo` (
  `id prestamo` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `tipo lote` varchar(45) NOT NULL,
  `pagado` varchar(45) DEFAULT NULL,
  `alumno_socio_DNI` varchar(9) NOT NULL,
  PRIMARY KEY (`id prestamo`,`alumno_socio_DNI`),
  KEY `fk_prestamo_alumno1_idx` (`alumno_socio_DNI`),
  CONSTRAINT `fk_prestamo_alumno1` FOREIGN KEY (`alumno_socio_DNI`) REFERENCES `alumno` (`socio_DNI`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
/*!40000 ALTER TABLE `prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socio`
--

DROP TABLE IF EXISTS `socio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socio` (
  `DNI` varchar(9) NOT NULL,
  `nombre` varchar(16) NOT NULL,
  `apellidos` varchar(25) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telefono` int(11) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `contraseña` varchar(45) NOT NULL,
  PRIMARY KEY (`DNI`),
  UNIQUE KEY `telefono_UNIQUE` (`telefono`),
  UNIQUE KEY `DNI_UNIQUE` (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socio`
--

LOCK TABLES `socio` WRITE;
/*!40000 ALTER TABLE `socio` DISABLE KEYS */;
INSERT INTO `socio` VALUES ('20034177G','Arcadi','Aranda Torres','arato@hotmail.com',636129281,'c/Ausias march n2','1331');
/*!40000 ALTER TABLE `socio` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-21 21:46:46
