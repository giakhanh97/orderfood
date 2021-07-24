package com.example.appnhac.Model;

import java.io.Serializable;

public class Monan implements Serializable {
    private int Id;
    private String Tenmon;
    private int Gia;
    private String Hinhanhmon;
    private String Mota;
    private String Danhgia;
    private int Id_loai;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenmon() {
        return Tenmon;
    }

    public void setTenmon(String tenmon) {
        Tenmon = tenmon;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public String getHinhanhmon() {
        return Hinhanhmon;
    }

    public void setHinhanhmon(String hinhanhmon) {
        Hinhanhmon = hinhanhmon;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getDanhgia() {
        return Danhgia;
    }

    public void setDanhgia(String danhgia) {
        Danhgia = danhgia;
    }

    public int getId_loai() {
        return Id_loai;
    }

    public void setId_loai(int id_loai) {
        Id_loai = id_loai;
    }

    public Monan(int id, String tenmon, int gia, String hinhanhmon, String mota, String danhgia, int id_loai) {
        Id = id;
        Tenmon = tenmon;
        Gia = gia;
        Hinhanhmon = hinhanhmon;
        Mota = mota;
        Danhgia = danhgia;
        Id_loai = id_loai;
    }
}
