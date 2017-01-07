package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.cases.Grille;
import static util.Utils.getFORME_GRILLE;
 
public class VueGrille extends Observable {
    private ArrayList<VueTuile> vueTuiles = new ArrayList<>();
    private JPanel grillePanel;
    
    public VueGrille (Grille grille) {
        grillePanel = new JPanel(new GridLayout(6,6));
        
        
        int t =0; //indice d'une tuile dans le tableau de tuile en parametre
       
        for (int i = 0; i <6; i++) {
            for (int j = 0 ; j < 6; j++) {
                if (getFORME_GRILLE()[i][j] != 0) {
                    VueTuile v = new VueTuile(grille.getTuiles().get(grille.getIdTuiles()[i][j]).getNom(), grille.getTuiles().get(grille.getIdTuiles()[i][j]).getId());
                    vueTuiles.add(v);
                    grillePanel.add(v.getTuilePanel());
                    
                    v.getTuilePanel().addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            setChanged();
                            notifyObservers(v.getId());
                            clearChanged();
                            System.out.println(v.getNom().toString());
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
//                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
//                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            v.getTuilePanel().setBorder(BorderFactory.createLineBorder(Color.red));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            v.getTuilePanel().setBorder(BorderFactory.createLineBorder(Color.black));
                        }
                    });
                    
                    
                    t++;
                    
                } else {
                  grillePanel.add(new JLabel(""));
                  
                } 
              
            }
        } 
    }

    public JPanel getGrillePanel() {
        return grillePanel;
    }

   
    
    
    
    
}
    
    

