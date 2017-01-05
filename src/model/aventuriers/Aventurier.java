package model.aventuriers;

import java.util.ArrayList;
import model.ObjetIdentifie;
import model.cartes.CarteTirage;
import model.cases.Tuile;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Aventurier extends ObjetIdentifie {
    private int nbAction;
    private Tuile position;
    private ArrayList<CarteTirage> main;

    public Aventurier(Tuile positionDepart) {
        super();
        this.nbAction = 3;
        this.position = positionDepart;
        this.main = new ArrayList<>();
    }

    public int getNbAction() {
        return nbAction;
    }

    public void setNbAction(int nbAction) {
        this.nbAction = nbAction;
    }

    public Tuile getPosition() {
        return position;
    }

    public void setPosition(Tuile position) {
        this.position = position;
    }

    public ArrayList<CarteTirage> getMain() {
        return main;
    }

    public void setMain(ArrayList<CarteTirage> main) {
        this.main = main;
    }
    
    
    
    
    
}

