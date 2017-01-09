/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author dolidont
 */
public class VueBouton {
    private JPanel mainPanel;
    
    
    public VueBouton(){
        mainPanel = new JPanel(new GridLayout(1,4));
        
        JPanel panel1 = new JPanel(new BorderLayout()); 
        mainPanel.add(panel1);
        ImageIcon deplacer = new ImageIcon(new ImageIcon("images/icones/iconMove.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT)); 
        JLabel dep = new JLabel(deplacer);
        panel1.add(dep, BorderLayout.CENTER);
        JLabel label = new JLabel("Se déplacer");
        panel1.add(label, BorderLayout.SOUTH);
        
        JPanel panel2 = new JPanel(new BorderLayout()); 
        mainPanel.add(panel2);
        ImageIcon assecher = new ImageIcon(new ImageIcon("images/icones/iconDry.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT)); 
        JLabel assech = new JLabel(assecher);
        panel2.add(assech);
        label = new JLabel("Assecher");
        panel2.add(label, BorderLayout.SOUTH);
        
        JPanel panel3 = new JPanel(new BorderLayout()); 
        mainPanel.add(panel3);
        ImageIcon donnerCarte = new ImageIcon(new ImageIcon("images/icones/iconGive.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT)); 
        JLabel give = new JLabel(donnerCarte);
        panel1.add(give);
        label = new JLabel("Donner une carte");
        panel1.add(label, BorderLayout.SOUTH);
        
        JPanel panel4 = new JPanel(new BorderLayout());
        mainPanel.add(panel4);
        ImageIcon tresor = new ImageIcon(new ImageIcon("images/icones/iconGet.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT)); 
        JLabel get = new JLabel(tresor);
        panel1.add(get);
        label = new JLabel("Recevoir trésor");
        panel1.add(label, BorderLayout.SOUTH);
        
    } 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    
    
    
}
