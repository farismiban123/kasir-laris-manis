/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Faris M Rabbani
 */
public class Resep {
    private int id, idMenu, idBahanBaku, kebutuhan;

    public Resep(int id, int idMenu, int idBahanBaku, int kebutuhan){
        this.id = id;
        this.idMenu = idMenu;
        this.idBahanBaku = idBahanBaku;
        this.kebutuhan = kebutuhan;
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
}
