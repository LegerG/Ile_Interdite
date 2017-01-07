package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.cases.Grille;

/**
 *
 * @author IUT2-Dept Info
 */
public class VuePlateau extends Observable {
    
    private JFrame window;
    private ArrayList<JButton> listeBouton = new ArrayList<>();
    protected VueGrille vueGrille;
    private JPanel mainPanel;
    
    public VuePlateau(Grille grille) {
               
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Plateau de Jeu");
        
        // Définit la taille de la fenêtre en pixels
        window.setSize(902, 902);
        window.setResizable(false);
        
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width, dim.height/2-window.getSize().height/2);
        
        vueGrille = new VueGrille(grille);
        mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        
        //On met le plateau dans le Center du BorderLayout
        mainPanel.add(vueGrille.getGrillePanel(), BorderLayout.CENTER);
        //Mettre les pioches inondations et Tirages sur les West ou Est (avec les défausses associés)
        
        
        window.add(mainPanel);
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
   
    
        
    }
    
    public static void main(String[] args) {
      //  VuePlateau vuePlateau = new VuePlateau(tuiles[]);
    }
    
}
