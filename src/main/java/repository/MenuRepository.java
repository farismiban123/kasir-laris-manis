package repository;

import java.sql.*;
import entity.Menu;
import model.MenuModel;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Faris M Rabbani
 */
public class MenuRepository {

    public List<Menu> getMenuItems(){
        List<Menu> list = new ArrayList<>();
        String query = "SELECT id, name, harga_jual, kategori, status " +
                       "FROM menu ORDER BY id ASC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()){
                Menu menu = new Menu(
                    rs.getInt("id"),
                    rs.getInt("harga_jual"),
                    rs.getString("name"),
                    rs.getString("kategori"),
                    rs.getString("status")
                );
                list.add(menu);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<MenuModel> getMenuItemsModel() {
        List<MenuModel> list = new ArrayList<>();

        String query =
            "SELECT m.id, m.name, m.harga_jual, m.kategori, " +
            "COALESCE(( " +
            "    SELECT MIN(FLOOR(bb.stok_tersedia / NULLIF(rm.kebutuhan, 0))) " +
            "    FROM resep_menu rm " +
            "    JOIN bahan_baku bb ON rm.id_bahan_baku = bb.id " +
            "    WHERE rm.id_menu = m.id " +
            "), 0) AS stok " +
            "FROM menu m " +
            "ORDER BY m.id ASC";

        try (Connection conn = DatabaseUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                MenuModel menu = new MenuModel(
                    rs.getInt("id"),
                    rs.getDouble("harga_jual"),
                    rs.getString("name"),
                    rs.getString("kategori"),
                    rs.getInt("stok")
                );

                list.add(menu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addMenu(String name, double hargaJual, String kategori, String status){
        String query = "INSERT INTO menu (name, harga_jual, kategori, status) " +
                       "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, name);
            pstmt.setDouble(2, hargaJual);
            pstmt.setString(3, kategori);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteMenu(int id){
        String query = "DELETE FROM menu WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateMenu(int id, String nama, double hargaJual, String kategori, String status){
        String query = "UPDATE menu SET nama = ?, harga_jual = ?, kategori = ?, status = ? " +
                       "WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, nama);
            pstmt.setDouble(2, hargaJual);
            pstmt.setString(3, kategori);
            pstmt.setString(4, status);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}