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
                       "LEFT JOIN detail_order d ON d.id_order = o.id GROUP BY o.id";

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

    public void addOrder(int nomorAntrean, String jenisOrder) {

        String query = "INSERT INTO orders " +
                       "(nomor_antrean, jenis_order) " +
                       "VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, nomorAntrean);
            pstmt.setString(2, jenisOrder);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(int nomorAntrean, String name, String jenisOrder) {

        String query = "INSERT INTO orders " +
                       "(nomor_antrean, name, jenis_order) " +
                       "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, nomorAntrean);
            pstmt.setString(2, name);
            pstmt.setString(3, jenisOrder);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int id) {

        String deleteDetail = "DELETE FROM detail_order WHERE id_order = ?";
        String deleteOrder = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection()) {

            conn.setAutoCommit(false);

            try {
                // Hapus detail order terlebih dahulu
                try (PreparedStatement pstmt = conn.prepareStatement(deleteDetail)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }

                // Hapus data order
                try (PreparedStatement pstmt = conn.prepareStatement(deleteOrder)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
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
}
