/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.LaporanHarian;

/**
 *
 * @author Faris M Rabbani
 */
public class LaporanHarianRepository {
    public List<LaporanHarian> getLaporanHarianItems(){
        List<LaporanHarian> list = new ArrayList<>();
        String query = "SELECT * " +
                       "FROM laporan_harian ORDER BY id ASC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()){
                LaporanHarian laporanHarian = new LaporanHarian(
                    rs.getInt("id"),
                    rs.getInt("jumlah_pesanan"),
                    rs.getDouble("total_pendapatan"),
                    rs.getObject("tanggal", LocalDate.class)
                );
                list.add(laporanHarian);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
