-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.4.3 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for db_kasir_laris_manis
CREATE DATABASE IF NOT EXISTS `db_kasir_laris_manis` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_kasir_laris_manis`;

-- Dumping structure for table db_kasir_laris_manis.bahan_baku
CREATE TABLE IF NOT EXISTS `bahan_baku` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `harga_satuan` double NOT NULL DEFAULT '0',
  `stok_tersedia` int NOT NULL DEFAULT '0',
  `batas_minimum` int NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.bahan_baku: ~4 rows (approximately)
INSERT INTO `bahan_baku` (`id`, `name`, `harga_satuan`, `stok_tersedia`, `batas_minimum`, `created_at`, `updated_at`) VALUES
	(2, 'nasi', 3000, 2, 3, '2026-11-19 16:41:30', '2026-11-24 16:38:52'),
	(3, 'ayam', 5000, 7, 4, '2026-11-19 17:02:24', '2026-11-24 16:38:52'),
	(5, 'indomie goreng', 3500, 12, 4, '2026-11-24 13:27:47', '2026-11-24 13:27:47'),
	(6, 'telur', 3000, 21, 10, '2026-11-24 13:28:47', '2026-11-24 13:28:47');

-- Dumping structure for table db_kasir_laris_manis.carts
CREATE TABLE IF NOT EXISTS `carts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.carts: ~4 rows (approximately)
INSERT INTO `carts` (`id`, `product_id`, `quantity`) VALUES
	(1, 1, 1),
	(2, 2, 2),
	(3, 1, 1),
	(4, 2, 2);

-- Dumping structure for table db_kasir_laris_manis.detail_order
CREATE TABLE IF NOT EXISTS `detail_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_order` int NOT NULL,
  `id_menu` int NOT NULL,
  `kuantitas` int NOT NULL,
  `catatan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `harga_satuan` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detail_order` (`id_order`),
  KEY `fk_detail_menu` (`id_menu`),
  CONSTRAINT `fk_detail_menu` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_detail_order` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.detail_order: ~6 rows (approximately)
INSERT INTO `detail_order` (`id`, `id_order`, `id_menu`, `kuantitas`, `catatan`, `harga_satuan`) VALUES
	(3, 1, 1, 1, 'paha atas', 12000),
	(4, 2, 1, 2, '', 12000),
	(5, 2, 2, 2, '', 5000),
	(6, 3, 1, 1, '', 12000),
	(7, 3, 2, 3, '', 5000),
	(8, 4, 4, 1, '', 13000),
	(9, 5, 4, 1, '', 13000),
	(10, 5, 3, 1, '', 4000),
	(11, 5, 1, 1, '', 12000),
	(12, 6, 5, 1, '', 8000),
	(13, 7, 1, 4, '', 12000),
	(14, 8, 4, 2, '', 13000),
	(17, 9, 1, 1, '', 12000),
	(18, 9, 4, 1, '', 13000);

-- Dumping structure for table db_kasir_laris_manis.laporan_harian
CREATE TABLE IF NOT EXISTS `laporan_harian` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tanggal` date NOT NULL,
  `jumlah_pesanan` int NOT NULL DEFAULT '0',
  `total_pendapatan` double NOT NULL DEFAULT (0),
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tanggal` (`tanggal`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.laporan_harian: ~1 rows (approximately)
INSERT INTO `laporan_harian` (`id`, `tanggal`, `jumlah_pesanan`, `total_pendapatan`, `updated_at`) VALUES
	(2, '2026-11-24', 8, 197000, '2026-11-24 16:31:16');

-- Dumping structure for table db_kasir_laris_manis.menu
CREATE TABLE IF NOT EXISTS `menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `kategori` enum('Makanan','Minuman') COLLATE utf8mb4_unicode_ci NOT NULL,
  `harga_jual` int NOT NULL DEFAULT '0',
  `status` enum('Aktif','Tidak Aktif') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Aktif',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.menu: ~5 rows (approximately)
INSERT INTO `menu` (`id`, `name`, `kategori`, `harga_jual`, `status`, `created_at`, `updated_at`) VALUES
	(1, 'ayam goreng', 'Makanan', 12000, 'Aktif', '2026-11-22 08:08:40', '2026-11-22 08:08:43'),
	(2, 'es teh', 'Minuman', 5000, 'Aktif', '2026-11-22 08:28:54', '2026-11-22 08:28:54'),
	(3, 'es teler', 'Minuman', 4000, 'Aktif', '2026-11-22 08:31:55', '2026-11-22 08:31:55'),
	(4, 'Ayam Bakar', 'Makanan', 13000, 'Aktif', '2026-11-22 09:47:20', '2026-11-22 09:47:20'),
	(5, 'indomie goreng', 'Makanan', 8000, 'Aktif', '2026-11-24 13:26:19', '2026-11-24 13:29:52');

-- Dumping structure for table db_kasir_laris_manis.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_laporan` int DEFAULT NULL,
  `nomor_antrean` int NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tanggal_transaksi` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `jenis_order` enum('Dine In','Takeaway') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` enum('Diproses','Selesai') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Diproses',
  `metode_bayar` enum('QRIS','Cash','Transfer') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.orders: ~4 rows (approximately)
INSERT INTO `orders` (`id`, `id_laporan`, `nomor_antrean`, `name`, `tanggal_transaksi`, `jenis_order`, `status`, `metode_bayar`, `total`) VALUES
	(1, 2, 1, 'iban', '2026-11-22 19:58:31', NULL, 'Selesai', 'QRIS', 12000),
	(2, 2, 1, NULL, '2026-11-24 17:53:38', 'Dine In', 'Selesai', 'Cash', 34000),
	(3, 2, 1, 'eshan', '2026-11-24 18:14:56', 'Dine In', 'Selesai', 'QRIS', 27000),
	(4, 2, 1, 'lgar', '2026-11-24 18:15:24', 'Takeaway', 'Selesai', 'Transfer', 13000),
	(5, 2, 1, NULL, '2026-11-24 20:10:16', 'Dine In', 'Selesai', 'Cash', 29000),
	(6, 2, 1, NULL, '2026-11-24 20:29:57', 'Dine In', 'Selesai', 'QRIS', 8000),
	(7, 2, 1, NULL, '2026-11-24 23:17:35', 'Takeaway', 'Selesai', 'QRIS', 48000),
	(8, 2, 1, NULL, '2026-11-24 23:30:54', 'Dine In', 'Selesai', 'QRIS', 26000),
	(9, NULL, 1, NULL, '2026-11-24 23:31:52', 'Takeaway', 'Diproses', NULL, NULL);

-- Dumping structure for table db_kasir_laris_manis.order_history
CREATE TABLE IF NOT EXISTS `order_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_order` int NOT NULL,
  `id_menu` int NOT NULL,
  `total` double NOT NULL DEFAULT (0),
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.order_history: ~4 rows (approximately)
INSERT INTO `order_history` (`id`, `id_order`, `id_menu`, `total`, `timestamp`) VALUES
	(1, 1, 1, 15000000, '2026-11-24 09:10:22'),
	(2, 2, 2, 500000, '2026-11-24 09:10:22'),
	(3, 1, 1, 15000000, '2026-11-24 09:10:34'),
	(4, 2, 2, 500000, '2026-11-24 09:10:34');

-- Dumping structure for table db_kasir_laris_manis.products
CREATE TABLE IF NOT EXISTS `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(15,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.products: ~8 rows (approximately)
INSERT INTO `products` (`id`, `name`, `price`, `stock`) VALUES
	(1, 'Laptop Asus ROG', 15000000.00, 10),
	(2, 'Mouse Logitech Wireless', 250000.00, 50),
	(3, 'Mechanical Keyboard Keychron', 1200000.00, 25),
	(4, 'Monitor LG 24 Inch', 1800000.00, 15),
	(5, 'Laptop Asus ROG', 15000000.00, 10),
	(6, 'Mouse Logitech Wireless', 250000.00, 50),
	(7, 'Mechanical Keyboard Keychron', 1200000.00, 25),
	(8, 'Monitor LG 24 Inch', 1800000.00, 15);

-- Dumping structure for table db_kasir_laris_manis.resep_menu
CREATE TABLE IF NOT EXISTS `resep_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_menu` int NOT NULL,
  `id_bahan_baku` int NOT NULL,
  `kebutuhan` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_menu_bahan` (`id_menu`,`id_bahan_baku`),
  KEY `fk_resep_bahan_baku` (`id_bahan_baku`),
  CONSTRAINT `fk_resep_bahan_baku` FOREIGN KEY (`id_bahan_baku`) REFERENCES `bahan_baku` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_resep_menu` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.resep_menu: ~5 rows (approximately)
INSERT INTO `resep_menu` (`id`, `id_menu`, `id_bahan_baku`, `kebutuhan`) VALUES
	(1, 1, 3, 1),
	(2, 4, 2, 1),
	(4, 4, 3, 1),
	(5, 5, 6, 1),
	(6, 5, 5, 1);

-- Dumping structure for table db_kasir_laris_manis.riwayat_stok
CREATE TABLE IF NOT EXISTS `riwayat_stok` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_bahan_baku` int NOT NULL,
  `jenis_perubahan` enum('Masuk','Keluar','Penyesuaian') COLLATE utf8mb4_unicode_ci NOT NULL,
  `jumlah` int NOT NULL DEFAULT (0),
  `keterangan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tanggal_perubahan` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_riwayat_bahan_baku` (`id_bahan_baku`),
  CONSTRAINT `fk_riwayat_bahan_baku` FOREIGN KEY (`id_bahan_baku`) REFERENCES `bahan_baku` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table db_kasir_laris_manis.riwayat_stok: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
