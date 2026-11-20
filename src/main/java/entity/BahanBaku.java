/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Faris M Rabbani
 */
public class BahanBaku {
    private int id, stokTersedia, batasMinimum;
    private double hargaSatuan;
    private String nama;
    
    public BahanBaku(){
        
    }
    
    public BahanBaku(int id, String nama, double hargaSatuan, int stokTersedia, int batasMinimum){
        this.id = id;
        this.stokTersedia = stokTersedia;
        this.batasMinimum = batasMinimum;
        this.hargaSatuan = hargaSatuan;
        this.nama = nama;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStokTersedia() {
        return stokTersedia;
    }

    public void setStokTersedia(int stokTersedia) {
        this.stokTersedia = stokTersedia;
    }

    public int getBatasMinimum() {
        return batasMinimum;
    }

    public void setBatasMinimum(int batasMinimum) {
        this.batasMinimum = batasMinimum;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
