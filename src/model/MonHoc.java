package model;

import java.io.Serializable;

/**
 *
 * @author ViNguyen
 */
public class MonHoc implements Serializable{
    private int maMH;
    private String tenMH;
    private int tongST;
    private String loaiMH;

    public MonHoc(int maMH, String tenMH, int tongST, String loaiMH) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.tongST = tongST;
        this.loaiMH = loaiMH;
    }

    public int getMaMH() {
        return maMH;
    }

    public void setMaMH(int maMH) {
        this.maMH = maMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public int getTongST() {
        return tongST;
    }

    public void setTongST(int tongST) {
        this.tongST = tongST;
    }

    public String getLoaiMH() {
        return loaiMH;
    }

    public void setLoaiMH(String loaiMH) {
        this.loaiMH = loaiMH;
    }
    
    public Object[] toObject() {
        return new Object[] {maMH,tenMH,tongST,loaiMH};
    }
}
