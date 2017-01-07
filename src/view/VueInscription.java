package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import util.Utils.Commandes;

/**
 *
 * @author IUT2-Dept Info
 */

    
public class VueInscription extends Observable  {
    private final JFrame window ;
    private final JButton boutonJouer;
    private final JButton boutonRegles;
    private final JButton boutonQuitter;
    private final JLabel messageErreur;
    private ArrayList<JTextField> jTextFieldJoueur = new ArrayList<>();
     

    public VueInscription(int nbJoueurs) {
        window = new JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 400);
        
        window.setTitle("Inscription");
        window.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);
        
        JPanel centralPanel = new JPanel(new GridLayout(nbJoueurs+3,1));
        mainPanel.add(centralPanel);
        
        centralPanel.add(new JLabel("Rentrer le nom des joueurs :"));
        
        for (int i = 0; i < nbJoueurs; i++){
            JPanel panel = new JPanel(new GridLayout(1,2));
            centralPanel.add(panel);
            panel.add(new JLabel("Joueur "+(i+1) +" :"));
            JTextField joueur = new JTextField("");
            panel.add(joueur);
            jTextFieldJoueur.add(joueur);
        }
        
        messageErreur = new JLabel("");
        centralPanel.add(messageErreur);
        messageErreur.setHorizontalAlignment(JLabel.CENTER);
        messageErreur.setForeground(Color.red);
        
        JPanel panelBouton = new JPanel(new GridLayout(1,3));
        centralPanel.add(panelBouton);
        
     
        
        boutonJouer = new JButton("Jouer");
        boutonJouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Commandes.VALIDER_INSCRIPTION);
                clearChanged();
            }
        });
        panelBouton.add(boutonJouer);
        
        boutonRegles = new JButton("Retour");
        boutonRegles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Commandes.RETOUR);
                clearChanged();
            }
        });
        panelBouton.add(boutonRegles);
        
        boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Commandes.QUITTER);
                clearChanged();
            }
        });
        panelBouton.add(boutonQuitter);
        
        window.setVisible(true);
        
    }

    public JFrame getWindow() {
        return window;
    }

   
    
    public void fermerFenetre() {
        window.dispose();
    }
    
     public void setMessageErreur(String message) {
        messageErreur.setText(message);
    }

    public ArrayList<String> getNomJoueur() {
        ArrayList<String> nomJoueurs = new ArrayList<>();
        for (JTextField t : jTextFieldJoueur) {
            nomJoueurs.add(t.getText());
        }
        
        return nomJoueurs;
    }
     
    
}

