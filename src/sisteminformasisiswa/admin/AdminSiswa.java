/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisteminformasisiswa.admin;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import sisteminformasisiswa.konekDB;
/**
 *
 * @author aris
 */
public class AdminSiswa extends javax.swing.JPanel {

    SimpleDateFormat formatTanggal = new SimpleDateFormat("dd-MMM-yyyy");
    private DefaultTableModel model;
    ResultSet RsUser;
    Statement stm;
    public AdminSiswa() {
        initComponents();
        model = new DefaultTableModel();
        tblSiswa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblSiswa.setModel(model);
        
        model.addColumn("No");
        
        
        
        model.addColumn("NIS");
        model.addColumn("Nama");
        model.addColumn("Kelas");
        model.addColumn("Angkatan");
        model.addColumn("Alamat");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Tempat Lahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Agama");
        model.addColumn("Telp");
        
        Scroll.setViewportView(tblSiswa);
        
        loadData();
        noEdit();
        
    }
    public void loadData()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
        Connection con = konekDB.koneksi();
        stm = con.createStatement();
        RsUser = stm.executeQuery("select * from d_siswa inner join tb_kelas on tb_kelas.kd_kelas=d_siswa.kd_kelas");
        
        while(RsUser.next())
        {
            Object[] o = new Object[11];
            
            
            o[0] = RsUser.getRow();
            o[1] = RsUser.getString("nis");
            o[2] = RsUser.getString("nm_siswa").toUpperCase();
            o[3] = RsUser.getString("nm_kelas");
            o[4] = RsUser.getString("tahun_angkatan");
            o[5] = RsUser.getString("alamat").toUpperCase();
            o[6] = formatTanggal.format(RsUser.getDate("tgl_lahir"));
            o[7] = RsUser.getString("tempat_lahir");
            o[8] = RsUser.getString("jenis_kelamin").toUpperCase();
            o[9] = RsUser.getString("agama").toUpperCase();
            o[10] = RsUser.getString("telp");
            model.addRow(o);
        }
        
        }catch(SQLException error)
        {
            JOptionPane.showMessageDialog(null, error);
        }
        
        
    }
    
    public void noEdit()
    {
            btnAdd.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnCancel.setEnabled(false);
    }
    public void bisaEdit()
    {
        this.panelSiswa.show(true);
            
    }

    public void tmbahData()
    {
        String nim = txtNis.getText();
        String nama = txtNama.getText();
        String alamat = txtAlamat.getText();
        String tmpt = txtTempat.getText();
        String agama = txtAgama.getText();
        String telp = txtTelp.getText();
        String kel = txtKelamin.getText();
        String kelas = "";
        String angkat = txtAngkatan.getText();
        java.util.Date lahir = (java.util.Date) txtLahir.getValue();
        String tingkat ="siswa";
                
        try
        {
            if(this.KelasPilih.getSelectedIndex() == 0)
            {
                kelas = "IA";
            }
            
            else
            {
                kelas = "IS";
            }
            
            Connection con = konekDB.koneksi();
            
            String sql = "insert into d_siswa values(?,?,?,?,?,?,?,?,?,?)";
            String sql2 = "insert into user values(?,?,?,?)";
            
            PreparedStatement p = con.prepareStatement(sql);
            PreparedStatement p2 = con.prepareStatement(sql2);
            
            p.setString(1, nim);
            p.setString(2, nama);
            p.setString(3, alamat);
            p.setString(4, tmpt);
            p.setDate(5, new java.sql.Date(lahir.getTime()));
            p.setString(6, agama);
            p.setString(7, kel);
            p.setString(8, telp);
            p.setString(9, angkat);
            p.setString(10, kelas);
            
            p2.setString(1, nim);
            p2.setString(2, nim);
            p2.setString(3, nim);
            p2.setString(4, tingkat);
            
            p.executeUpdate();
            p2.executeUpdate();
            p.close();
            p2.close();
            
      
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        
        }finally
        {
            loadData();
            
        }
    }
    public void hapusData()
    {
        String nim = txtNis.getText();
        
        try
        {
            Connection con = konekDB.koneksi();
            for (int i = 0;i<=1;i++)
            {
                if (i==0)
                {
                    String sql1 = "Delete from user where id_user='"+nim+"'";
                    
            
                    PreparedStatement p = con.prepareStatement(sql1);
            
            
                    p.executeUpdate();
            
                    p.close();
            
                }
                else
                {
                    String sql2 = "Delete from d_siswa where nis ='"+nim+"'";
                    PreparedStatement p2 = con.prepareStatement(sql2);
                    p2.executeUpdate();
                    p2.close();
                }
            }
            
            
            
        }catch(SQLException er)
        {
            JOptionPane.showMessageDialog(null, er);
        }finally
        {
            loadData();
            noEdit();
        }
        
        
    }
    public void tampilData()
    {
        int i = tblSiswa.getSelectedRow();
        
        if (i ==-1)
        {
            return;
        }
        String nis = (String) model.getValueAt(i, 1);
        txtNis.setText(nis);
        String Nama = (String) model.getValueAt(i, 2);
        txtNama.setText(Nama);
        String alamat = (String) model.getValueAt(i, 3);
        txtAlamat.setText(alamat);
        String tmpat = (String) model.getValueAt(i, 4);
        txtTempat.setText(tmpat);
        String lahir = (String) model.getValueAt(i, 5);
        txtLahir.setText(lahir);
        String agama = (String) model.getValueAt(i, 6);
        txtAgama.setText(agama);
        String kel = (String) model.getValueAt(i, 7);
        txtKelamin.setText(kel);
        String telp = (String) model.getValueAt(i, 8);
        txtTelp.setText(telp);
        String angkat = (String) model.getValueAt(i, 9);
        txtAngkatan.setText(angkat);

    }
    
    
    
    public void editData()
    {
        
        
        String nis= txtNis.getText();
        
        String nama = txtNama.getText();
        String alamat = txtAlamat.getText();
        String tmpt = txtTempat.getText();
        String agama = txtAgama.getText();
        String telp = txtTelp.getText();
        String kel = txtKelamin.getText();
        String kelas = "";
        String angkat = txtAngkatan.getText();
        java.util.Date lahir = (java.util.Date) txtLahir.getValue();
        
        if(this.KelasPilih.getSelectedIndex() == 0)
            {
                kelas = "IA";
            }
            
            else
            {
                kelas = "IS";
            }
        
        try{
            
            
            Connection con = konekDB.koneksi();
            String sql = "update d_siswa set nm_siswa=?,alamat=?,tempat_lahir=?,"
                    + "tgl_lahir=?,agama=?,jenis_kelamin=?,telp=?,tahun_angkatan=?,kd_kelas=? where nis=?";
            
            PreparedStatement p = con.prepareStatement(sql);
            
            
            p.setString(1, nama);
            p.setString(2, alamat);
            p.setString(3, tmpt);
            p.setDate(4, new java.sql.Date(lahir.getTime()));
            p.setString(5, agama);
            p.setString(6, kel);
            p.setString(7, telp);
            p.setString(8, angkat);
            p.setString(9, kelas);
            p.setString(10, nis);
            
            p.executeUpdate();
            p.close();
        
        }catch(SQLException error)
        {
            JOptionPane.showMessageDialog(null, error);
        }finally{
            loadData();
            
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtCariNis = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        PanelAdminSiswa = new javax.swing.JDesktopPane();
        panelSiswa = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNis = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblAgama = new javax.swing.JLabel();
        lblAgama1 = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        txtKelamin = new javax.swing.JTextField();
        txtAgama = new javax.swing.JTextField();
        txtTelp = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtLahir = new javax.swing.JFormattedTextField();
        txtTempat = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAngkatan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        KelasPilih = new javax.swing.JComboBox();
        btnUpdate = new javax.swing.JButton();
        Scroll = new javax.swing.JScrollPane();
        tblSiswa = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/window-new.png"))); // NOI18N
        btnTambah.setText("TAMBAH DATA");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/edit-copy.png"))); // NOI18N
        btnEdit.setText("EDIT DATA");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/gtk-undelete-rtl.png"))); // NOI18N
        btnHapus.setText("HAPUS DATA");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtCariNis.setText("Cari NIS Siswa");

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/lxfind.png"))); // NOI18N
        btnCari.setText("OK");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah)
                .addGap(18, 18, 18)
                .addComponent(btnEdit)
                .addGap(18, 18, 18)
                .addComponent(btnHapus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCariNis, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(btnCari)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnEdit)
                    .addComponent(btnHapus)
                    .addComponent(txtCariNis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("NIS");

        jLabel2.setText("NAMA");

        jLabel3.setText("KELAS");

        jLabel4.setText("ALAMAT");

        jLabel5.setText("TGL LAHIR");

        jLabel6.setText("JENIS KELAMIN");

        lblAgama.setText("AGAMA");

        lblAgama1.setText("TELP");

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/package-reinstall.png"))); // NOI18N
        btnAdd.setText("ADD");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/package-purge.png"))); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtLahir.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd-MMM-yyyy"))));

        jLabel7.setText("TMPT LAHIR");

        jLabel8.setText("Angkatan");

        KelasPilih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "IPA", "IPS" }));

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/package-upgrade.png"))); // NOI18N
        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSiswaLayout = new javax.swing.GroupLayout(panelSiswa);
        panelSiswa.setLayout(panelSiswaLayout);
        panelSiswaLayout.setHorizontalGroup(
            panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSiswaLayout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSiswaLayout.createSequentialGroup()
                        .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(29, 29, 29)
                        .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTempat)
                            .addComponent(txtKelamin)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSiswaLayout.createSequentialGroup()
                        .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAgama)
                            .addComponent(lblAgama1))
                        .addGap(79, 79, 79)
                        .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAgama)
                            .addComponent(txtTelp)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSiswaLayout.createSequentialGroup()
                        .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(87, 87, 87)
                        .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNama)
                            .addComponent(txtNis)))
                    .addGroup(panelSiswaLayout.createSequentialGroup()
                        .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(57, 57, 57)
                        .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLahir)
                            .addComponent(txtAlamat)
                            .addComponent(KelasPilih, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAngkatan))))
                .addContainerGap())
        );
        panelSiswaLayout.setVerticalGroup(
            panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(KelasPilih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAngkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(19, 19, 19)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTempat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(21, 21, 21)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAgama)
                    .addComponent(txtAgama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAgama1)
                    .addComponent(txtTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addGroup(panelSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnCancel)
                    .addComponent(btnUpdate))
                .addContainerGap())
        );

        Scroll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ScrollMouseClicked(evt);
            }
        });

        tblSiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSiswaMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tblSiswa);

        javax.swing.GroupLayout PanelAdminSiswaLayout = new javax.swing.GroupLayout(PanelAdminSiswa);
        PanelAdminSiswa.setLayout(PanelAdminSiswaLayout);
        PanelAdminSiswaLayout.setHorizontalGroup(
            PanelAdminSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAdminSiswaLayout.createSequentialGroup()
                .addComponent(panelSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PanelAdminSiswaLayout.setVerticalGroup(
            PanelAdminSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAdminSiswaLayout.createSequentialGroup()
                .addGroup(PanelAdminSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Scroll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                    .addComponent(panelSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelAdminSiswa.setLayer(panelSiswa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        PanelAdminSiswa.setLayer(Scroll, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelAdminSiswa)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelAdminSiswa))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        try
        {
            btnUpdate.show(false);
            btnAdd.setEnabled(true);
            btnCancel.setEnabled(true);
            btnAdd.show();
            btnCancel.show();
                    
            clear();
        }catch(Exception er)
        {
            JOptionPane.showMessageDialog(null, er);
        }
        
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        tmbahData();
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        noEdit();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try
        {
            bisaEdit();
            btnAdd.show(false);
            btnUpdate.show(true);
            btnAdd.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnCancel.setEnabled(true);
            btnCancel.show(true);
            
            clear();
        }catch(Exception er)
        {
        }
        
    }//GEN-LAST:event_btnEditActionPerformed

    private void ScrollMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScrollMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ScrollMouseClicked

    private void tblSiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSiswaMouseClicked
        try
        {
            int i = tblSiswa.getSelectedRow();
            txtNis.setEditable(false);
        
            if (i ==-1)
            {
                return;
            }
            String nis = (String) model.getValueAt(i, 1);
            txtNis.setText(nis);
            String Nama = (String) model.getValueAt(i, 2);
            txtNama.setText(Nama);
            String alamat = (String) model.getValueAt(i, 5);
            txtAlamat.setText(alamat);
            String tmpat = (String) model.getValueAt(i, 7);
            txtTempat.setText(tmpat);
            String lahir = (String) model.getValueAt(i, 6);
            txtLahir.setText(lahir);
            String agama = (String) model.getValueAt(i, 9);
            txtAgama.setText(agama);
            String kel = (String) model.getValueAt(i, 8);
            txtKelamin.setText(kel);
            String telp = (String) model.getValueAt(i, 10);
            txtTelp.setText(telp);
            String angkat = (String) model.getValueAt(i, 4);
            txtAngkatan.setText(angkat);
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tblSiswaMouseClicked

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try{
        Connection con = konekDB.koneksi();
        stm = con.createStatement();
        String cariNis = txtCariNis.getText();
        
        RsUser = stm.executeQuery("select * from d_siswa inner join tb_kelas on tb_kelas.kd_kelas=d_siswa.kd_kelas where d_siswa.nis = '"+cariNis+"'");
        
        while(RsUser.next())
        {
            Object[] o = new Object[11];

            o[0] = RsUser.getRow();
            o[1] = RsUser.getString("nis");
            o[2] = RsUser.getString("nm_siswa").toUpperCase();
            o[3] = RsUser.getString("nm_kelas");
            o[4] = RsUser.getString("tahun_angkatan");
            o[5] = RsUser.getString("alamat").toUpperCase();
            o[6] = formatTanggal.format(RsUser.getDate("tgl_lahir"));
            o[7] = RsUser.getString("tempat_lahir");
            o[8] = RsUser.getString("jenis_kelamin").toUpperCase();
            o[9] = RsUser.getString("agama").toUpperCase();
            o[10] = RsUser.getString("telp");
            model.addRow(o);
        }
        }
        catch(SQLException error)
        {
            JOptionPane.showMessageDialog(null,error);
        }
        
        
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try
        {
            editData();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    public void clear()
    {
        txtNis.setText(null);
        txtNama.setText(null);
        txtAlamat.setText(null);
        txtTempat.setText(null);
        txtAgama.setText(null);
        txtTelp.setText(null);
        txtKelamin.setText(null);
        txtAngkatan.setText("");
        txtLahir.setValue(null);
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox KelasPilih;
    private javax.swing.JDesktopPane PanelAdminSiswa;
    private javax.swing.JScrollPane Scroll;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAgama;
    private javax.swing.JLabel lblAgama1;
    private javax.swing.JPanel panelSiswa;
    private javax.swing.JTable tblSiswa;
    private javax.swing.JTextField txtAgama;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtAngkatan;
    private javax.swing.JTextField txtCariNis;
    private javax.swing.JTextField txtKelamin;
    private javax.swing.JFormattedTextField txtLahir;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNis;
    private javax.swing.JTextField txtTelp;
    private javax.swing.JTextField txtTempat;
    // End of variables declaration//GEN-END:variables
}
