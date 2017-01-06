package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import javax.swing.JFrame;
 
public class VueAventurier extends Observable {
    
    private JFrame window;
    
    
    public VueAventurier(int i){
        window = new JFrame();
        window.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width-200,i * 200);
        window.setSize(300,175);
        window.setTitle("Aventurier" + i);
    }
    
   
}