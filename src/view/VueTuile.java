package view;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueTuile extends JLabel{
    private JPanel tuilePanel = new JPanel();
    private String nomFichier;
    private Integer id;
    private static final String cheminDAcces = "/users/info/etu-s2/legergw/Documents/ileInterdite_DODE_BAUNEZ_DOLIDON_LEGER/ileInterdite_sujet/images/tuiles/";
    
    public VueTuile(String nom, Integer id ) {
        this.nomFichier = nom;
        this.id = id;
//        this.tuilePanel.add(this.nom);
        this.tuilePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        ImageIcon icon = new ImageIcon(new ImageIcon(cheminDAcces + this.nomFichier + ".png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        JLabel image = new JLabel(icon);
        this.tuilePanel.setLayout(new BorderLayout());
        this.tuilePanel.add(image, BorderLayout.CENTER);
    }

    public Integer getId() {
        return id;
    }

    public JPanel getTuilePanel() {
        return tuilePanel;
    }

    public String getNomFichier() {
        return nomFichier;
    }
    
    
     
    
}