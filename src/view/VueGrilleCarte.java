/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.cartes.Carte;

/**
 *
 * @author legergw
 */
class VueGrilleCarte extends JPanel{
    private VuePlateau vuePlateau;
    private Integer id;
    private HashMap<Integer, VueCarte> vuesCartes = new HashMap<>();
    
    public VueGrilleCarte(VuePlateau vuePlateau) {
        this.vuePlateau = vuePlateau;
        this.setLayout(new GridLayout(3, 3));
        
        
        
        
    }
    
    public void ajouterCarte(Carte carte) {
        
        VueCarte v = new VueCarte(carte.getId(), carte.getNomFichier());

        v.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            vuePlateau.notifierObservateur((Integer) v.getId());
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    });
    }

    
    
    
}
