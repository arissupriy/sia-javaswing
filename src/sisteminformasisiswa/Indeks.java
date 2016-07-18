/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisteminformasisiswa;

import javax.swing.JOptionPane;

/**
 *
 * @author aris
 */
public class Indeks {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainMenu utama = new MainMenu();
        try{
            utama.setVisible(true);
        }catch(Exception error)
        {
            JOptionPane.showMessageDialog(utama, error);
        }
        
    }
    
}
