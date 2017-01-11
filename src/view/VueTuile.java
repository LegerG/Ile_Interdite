package view;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.aventuriers.Aventurier;
import model.cases.Tuile;
import util.Utils.Pion;

public class VueTuile extends JPanel {
    private final String nomFichier;
    private final Integer id;
    private ImageIcon imageTuile;
    private ArrayList<Pion> pionsJoueurs = new ArrayList<>();
    private ArrayList<JLabel> labelsJoueurs = new ArrayList<>();
    
    
    
    public VueTuile(String nom, Integer id ) {
        this.setSize(150, 150);
        
        
        this.nomFichier = nom;
        this.id = id;
        this.setLayout(new GridLayout(2, 2));
        this.imageTuile = new ImageIcon(new ImageIcon("images/tuiles/" + nomFichier.trim() + ".png").getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT));
        this.setOpaque(false);
        
        for (int i = 0; i < 4; i++) {
            
            labelsJoueurs.add(new JLabel());
            this.add(labelsJoueurs.get(i));
        }
        
        
        
    }

    public Integer getId() {
        return id;
    }

    public String getNomFichier() {
        return nomFichier;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(imageTuile.getImage(), 0, 0, null);
    }
    
    public void ajouterPion(Pion pion) {
        if (!pionsJoueurs.contains(pion)) {
            pionsJoueurs.add(pion);
            afficherPion(pion);
        }
    }
    
    public void afficherPion(Pion pion) {
        ImageIcon iconPion = new ImageIcon(new ImageIcon("images/pions/" + pion.getPath()).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
        labelsJoueurs.add(new JLabel(iconPion));
        labelsJoueurs.get(pionsJoueurs.indexOf(pion)).setIcon(iconPion);
    }
    
    public void effacerPion(Pion pion) {
        if (pionsJoueurs.contains(pion)) {
            labelsJoueurs.get(pionsJoueurs.indexOf(pion)).setIcon(null);
            pionsJoueurs.remove(pion);
        }
       
    }
    
    public void inonderTuile() {
        this.imageTuile = new ImageIcon(new ImageIcon("images/tuiles/" + nomFichier.trim() + "_Inonde.png").getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT));
    }
    
    public void coulerTuile() {
        this.imageTuile = new ImageIcon(new ImageIcon("images/tuiles/" + nomFichier.trim() + "_Inonde.png").getImage().getScaledInstance(0, 0, Image.SCALE_DEFAULT));
    }
    
    public void assecherTuile() {
        this.imageTuile = new ImageIcon(new ImageIcon("images/tuiles/" + nomFichier.trim() + ".png").getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT));
    }
}