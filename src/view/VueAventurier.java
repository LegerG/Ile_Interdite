package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.aventuriers.Aventurier;
 
public class VueAventurier  {
    
    private JPanel panelAventurier;
    private JPanel panelCartes;
    private JPanel panelNom;
    private JLabel labelNom;
    private ImageIcon iconAventurier;
    
    public VueAventurier(Aventurier aventurier){
       
        panelAventurier = new JPanel(new BorderLayout());
        
        panelNom = new JPanel (new GridLayout(2,1));
        labelNom = new JLabel(aventurier.getNom());
        panelNom.add(labelNom);
        
        iconAventurier = new ImageIcon(new ImageIcon("images/personnages/" + aventurier.getRoleAventurier().toString() + ".png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        JLabel image = new JLabel(iconAventurier);
        panelNom.add(image);
        panelAventurier.add(panelNom, BorderLayout.NORTH);
        
        panelCartes = new JPanel(new GridLayout(3,3));
        
        
        
        
        
    }
    
   
}
