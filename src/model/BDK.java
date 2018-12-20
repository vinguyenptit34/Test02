package model;

import java.io.Serializable;

/**
 *
 * @author ViNguyen
 */
public class BDK implements Serializable{
    private SinhVien sinhVien;
    private MonHoc monHoc;
    private String tgdk;

    public BDK(SinhVien sinhVien, MonHoc monHoc, String tgdk) {
        this.sinhVien = sinhVien;
        this.monHoc = monHoc;
        this.tgdk = tgdk;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public String getTgdk() {
        return tgdk;
    }

    public void setTgdk(String tgdk) {
        this.tgdk = tgdk;
    }
    
    public int getmaSV() {
       return sinhVien.getMaSV();
    }
    
    public String getHoTen() {
        return sinhVien.getHoTen();
    }
    
    public int getmaMH() {
        return monHoc.getMaMH();
    }
    
    public String getTenMH() {
        return monHoc.getTenMH();
    }
    
    public Object[] toObject() {
        return new Object[] {getmaSV(),getHoTen(),getmaMH(),getTenMH(),tgdk};
    }
}
