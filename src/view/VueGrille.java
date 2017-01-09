package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.cases.Grille;
import static util.Utils.getFORME_GRILLE;
 
public class VueGrille extends Observable {
    private HashMap<Integer, VueTuile> vuesTuiles = new HashMap<Integer, VueTuile>();
    private JPanel grillePanel;
    
    public VueGrille (Grille grille) {
        grillePanel = new JPanel(new GridLayout(6,6));
        
        
        int t =0; //indice d'une tuile dans le tableau de tuile en parametre
       
        for (int i = 0; i <6; i++) {
            for (int j = 0 ; j < 6; j++) {
                if (getFORME_GRILLE()[i][j] != 0) {
                    
                    String nomTuile = grille.getTuiles().get(grille.getIdTuiles()[i][j] - 1).getNom();
                    VueTuile v;
                    v = new VueTuile(nomTuile, grille.getTuiles().get(grille.getIdTuiles()[i][j] - 1).getId());
                    vuesTuiles.put(v.getId(), v);
                    grillePanel.add(v);
                    
                    v.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            setChanged();
                            notifyObservers((Integer) v.getId());
                            clearChanged();
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                         
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            
                        }
                    });
                    
                    
                    t++;
                    
                } else {
                    ImageIcon icon = new ImageIcon(new ImageIcon("images/ocean.jpg").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT)); 
                    JLabel image = new JLabel(icon);
                    grillePanel.add(image);
                  
                } 
              
            }
        } 
    }

    public JPanel getGrillePanel() {
        return grillePanel;
    }

    public HashMap<Integer, VueTuile> getVuesTuiles() {
        return vuesTuiles;
    }

    
   
    public void surbriller(Integer i) {
        vuesTuiles.get(i).setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
    }
    
    
}
    
    

