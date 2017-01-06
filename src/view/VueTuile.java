package view;
 
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.cases.Tuile;

public class VueTuile extends JLabel{
    private JPanel tuilePanel = new JPanel();
    private JLabel nom;
    private Integer id;
    
    public VueTuile(String nom, Integer id ) {
        this.nom = new JLabel(nom);
        this.id = id;
        this.tuilePanel.add(this.nom);
        this.tuilePanel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Integer getId() {
        return id;
    }

    public JPanel getTuilePanel() {
        return tuilePanel;
    }

    public JLabel getNom() {
        return nom;
    }
    
    
     
    
}