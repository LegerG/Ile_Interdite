package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author IUT2-Dept Info
 */
public class VuePlateau extends Observable {
    
    private JFrame window;
    
    public VuePlateau(){
        
        
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // Définit la taille de la fenêtre en pixels
        window.setSize(1138, 831);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width, dim.height/2-window.getSize().height/2);
        
        VueGrille mainPanel = new VueGrille(new GridLayout(6,6));
        
        window.add(mainPanel);
        window.setVisible(true);
        
        JButton test = new JButton("TEST");
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println( window.getWidth() + " "+window.getHeight() );
            }
        });
        JPanel commandePanel = new JPanel();
        window.add(commandePanel);
        commandePanel.add(test);
    }
    
}
