package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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
    protected VueGrille vueGrille;
    private JPanel mainPanel;
    private ImageIcon imageFond;
    private VueNiveau vueNiveau;
    private VueBouton vueBouton;
    
    public VuePlateau(Grille grille, Observer o) {
               
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        window.setTitle("Plateau de Jeu");
        
        // Définit la taille de la fenêtre en pixels
        window.setSize(902, 902);
        window.setResizable(false);
        
        
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        window.setLocation(dim.width/2-window.getSize().width, dim.height/2-window.getSize().height/2);
//        
//        vueGrille = new VueGrille(grille);
//        vueGrille.addObserver(o);
//        
//        mainPanel = new JPanel(new BorderLayout()); 
//        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
//        
//        //On met le plateau dans le Center du BorderLayout
//        mainPanel.add(vueGrille.getGrillePanel(), BorderLayout.CENTER);
//        //Mettre les pioches inondations et Tirages sur les West ou Est (avec les défausses associés)
        
        mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);
        
        
        // panel de gauche
        vueNiveau = new VueNiveau(2);   
        mainPanel.add(vueNiveau.getMainPanel(), BorderLayout.WEST);
        
        // panel de droite 
        
        // panel du milieu
        
        JPanel panelMilieu = new JPanel(new BorderLayout());
        mainPanel.add(panelMilieu);
        
        vueGrille = new VueGrille(grille, this);
        
        mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        //On met le plateau dans le Center du BorderLayout
        mainPanel.add(vueGrille, BorderLayout.CENTER);
        //Mettre les pioches inondations et Tirages sur les West ou Est (avec les défausses associés)
        
        
        window.add(mainPanel);
        vueBouton = new VueBouton();
        panelMilieu.add(vueBouton.getMainPanel(), BorderLayout.CENTER);
        
        window.setLocationRelativeTo(null);
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
