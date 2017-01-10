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
public class VueBouton extends JPanel{  
    private JLabel label1 ;
    private JLabel label2 ;
    private JLabel label3 ;
    private JLabel label4 ;
    private JLabel label5 ;
    private JPanel panel1 ;
    private JPanel panel2 ;
    private JPanel panel3 ;
    private JPanel panel4 ;
    private JPanel panel5 ;
    
    public VueBouton(){
        this.setLayout(new GridLayout(1,5));
      
        
        panel1 = new JPanel(new BorderLayout()); 
        this.add(panel1);
        ImageIcon deplacer = new ImageIcon(new ImageIcon("images/icones/iconMove.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)); 
        JLabel dep = new JLabel(deplacer);
        panel1.add(dep, BorderLayout.CENTER);
        label1 = new JLabel("Se déplacer");
        label1.setHorizontalAlignment(JLabel.CENTER);
        panel1.add(label1, BorderLayout.SOUTH);
        
        panel2 = new JPanel(new BorderLayout()); 
        this.add(panel2);
        ImageIcon assecher = new ImageIcon(new ImageIcon("images/icones/iconDry.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)); 
        JLabel assech = new JLabel(assecher);
        panel2.add(assech, BorderLayout.CENTER);
        label2 = new JLabel("Assecher");
        label2.setHorizontalAlignment(JLabel.CENTER);
        panel2.add(label2, BorderLayout.SOUTH);
        
        panel3 = new JPanel(new BorderLayout()); 
        this.add(panel3);
        ImageIcon useCarte = new ImageIcon(new ImageIcon("images/icones/iconShift.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)); 
        JLabel carte = new JLabel(useCarte);
        panel3.add(carte, BorderLayout.CENTER);
        label3 = new JLabel("Utiliser carte");
        label3.setHorizontalAlignment(JLabel.CENTER);
        panel3.add(label3, BorderLayout.SOUTH);
        
        panel4 = new JPanel(new BorderLayout()); 
        this.add(panel4);
        ImageIcon donnerCarte = new ImageIcon(new ImageIcon("images/icones/iconGive.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)); 
        JLabel give = new JLabel(donnerCarte);
        panel4.add(give, BorderLayout.CENTER);
        label4 = new JLabel("Donner une carte");
        label4.setHorizontalAlignment(JLabel.CENTER);
        panel4.add(label4, BorderLayout.SOUTH);
        
        panel5 = new JPanel(new BorderLayout());
        this.add(panel5);
        ImageIcon tresor = new ImageIcon(new ImageIcon("images/icones/iconGet.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)); 
        JLabel get = new JLabel(tresor);
        panel5.add(get, BorderLayout.CENTER);
        label5 = new JLabel("Recevoir trésor");
        label5.setHorizontalAlignment(JLabel.CENTER);
        panel5.add(label5, BorderLayout.SOUTH);
        
    } 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    
}
