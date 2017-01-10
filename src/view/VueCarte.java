/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author legergw
 */
public class VueCarte extends JPanel{
    private Integer id;
    private String nomFichier;
    private ImageIcon imageCarte;

    public VueCarte(Integer id, String nomFichier) {
        this.id = id;
        this.nomFichier = nomFichier;
        
        this.imageCarte = new ImageIcon(new ImageIcon("images/cartes/" + nomFichier.trim() + ".png").getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT));
        this.setOpaque(false);
    }

    public Integer getId() {
        return id;
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(imageCarte.getImage(), 0, 0, null);
    }
    
}
