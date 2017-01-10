package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.aventuriers.Aventurier;
import util.Parameters;
 
public class VueAventurier extends JPanel{
    
    private HashMap<Integer, JPanel> panelsAventuriers;
    private JPanel panelCartes;
    private JPanel panelNom;
    private JLabel labelNom;
    private ImageIcon iconAventurier;
    private VuePlateau vuePlateau;
    private ArrayList<VueGrilleCarte> vuesCartes;
    
    public VueAventurier(ArrayList<Aventurier> aventuriers, VuePlateau vuePlateau){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.vuePlateau = vuePlateau;
        this.setLayout(new GridLayout(aventuriers.size(), 1));
        this.setSize(dim.width - 900, Parameters.HAUTEUR_AUTRES_VUES);
        
        for (Aventurier a : aventuriers) {
            //itération pour fabriquer nos vues aventurier
            JPanel panel = new JPanel(new BorderLayout());
            panelsAventuriers.put(a.getId(), panel);
            this.add(panel);
        }
        
        
    }    
        
        
    
    
   
}
