/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisteminformasisiswa.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sisteminformasisiswa.konekDB;

/**
 *
 * @author aris
 */
public class AdminGuru extends javax.swing.JPanel {


    SimpleDateFormat formatTanggal = new SimpleDateFormat("dd-MMM-yyyy");
    private DefaultTableModel model;
    ResultSet RsUser;
    Statement stm;
    
    
    public AdminGuru() {
        initComponents();
        
        model = new DefaultTableModel();
        tblGuru.setModel(model);
        
        model.addColumn("No");
        model.addColumn("NIP");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Tgl_lahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Mapel");
        model.addColumn("Kelas");
        model.addColumn("Telp");
        
        Scroll.setViewportView(tblGuru);
        btnAdd.setEnabled(false);
        btnUpdate.setEnabled(false);
        loadData();
    }
    
    public void loadData()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try
        {
            Connection con = konekDB.koneksi();
            stm = con.createStatement();
            RsUser = stm.executeQuery("select * from tb_pengampu\n" +
            "inner join tb_guru \n" +
            "on tb_pengampu.kd_guru = tb_guru.nip\n" +
            "inner join tb_mapel\n" +
            "on tb_pengampu.kd_mapel = tb_mapel.kd_mapel\n"+
            "inner join tb_kelas\n" +
            "on tb_pengampu.kd_kelas = tb_kelas.kd_kelas");
            
            while(RsUser.next())
            {
                Object[] o = new Object[9];
                
                o[0] = RsUser.getRow();
                o[1] = RsUser.getString("nip");
                o[2] = RsUser.getString("nm_guru").toUpperCase();
                o[3] = RsUser.getString("alamat").toUpperCase();
                o[4] = formatTanggal.format(RsUser.getDate("tgl_lahir"));
                o[5] = RsUser.getString("jenis_kelamin").toUpperCase();
                o[6] = RsUser.getString("nm_mapel").toUpperCase();
                o[7] = RsUser.getString("nm_kelas").toUpperCase();
                o[8] = RsUser.getString("telp");
                
                model.addRow(o);
            }
            
        }catch(SQLException error)
        {
            JOptionPane.showMessageDialog(null, error);
        }
        
        
    }
    private void hapus()
    {
        String nip = txtNip.getText();
        
        try
        {
            Connection con = konekDB.koneksi();
            
            
            
            
            
            for (int i=0;i<=2;i++)
            {
                if (i==0)
                {
                    String sql3 = "delete from usr_guru where id_user=?";
                    PreparedStatement p3 = con.prepareStatement(sql3);     
                    p3.setString(1, nip);
                    p3.executeUpdate();
                    p3.close();
                }
                else if (i==1)
                {
                    String sql2 = "delete from tb_pengampu where kd_guru=?";
                    PreparedStatement p2 = con.prepareStatement(sql2);
                    p2.setString(1, nip);
                    p2.executeUpdate();
                    p2.close();
                }
                else 
                {
                    String sql = "delete from tb_guru where nip=?";
                    PreparedStatement p = con.prepareStatement(sql);
                    p.setString(1, nip);
                    p.executeUpdate();
                    p.close();
                }
            }
    
        
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        
        }finally
        {
            loadData();
        }
        
    }
    
    private void tambahData()
    {
        
        
        String nip = txtNip.getText();
        String nama = txtNama.getText();
        String alamat = txtAlamat.getText();
        java.util.Date lahir = (java.util.Date) txtLahir.getValue();
        String telp = txtHp.getText();
        String mapel ="";
        String kelamin ="";
        String kelas ="";
        
        try
        {
            //KELAS
            if (pilihKelas.getSelectedIndex()==0)
            {
                kelas = "IA";
            }
            else if (pilihKelas.getSelectedIndex()==1)
            {
                kelas ="IS";
            }
            
            //KELAMIN
            if (pilihJK.getSelectedIndex()==0)
            {
                kelamin = "L";
            }
            else if (pilihJK.getSelectedIndex()==1)
            {
                kelamin = "P";
            }
            
            //MAPEL
            if (pilihMapel.getSelectedIndex()==0)
            {
                mapel = "MPBIO";
            }
            else if (pilihMapel.getSelectedIndex()==1)
            {
                mapel = "MPFIS";
            }
            else if (pilihMapel.getSelectedIndex()==2)
            {
                mapel = "MPINDO";
                
            }
            else if (pilihMapel.getSelectedIndex()==3)
            {
                mapel = "MPING";
                
            }
            else if (pilihMapel.getSelectedIndex()==4)
            {
                mapel = "MPKIMIA";
                
            }
            else if (pilihMapel.getSelectedIndex()==5)
            {
                mapel = "MPMAT";
            }
            
            Connection con = konekDB.koneksi();
            
            String sql = "insert into tb_guru values(?,?,?,?,?,?)";
            String sql2 = "insert into tb_pengampu values(?,?,?)";
            String sql3 = "insert into usr_guru values(?,?,?,?)";
            
            
            
            PreparedStatement p = con.prepareStatement(sql);
            PreparedStatement p2 = con.prepareStatement(sql2);
            PreparedStatement p3 = con.prepareStatement(sql3);            
            
            p.setString(1, nip);
            p.setString(2, nama);
            p.setString(3, kelamin);
            p.setString(4, telp);
            p.setString(5, alamat);
            p.setDate(6, new java.sql.Date(lahir.getTime()));
            
            p2.setString(1, kelas);
            p2.setString(2, mapel);
            p2.setString(3, nip);
            
            p3.setString(1, nip);
            p3.setString(2, nip);
            p3.setString(3, nip);
            p3.setString(4, "Guru");
            
            p.executeUpdate();
            p2.executeUpdate();
            p3.executeUpdate();
            
            p.close();
            p2.close();
            p3.close();
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }finally
        {
            loadData();
        }
    }
    
    private void editData()
    {
        
        int i = tblGuru.getSelectedRow();
        if (i == -i)
        {
            return;
        }
        
        String a= (String) model.getValueAt(i, 1);
        String nip = a.toString();
        
        String nama = txtNama.getText();
        String alamat = txtAlamat.getText();
        java.util.Date lahir = (java.util.Date) txtLahir.getValue();
        String telp = txtHp.getText();
        String mapel ="";
        String kelamin ="";
        String kelas ="";
        

        try{
            if (pilihKelas.getSelectedIndex()==0)
            {
                kelas = "IA";
            }
            else if (pilihKelas.getSelectedIndex()==1)
            {
                kelas ="IS";
            }
            
            //KELAMIN
            if (pilihJK.getSelectedIndex()==0)
            {
                kelamin = "L";
            }
            else if (pilihJK.getSelectedIndex()==1)
            {
                kelamin = "P";
            }
            
            //MAPEL
            if (pilihMapel.getSelectedIndex()==0)
            {
                mapel = "MPBIO";
            }
            else if (pilihMapel.getSelectedIndex()==1)
            {
                mapel = "MPFIS";
            }
            else if (pilihMapel.getSelectedIndex()==2)
            {
                mapel = "MPINDO";
                
            }
            else if (pilihMapel.getSelectedIndex()==3)
            {
                mapel = "MPING";
                
            }
            else if (pilihMapel.getSelectedIndex()==4)
            {
                mapel = "MPKIMIA";
                
            }
            else if (pilihMapel.getSelectedIndex()==5)
            {
                mapel = "MPMAT";
            }
            
            Connection con = konekDB.koneksi();
            
            for (i=0;i<=1;i++)
            {
                if (i==0)
                {
                    String sql = "update tb_guru set nm_guru=?,jenis_kelamin=?,telp=?,alamat=?,tgl_lahir=? where nip=?";
                    PreparedStatement p = con.prepareStatement(sql);

                    p.setString(6, nip);
                    p.setString(1, nama);
                    p.setString(2, kelamin);
                    p.setString(3, telp);
                    p.setString(4, alamat);
                    p.setDate(5, new java.sql.Date(lahir.getTime()));
                    
                    p.executeUpdate();
                    p.close();
                }
                else if (i==1)
                {
                    String sql2 = "update tb_pengampu set kd_kelas=?,kd_mapel=? where kd_guru=?";
                    PreparedStatement p2 = con.prepareStatement(sql2);
                    p2.setString(1, kelas);
                    p2.setString(2, mapel);
                    p2.setString(3, nip);
                    
                    p2.executeUpdate();
                    p2.close();
                }
                
            }
            
            
        
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

        PanelAdminGuru = new javax.swing.JDesktopPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNip = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        txtHp = new javax.swing.JTextField();
        txtLahir = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        pilihMapel = new javax.swing.JComboBox();
        pilihJK = new javax.swing.JComboBox();
        pilihKelas = new javax.swing.JComboBox();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Scroll = new javax.swing.JScrollPane();
        tblGuru = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();

        jLabel1.setText("NIP");

        jLabel2.setText("NAMA");

        jLabel3.setText("TGL LAHIR");

        jLabel4.setText("KELAS");

        jLabel5.setText("ALAMAT");

        jLabel6.setText("HP");

        jLabel7.setText("MAPEL");

        txtLahir.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd-MMM-yyyy"))));

        jLabel8.setText("J KELAMIN");

        pilihMapel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MPBIO   (Biologi)", "MPFIS    (Fisika)", "MPINDO (B. Indonesia)", "MPING   (B. Inggris)", "MPKIMIA (Kimia)", "MPMAT  (Matematika)" }));

        pilihJK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-Laki", "Perempuan" }));

        pilihKelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "IPA", "IPS" }));

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/package-reinstall.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/package-upgrade.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNip, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHp, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLahir)
                            .addComponent(pilihKelas, 0, 134, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pilihMapel, 0, 1, Short.MAX_VALUE)
                            .addComponent(pilihJK, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pilihKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtHp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(pilihMapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(pilihJK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate))
                .addContainerGap())
        );

        tblGuru.setModel(new javax.swing.table.DefaultTableModel(
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
        tblGuru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGuruMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tblGuru);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelAdminGuruLayout = new javax.swing.GroupLayout(PanelAdminGuru);
        PanelAdminGuru.setLayout(PanelAdminGuruLayout);
        PanelAdminGuruLayout.setHorizontalGroup(
            PanelAdminGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAdminGuruLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelAdminGuruLayout.setVerticalGroup(
            PanelAdminGuruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelAdminGuru.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        PanelAdminGuru.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/window-new.png"))); // NOI18N
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/edit-copy.png"))); // NOI18N
        btnEdit.setText("EDIT");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/gtk-undelete-rtl.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisteminformasisiswa/icon/lxfind.png"))); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCari)
                                .addComponent(btnEdit)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelAdminGuru)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelAdminGuru))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblGuruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGuruMouseClicked
        int i = tblGuru.getSelectedRow();
        
        if (i == -1)
        {
            return;
        }
        
        txtNip.setEnabled(false);
        String nip = (String) model.getValueAt(i, 1);
        txtNip.setText(nip);
        String nama = (String) model.getValueAt(i, 2);
        txtNama.setText(nama);
        String Alamat = (String) model.getValueAt(i, 3);
        txtAlamat.setText(Alamat);
        String lahir = (String) model.getValueAt(i, 4);
        txtLahir.setText(lahir);
        String kelamin = (String) model.getValueAt(i, 5);
        if(kelamin.equals("l"))
        {
            pilihJK.setSelectedItem("Laki-laki");
        }
        else if (kelamin.equals("p"))
        {
            pilihJK.setSelectedItem("Perempuan");
        }
        String Mapel = (String) model.getValueAt(i, 6);
        
        String kelas = (String) model.getValueAt(i, 7);
        if (kelas.equals("IPA"))
        {
            pilihKelas.setSelectedItem("IPA");
        }
        else if (kelas.equals("IPS"))
        {
            pilihKelas.setSelectedItem("IPS");
        }
        
        String telp = (String) model.getValueAt(i, 8);
        txtHp.setText(telp);
        
        
        
        
        
    }//GEN-LAST:event_tblGuruMouseClicked

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        btnAdd.setEnabled(true);
        btnUpdate.setVisible(false);
        btnUpdate.setEnabled(false);
        txtNip.setText(null);
        
        txtNama.setText(null);
        
        txtAlamat.setText(null);
        
        txtLahir.setText(null);
        txtHp.setText(null);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try
        {
            tambahData();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try
        {
            editData();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try
        {
            btnAdd.setEnabled(false);
            btnAdd.setVisible(false);
            btnUpdate.setVisible(true);
            btnUpdate.setEnabled(true);
        
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try
        {
            hapus();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try{
        Connection con = konekDB.koneksi();
        stm = con.createStatement();
        String cariNip = txtCari.getText();
        
        RsUser = stm.executeQuery("select * from tb_pengampu\n" +
            "inner join tb_guru \n" +
            "on tb_pengampu.kd_guru = tb_guru.nip\n" +
            "inner join tb_mapel\n" +
            "on tb_pengampu.kd_mapel = tb_mapel.kd_mapel\n"+
            "inner join tb_kelas\n" +
            "on tb_pengampu.kd_kelas = tb_kelas.kd_kelas where tb_pengampu.kd_guru='"+cariNip+"'");
        
        while(RsUser.next())
        {
            Object[] o = new Object[9];

            o[0] = RsUser.getRow();
                o[1] = RsUser.getString("nip");
                o[2] = RsUser.getString("nm_guru").toUpperCase();
                o[3] = RsUser.getString("alamat").toUpperCase();
                o[4] = formatTanggal.format(RsUser.getDate("tgl_lahir"));
                o[5] = RsUser.getString("jenis_kelamin").toUpperCase();
                o[6] = RsUser.getString("nm_mapel").toUpperCase();
                o[7] = RsUser.getString("nm_kelas").toUpperCase();
                o[8] = RsUser.getString("telp");
                
                model.addRow(o);
        }
        }
        catch(SQLException error)
        {
            JOptionPane.showMessageDialog(null,error);
        }
    }//GEN-LAST:event_btnCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane PanelAdminGuru;
    private javax.swing.JScrollPane Scroll;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JComboBox pilihJK;
    private javax.swing.JComboBox pilihKelas;
    private javax.swing.JComboBox pilihMapel;
    private javax.swing.JTable tblGuru;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHp;
    private javax.swing.JFormattedTextField txtLahir;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNip;
    // End of variables declaration//GEN-END:variables
}
