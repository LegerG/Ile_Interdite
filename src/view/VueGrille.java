package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.aventuriers.Aventurier;
import model.cases.Grille;
import model.cases.Tuile;
import static util.Utils.getFORME_GRILLE;
 
public class VueGrille extends JPanel{
    private HashMap<Integer, VueTuile> vuesTuiles = new HashMap<Integer, VueTuile>();
    private VuePlateau vuePlateau;
    ImageIcon imageFond ;
    
    public VueGrille (Grille grille, VuePlateau vuePlateau) {
        this.setLayout(new GridLayout(6,6));
        this.vuePlateau = vuePlateau;
        this.setSize(900, 900);
        int t =0; //indice d'une tuile dans le tableau de tuile en parametre
       
        for (int i = 0; i <6; i++) {
            for (int j = 0 ; j < 6; j++) {
                if (getFORME_GRILLE()[i][j] != 0) {
                    
                    String nomTuile = grille.getTuiles().get(grille.getIdTuiles()[i][j]).getNom();
                    VueTuile v;
                    v = new VueTuile(nomTuile, grille.getTuiles().get(grille.getIdTuiles()[i][j]).getId());
                    vuesTuiles.put(v.getId(), v);
                    this.add(v);
                    
                    v.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            vuePlateau.notifierObservateur((Integer) v.getId());
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
//                    ImageIcon icon = new ImageIcon(new ImageIcon("images/ocean.jpg").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT)); 
//                    JLabel image = new JLabel(icon);
                    this.add(new JLabel());
                  
                } 
              
            }
        }
        imageFond = new ImageIcon(new ImageIcon("images/oceanFond.jpg").getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT)); 
        this.setOpaque(false);
        
    }

    public HashMap<Integer, VueTuile> getVuesTuiles() {
        return vuesTuiles;
    }

    
   
    public void surbriller(Integer i) {
        vuesTuiles.get(i).setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
    }
    
    public void desurbriller() {
        for (VueTuile v : vuesTuiles.values()){
            v.setBorder(null);
        }
        
    }
    
    public void setPosition(Aventurier jCourant, Tuile nouvellePosition, Tuile anciennePosition) {
        //Suppression du pion de jCourant sur l'ancienne tuile
        VueTuile ancienneTuile = vuesTuiles.get(anciennePosition.getId());
        ancienneTuile.effacerPion(jCourant.getPion());
        
        //Ajout du pion de jCourant sur la nouvelle tuile
        VueTuile nouvelleTuile = vuesTuiles.get(nouvellePosition.getId());
        nouvelleTuile.ajouterPion(jCourant.getPion());        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(imageFond.getImage(), 0, 0, null);
    }
    
    public void inonderTuile(Tuile tuile) {
        vuesTuiles.get(tuile.getId()).inonderTuile();
    }
    
    public void coulerTuile(Tuile tuile) {
        vuesTuiles.get(tuile.getId()).coulerTuile();
    }
}
    
    

