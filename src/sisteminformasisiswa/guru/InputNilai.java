/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisteminformasisiswa.guru;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import sisteminformasisiswa.konekDB;

/**
 *
 * @author aris
 */
public class InputNilai extends javax.swing.JPanel {

    private DefaultTableModel model;
    String nipGuru="";
    String nipBaru="";
    ResultSet RsUser;
    String kode="";
    String kodeBaru="";
    
    Statement stm;
    
    
    public InputNilai() {
        initComponents();
        model = new DefaultTableModel();
        
        ScrollNilai.setViewportView(tblNilai);
        tblNilai.setModel(model);
        
        model.addColumn("No");
        model.addColumn("NIS");
        model.addColumn("Nama");
        model.addColumn("Tugas");
        model.addColumn("Ulangan");
        model.addColumn("UTS");
        model.addColumn("UAS");
        
        txtKode.setEditable(false);
        txtMapel.setEditable(false);
        txtNama.setEnabled(false);
        
        
        
        loadData(nipBaru);
        //load(nipBaru);
    }
    
    
    public void diri(String nip)
    {
        try
        {
        String nipGuru = new String(nip);
        Connection con = konekDB.koneksi();
        stm = con.createStatement();
        RsUser = stm.executeQuery("select * from tb_pengampu inner join tb_mapel\n" +
                                    "on tb_mapel.kd_mapel = tb_pengampu.kd_mapel where kd_guru ='"+nipGuru+"'");
        while(RsUser.next())
        {
            lblMapel.setText(RsUser.getString("nm_mapel"));
            
            txtMapel.setText(RsUser.getString("nm_mapel"));
            
            kode = (RsUser.getString("kd_mapel"));
            txtKode.setText(kode);
            
            kodeBaru= kode;
            
            
        }
        RsUser.close();
            
        stm.close();
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
    } 
    public void loadData(String nip)
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try
        {
            String nipGuru = new String(nip);
            
            Connection c = konekDB.koneksi();
            stm = c.createStatement();
            RsUser = stm.executeQuery("select * from tb_rapor\n" +
                                        "inner join d_siswa \n" +
                                        "on d_siswa.nis = tb_rapor.nis\n" +
                                        "where tb_rapor.nip='"+nipGuru+"'");
            
            nipBaru = nipGuru;

            while(RsUser.next())
            {
                txtUlangan.setText(kode);
                
                
                
                Object[] o = new Object[7];
                o[0] = RsUser.getRow();
                o[1] = RsUser.getString("d_siswa.nis");
                o[2] = RsUser.getString("d_siswa.nm_siswa");
                o[3] = RsUser.getString("tb_rapor.ulangan").toUpperCase();
                o[4] = RsUser.getString("tb_rapor.tugas").toUpperCase();
                o[5] = RsUser.getString("tb_rapor.uts").toUpperCase();
                o[6] = RsUser.getString("tb_rapor.uas").toUpperCase();
                
                model.addRow(o);
                
            }
            
            RsUser.close();
            
            stm.close();
            
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void tmbahNilai()
    {
        
        String kode = txtKode.getText();
        String mapel = txtMapel.getText();
        String nim = txtNis.getText();
        String nama = txtNama.getText();
        String tugas = txtTugas.getText();
        String ulangan = txtUlangan.getText();
        String uts = txtUts.getText();
        String uas = txtUas.getText();
        
        
                
        try
        {
            
            
            Connection con = konekDB.koneksi();
            
            
            String sql = "insert into tb_rapor values(?,?,?,?,?,?,?)";
            
            
            PreparedStatement p = con.prepareStatement(sql);
            
            
            
            p.setString(1, kode);
            p.setString(2, nipBaru);
            p.setString(3, nim);
            p.setString(4, tugas);
            p.setString(5, ulangan);
            p.setString(6, uts);
            p.setString(7, uas);
            
            
            p.executeUpdate();
            
            p.close();
         
      
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        
        }finally
        {
            loadData(nipBaru);
            
        }
    
    }
    
    private void clear()
    {
        txtNis.setText(null);
        txtUlangan.setText(null);
        txtTugas.setText(null);
        txtUts.setText(null);
        txtUas.setText(null);
    }
    private void editData()
    {
        int i = tblNilai.getSelectedRow();
        
        String nis= txtNis.getText();
                
        String kode = txtKode.getText();
        String mapel = txtMapel.getText();
        
        String nama = txtNama.getText();
        String tugas = txtTugas.getText();
        String ulangan = txtUlangan.getText();
        String uts = txtUts.getText();
        String uas = txtUas.getText();
        
        try
        {
            Connection con = konekDB.koneksi();
            String sql = "update tb_rapor set tugas=?,ulangan=?,uts=?,uas=? where nis=? and nip=?";
            
            PreparedStatement p = con.prepareStatement(sql);
            
            p.setString(1, tugas);
            p.setString(2, ulangan);
            p.setString(3, uts);
            p.setString(4, uas);
            p.setString(5, nis);
            p.setString(6, nipBaru);
            
            p.executeUpdate();
            p.close();
        }catch(SQLException e)
        {
           JOptionPane.showMessageDialog(null, e);
        }finally
        {
            loadData(nipBaru);
        }
    }
    
    private void hapus()
    {
        String nis= txtNis.getText();
        
        try
        {
            Connection con = konekDB.koneksi();
            
               
                    String sql = "Delete from tb_rapor where nis=? and nip=?";
                    
            
                    PreparedStatement p = con.prepareStatement(sql);
                    
                    p.setString(1, nis);
                    p.setString(2, nipBaru);
                    p.executeUpdate();
            
                    p.close();
              
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }finally
        {
            loadData(nipBaru);
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
        jPanel2 = new javax.swing.JPanel();
        lblMapel = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        txtMapel = new javax.swing.JTextField();
        txtNis = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtTugas = new javax.swing.JTextField();
        txtUlangan = new javax.swing.JTextField();
        txtUts = new javax.swing.JTextField();
        txtUas = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        ScrollNilai = new javax.swing.JScrollPane();
        tblNilai = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        lblMapel.setText("MAPEL");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMapel)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMapel)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)))
        );

        jLabel1.setText("NAMA MAPEL");

        jLabel3.setText("NIS");

        jLabel4.setText("NAMA");

        jLabel2.setText("KODE MAPEL");

        jLabel5.setText("NILAI");

        jLabel6.setText("TUGAS");

        jLabel7.setText("ULANGAN HARIAN");

        jLabel8.setText("UTS");

        jLabel9.setText("UAS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(87, 87, 87)
                        .addComponent(txtTugas))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUlangan))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(106, 106, 106)
                        .addComponent(txtUts))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(105, 105, 105)
                        .addComponent(txtUas))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 136, Short.MAX_VALUE))
                            .addComponent(txtKode)
                            .addComponent(txtMapel)
                            .addComponent(txtNis)
                            .addComponent(txtNama))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtUlangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtUts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtUas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblNilai.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNilai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNilaiMouseClicked(evt);
            }
        });
        ScrollNilai.setViewportView(tblNilai);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollNilai, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollNilai, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNilaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNilaiMouseClicked
        try
        {
            int i = tblNilai.getSelectedRow();
            
        
            if (i ==-1)
            {
                return;
            }
            String nis = (String) model.getValueAt(i, 1);
            txtNis.setText(nis);
            String Nama = (String) model.getValueAt(i, 2);
            txtNama.setText(Nama);
            String Tugas = (String) model.getValueAt(i, 3);
            txtTugas.setText(Tugas);
            String Ulangan = (String) model.getValueAt(i, 4);
            txtUlangan.setText(Ulangan);
            String UTS = (String) model.getValueAt(i, 5);
            txtUts.setText(UTS);
            String UAS = (String) model.getValueAt(i, 6);
            txtUas.setText(UAS);
            
            
            
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tblNilaiMouseClicked

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        
        try
        {
            tmbahNilai();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }finally
        {
            clear();
        }
        
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try
        {
            editData();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }finally
        {
            clear();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try
        {
            hapus();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }finally
        {
            clear();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollNilai;
    private javax.swing.JButton btnDelete;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblMapel;
    private javax.swing.JTable tblNilai;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtMapel;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNis;
    private javax.swing.JTextField txtTugas;
    private javax.swing.JTextField txtUas;
    private javax.swing.JTextField txtUlangan;
    private javax.swing.JTextField txtUts;
    // End of variables declaration//GEN-END:variables
}
