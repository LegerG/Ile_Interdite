/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import util.Utils;

/**
 *
 * @author Gwenaël Léger
 */
public class VueRegles extends Observable{
    
    private JFrame window;
    private JPanel mainPanel;
    private JPanel gridPanel;
    private JPanel southPanel;
    private JButton boutonQuitter;
    private JScrollPane scroll;
    
    public VueRegles() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Règles du jeu");
        window.setSize(900, 900);
        
        
        mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);
        
        gridPanel = new JPanel(new GridLayout(8, 1));
//        tentative de scroll pour les regles
//        scroll = new JScrollPane(gridPanel);
//        window.add(scroll);
//        scroll.setLayout(new ScrollPaneLayout());
                
        southPanel = new JPanel(new GridLayout(1, 3));
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        
        
        for (Integer i = 0; i < 8; i++) {
            int j = i + 1;
            ImageIcon icon = new ImageIcon(new ImageIcon("images/regles/regle-page-00" + j + ".jpg").getImage()); 
            JLabel image = new JLabel(icon);
            gridPanel.add(image);
            image.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            
            
        }
        
        boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Utils.Commandes.QUITTER);
                clearChanged();
            }
        });
        southPanel.add(new JLabel("                    "));
        southPanel.add(boutonQuitter);
        southPanel.add(new JLabel("                    "));
        
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
    
    public void fermerFenetre() {
        window.dispose();
    }
    
    
    public static void main(String[] args) {
        VueRegles vueRegle = new VueRegles();
    }
}
