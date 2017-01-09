/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import util.Utils;

/**
 *
 * @author Gwenaël Léger
 */
public class VueRegles extends Observable{
    
    private JFrame window;
    private JPanel mainPanel;
    private JPanel gridPanel;
    private JPanel southPanel;
    private JButton boutonQuitter;
    private JScrollPane scroll;
    
    public VueRegles() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Règles du jeu");
        window.setSize(900, 900);
        
        
        mainPanel = new JPanel(new BorderLayout());
        window.add(mainPanel);
        
        JEditorPane html = new JEditorPane();
        html.setContentType("text/html");
        html.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(html);        
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER) ;
        
        String texte = "";
        
        
        for (Integer i = 0; i < 8; i++) {
            int j = i + 1;
            texte += "<div> <p> <img src=\"file:images/regles/regle-page-00" + j + ".jpg\" width=900 height=1200 /> </p>  </div>" ; //style=\"width:700px;height:933px;\" 
            html.setText(texte);  

        }
        
        
        southPanel = new JPanel(new GridLayout(1, 3));
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        
        boutonQuitter = new JButton("J'ai compris");
        boutonQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Utils.Commandes.QUITTER);
                clearChanged();
            }
        });
        southPanel.add(new JLabel("                    "));
        southPanel.add(boutonQuitter);
        southPanel.add(new JLabel("                    "));
        
        window.setLocationRelativeTo(null);
    }
    
    public void fermerFenetre() {
        window.dispose();
    }
    
    public void afficherFenetre() {
        window.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        VueRegles vueRegle = new VueRegles();
    }
}
