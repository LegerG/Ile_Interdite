/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class VueDefausse extends Observable{
    private JFrame window;
    private final JEditorPane html ;
    private final JScrollPane scrollPane;
    private final JEditorPane html2 ;
    private final JScrollPane scrollPane2;
    private String texteTirage = "";
    private String texteInondation =  "";
    
    public VueDefausse(ArrayList<Utils.Tresor> tresorsGagnes, ArrayList<CarteInondation> defausseInondation, ArrayList<CarteTirage> defausseTirage){
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        window.setTitle("Trésor et défausse");
        window.setAlwaysOnTop(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Définit la taille de la fenêtre en pixels
        window.setSize(850, 500);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);
        
        JPanel panelCentre = new JPanel(new GridLayout(1,3));
        mainPanel.add(panelCentre);
        
        Font font = new Font("Arial",Font.BOLD,15);
        
        
        // LA PANEL des tresors 
        JPanel borderTresor = new JPanel(new BorderLayout());
        panelCentre.add(borderTresor);
        JLabel label1 = new JLabel("Trésor obtenus ");
        borderTresor.add(label1, BorderLayout.NORTH);
        label1.setFont(font);
        JPanel panelTresor = new JPanel(new GridLayout(tresorsGagnes.size(),1));
        for (int i =0; i< tresorsGagnes.size(); i++){
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(tresorsGagnes.get(i).toString());
            panel.add(label, BorderLayout.NORTH);
            label.setHorizontalAlignment(JLabel.CENTER);
            ImageIcon deplacer = new ImageIcon(new ImageIcon(tresorsGagnes.get(i).getPathPicture()).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));;
            panel.add(new JLabel (deplacer), BorderLayout.CENTER);
            panelTresor.add(panel);
        }
        borderTresor.add(panelTresor, BorderLayout.CENTER); 
        
        // panel des cartes tirage
        JPanel panelTirage = new JPanel(new BorderLayout());
        panelCentre.add(panelTirage);
        html = new JEditorPane();
        html.setContentType("text/html");
        html.setEditable(false);
        scrollPane = new JScrollPane(html); 
        
        JLabel label2 = new JLabel("Défausse des cartes inondation");
       
        label2.setFont(font);
        panelTirage.add(label2, BorderLayout.NORTH);
        panelTirage.add(scrollPane, BorderLayout.CENTER) ;
        for (CarteInondation carteInondation : defausseInondation){
                texteTirage += "<br>- "+carteInondation.getNomFichier() ;
                html.setText(texteTirage);
        }
        
        
//         panel des cartes inondation
        JPanel panelInondation = new JPanel(new BorderLayout());
        panelCentre.add(panelInondation);
        html2 = new JEditorPane();
        html2.setContentType("text/html");
        html2.setEditable(false);
        scrollPane2 = new JScrollPane(html2);  
        
        JLabel label3 = new JLabel("Défausse des cares tirage");
        label3.setFont(font);
        panelInondation.add(label3, BorderLayout.NORTH);
        
        panelInondation.add(scrollPane2, BorderLayout.CENTER) ;
        for (CarteTirage carteTirage : defausseTirage){
                texteTirage += "<br>- "+carteTirage.getNomFichier() ;
                html.setText(texteTirage);
        }
        
        JButton bouton = new JButton("OK !");
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               setChanged();
               notifyObservers(Utils.Commandes.QUITTER);
               clearChanged();
            }
        });
        mainPanel.add(bouton, BorderLayout.SOUTH);
        
        window.setVisible(true);
    }
    
    public void fermerFenetre() {
        window.dispose();
    }
}
