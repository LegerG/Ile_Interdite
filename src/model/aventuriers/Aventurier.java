package model.aventuriers;

import java.util.ArrayList;
import model.ObjetIdentifie;
import model.cartes.CarteTirage;
import model.cartes.CarteTresor;
import model.cases.Tuile;
import util.Utils.Pion;
import util.Utils.RoleAventurier;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Aventurier extends ObjetIdentifie {
    protected int nbAction;
    protected Pion pion;
    protected Tuile position;
    protected ArrayList<CarteTirage> main;
    protected ArrayList<CarteTresor> tresors;
    protected String nom;
    protected RoleAventurier roleAventurier;

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
    public void addCarteTresor(CarteTresor carte){
        tresors.add(carte);
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

    public String getNom() {
        return nom;
    }

    public ArrayList<CarteTresor> getTresors() {
        return tresors;
    }

    public void setTresors(ArrayList<CarteTresor> tresors) {
        this.tresors = tresors;
    }

    public RoleAventurier getRoleAventurier() {
        return roleAventurier;
    }

    public Pion getPion() {
        return pion;
    }
    
    public Boolean isPilote() {
        return false ;
    }
    public Boolean isNavigateur() {
        return false ;
    }
    public Boolean isIngenieur() {
        return false ;
    }
    public Boolean isExplorateur() {
        return false ;
    }
    public Boolean isMessager() {
        return false ;
    }
    public Boolean isPlongeur() {
        return false ;
    }
    
    
    
}


