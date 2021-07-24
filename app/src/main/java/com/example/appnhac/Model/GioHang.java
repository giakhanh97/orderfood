package com.example.appnhac.Model;

public class GioHang {
    public int idmon;
    public String tenmon;
    public long gia;
    public String hinhmon;
    public int soluongmon;

    public int getIdmon() {
        return idmon;
    }

    public void setIdmon(int idmon) {
        this.idmon = idmon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public String getHinhmon() {
        return hinhmon;
    }

    public void setHinhmon(String hinhmon) {
        this.hinhmon = hinhmon;
    }

    public int getSoluongmon() {
        return soluongmon;
    }

    public void setSoluongmon(int soluongmon) {
        this.soluongmon = soluongmon;
    }

    public GioHang(int idmon, String tenmon, long gia, String hinhmon, int soluongmon) {
        this.idmon = idmon;
        this.tenmon = tenmon;
        this.gia = gia;
        this.hinhmon = hinhmon;
        this.soluongmon = soluongmon;
    }
}
