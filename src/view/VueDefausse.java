/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import model.cartes.CarteInondation;
import model.cartes.CarteTirage;
import util.Utils;

/**
 *
 * @author Julie
 */
public class VueDefausse {
    private JFrame window;
    private final JEditorPane html ;
    private final JScrollPane scrollPane;
    private final JEditorPane html2 ;
    private final JScrollPane scrollPane2;
    private String texteTirage = "bibibibibibiibibi";
    private String texteInondation =  "blblblbl";
    
    public VueDefausse(ArrayList<Utils.Tresor> tresorsGagnes, ArrayList<CarteInondation> defausseInondation, ArrayList<CarteTirage> defausseTirage){
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        window.setTitle("Trésor et défausse");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Définit la taille de la fenêtre en pixels
        window.setSize(400, 400);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);
        
        JPanel panelCentre = new JPanel(new GridLayout(1,3));
        
        // LA PANEL des tresors 
        JPanel panelTresor = new JPanel(new GridLayout(1,tresorsGagnes.size()));
        panelCentre.add(panelTresor);
        
        // panel des cartes inondation
        html = new JEditorPane();
        html.setContentType("text/html");
        scrollPane = new JScrollPane(html);        
        panelCentre.add(scrollPane, BorderLayout.SOUTH) ;
        for (CarteTirage carteTirage : defausseTirage){
                texteTirage += "- "+carteTirage.getNomFichier()+"<br>" ;
                html.setText(texteTirage);
        }
        
        // panel des cartes 
        html2 = new JEditorPane();
        html2.setContentType("text/html");
        scrollPane2 = new JScrollPane(html2);        
        panelCentre.add(scrollPane2, BorderLayout.SOUTH) ;
        for (CarteInondation carteInondation : defausseInondation){
                texteInondation += "- "+carteInondation.getNomFichier()+"<br>" ;
                html.setText(texteInondation);
        }
        
        JButton bouton = new JButton("OK !");
//        bouton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//               setChanged();
//               notifyObservers(Utils.Commandes.QUITTER);
//               clearChanged();
//            }
//        });
        mainPanel.add(bouton, BorderLayout.SOUTH);
        
        window.setVisible(true);
    }
    
    public void fermerFenetre() {
        window.dispose();
    }
}
