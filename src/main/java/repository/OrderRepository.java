/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.OrderModel;

/**
 *
 * @author Faris M Rabbani
 */
public class OrderRepository {
    public List<OrderModel> getOrders(){
        List<OrderModel> list = new ArrayList<>();
        String query = "SELECT o.*, SUM(d.harga_satuan * d.kuantitas) AS total_pesanan FROM orders o " +
                       "LEFT JOIN detail_order d ON d.id_order = o.id WHERE o.status = 'Diproses' GROUP BY o.id";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()){
                OrderModel order = new OrderModel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("nomor_antrean"),
                    rs.getString("jenis_order"),
                    rs.getObject("tanggal_transaksi", LocalDateTime.class),
                    rs.getDouble("total_pesanan")
                );
                list.add(order);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public List<OrderModel> getOrdersLaporan(int idLaporan) {
        List<OrderModel> list = new ArrayList<>();

        String query = "SELECT o.*, SUM(d.harga_satuan * d.kuantitas) AS total_pesanan FROM orders o " +
                    "LEFT JOIN detail_order d ON d.id_order = o.id WHERE o.status = 'Selesai' && o.id_laporan = ? GROUP BY o.id";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idLaporan);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderModel order = new OrderModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("nomor_antrean"),
                        rs.getString("jenis_order"),
                        rs.getObject("tanggal_transaksi", LocalDateTime.class),
                        rs.getDouble("total_pesanan")
                    );

                    list.add(order);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addOrder(String jenisOrder) {

        String query = "INSERT INTO orders " +
                       "(nomor_antrean, jenis_order) " +
                       "VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            int nomorAntrean = getNomorAntrean(conn);
            pstmt.setInt(1, nomorAntrean);
            pstmt.setString(2, jenisOrder);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(String name, String jenisOrder) {

        String query = "INSERT INTO orders " +
                       "(nomor_antrean, name, jenis_order) " +
                       "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            int nomorAntrean = getNomorAntrean(conn);
            pstmt.setInt(1, nomorAntrean);
            pstmt.setString(2, name);
            pstmt.setString(3, jenisOrder);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int id) {
        String restoreStock = "UPDATE bahan_baku bb " +
                            "JOIN ( " +
                            "    SELECT r.id_bahan_baku, SUM(r.kebutuhan * d.kuantitas) AS total " +
                            "    FROM detail_order d " +
                            "    JOIN resep_menu r ON r.id_menu = d.id_menu " +
                            "    WHERE d.id_order = ? " +
                            "    GROUP BY r.id_bahan_baku " +
                            ") x ON bb.id = x.id_bahan_baku " +
                            "SET bb.stok_tersedia = bb.stok_tersedia + x.total";

        String deleteDetail = "DELETE FROM detail_order WHERE id_order = ?";
        String deleteOrder = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // 1. Kembalikan stok seluruh bahan baku
                try (PreparedStatement ps = conn.prepareStatement(restoreStock)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                // 2. Hapus seluruh detail order
                try (PreparedStatement ps = conn.prepareStatement(deleteDetail)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                // 3. Hapus order
                try (PreparedStatement ps = conn.prepareStatement(deleteOrder)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean selesaikanOrder(int idOrder, String metodeBayar) {
        // Waktu diambil sekali dari Java supaya waktu_selesai dan tanggal laporan pasti konsisten
        LocalDateTime sekarang = LocalDateTime.now();

        String qBuatLaporan = "INSERT IGNORE INTO laporan_harian (tanggal) VALUES (?)";
        String qAmbilLaporan = "SELECT id FROM laporan_harian WHERE tanggal = ?";
        String qSelesai = "UPDATE orders o " +
                          "SET o.status = 'Selesai', o.metode_bayar = ?, o.id_laporan = ?, " +
                          "    o.total = (SELECT COALESCE(SUM(d.harga_satuan * d.kuantitas), 0) " +
                          "               FROM detail_order d WHERE d.id_order = o.id) " +
                          "WHERE o.id = ? AND o.status = 'Diproses'";
        String qRekap = "UPDATE laporan_harian l " +
                        "SET l.jumlah_pesanan = (SELECT COUNT(*) FROM orders " +
                        "                        WHERE id_laporan = l.id AND status = 'Selesai'), " +
                        "    l.total_pendapatan = (SELECT COALESCE(SUM(total), 0) FROM orders " +
                        "                          WHERE id_laporan = l.id AND status = 'Selesai') " +
                        "WHERE l.id = ?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // 1. Pastikan baris laporan hari ini ada, lalu ambil id-nya
                int idLaporan;
                try (PreparedStatement ps = conn.prepareStatement(qBuatLaporan)) {
                    ps.setDate(1, Date.valueOf(sekarang.toLocalDate()));
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(qAmbilLaporan)) {
                    ps.setDate(1, Date.valueOf(sekarang.toLocalDate()));
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        idLaporan = rs.getInt(1);
                    }
                }

                // 2. Tandai order selesai (hanya jika masih 'Diproses')
                try (PreparedStatement ps = conn.prepareStatement(qSelesai)) {
                    ps.setString(1, metodeBayar);
                    ps.setInt(2, idLaporan);
                    ps.setInt(3, idOrder);
                    if (ps.executeUpdate() == 0) {
                        conn.rollback();
                        return false; // order tidak ada atau sudah selesai/batal
                    }
                }

                // 3. Hitung ulang rekap laporan
                try (PreparedStatement ps = conn.prepareStatement(qRekap)) {
                    ps.setInt(1, idLaporan);
                    ps.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getNomorAntrean(Connection conn) throws SQLException {
        String query = "SELECT COALESCE(MAX(nomor_antrean), 0) + 1 AS nomor_baru FROM orders WHERE DATE(tanggal_transaksi) = CURDATE()";

        try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("nomor_baru");
            }
        }

        return 1;
    }
}
