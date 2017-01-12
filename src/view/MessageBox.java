package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author IUT2-Dept Info
 */
public class MessageBox {
    private final JPanel window ;
    private final JEditorPane html ;
    private final JScrollPane scrollPane;
    String texte ;
    public MessageBox() {
        window = new JPanel() ;
        
        JPanel mainPanel = new JPanel();
        window.add(mainPanel);
        
        html = new JEditorPane();
        html.setContentType("text/html");
        scrollPane = new JScrollPane(html);
        html.setEditable(false);
        this.scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
        
        html.setMinimumSize(new Dimension(300, 480));
        html.setPreferredSize(new Dimension(300, 480));
        scrollPane.setPreferredSize(new Dimension(300, 480));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setMinimumSize(new Dimension(300, 480));
        
        html.setText("<html><h1 style=\"text-align:center; color:blue;\">Bienvenue dans<br>l'Île Interdite</h1></html>");
        mainPanel.add(scrollPane) ;
        
        window.setVisible(true);
        
        this.texte = "" ;
    }
    
    public void displayMessage(String texte, Color couleur, Boolean precederDunTrait, Boolean garderCouleur) {
        html.setText("<html>" + (precederDunTrait ? "<hr>" : "") + this.texte + "<div style=\"color : " + toRGB(couleur) + "\">"+ texte + "</div></html>");
        if (garderCouleur)
            this.texte += "<div style=\"color : " + toRGB(couleur) + "\">" + (precederDunTrait ? "<hr>" : "") + texte + "</div>" ;
        else 
            this.texte += "<div>" + (precederDunTrait ? "<hr>" : "") + texte + "</div>" ;
    }
    
    public void displayAlerte(String texte) {
        this.texte += "<div style=\"color : " + toRGB(Color.RED) + "\"><b>" + texte + "</b></div>" ;
        html.setText("<html>" + this.texte +  "</html>");
    }
    
    public String toRGB(Color couleur) {
        return "#"+Integer.toHexString(couleur.getRGB()).substring(2);
    }

    public JPanel getWindow() {
        return window;
    }
    
    
    
}
