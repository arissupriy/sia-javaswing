/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisteminformasisiswa;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author aris
 */
public class konekDB {
    private static Connection konek;
    public static Connection koneksi()
    {
    if(konek==null)
    {       
        try
        {
            String url = new String();
            String user = new String();
            String pass = new String();
            url = "jdbc:mysql://localhost:3306/raport";
            user = "sisfo";
            pass = "sisfo1996";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            konek = (Connection) DriverManager.getConnection(url,user,pass);
                
        }catch(SQLException error)
        {
            JOptionPane.showMessageDialog(null, "SQLException: "+error.getMessage()+""
                        + "\nSQLState: "+error.getSQLState()+"\n"
                        + "VendorError: "+error.getErrorCode()+"");
        }
    }
    return konek;
    }
    
    
}
