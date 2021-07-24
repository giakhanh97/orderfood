package com.example.appnhac.Model;

import java.io.Serializable;

public class ChiTietDonHang implements Serializable {
    private int Id_ctdh , id_dh , id_mon, gia , soluong;
    private String Tenmon;

    public int getId_ctdh() {
        return Id_ctdh;
    }

    public void setId_ctdh(int id_ctdh) {
        Id_ctdh = id_ctdh;
    }

    public int getId_dh() {
        return id_dh;
    }

    public void setId_dh(int id_dh) {
        this.id_dh = id_dh;
    }

    public int getId_mon() {
        return id_mon;
    }

    public void setId_mon(int id_mon) {
        this.id_mon = id_mon;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getTenmon() {
        return Tenmon;
    }

    public void setTenmon(String tenmon) {
        Tenmon = tenmon;
    }

    public ChiTietDonHang(int id_ctdh, int id_dh, int id_mon, int gia, int soluong, String tenmon) {
        Id_ctdh = id_ctdh;
        this.id_dh = id_dh;
        this.id_mon = id_mon;
        this.gia = gia;
        this.soluong = soluong;
        Tenmon = tenmon;
    }
}
