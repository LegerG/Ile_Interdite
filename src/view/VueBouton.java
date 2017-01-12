/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils;

/**
 *
 * @author dolidont
 */
public class VueBouton extends JPanel{  
    private JLabel label1 ;
    private JLabel label2 ;
    private JLabel label3 ;
    private JLabel label4 ;
    private JLabel label5 ;
    private JLabel label6 ;
    private JPanel panel1 ;
    private JPanel panel2 ;
    private JPanel panel3 ;
    private JPanel panel4 ;
    private JPanel panel5 ;
    private JPanel panel6 ;
    private VuePlateau vuePlateau;
    
    public VueBouton(VuePlateau vuePlateau){
        this.setLayout(new GridLayout(1,6));
        this.vuePlateau = vuePlateau;
        this.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        int taille = 60;
        
        panel1 = new JPanel(new BorderLayout()); 
        this.add(panel1);
        ImageIcon deplacer = new ImageIcon(new ImageIcon("images/icones/iconMove.png").getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT)); 
        JLabel dep = new JLabel(deplacer);
        panel1.add(dep, BorderLayout.CENTER);
        label1 = new JLabel("Se déplacer");
        label1.setHorizontalAlignment(JLabel.CENTER);
        panel1.add(label1, BorderLayout.SOUTH);
        panel1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        panel1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifierObservateur(Utils.Commandes.BOUGER);
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            }
        });
        
        panel2 = new JPanel(new BorderLayout()); 
        this.add(panel2);
        ImageIcon assecher = new ImageIcon(new ImageIcon("images/icones/iconDry.png").getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT)); 
        JLabel assech = new JLabel(assecher);
        panel2.add(assech, BorderLayout.CENTER);
        label2 = new JLabel("Assecher");
        label2.setHorizontalAlignment(JLabel.CENTER);
        panel2.add(label2, BorderLayout.SOUTH);
        panel2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        panel2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifierObservateur(Utils.Commandes.ASSECHER);
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            }
        });
        
        panel3 = new JPanel(new BorderLayout()); 
        this.add(panel3);
        ImageIcon useCarte = new ImageIcon(new ImageIcon("images/icones/iconShift.png").getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT)); 
        JLabel carte = new JLabel(useCarte);
        panel3.add(carte, BorderLayout.CENTER);
        label3 = new JLabel("Utiliser carte");
        label3.setHorizontalAlignment(JLabel.CENTER);
        panel3.add(label3, BorderLayout.SOUTH);
        panel3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        panel3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifierObservateur(Utils.Commandes.CHOISIR_CARTE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel3.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            }
        });
        
        panel4 = new JPanel(new BorderLayout()); 
        this.add(panel4);
        ImageIcon donnerCarte = new ImageIcon(new ImageIcon("images/icones/iconGive.png").getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT)); 
        JLabel give = new JLabel(donnerCarte);
        panel4.add(give, BorderLayout.CENTER);
        label4 = new JLabel("Donner une carte");
        label4.setHorizontalAlignment(JLabel.CENTER);
        panel4.add(label4, BorderLayout.SOUTH);
        panel4.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        panel4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifierObservateur(Utils.Commandes.DONNER);
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel4.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel4.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            }
        });
        
        panel5 = new JPanel(new BorderLayout());
        this.add(panel5);
        ImageIcon tresor = new ImageIcon(new ImageIcon("images/icones/iconGet.png").getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT)); 
        JLabel get = new JLabel(tresor);
        panel5.add(get, BorderLayout.CENTER);
        label5 = new JLabel("Récupérer trésor");
        label5.setHorizontalAlignment(JLabel.CENTER);
        panel5.add(label5, BorderLayout.SOUTH);
        panel5.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        panel5.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifierObservateur(Utils.Commandes.RECUPERER_TRESOR);
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel5.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel5.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            }
        });
        
        panel6 = new JPanel(new BorderLayout()); 
        this.add(panel6);
        ImageIcon finTour = new ImageIcon(new ImageIcon("images/icones/iconDone.png").getImage().getScaledInstance(taille, taille, Image.SCALE_DEFAULT)); 
        JLabel fin = new JLabel(finTour);
        panel6.add(fin, BorderLayout.CENTER);
        label6 = new JLabel("Finir Tour");
        label6.setHorizontalAlignment(JLabel.CENTER);
        panel6.add(label6, BorderLayout.SOUTH);
        panel6.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        panel6.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifierObservateur(Utils.Commandes.TERMINER);
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel6.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel6.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            }
        });
        
        
        
    } 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void notifierObservateur(Object arg) {
        vuePlateau.notifierObservateur(arg);
    }
    
    public void enableBouton(boolean b){
        this.label1.setEnabled(b);
        this.label2.setEnabled(b);
        this.label3.setEnabled(b);
        this.label4.setEnabled(b);
        this.label5.setEnabled(b);
        this.label6.setEnabled(b);
        
        
    }
}
