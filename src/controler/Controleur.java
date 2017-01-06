package controler;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {

    public Controleur() {
    }


    @Override
    public void update(Observable o, Object arg) {
        initialiserPartie();
    }
    
    public void initialiserPartie() {
        
    }

}
