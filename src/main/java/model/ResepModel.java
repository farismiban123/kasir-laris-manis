/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Faris M Rabbani
 */
public class ResepModel {
    private int id, idMenu, idBahanBaku, kebutuhan;
    private double hargaSatuan, hargaTotal;
    String nama;

    public ResepModel(int id, int idMenu, int idBahanBaku, int kebutuhan, String nama, double hargaSatuan, double hargaTotal){
        this.id = id;
        this.idMenu = idMenu;
        this.idBahanBaku = idBahanBaku;
        this.kebutuhan = kebutuhan;
        this.nama = nama;
        this.hargaSatuan = hargaSatuan;
        this.hargaTotal = hargaTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdBahanBaku() {
        return idBahanBaku;
    }

    public void setIdBahanBaku(int idBahanBaku) {
        this.idBahanBaku = idBahanBaku;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getKebutuhan() {
        return kebutuhan;
    }

    public void setKebutuhan(int kebutuhan) {
        this.kebutuhan = kebutuhan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public double getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(double hargaTotal) {
        this.hargaTotal = hargaTotal;
    }
}
