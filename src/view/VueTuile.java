package view;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils.Pion;

public class VueTuile {
    private JPanel tuilePanel = new JPanel();
    private JLabel nomFichier;
    private Integer id;
    private ImageIcon iconTuile;
    
    
    public VueTuile(String nom, Integer id ) {
        this.nomFichier = new JLabel(nom);
        this.id = id;
        iconTuile = new ImageIcon(new ImageIcon("images/tuiles/" + nomFichier.getText() + ".png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        JLabel image = new JLabel(iconTuile);
        this.tuilePanel.setLayout(new BorderLayout());
        this.tuilePanel.add(image, BorderLayout.CENTER);
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
    
    /*
        Cette méthode permet de rajouter un pion sur l'image de la tuile.
    */
    public void ajouterPion(Pion pion) {
        ImageIcon iconPion = new ImageIcon(new ImageIcon("images/pions/pionBleu.png").getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
        Image imageTuile = iconTuile.getImage();
        Image imagePion = iconPion.getImage();
        
        BufferedImage image = new BufferedImage(150, 150,  TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) imageTuile.getGraphics();
        g2.drawImage(imageTuile, 150, 150, null);
        g2.drawImage(imagePion, 150, 150, null);

    }
}