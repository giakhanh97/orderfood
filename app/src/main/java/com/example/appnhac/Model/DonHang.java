package com.example.appnhac.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class DonHang implements Serializable {
    private int Id;
    private String tenkh;
    private int sdt;
    private String diachi;
    private String email;
    ArrayList<Monan> arrayList;
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DonHang(int id, String tenkh, int sdt, String diachi, String email) {
        Id = id;
        this.tenkh = tenkh;
        this.sdt = sdt;
        this.diachi = diachi;
        this.email = email;
    }
}
