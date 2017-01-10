package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.aventuriers.Aventurier;
import util.Parameters;
 
public class VueAventurier extends JPanel{
    
    private HashMap<Integer, JPanel> panelsAventuriers;
    private VuePlateau vuePlateau;
    private ArrayList<VueGrilleCarte> vuesGrilleCartes;
    
    public VueAventurier(ArrayList<Aventurier> aventuriers, VuePlateau vuePlateau){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.vuePlateau = vuePlateau;
        this.setLayout(new GridLayout(aventuriers.size(), 1));
        this.setSize(dim.width - 900, Parameters.HAUTEUR_AUTRES_VUES);
        
        for (Aventurier a : aventuriers) {
            //itération pour fabriquer nos vues aventurier
            JPanel panel = new JPanel(new BorderLayout());
            panelsAventuriers.put(a.getId(), panel);
            panel.add(new JLabel(a.getNom()), BorderLayout.CENTER);
            panel.add(new VueGrilleCarte(vuePlateau), BorderLayout.SOUTH);
            
            this.add(panel);
        }    
        
        
    
    }
   
}
