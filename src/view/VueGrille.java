package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
                    
                    String nomTuile = grille.getTuiles().get(grille.getIdTuiles()[i][j] - 1).getNom();
                    VueTuile v;
                    v = new VueTuile(nomTuile, grille.getTuiles().get(grille.getIdTuiles()[i][j] - 1).getId());
                    vueTuiles.add(v);
                    grillePanel.add(v.getTuilePanel());
                    
                    v.getTuilePanel().addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            setChanged();
                            notifyObservers(v.getId());
                            clearChanged();
                            
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
                            v.getTuilePanel().setBorder(null);
                        }
                    });
                    
                    
                    t++;
                    
                } else {
                    ImageIcon icon = new ImageIcon(new ImageIcon("C:\\Users\\Gwenaël Léger\\Documents\\NetBeansProjects\\Ile_Interdite\\images\\ocean.jpg").getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT)); //
                    JLabel image = new JLabel(icon);
                    grillePanel.add(image);
                  
                } 
              
            }
        } 
    }

    public JPanel getGrillePanel() {
        return grillePanel;
    }

   
    
    
    
    
}
    
    

