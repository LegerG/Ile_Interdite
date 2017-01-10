/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.cartes.CarteInondation;
import model.cartes.CarteTirage;
import util.Utils;

/**
 *
 * @author Julie
 */
public class VueDefausse {
    private JFrame window;
    
    public VueDefausse(ArrayList<Utils.Tresor> tresorsGagnes, ArrayList<CarteInondation> defausseInondation, ArrayList<CarteTirage> defausseTirage){
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        window.setTitle("Trésor et défausse");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Définit la taille de la fenêtre en pixels
        window.setSize(400, 400);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);
        
        
        
        JButton bouton = new JButton("OK !");
//        bouton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//               setChanged();
//               notifyObservers(Utils.Commandes.QUITTER);
//               clearChanged();
//            }
//        });
        mainPanel.add(bouton, BorderLayout.SOUTH);
        
        window.setVisible(true);
    }
    
    public void fermerFenetre() {
        window.dispose();
    }
}
