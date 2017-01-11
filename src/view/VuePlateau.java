package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import model.aventuriers.Aventurier;
import model.cases.Grille;
import model.cases.Tuile;
import util.Utils;

/**
 *
 * @author IUT2-Dept Info
 */
public class VuePlateau extends Observable {
    
    private JFrame window;
    private ArrayList<JButton> listeBouton = new ArrayList<>();
    private JPanel mainPanel;
    private JPanel panelMilieu;
    private ImageIcon imageFond;
    private VueNiveau vueNiveau;
    private VueBouton vueBouton;
    private VueGrille vueGrille;
    private MessageBox messageBox;
    private ArrayList<VueAventurier> vuesAventuriers = new ArrayList<>();
    
    
    public VuePlateau(Grille grille, ArrayList<Aventurier> aventuriers, int nvEau, Observer o) {
               
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        window.setTitle("Ile interdite");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Définit la taille de la fenêtre en pixels
        window.setSize(dim.width, dim.height);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        
        
        window.setLayout(new BorderLayout());
        
        
        // partie gauche (niveau eau)
        vueNiveau = new VueNiveau(nvEau);   
        window.add(vueNiveau, BorderLayout.WEST);
        
        //partie milieu ( plateau + bouton) 
        JPanel panelMilieu = new JPanel(new BorderLayout());
        window.add(panelMilieu);
        
        vueGrille = new VueGrille(grille, this);
        panelMilieu.add(vueGrille, BorderLayout.CENTER);
        
        vueBouton = new VueBouton(this);
        panelMilieu.add(vueBouton, BorderLayout.SOUTH);
        
        // partie haut droite(joueus)
        
        JPanel panelDroite = new JPanel(new GridLayout(2,1));
        window.add(panelDroite, BorderLayout.EAST);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        panelDroite.add(tabbedPane);
        
         for (Aventurier a : aventuriers) {
            //itération pour fabriquer nos vues aventurier
            VueAventurier vueAventurier = new VueAventurier(this, a);
            vuesAventuriers.add(vueAventurier);
            tabbedPane.add(vueAventurier, a.getNom());
            
        }   
         
        // partie bas-droite (message box + defausse)
        JPanel panelBasDroite = new JPanel(new GridLayout (1,2));
        panelDroite.add(panelBasDroite);
         
        messageBox = new MessageBox();
        panelBasDroite.add(messageBox.getWindow());
        
        JPanel panelDefausse = new JPanel(new BorderLayout());
        panelDefausse.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        ImageIcon carteVerso = new ImageIcon(new ImageIcon("images/cartes/Fond bleu.png").getImage().getScaledInstance(300, 470, Image.SCALE_DEFAULT)); 
        JLabel defausse = new JLabel(carteVerso);
        panelDefausse.add(defausse, BorderLayout.CENTER);
        JLabel label = new JLabel("Voir les cartes de la défausse");
        panelDefausse.add(label, BorderLayout.NORTH);
        label.setHorizontalAlignment(JLabel.CENTER);
        Font font = new Font("Arial",Font.BOLD,16);
        label.setFont(font);
        
        JLabel label2 = new JLabel("Et les trésors obtenus");
        panelDefausse.add(label2, BorderLayout.SOUTH);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setFont(font);
        
        
        panelBasDroite.add(panelDefausse);
        panelDefausse.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifierObservateur(Utils.Commandes.VOIR_DEFAUSSE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelDefausse.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelDefausse.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            }
        });
        
        window.setVisible(true);
        
   
//        vueGrille.getVuesTuiles().get(0).ajouterPion(Utils.Pion.VERT);
        
    }
    
    public void surbriller(Integer i) {
        vueGrille.surbriller(i);
    }
    
     public void desurbriller() {
        vueGrille.desurbriller();
    }
    
    public static void main(String[] args) {
      //  VuePlateau vuePlateau = new VuePlateau(tuiles[]);
    }
    
    public void setPosition(Aventurier jCourant, Tuile nouvellePosition, Tuile anciennePosition) {
        vueGrille.setPosition(jCourant, nouvellePosition, anciennePosition);
    }
    
    public void notifierObservateur(Object arg) {
        setChanged();
        notifyObservers(arg);
        clearChanged();
    }
    
    public void inonderTuile(Tuile tuile) {
        vueGrille.inonderTuile(tuile);
    }
    
    public void coulerTuile(Tuile tuile) {
        vueGrille.coulerTuile(tuile);
    }
    
    public void afficherCartesAventurier(Aventurier a, int i) {
       vuesAventuriers.get(i).afficherCartesAventurier(a);
    }  
       
    public void assecherTuile(Tuile tuile) {
        vueGrille.assecherTuile(tuile);
    }

    public JFrame getWindow() {
        return window;
    }

    public MessageBox getMessageBox() {
        return messageBox;
    }
    
    public void enableBouton(boolean b){
        this.vueBouton.enableBouton(b);
    }

    public VueNiveau getVueNiveau() {
        return vueNiveau;
    }
    
    
}
