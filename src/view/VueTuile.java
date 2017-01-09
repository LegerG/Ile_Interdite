package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueTuile extends JPanel {
    private final String nomFichier;
    private final Integer id;
    private ImageIcon imageTuile;
    private ArrayList<JLabel> joueursLabels;
    
    
    public VueTuile(String nom, Integer id ) {
        this.setSize(150, 150);
        
        
        this.nomFichier = nom;
        this.id = id;
        this.setLayout(new GridLayout(2, 2));
        this.imageTuile = new ImageIcon(new ImageIcon("images/tuiles/" + nomFichier.trim() + ".png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        this.setOpaque(false);
        
        
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

}