package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.aventuriers.Aventurier;
import model.cases.Grille;
import model.cases.Tuile;

/**
 *
 * @author IUT2-Dept Info
 */
public class VuePlateau extends Observable {
    
    private JFrame window;
    private ArrayList<JButton> listeBouton = new ArrayList<>();
    private JPanel mainPanel;
    private JPanel panelMilieu;
    private ImageIcon imageFond;
    private VueNiveau vueNiveau;
    private VueBouton vueBouton;
    private VueGrille vueGrille;
    private ArrayList<VueAventurier> vuesAventuriers = new ArrayList<>();
    
    
    public VuePlateau(Grille grille, ArrayList<Aventurier> aventuriers, int nvEau, Observer o) {
               
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        window.setTitle("Plateau de Jeu");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Définit la taille de la fenêtre en pixels
        window.setSize(dim.width, dim.height);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        
        
        window.setLayout(new BorderLayout());
        
        vueNiveau = new VueNiveau(nvEau);   
        window.add(vueNiveau, BorderLayout.WEST);
        
        vueGrille = new VueGrille(grille, this);
        window.add(vueGrille, BorderLayout.CENTER);
        
        vueBouton = new VueBouton(this);
        window.add(vueBouton, BorderLayout.SOUTH);
        
        JPanel panelAventuriers = new JPanel(new GridLayout(aventuriers.size(), 1));
        window.add(panelAventuriers, BorderLayout.EAST);
        
        for (Aventurier a : aventuriers) {
            //itération pour fabriquer nos vues aventurier
            System.out.println("blblbl");
            VueAventurier vueAventurier = new VueAventurier(this, a);
            vuesAventuriers.add(vueAventurier);
            panelAventuriers.add(vueAventurier);
            
        }    
        
        /**
        
        window.setLocation(dim.width/2-window.getSize().width, dim.height/2-window.getSize().height/2);
        
        vueGrille = new VueGrille(grille);
        vueGrille.addObserver(o);
        
        mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        
        //On met le plateau dans le Center du BorderLayout
        mainPanel.add(vueGrille.getGrillePanel(), BorderLayout.CENTER);
        //Mettre les pioches inondations et Tirages sur les West ou Est (avec les défausses associés)
        
        mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);
        
        
        // panel de gauche
        vueNiveau = new VueNiveau(nvEau);   
        mainPanel.add(vueNiveau.getMainPanel(), BorderLayout.WEST);
        
        // panel de droite 
        
        // panel du milieu
        
        panelMilieu = new JPanel(new BorderLayout());
        mainPanel.add(panelMilieu, BorderLayout.CENTER);
        
        vueGrille = new VueGrille(grille, this);
        
        mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        //On met le plateau dans le Center du BorderLayout
        panelMilieu.add(vueGrille, BorderLayout.CENTER);
        //Mettre les pioches inondations et Tirages sur les West ou Est (avec les défausses associés)
        
        
        
        vueBouton = new VueBouton();
        panelMilieu.add(vueBouton, BorderLayout.SOUTH);
        
        vueAventurier = new VueAventurier(aventuriers, this);
        mainPanel.add(vueAventurier, BorderLayout.EAST);
        
        */
        
        window.setVisible(true);
        
   
//        vueGrille.getVuesTuiles().get(0).ajouterPion(Utils.Pion.VERT);
        
    }
    
    public void surbriller(Integer i) {
        vueGrille.surbriller(i);
    }
    
    public static void main(String[] args) {
      //  VuePlateau vuePlateau = new VuePlateau(tuiles[]);
    }
    
    public void setPosition(Aventurier jCourant, Tuile nouvellePosition, Tuile anciennePosition) {
        vueGrille.setPosition(jCourant, nouvellePosition, anciennePosition);
    }
    
    public void notifierObservateur(Object arg) {
        setChanged();
        notifyObservers(arg);
        clearChanged();
    }
    
}
