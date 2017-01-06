package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.cases.Grille;
import model.cases.Tuile;
import static util.Utils.getFORME_GRILLE;

/**
 *
 * @author IUT2-Dept Info
 */
public class VuePlateau extends Observable {
    
    private JFrame window;
    private ArrayList<JButton> listeBouton = new ArrayList<>();
    protected JPanel grillePanel = new JPanel(new GridLayout(6,6));
    
    public VuePlateau(Grille grille) {
               
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Définit la taille de la fenêtre en pixels
        window.setSize(1138, 831);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width, dim.height/2-window.getSize().height/2);
        
        
        
        
        window.add(grillePanel);
        window.setVisible(true);
        
        int t =0; //indice d'une tuile dans le tableau de tuile en parametre
       
        for (int i = 0; i <6; i++) {
            for (int j = 0 ; j < 6; j++) {
                if (getFORME_GRILLE()[i][j] != 0) {
                    JButton bouton = new JButton(grille.getTuiles().get(grille.getIdTuiles()[i][j]).getNom());
                    listeBouton.add(bouton);
                    grillePanel.add(bouton);
                    t++;
                    
                } else {
                  grillePanel.add(new JLabel(""));
                  
                } 
              
            }
        } 
   
    
        
    }
    
    public static void main(String[] args) {
      //  VuePlateau vuePlateau = new VuePlateau(tuiles[]);
    }
    
}
