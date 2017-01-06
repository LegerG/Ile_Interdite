package model.aventuriers;

import java.util.ArrayList;
import model.ObjetIdentifie;

import model.cases.Grille;

/**
 *
 * @author IUT2-Dept Info
 */

 

import model.cartes.CarteTirage;
import model.cases.Tuile;
import util.Utils.Pion;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Aventurier extends ObjetIdentifie {
    protected int nbAction;
    protected Pion pion;
    protected Tuile position;
    protected ArrayList<CarteTirage> main;

    public Aventurier(Tuile positionDepart, Pion pion) {
        super();
        this.nbAction = 3;
        this.position = positionDepart;
        this.main = new ArrayList<>();
        this.pion = pion;
    }

    //METHODES
    
    public void removeCarte(CarteTirage carte){
        main.remove(carte);
    }
    
    public void addCarte(CarteTirage carte){
        main.add(carte);
    }
    
    //GETTER AND SETTER
    
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
    
     public ArrayList<Integer> getCasesAccessibles(Grille grille){
        
      ArrayList<Integer> listeID = new ArrayList<>();
  
//        listeID.add(grille.getTuiles()[][])
        
        
        
        return listeID;
        
    }
    
    
    
    
    
}


