package repository;

import java.sql.*;
import entity.BahanBaku;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Faris M Rabbani
 */
public class BahanBakuRepository {

    public List<BahanBaku> getBahanBakuItems(){
        List<BahanBaku> list = new ArrayList<>();
        String query = "SELECT id, name, harga_satuan, stok_tersedia, batas_minimum " +
                       "FROM bahan_baku ORDER BY id ASC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()){
                BahanBaku bahan = new BahanBaku(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("harga_satuan"),
                    rs.getInt("stok_tersedia"),
                    rs.getInt("batas_minimum")
                );
                list.add(bahan);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void addBahanBaku(String name, double hargaSatuan, int stokTersedia, int batasMinimum){
        String query = "INSERT INTO bahan_baku (name, harga_satuan, stok_tersedia, batas_minimum) " +
                       "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, name);
            pstmt.setDouble(2, hargaSatuan);
            pstmt.setInt(3, stokTersedia);
            pstmt.setInt(4, batasMinimum);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteBahanBaku(int id){
        String query = "DELETE FROM bahan_baku WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateBahanBaku(int id, String name, double hargaSatuan, int stokTersedia, int batasMinimum){
        String query = "UPDATE bahan_baku SET name = ?, harga_satuan = ?, stok_tersedia = ?, batas_minimum = ? " +
                       "WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, name);
            pstmt.setDouble(2, hargaSatuan);
            pstmt.setInt(3, stokTersedia);
            pstmt.setInt(4, batasMinimum);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}