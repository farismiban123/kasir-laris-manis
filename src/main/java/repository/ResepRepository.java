/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.ResepModel;

/**
 *
 * @author Faris M Rabbani
 */
public class ResepRepository {
    public List<ResepModel> getResepItems(int idMenu) {
        List<ResepModel> list = new ArrayList<>();

        String query = "SELECT r.id, r.id_menu, r.id_bahan_baku, " +
                    "r.kebutuhan, b.name, b.harga_satuan, " +
                    "(r.kebutuhan * b.harga_satuan) AS harga_total " +
                    "FROM resep_menu r " +
                    "JOIN bahan_baku b ON r.id_bahan_baku = b.id " +
                    "WHERE r.id_menu = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idMenu);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ResepModel resep = new ResepModel(
                        rs.getInt("id"),
                        rs.getInt("id_menu"),
                        rs.getInt("id_bahan_baku"),
                        rs.getInt("kebutuhan"),
                        rs.getString("name"),
                        rs.getDouble("harga_satuan"),
                        rs.getDouble("harga_total")
                    );

                    list.add(resep);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addResep(int idMenu, int idBahanBaku, int kebutuhan) {
        String query = "INSERT INTO resep_menu (id_menu, id_bahan_baku, kebutuhan) " +
                    "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idMenu);
            pstmt.setInt(2, idBahanBaku);
            pstmt.setInt(3, kebutuhan);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResep(int id) {
        String query = "DELETE FROM resep_menu WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateResep(int id, int idBahanBaku, int kebutuhan) {
        String query = "UPDATE resep_menu SET id_bahan_baku = ?, kebutuhan = ? " +
                    "WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idBahanBaku);
            pstmt.setInt(2, kebutuhan);
            pstmt.setInt(3, id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
