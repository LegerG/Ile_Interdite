package view;
 
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueTuile extends JLabel{
    private JPanel tuilePanel = new JPanel();
    private JLabel nomFichier;
    private Integer id;
    
    
    public VueTuile(String nom, Integer id ) {
        this.nomFichier = new JLabel(nom);
        this.id = id;
//        this.tuilePanel.add(this.nom);
//        this.tuilePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        ImageIcon icon = new ImageIcon(new ImageIcon("images/tuiles/" + nomFichier.getText() + ".png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
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

    public JLabel getNomFichier() {
        return nomFichier;
    }
    
    
     
    
}