package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.aventuriers.Aventurier;
import util.Parameters;
 
public class VueAventurier extends JPanel{
    
    
    private VuePlateau vuePlateau;
    private ArrayList<VueCarte> vuesCartes;
    private JPanel grilleCarte;
    private JLabel nom;
    private final JEditorPane html ;
    
    public VueAventurier(VuePlateau vuePlateau, Aventurier aventurier){
        this.setLayout(new BorderLayout());
        
        
        html = new JEditorPane();
        html.setContentType("text/html");
        html.setText(aventurier.getNom());
        
        html.setMinimumSize(new Dimension(400, 30));
        html.setPreferredSize(new Dimension(400, 30));
        html.setEditable(false);
        this.add(html, BorderLayout.NORTH);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        
        this.vuePlateau = vuePlateau;
        this.setSize(dim.width - 900, Parameters.HAUTEUR_AUTRES_VUES);
        this.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
        grilleCarte = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            ImageIcon image = new ImageIcon(new ImageIcon("images/tuiles/Heliport.png").getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT));
            grilleCarte.add(new JLabel(image));
           
        }
         
    
    }

    public void setNom(JLabel nom) {
        this.nom = nom;
    }
    
    
   
}
