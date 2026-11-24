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

    public void addDetailOrder(int idOrder, int idMenu, String catatan, int kuantitas, double hargaSatuan) {
        String query = "INSERT INTO detail_order (id_order, id_menu, catatan, kuantitas, harga_satuan) " +
                    "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idOrder);
            pstmt.setInt(2, idMenu);
            pstmt.setString(3, catatan);
            pstmt.setInt(4, kuantitas);
            pstmt.setDouble(5, hargaSatuan);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDetailOrder(int id) {
        String query = "DELETE FROM detail_order WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDetailOrder(int id, String catatan, int kuantitas) {
        String query = "UPDATE detail_order SET catatan = ?, kuantitas = ? " +
                    "WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, catatan);
            pstmt.setInt(2, kuantitas);
            pstmt.setInt(3, id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
