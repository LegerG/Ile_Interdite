package view;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueTuile extends JPanel {
    private JPanel tuilePanel = new JPanel();
    private JLabel nomFichier;
    private Integer id;
    private Image imageTuile;
    private ArrayList<JLabel> joueursLabels;
    
    
    public VueTuile(String nom, Integer id ) {
        super();
        
        
        this.nomFichier = new JLabel(nom);
        this.id = id;
        
        try {
            this.imageTuile = ImageIO.read(new File("images/tuiles/" + nomFichier.getText() + ".png"));
        } catch (IOException ex) {
            System.err.println("Erreur de lecture du fichier" + nomFichier.getText() + ".png");
        }
        
        
        this.tuilePanel.setLayout(new GridLayout(2, 2));
        this.repaint();
       
    }

    public Integer getId() {
        return id;
    }

    public JPanel getTuilePanel() {
        return tuilePanel;
    }

    public JLabel getNomFichier() {
        return nomFichier;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageTuile, 0, 0, 150, 150, null, this);
    }

}