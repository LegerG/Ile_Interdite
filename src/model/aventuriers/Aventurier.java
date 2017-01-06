package model.aventuriers;

import java.util.ArrayList;
import java.util.HashMap;
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
    protected String nom;
    protected Boolean deplaceDiagonale=false;
    protected Boolean deplacePartout=false;
    protected Boolean deplaceEau=false;

    public Aventurier(Tuile positionDepart, Pion pion, String nom) {
        super();
        this.nbAction = 3;
        this.position = positionDepart;
        this.main = new ArrayList<>();
        this.pion = pion;
        this.nom = nom;
        
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
    
   

    public HashMap<String,Boolean> getContraintes() {
        HashMap<String,Boolean> listeContraintes = new HashMap<>();
        listeContraintes.put("plongeur", deplaceEau);
        listeContraintes.put("explorateur", deplaceDiagonale);
        listeContraintes.put("pilote", deplacePartout);
    
    return listeContraintes;
    }

    public String getNom() {
        return nom;
    }
    
    
    
    
    
    
}


