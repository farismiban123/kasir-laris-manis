/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Faris M Rabbani
 */
public class OrderModel {
    private int id, nomorAntrean;
    private String nama, jenisOrder;
    private LocalDateTime tanggalTransaksi;
    private double totalHarga;

    public OrderModel(int id, String nama, int nomorAntrean, String jenisOrder, LocalDateTime tanggalTransaksi, double totalHarga){
        this.id = id;
        this.nama = nama;
        this.nomorAntrean = nomorAntrean;
        this.jenisOrder = jenisOrder;
        this.tanggalTransaksi = tanggalTransaksi;
        this.totalHarga = totalHarga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNomorAntrean() {
        return nomorAntrean;
    }

    public void setNomorAntrean(int nomorAntrean) {
        this.nomorAntrean = nomorAntrean;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getJenisOrder() {
        return jenisOrder;
    }

    public void setJenisOrder(String jenisOrder) {
        this.jenisOrder = jenisOrder;
    }

    public LocalDateTime getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(LocalDateTime tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }
}
