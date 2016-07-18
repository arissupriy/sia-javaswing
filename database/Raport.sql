CREATE DATABASE  IF NOT EXISTS `raport` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `raport`;
-- MySQL dump 10.13  Distrib 5.5.49, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: raport
-- ------------------------------------------------------
-- Server version	5.5.49-0ubuntu0.14.04.1

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id_admin` varchar(10) NOT NULL,
  `pass_admin` varchar(25) DEFAULT NULL,
  `tingkat_admin` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin','admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `d_siswa`
--

DROP TABLE IF EXISTS `d_siswa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `d_siswa` (
  `nis` varchar(8) NOT NULL,
  `nm_siswa` varchar(25) DEFAULT NULL,
  `alamat` varchar(20) DEFAULT NULL,
  `tempat_lahir` varchar(15) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `agama` varchar(10) DEFAULT NULL,
  `jenis_kelamin` enum('l','p') DEFAULT NULL,
  `telp` varchar(12) DEFAULT NULL,
  `tahun_angkatan` int(11) DEFAULT NULL,
  `kd_kelas` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`nis`),
  KEY `fk_d_siswa_kelas_idx` (`kd_kelas`),
  CONSTRAINT `fk_d_siswa_kelas` FOREIGN KEY (`kd_kelas`) REFERENCES `tb_kelas` (`kd_kelas`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `d_siswa`
--

LOCK TABLES `d_siswa` WRITE;
/*!40000 ALTER TABLE `d_siswa` DISABLE KEYS */;
INSERT INTO `d_siswa` VALUES ('IA000001','Abdul Muchit','Lasem, Rembang','Rembang','1995-10-05','Islam','l','085711289089',2013,'IA'),('IA000002','Abdul Zen','Sluke, Rembang','Rembang','1996-01-01','Islam','l','085789889878',2013,'IA'),('IA000003','Ahmad Abdur','Sedan, Rembang','Rembang','1996-01-05','Islam','l','087567456234',2013,'IA'),('IA000004','AHMAD ARI S','PAMOTAN, REMBANG','Rembang','1996-05-01','ISLAM','l','089765432431',2013,'IA'),('IA000005','ABDURROHMAN M','REMBANG','Rembang','1996-02-02','ISLAM','l','089098890098',2013,'IA'),('IA000006','AMAL SETIAWAN SE','ACEH','Aceh','1997-09-12','ISLAM','l','089765678765',2013,'IA'),('IA000007','ILHAM ABDDD','SARANG','Rembang','1995-01-15','ISLAM','l','087678766543',2013,'IA');
/*!40000 ALTER TABLE `d_siswa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_guru`
--

DROP TABLE IF EXISTS `tb_guru`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_guru` (
  `nip` varchar(10) NOT NULL,
  `nm_guru` varchar(30) DEFAULT NULL,
  `jenis_kelamin` varchar(1) DEFAULT NULL,
  `telp` varchar(12) DEFAULT NULL,
  `alamat` varchar(45) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  PRIMARY KEY (`nip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_guru`
--

LOCK TABLES `tb_guru` WRITE;
/*!40000 ALTER TABLE `tb_guru` DISABLE KEYS */;
INSERT INTO `tb_guru` VALUES ('G001','Bagyo','L','2147483647','Rembang','1987-10-10'),('G002','BAMBANG S','L','085713776775','REMBANG','1986-08-03'),('G003','Siti','P','089789678567','Rembang','1976-04-13'),('G004','FEBRIyanto','L','089789789789','INDERAMAYU','1978-03-15'),('G005','ABDULLAH LUTHFI','L','085713456789','REMBANG','1978-03-15');
/*!40000 ALTER TABLE `tb_guru` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_kelas`
--

DROP TABLE IF EXISTS `tb_kelas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_kelas` (
  `kd_kelas` varchar(10) NOT NULL,
  `nm_kelas` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`kd_kelas`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_kelas`
--

LOCK TABLES `tb_kelas` WRITE;
/*!40000 ALTER TABLE `tb_kelas` DISABLE KEYS */;
INSERT INTO `tb_kelas` VALUES ('IA','IPA'),('IS','IPS');
/*!40000 ALTER TABLE `tb_kelas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_keluarga`
--

DROP TABLE IF EXISTS `tb_keluarga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_keluarga` (
  `nis` varchar(10) NOT NULL,
  `status_` varchar(5) DEFAULT NULL,
  `anak_ke` int(11) DEFAULT NULL,
  `nm_ayah` varchar(30) DEFAULT NULL,
  `kerja_ayah` varchar(20) DEFAULT NULL,
  `nm_ibu` varchar(30) DEFAULT NULL,
  `kerja_ibu` varchar(20) DEFAULT NULL,
  `alamat_ortu` varchar(30) DEFAULT NULL,
  `telp_ortu` int(11) DEFAULT NULL,
  `nama_wali` varchar(30) DEFAULT NULL,
  `kerja_wali` varchar(20) DEFAULT NULL,
  `telp_wali` int(11) DEFAULT NULL,
  KEY `fk_tb_keluarga_nis_idx` (`nis`),
  CONSTRAINT `fk_tb_keluarga_nis` FOREIGN KEY (`nis`) REFERENCES `d_siswa` (`nis`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_keluarga`
--

LOCK TABLES `tb_keluarga` WRITE;
/*!40000 ALTER TABLE `tb_keluarga` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_keluarga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_mapel`
--

DROP TABLE IF EXISTS `tb_mapel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mapel` (
  `kd_mapel` varchar(10) NOT NULL,
  `nm_mapel` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`kd_mapel`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_mapel`
--

LOCK TABLES `tb_mapel` WRITE;
/*!40000 ALTER TABLE `tb_mapel` DISABLE KEYS */;
INSERT INTO `tb_mapel` VALUES ('MPBIO','BIOLOGI'),('MPFIS','FISIKA'),('MPINDO','BAHASA INDONESIA'),('MPING','BAHASA INGGRIS'),('MPKIMIA','KIMIA'),('MPMAT','MATEMATIKA');
/*!40000 ALTER TABLE `tb_mapel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_pengampu`
--

DROP TABLE IF EXISTS `tb_pengampu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_pengampu` (
  `kd_kelas` varchar(10) DEFAULT NULL,
  `kd_mapel` varchar(10) DEFAULT NULL,
  `kd_guru` varchar(10) DEFAULT NULL,
  KEY `fk_tb_pengampu_nip_idx` (`kd_guru`),
  KEY `fk_tb_pengampu_kelas_idx` (`kd_kelas`),
  KEY `fk_tb_pengampu_mapel_idx` (`kd_mapel`),
  CONSTRAINT `fk_tb_pengampu_kelas` FOREIGN KEY (`kd_kelas`) REFERENCES `tb_kelas` (`kd_kelas`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_pengampu_mapel` FOREIGN KEY (`kd_mapel`) REFERENCES `tb_mapel` (`kd_mapel`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_pengampu_nip` FOREIGN KEY (`kd_guru`) REFERENCES `tb_guru` (`nip`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_pengampu`
--

LOCK TABLES `tb_pengampu` WRITE;
/*!40000 ALTER TABLE `tb_pengampu` DISABLE KEYS */;
INSERT INTO `tb_pengampu` VALUES ('IA','MPFIS','G001'),('IA','MPBIO','G002'),('IA','MPBIO','G003'),('IA','MPBIO','G004'),('IA','MPING','G005');
/*!40000 ALTER TABLE `tb_pengampu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_rapor`
--

DROP TABLE IF EXISTS `tb_rapor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_rapor` (
  `kd_mapel` varchar(10) DEFAULT NULL,
  `nip` varchar(10) DEFAULT NULL,
  `nis` varchar(10) DEFAULT NULL,
  `tugas` float DEFAULT NULL,
  `ulangan` float DEFAULT NULL,
  `uts` float DEFAULT NULL,
  `uas` float DEFAULT NULL,
  KEY `fk_tb_rapor_mapel_idx` (`kd_mapel`),
  KEY `fk_tb_rapor_nis_idx` (`nis`),
  KEY `fk_tb_rapor_nip_idx` (`nip`),
  CONSTRAINT `fk_tb_rapor_mapel` FOREIGN KEY (`kd_mapel`) REFERENCES `tb_mapel` (`kd_mapel`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_rapor_nip` FOREIGN KEY (`nip`) REFERENCES `tb_guru` (`nip`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_rapor_nis` FOREIGN KEY (`nis`) REFERENCES `d_siswa` (`nis`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_rapor`
--

LOCK TABLES `tb_rapor` WRITE;
/*!40000 ALTER TABLE `tb_rapor` DISABLE KEYS */;
INSERT INTO `tb_rapor` VALUES ('MPFIS','G001','IA000001',80,69,87,77),('MPBIO','g002','IA000001',90,90,90,90),('MPING','g005','IA000001',80,80,80,80),('MPBIO','g002','IA000002',779,86,88,99);
/*!40000 ALTER TABLE `tb_rapor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ruangkelas`
--

DROP TABLE IF EXISTS `tb_ruangkelas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_ruangkelas` (
  `kd_rkelas` varchar(10) NOT NULL,
  `nm_kelas` varchar(10) DEFAULT NULL,
  `tampung` int(11) DEFAULT NULL,
  PRIMARY KEY (`kd_rkelas`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ruangkelas`
--

LOCK TABLES `tb_ruangkelas` WRITE;
/*!40000 ALTER TABLE `tb_ruangkelas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_ruangkelas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_walikelas`
--

DROP TABLE IF EXISTS `tb_walikelas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_walikelas` (
  `kd_walikelas` varchar(10) NOT NULL,
  `nip` varchar(10) DEFAULT NULL,
  `kd_kelas` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`kd_walikelas`),
  KEY `fk_tb_walikelas_nip_idx` (`nip`),
  CONSTRAINT `fk_tb_walikelas_nip` FOREIGN KEY (`nip`) REFERENCES `tb_guru` (`nip`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_walikelas`
--

LOCK TABLES `tb_walikelas` WRITE;
/*!40000 ALTER TABLE `tb_walikelas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_walikelas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` varchar(10) NOT NULL,
  `nis` varchar(10) DEFAULT NULL,
  `pass` varchar(25) DEFAULT NULL,
  `tingkat` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  KEY `fk_user_nis_idx` (`nis`),
  CONSTRAINT `fk_user_nis` FOREIGN KEY (`nis`) REFERENCES `d_siswa` (`nis`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('IA000001','IA000001','IA000001','siswa'),('IA000002','IA000002','IA000002','siswa'),('IA000003','IA000003','IA000003','siswa'),('IA000004','IA000004','IA000004','siswa'),('IA000005','IA000005','IA000005','siswa'),('IA000006','IA000006','IA000006','siswa'),('IA000007','IA000007','IA000007','siswa');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usr_guru`
--

DROP TABLE IF EXISTS `usr_guru`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usr_guru` (
  `id_user` varchar(10) NOT NULL,
  `nip` varchar(10) DEFAULT NULL,
  `pass` varchar(25) DEFAULT NULL,
  `tingkat` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  KEY `fk_usr_guru_nip_idx` (`nip`),
  CONSTRAINT `fk_usr_guru_nip` FOREIGN KEY (`nip`) REFERENCES `tb_guru` (`nip`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usr_guru`
--

LOCK TABLES `usr_guru` WRITE;
/*!40000 ALTER TABLE `usr_guru` DISABLE KEYS */;
INSERT INTO `usr_guru` VALUES ('G001','G001','G001','Guru'),('G002','G002','G002','Guru'),('G003','G003','G003','Guru'),('G004','G004','G004','Guru'),('G005','G005','G005','Guru');
/*!40000 ALTER TABLE `usr_guru` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-19  9:28:25
