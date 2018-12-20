package model;

import java.io.Serializable;

/**
 *
 * @author ViNguyen
 */
public class SinhVien implements Serializable{
    private int maSV;
    private String hoTen,diaChi,soDT;

    public SinhVien(int maSV, String hoTen, String diaChi, String soDT) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.soDT = soDT;
    }

    public int getMaSV() {
        return maSV;
    }

    public void setMaSV(int maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }
    
    public Object[] toObject() {
        return new Object[] {maSV,hoTen,diaChi,soDT};
    }
}
