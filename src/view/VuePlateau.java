package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
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
        
        // Définit la taille de la fenêtre en pixels
        window.setSize(900, 900);
        window.setResizable(false);
        
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width, dim.height/2-window.getSize().height/2);
        
        vueGrille = new VueGrille(grille);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(vueGrille.getGrillePanel(), BorderLayout.CENTER);
        
        window.add(mainPanel);
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
   
    
        
    }
    
    public static void main(String[] args) {
      //  VuePlateau vuePlateau = new VuePlateau(tuiles[]);
    }
    
}
