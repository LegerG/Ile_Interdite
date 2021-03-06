package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.aventuriers.Aventurier;
import model.aventuriers.Explorateur;
import model.aventuriers.Ingenieur;
import model.aventuriers.Messager;
import model.aventuriers.Navigateur;
import model.aventuriers.Pilote;
import model.aventuriers.Plongeur;
 
public class VueAventurier extends JPanel {
    
    private VuePlateau vuePlateau;
    private ArrayList<JLabel> listeLabel = new ArrayList<>();
    private JPanel grilleCarte;
    private final JEditorPane html ;
    
    public VueAventurier(VuePlateau vuePlateau, Aventurier aventurier){
        this.setLayout(new BorderLayout());
        int taille = 680;
        
        html = new JEditorPane();
        html.setContentType("text/html");
        html.setText(aventurier.getRoleAventurier().name());
        Font font = new Font("Arial",Font.BOLD,20);
        html.setFont(font);
        html.setBackground(aventurier.getPion().getCouleur());
        html.setEditable(false);
        html.setMinimumSize(new Dimension(taille, 20));
        html.setPreferredSize(new Dimension(taille, 20));
        html.setMaximumSize(new Dimension(taille, 20));
        html.setEditable(false);
        
        this.add(html, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setBackground(aventurier.getPion().getCouleur());
        this.add(panel, BorderLayout.SOUTH);
        
        
        this.vuePlateau = vuePlateau;
        
        JPanel panelCentre = new JPanel(new BorderLayout()); 

        grilleCarte = new JPanel(new GridLayout(2, 4));
        grilleCarte.setBackground(Color.white);
        for (int i = 0; i < 10; i++) {
            
            JLabel label = new JLabel();
            grilleCarte.add(label);
            label.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            this.listeLabel.add(label);
        }
        
        panelCentre.add(grilleCarte, BorderLayout.CENTER);
        this.add(panelCentre, BorderLayout.CENTER);
        
        JLabel labelCapacite = new JLabel();
        
        
        if (aventurier instanceof Pilote) {
            labelCapacite.setText("Une fois par tour, déplacez vous sur n'importe quelle case pour une action");
        } 
        else if (aventurier instanceof Ingenieur) {
            labelCapacite.setText("Asséchez deux tuiles pour une action ");
        } 
        else if (aventurier instanceof Plongeur) {
            labelCapacite.setText("Passez par une ou plusieurs tuiles innondées"); // coulées pour une action \n(Vous devez terminer votre tour sur une tuile) ");
        } 
        else if (aventurier instanceof Messager) {
            labelCapacite.setText("Donnez des cartes trésors à un joueur n'importe où sur l'île pour une action par carte ");
        } 
        else if (aventurier instanceof Navigateur) {
            labelCapacite.setText("Vous avez 4 actions au lieu de 3");
        } 
        else if (aventurier instanceof Explorateur) {
            labelCapacite.setText("Peut se déplacer et assécher en diagonale");
        }
       
        
        
        panelCentre.add(labelCapacite, BorderLayout.NORTH);
        
    }    
 
    public void afficherCartesAventurier(Aventurier a) {
        
            
        effacerCartesAventurier(a);
        for (int i =0; i< a.getMain().size(); i++) {
            ImageIcon carteVerso = new ImageIcon(new ImageIcon("images/cartes/"+a.getMain().get(i).getNomFichier()+".png").getImage().getScaledInstance(120, 180, Image.SCALE_DEFAULT));
            this.listeLabel.get(i).setIcon(carteVerso);
            
            
        }
       
        
        
    }
    
    private void effacerCartesAventurier(Aventurier a){
        for (int i =0; i< listeLabel.size(); i++) {
            ImageIcon carteVerso = new ImageIcon(new ImageIcon("images/ca.png").getImage().getScaledInstance(120, 180, Image.SCALE_DEFAULT));
            this.listeLabel.get(i).setIcon(carteVerso); 
        }
        
    }
    
    public void setListener(Aventurier a){
        //for (int i = 0; i < a.getMain().size() && i < listeLabel.size(); i++){
           for(int i=0;i<9;i++){
            int j = i;
            
            listeLabel.get(i).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vuePlateau.notifierObservateur((Integer) j);
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
            
            
            
        }
    }
    
   
}
