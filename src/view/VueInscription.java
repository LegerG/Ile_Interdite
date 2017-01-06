package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final JTextField joueur1;
    private final JTextField joueur2;
    private final JTextField joueur3;
    private final JTextField joueur4;
     

    public VueInscription() {
        window = new JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 400);
        
        window.setTitle("Connexion");
        window.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);
        
        JPanel centralPanel = new JPanel(new GridLayout(6,1));
        mainPanel.add(centralPanel);
        
        centralPanel.add(new JLabel("Inscription des joueurs :"));
        
        JPanel panelJ1 = new JPanel(new GridLayout(1,2));
        centralPanel.add(panelJ1);
        panelJ1.add(new JLabel("Joueur 1 :"));
        joueur1 = new JTextField("");
        panelJ1.add(joueur1);
        
        JPanel panelJ2 = new JPanel(new GridLayout(1,2));
        centralPanel.add(panelJ2);
        panelJ2.add(new JLabel("joueur 2 :"));
        joueur2 = new JTextField("");
        panelJ2.add(joueur2);
        
        JPanel panelJ3 = new JPanel(new GridLayout(1,2));
        centralPanel.add(panelJ3);
        panelJ3.add(new JLabel("Joueur 3 :"));
        joueur3 = new JTextField("");
        panelJ3.add(joueur3);
        
        JPanel panelJ4 = new JPanel(new GridLayout(1,2));
        centralPanel.add(panelJ4);
        panelJ4.add(new JLabel("joueur 4 :"));
        joueur4 = new JTextField("");
        panelJ4.add(joueur4);
        
        
        JPanel panelBouton = new JPanel(new GridLayout(1,3));
        centralPanel.add(panelBouton);
        
        centralPanel.add(new JLabel(""));
        
        boutonJouer = new JButton("Jouer");
        boutonJouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Commandes.JOUER);
                clearChanged();
            }
        });
        panelBouton.add(boutonJouer);
        
        boutonRegles = new JButton("Règles du jeu");
        boutonRegles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Commandes.REGLES);
                clearChanged();
            }
        });
        panelBouton.add(boutonJouer);
        
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

    public JTextField getJoueur1() {
        return joueur1;
    }

    public JTextField getJoueur2() {
        return joueur2;
    }
    
    public String getChampJoueur1(){
        return joueur1.getText();
    }
    
    public String getChampJoueur2(){
        return joueur2.getText();
    }

    public JTextField getJoueur3() {
        return joueur3;
    }

    public JTextField getJoueur4() {
        return joueur4;
    }
    
    
    
    public static void main(String[] args) {
        VueInscription vue = new VueInscription();
    }
}