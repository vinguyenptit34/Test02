package view;

import controller.IOFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.BDK;
import model.MonHoc;
import model.SinhVien;

/**
 *
 * @author ViNguyen
 */
public class Main extends javax.swing.JFrame {
    enum State{
        normal,
        themSV,
        themMH,
        themBDK
    }
    
    private State state;
    private ArrayList<SinhVien> listSinhVien = new ArrayList<> ();
    private ArrayList<MonHoc> listMonHoc = new ArrayList<> ();
    private ArrayList<BDK> listBDK = new ArrayList<> ();
    DefaultTableModel tm1,tm2,tm3;
    
    public Main() {
        initComponents();
        tm1 = (DefaultTableModel)jTable1.getModel();
        tm2 = (DefaultTableModel)jTable2.getModel();
        tm3 = (DefaultTableModel)jTable3.getModel();
        changeState(state.normal);
        IOFile.readFile(listMonHoc, "MonHoc.dat");
        IOFile.readFile(listSinhVien, "SinhVien.dat");
        IOFile.readFile(listBDK, "BDK.dat");
    }
    
    private void changeState(State state) {
        this.state = state;
        if(state==State.normal) {
            btcn1.setEnabled(false);
            btbq1.setEnabled(false);
            btcn2.setEnabled(false);
            btbq2.setEnabled(false);
            btcn3.setEnabled(false);
            btbq3.setEnabled(false);
            bttm1.setEnabled(true);
            btluu1.setEnabled(true);
            bttm2.setEnabled(true);
            btluu2.setEnabled(true);
            bttm3.setEnabled(true);
            btluu3.setEnabled(true);
        }
        else if((state==State.themMH)) {
            bttm1.setEnabled(false);
            btluu1.setEnabled(false);
            btcn1.setEnabled(true);
            btbq1.setEnabled(true);
        }
        else if((state==State.themSV)) {
            bttm2.setEnabled(false);
            btluu2.setEnabled(false);
            btcn2.setEnabled(true);
            btbq2.setEnabled(true);
        }
        else if((state==State.themBDK)) {
            bttm3.setEnabled(false);
            btluu3.setEnabled(false);
            btcn3.setEnabled(true);
            btbq3.setEnabled(true);
        }
    }
    
    private MonHoc newMH() {
        MonHoc mh = null;
        try{
            String tenMH = jTextField2.getText();
            int tongST = Integer.parseInt(jTextField3.getText());
            String loaiMH = jComboBox1.getSelectedItem().toString();
            if(tenMH.equals("")||tongST<0) {
                JOptionPane.showMessageDialog(this, "Nhap lai");
            }
            else {
                int maMH = Integer.parseInt(jTextField1.getText());
                mh = new MonHoc(maMH,tenMH,tongST,loaiMH);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Nhap lai");
        }
        return mh;
    }
    
    private void hienthiMH() {
        tm1.setRowCount(0);
        for(MonHoc mh:listMonHoc) {
            tm1.addRow(mh.toObject());
        }
    }
    
    private void themMonHoc() {
        MonHoc mh = newMH();
        if(mh!=null) {
            listMonHoc.add(mh);
            hienthiMH();
        }
    }
    
    private MonHoc timMonHoc(int maMH) {
        for(int i=0;i<listMonHoc.size();i++) {
            if(listMonHoc.get(i).getMaMH()==maMH) {
                return listMonHoc.get(i);
            }
        }
        return null;
    }
    
    private SinhVien newSV() {
        SinhVien sv = null;
        try{
            String hoTen = jTextField5.getText(),
                    diaChi = jTextField6.getText(),
                    soDT = jTextField7.getText();
            if(hoTen.equals("")||diaChi.equals("")||soDT.equals("")) {
                JOptionPane.showMessageDialog(this, "Nhap lai");
            }
            else {
                int maSV = Integer.parseInt(jTextField4.getText());
                sv = new SinhVien(maSV,hoTen,diaChi,soDT);
            }
        }catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Nhap lai");
        }
        return sv;
    }
    
    private void hienthiSV() {
        tm2.setRowCount(0);
        for(SinhVien sv:listSinhVien){
            tm2.addRow(sv.toObject());
        }
    }
    
    private void themSV() {
        SinhVien sv = newSV();
        if(sv!=null) {
            listSinhVien.add(sv);
            hienthiSV();
        }
    }
    
    private SinhVien timSV(int maSV) {
        for(int i=0;i<listSinhVien.size();i++) {
            if(listSinhVien.get(i).getMaSV()==maSV) {
                return listSinhVien.get(i);
            }
        }
        return null;
    }
    
    private boolean isBDK (int masv,int mamh) {
        for(BDK b:listBDK) {
            if(b.getmaSV()==masv && b.getmaMH()==mamh){
                return true;
            }
        }
        return false;
    }
    
    private BDK newBDK() {
        BDK b = null;
        int masv=0,mamh=0;
        try{
            masv = Integer.parseInt(jComboBox2.getSelectedItem().toString());
            mamh = Integer.parseInt(jComboBox3.getSelectedItem().toString());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String tgdk = sdf.format(date.getTime());
            b = new BDK(timSV(masv),timMonHoc(mamh),tgdk);
        }catch(Exception ex) {}
        return b;
    }
    
    private void hienthiBDK() {
        tm3.setRowCount(0);
        for(BDK b :listBDK) {
            tm3.addRow(b.toObject());
        }
    }
    
    private void themBDK() {
        BDK b = newBDK();
        if(b!=null) {
            listBDK.add(b);
            hienthiBDK();
        }
    }
    
    private void refresh2Ma() {
        jComboBox2.removeAllItems();
        jComboBox3.removeAllItems();
        jComboBox4.removeAllItems();
        for(SinhVien sv : listSinhVien ) {
            jComboBox2.addItem(Integer.toString(sv.getMaSV()));
        }
        for(MonHoc mh :listMonHoc) {
            jComboBox3.addItem(Integer.toString(mh.getMaMH()));
            jComboBox4.addItem(mh.getTenMH());
        }
    }
    
    private String timSVDK(String tenMH) {
        String re="";
        int sum=1;
        for(int i=0;i<listBDK.size();i++) {
            if(listBDK.get(i).getTenMH().equals(tenMH)) {
                re+="\n"+sum+"  \t"+listBDK.get(i).getHoTen()+"\t"+listBDK.get(i).getSinhVien().getSoDT()+"\n";
                sum++;
            }
        }
        return re;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        bttm1 = new javax.swing.JButton();
        btluu1 = new javax.swing.JButton();
        btcn1 = new javax.swing.JButton();
        btbq1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        bttm2 = new javax.swing.JButton();
        btluu2 = new javax.swing.JButton();
        btcn2 = new javax.swing.JButton();
        btbq2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        bttm3 = new javax.swing.JButton();
        btluu3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        btcn3 = new javax.swing.JButton();
        btbq3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        btsx = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        btds = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã môn học", "Tên môn học", "Tổng số tiết", "Loại môn học"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        bttm1.setText("Thêm mới");
        bttm1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttm1ActionPerformed(evt);
            }
        });

        btluu1.setText("Lưu");
        btluu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btluu1ActionPerformed(evt);
            }
        });

        btcn1.setText("Cập nhật");
        btcn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcn1ActionPerformed(evt);
            }
        });

        btbq1.setText("Bỏ qua");
        btbq1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbq1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Tên môn học");

        jLabel2.setText("Mã môn học");

        jLabel3.setText("Tổng số tiết");

        jLabel4.setText("Loại môn học");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đại cương", "Cơ sở ngành", "Chuyên ngành bắt buộc", "Chuyên ngành tự chọn" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jTextField2)
                    .addComponent(jTextField3)
                    .addComponent(jComboBox1, 0, 236, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btbq1)
                    .addComponent(btcn1))
                .addGap(123, 123, 123))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(bttm1)
                .addGap(204, 204, 204)
                .addComponent(btluu1)
                .addContainerGap(273, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttm1)
                    .addComponent(btluu1))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addComponent(jLabel2))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btcn1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btbq1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Môn học", jPanel1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sinh viên", "Họ tên", "Địa chỉ", "Số điện thoại"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        bttm2.setText("Thêm mới");
        bttm2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttm2ActionPerformed(evt);
            }
        });

        btluu2.setText("Lưu");
        btluu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btluu2ActionPerformed(evt);
            }
        });

        btcn2.setText("Cập nhật");
        btcn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcn2ActionPerformed(evt);
            }
        });

        btbq2.setText("Bỏ qua");
        btbq2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbq2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Mã sinh viên");

        jLabel6.setText("Họ tên");

        jLabel7.setText("Số điện thoại");

        jLabel8.setText("Địa chỉ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btcn2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btbq2)))
                .addGap(128, 128, 128))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bttm2)
                        .addGap(178, 178, 178)
                        .addComponent(btluu2))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(339, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttm2)
                    .addComponent(btluu2))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btcn2)
                        .addGap(78, 78, 78)
                        .addComponent(btbq2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sinh viên", jPanel2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sinh viên", "Tên sinh viên", "Mã môn học", "Tên môn học", "Thời gian đăng ký"
            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        bttm3.setText("Thêm mới");
        bttm3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttm3ActionPerformed(evt);
            }
        });

        btluu3.setText("Lưu");
        btluu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btluu3ActionPerformed(evt);
            }
        });

        jLabel9.setText("Mã sinh viên");

        jLabel10.setText("Mã môn học");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btcn3.setText("Cập nhật");
        btcn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcn3ActionPerformed(evt);
            }
        });

        btbq3.setText("Bỏ qua");
        btbq3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbq3ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Sắp xếp"));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Tên sinh viên");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Thời gian đăng ký");

        btsx.setText("Sắp xếp");
        btsx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(btsx)
                .addGap(32, 32, 32))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btsx)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(bttm3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btluu3)
                .addGap(219, 219, 219))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(65, 65, 65)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox2, 0, 119, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(152, 152, 152)
                .addComponent(btcn3)
                .addGap(83, 83, 83)
                .addComponent(btbq3)
                .addContainerGap(120, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bttm3)
                            .addComponent(btluu3))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btcn3)
                            .addComponent(btbq3))
                        .addGap(10, 10, 10)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Bảng đăng ký", jPanel4);

        jLabel11.setText("Tên môn học");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btds.setText("Danh sách");
        btds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdsActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(jLabel11)
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btds)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btds)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jTabbedPane1.addTab("Danh sách lớp", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttm1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttm1ActionPerformed
        changeState(State.themMH);
        int maMH = listMonHoc.size()+100;
        while(true) {
            if(timMonHoc(maMH)==null) {
                jTextField1.setText(maMH+"");
                break;
            }
            maMH++;
        }
    }//GEN-LAST:event_bttm1ActionPerformed

    private void btluu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btluu1ActionPerformed
        IOFile.writeFile(listMonHoc, "MonHoc.dat");
    }//GEN-LAST:event_btluu1ActionPerformed

    private void btcn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcn1ActionPerformed
        themMonHoc();
        refresh2Ma();
        changeState(State.normal);
    }//GEN-LAST:event_btcn1ActionPerformed

    private void btbq1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbq1ActionPerformed
        changeState(State.normal);
    }//GEN-LAST:event_btbq1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int check = jTable1.getSelectedRow();
        jTextField1.setText(tm1.getValueAt(check, 0).toString());
        jTextField2.setText(tm1.getValueAt(check, 1).toString());
        jTextField3.setText(tm1.getValueAt(check, 2).toString());
        jComboBox1.setSelectedItem(tm1.getValueAt(check, 3).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void bttm2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttm2ActionPerformed
        changeState(State.themSV);
        int masv = listSinhVien.size()+10000;
        while(true) {
            if(timSV(masv)==null) {
                jTextField4.setText(masv+"");
                break;
            }
            masv++;
        }
    }//GEN-LAST:event_bttm2ActionPerformed

    private void btluu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btluu2ActionPerformed
        IOFile.writeFile(listSinhVien, "SinhVien.dat");
    }//GEN-LAST:event_btluu2ActionPerformed

    private void btcn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcn2ActionPerformed
        themSV();
        refresh2Ma();
        changeState(State.normal);
    }//GEN-LAST:event_btcn2ActionPerformed

    private void btbq2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbq2ActionPerformed
        changeState(State.normal);
    }//GEN-LAST:event_btbq2ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int check = jTable2.getSelectedRow();
        jTextField4.setText(tm2.getValueAt(check, 0).toString());
        jTextField5.setText(tm2.getValueAt(check, 1).toString());
        jTextField6.setText(tm2.getValueAt(check, 2).toString());
        jTextField7.setText(tm2.getValueAt(check, 3).toString());
    }//GEN-LAST:event_jTable2MouseClicked

    private void bttm3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttm3ActionPerformed
        changeState(State.themBDK);
    }//GEN-LAST:event_bttm3ActionPerformed

    private void btluu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btluu3ActionPerformed
        IOFile.writeFile(listBDK, "BDK.dat");
    }//GEN-LAST:event_btluu3ActionPerformed

    private void btcn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcn3ActionPerformed
        themBDK();
        changeState(State.normal);
    }//GEN-LAST:event_btcn3ActionPerformed

    private void btbq3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbq3ActionPerformed
        changeState(State.normal);
    }//GEN-LAST:event_btbq3ActionPerformed

    private void btsxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsxActionPerformed
        if(jRadioButton1.isSelected()) {
            Collections.sort(listBDK, new Comparator<BDK>() {
                @Override
                public int compare(BDK o1, BDK o2) {
                    return o1.getHoTen().compareToIgnoreCase(o2.getHoTen());
                }
            });
        }
        else {
            Collections.sort(listBDK, new Comparator<BDK>() {
                @Override
                public int compare(BDK o1, BDK o2) {
                    return o1.getTgdk().compareToIgnoreCase(o2.getTgdk());
                }
            });
        }
        tm3.setRowCount(0);
        for(BDK b:listBDK) {
            tm3.addRow(b.toObject());
        }
    }//GEN-LAST:event_btsxActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseClicked

    private void btdsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdsActionPerformed
        jTextArea1.setText("STT\tHọ tên\tSố điện thoại\n");
        jTextArea1.append(timSVDK(jComboBox4.getSelectedItem().toString()));
    }//GEN-LAST:event_btdsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbq1;
    private javax.swing.JButton btbq2;
    private javax.swing.JButton btbq3;
    private javax.swing.JButton btcn1;
    private javax.swing.JButton btcn2;
    private javax.swing.JButton btcn3;
    private javax.swing.JButton btds;
    private javax.swing.JButton btluu1;
    private javax.swing.JButton btluu2;
    private javax.swing.JButton btluu3;
    private javax.swing.JButton btsx;
    private javax.swing.JButton bttm1;
    private javax.swing.JButton bttm2;
    private javax.swing.JButton bttm3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
