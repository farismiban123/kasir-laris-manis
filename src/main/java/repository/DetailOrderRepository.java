/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.DetailOrderModel;

/**
 *
 * @author Faris M Rabbani
 */
public class DetailOrderRepository {
    public List<DetailOrderModel> getDetailOrderItems(int idOrder) {
        List<DetailOrderModel> list = new ArrayList<>();

        String query = "SELECT d.*, m.name, (d.kuantitas * d.harga_satuan) AS harga_total FROM detail_order d " + 
                       "JOIN menu m ON m.id = d.id_menu WHERE id_order = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idOrder);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DetailOrderModel detailOrder = new DetailOrderModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("id_order"),
                        rs.getInt("id_menu"),
                        rs.getInt("kuantitas"),
                        rs.getString("catatan"),
                        rs.getDouble("harga_satuan"),
                        rs.getDouble("harga_total")
                    );

                    list.add(detailOrder);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addDetailOrder(int idOrder, int idMenu, String catatan, int kuantitas, double hargaSatuan) {
        String query = "INSERT INTO detail_order (id_order, id_menu, catatan, kuantitas, harga_satuan) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);

            try {
                if (!updateStokBahanBaku(conn, idMenu, kuantitas, true)) {
                    conn.rollback();
                    return false;
                }

                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, idOrder);
                    pstmt.setInt(2, idMenu);
                    pstmt.setString(3, catatan);
                    pstmt.setInt(4, kuantitas);
                    pstmt.setDouble(5, hargaSatuan);
                    pstmt.executeUpdate();
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

    public boolean deleteDetailOrder(int id) {
        String selectQuery = "SELECT id_menu, kuantitas FROM detail_order WHERE id = ?";
        String deleteQuery = "DELETE FROM detail_order WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);

            try {
                int idMenu;
                int kuantitas;

                try (PreparedStatement ps = conn.prepareStatement(selectQuery)) {
                    ps.setInt(1, id);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }

                        idMenu = rs.getInt("id_menu");
                        kuantitas = rs.getInt("kuantitas");
                    }
                }

                if (!updateStokBahanBaku(conn, idMenu, kuantitas, false)) {
                    conn.rollback();
                    return false;
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteQuery)) {
                    ps.setInt(1, id);
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

    public boolean updateDetailOrder(int id, String catatan, int kuantitas) {
        String selectQuery = "SELECT id_menu, kuantitas FROM detail_order WHERE id = ?";
        String updateQuery = "UPDATE detail_order SET catatan = ?, kuantitas = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);

            try {
                int idMenu;
                int kuantitasLama;

                try (PreparedStatement ps = conn.prepareStatement(selectQuery)) {
                    ps.setInt(1, id);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }

                        idMenu = rs.getInt("id_menu");
                        kuantitasLama = rs.getInt("kuantitas");
                    }
                }

                int selisih = kuantitas - kuantitasLama;

                if (selisih > 0) {
                    if (!updateStokBahanBaku(conn, idMenu, selisih, true)) {
                        conn.rollback();
                        return false;
                    }
                } else if (selisih < 0) {
                    if (!updateStokBahanBaku(conn, idMenu, Math.abs(selisih), false)) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
                    ps.setString(1, catatan);
                    ps.setInt(2, kuantitas);
                    ps.setInt(3, id);
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

    private boolean updateStokBahanBaku(Connection conn, int idMenu, int kuantitas, boolean kurangi) throws SQLException {
        String cekQuery = "SELECT COUNT(*) FROM resep_menu WHERE id_menu = ?";
        try (PreparedStatement ps = conn.prepareStatement(cekQuery)) {
            ps.setInt(1, idMenu);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getInt(1) == 0) return false;
            }
        }

        if (kurangi) {
            String cekStok = "SELECT COUNT(*) FROM bahan_baku bb JOIN (SELECT id_bahan_baku, SUM(kebutuhan * ?) AS total FROM resep_menu WHERE id_menu = ? GROUP BY id_bahan_baku) r ON bb.id = r.id_bahan_baku WHERE bb.stok_tersedia < r.total";

            try (PreparedStatement ps = conn.prepareStatement(cekStok)) {
                ps.setInt(1, kuantitas);
                ps.setInt(2, idMenu);

                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    if (rs.getInt(1) > 0) return false;
                }
            }
        }

        String updateQuery = kurangi
            ? "UPDATE bahan_baku bb JOIN (SELECT id_bahan_baku, SUM(kebutuhan * ?) AS total FROM resep_menu WHERE id_menu = ? GROUP BY id_bahan_baku) r ON bb.id = r.id_bahan_baku SET bb.stok_tersedia = bb.stok_tersedia - r.total"
            : "UPDATE bahan_baku bb JOIN (SELECT id_bahan_baku, SUM(kebutuhan * ?) AS total FROM resep_menu WHERE id_menu = ? GROUP BY id_bahan_baku) r ON bb.id = r.id_bahan_baku SET bb.stok_tersedia = bb.stok_tersedia + r.total";

        try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
            ps.setInt(1, kuantitas);
            ps.setInt(2, idMenu);
            ps.executeUpdate();
        }

        return true;
    }
}
