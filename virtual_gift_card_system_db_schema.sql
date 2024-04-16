-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 16, 2024 at 01:45 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `virtual_gift_card_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `bank`
--

CREATE TABLE `bank` (
  `bank_id` bigint(20) NOT NULL,
  `balance` bigint(20) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bank`
--

INSERT INTO `bank` (`bank_id`, `balance`, `created_time`, `customer_name`, `last_update_time`) VALUES
(1, 2000, '2024-04-16 16:30:56', 'Azar', '2024-04-16 16:32:29'),
(2, 9000, '2024-04-16 16:31:11', 'Divv', '2024-04-16 16:33:23');

-- --------------------------------------------------------

--
-- Table structure for table `bank_seq`
--

CREATE TABLE `bank_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bank_seq`
--

INSERT INTO `bank_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Table structure for table `gift_card`
--

CREATE TABLE `gift_card` (
  `card_id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `is_blocked` bit(1) DEFAULT NULL,
  `card_type` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL,
  `bank_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `gift_card`
--

INSERT INTO `gift_card` (`card_id`, `active`, `amount`, `is_blocked`, `card_type`, `created_time`, `last_update_time`, `pin`, `bank_id`) VALUES
(1, b'1', 3000, b'0', 'S', '2024-04-16 16:31:41', '2024-04-16 16:32:14', '1111', 1),
(2, b'1', 1000, b'0', 'S', '2024-04-16 16:32:29', '2024-04-16 16:32:29', '2222', 1),
(3, b'0', 5000, b'0', 'S', '2024-04-16 16:32:48', '2024-04-16 16:34:34', '3333', 2);

-- --------------------------------------------------------

--
-- Table structure for table `gift_card_seq`
--

CREATE TABLE `gift_card_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `gift_card_seq`
--

INSERT INTO `gift_card_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` bigint(20) NOT NULL,
  `transaction_amount` bigint(20) DEFAULT NULL,
  `card_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_seq`
--

CREATE TABLE `transaction_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction_seq`
--

INSERT INTO `transaction_seq` (`next_val`) VALUES
(1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bank`
--
ALTER TABLE `bank`
  ADD PRIMARY KEY (`bank_id`);

--
-- Indexes for table `gift_card`
--
ALTER TABLE `gift_card`
  ADD PRIMARY KEY (`card_id`),
  ADD KEY `FKpffga2s0lfs2a3l5ar51qllui` (`bank_id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `FKeacrrgthab22q5305onk4bsd9` (`card_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
