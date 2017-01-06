/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Observable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import util.Utils;

/**
 *
 * @author legergw
 */
public class VueConnexion extends Observable{
    private JFrame window ;
    private HashMap<Integer, JRadioButton> boutonsRadiosNbJoueurs ;    
    private HashMap<Integer, JRadioButton> boutonsRadiosDifficulte ; 
    private JLabel messageErreur;
    private JLabel messageBonjour;
    private final JButton boutonJouer;
    private final JButton boutonRegles;
    private final JButton boutonQuitter;
    
    public VueConnexion() {
        window = new JFrame();
        window.setTitle("L'ILE INTERDITE");
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.setSize(700, 200);
        window.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel(new GridLayout(5,1));
        window.add(mainPanel);
        
        messageBonjour = new JLabel("Bienvenue sur l'île interdite :");
        mainPanel.add(messageBonjour);
        messageBonjour.setHorizontalAlignment(JLabel.CENTER);
              
        
        
        ButtonGroup groupeNbJoueurs = new ButtonGroup();
        boutonsRadiosNbJoueurs = new HashMap<>();
        
        
        JPanel panelJoueurs = new JPanel(new GridLayout(1,4));
        mainPanel.add(panelJoueurs);
        panelJoueurs.add(new JLabel("Nombre de joueurs :")) ;
        
        JRadioButton boutonRadio = new JRadioButton("2");
        panelJoueurs.add(boutonRadio);
        groupeNbJoueurs.add(boutonRadio);
        boutonsRadiosNbJoueurs.put(boutonsRadiosNbJoueurs.size(), boutonRadio);

        boutonRadio = new JRadioButton("3");
        panelJoueurs.add(boutonRadio);
        groupeNbJoueurs.add(boutonRadio);
        boutonsRadiosNbJoueurs.put(boutonsRadiosNbJoueurs.size(), boutonRadio);

        boutonRadio = new JRadioButton("4");
        panelJoueurs.add(boutonRadio);
        groupeNbJoueurs.add(boutonRadio);
        boutonsRadiosNbJoueurs.put(boutonsRadiosNbJoueurs.size(), boutonRadio);
        
        
        
        ButtonGroup groupeDifficultes = new ButtonGroup();
        boutonsRadiosDifficulte = new HashMap<>();
        
        
        JPanel panelDifficultes = new JPanel(new GridLayout(1,5));
        mainPanel.add(panelDifficultes);
        panelDifficultes.add(new JLabel("Difficultés :")) ;
        
        boutonRadio = new JRadioButton("Novice");
        panelDifficultes.add(boutonRadio);
        groupeDifficultes.add(boutonRadio);
        boutonsRadiosDifficulte.put(boutonsRadiosDifficulte.size(), boutonRadio);

        boutonRadio = new JRadioButton("Normal");
        panelDifficultes.add(boutonRadio);
        groupeDifficultes.add(boutonRadio);
        boutonsRadiosDifficulte.put(boutonsRadiosDifficulte.size(), boutonRadio);

        boutonRadio = new JRadioButton("Elite");
        panelDifficultes.add(boutonRadio);
        groupeDifficultes.add(boutonRadio);
        boutonsRadiosDifficulte.put(boutonsRadiosDifficulte.size(), boutonRadio);
        
        boutonRadio = new JRadioButton("Légendaire");
        panelDifficultes.add(boutonRadio);
        groupeDifficultes.add(boutonRadio);
        boutonsRadiosDifficulte.put(boutonsRadiosDifficulte.size(), boutonRadio);
 
        messageErreur = new JLabel("");
        mainPanel.add(messageErreur);
        messageErreur.setHorizontalAlignment(JLabel.CENTER);
        messageErreur.setForeground(Color.red);
        
        JPanel panelBouton = new JPanel(new GridLayout(1,5));
        mainPanel.add(panelBouton);
        
        boutonJouer = new JButton("Jouer");
        boutonJouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Utils.Commandes.VALIDER_CONNEXION);
                clearChanged();
            }
        });
        panelBouton.add(boutonJouer);
        panelBouton.add(new JLabel("")) ;
        
        boutonRegles = new JButton("Règles");
        boutonRegles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Utils.Commandes.REGLES);
                clearChanged();
            }
        });
        panelBouton.add(boutonRegles);
        panelBouton.add(new JLabel("")) ;
        
        boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Utils.Commandes.QUITTER);
                clearChanged();
            }
        });
        panelBouton.add(boutonQuitter);
        window.setVisible(true);  
      
    }

    public int getNbJoueurs() {
        int nbJoueurs = -1;
        
        for (Integer i : boutonsRadiosNbJoueurs.keySet()) {
            if (boutonsRadiosNbJoueurs.get(i).isSelected()) {
                nbJoueurs = i + 2;
            }
        }
        
        return nbJoueurs;
    }
    
    public int getDifficulte() {
        int difficulte = -1;
        
        for (Integer i : boutonsRadiosDifficulte.keySet()) {
            if (boutonsRadiosDifficulte.get(i).isSelected()) {
                difficulte = i;
            }
        }
        
        return difficulte;
    }
    
    public void fermerFenetre() {
        window.dispose();
    }
    
    public void ouvrirFenetre() {
        window.setVisible(true);
    }

    public void setMessageErreur(String message) {
        messageErreur.setText(message);
    }
}