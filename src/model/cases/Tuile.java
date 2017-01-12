package model.cases;

import java.util.ArrayList;
import model.ObjetIdentifie;
import model.aventuriers.Aventurier;
import util.Utils.EtatTuile;
import util.Utils.Tresor;

/**
 *
 * @author IUT2-Dept Info
 */
public class Tuile extends ObjetIdentifie {
    private Tresor tresor;
    private String nom;
    private EtatTuile etatTuile;
    private ArrayList<Aventurier> aventuriers = new ArrayList<>();

    public Tuile(Tresor tresor, String nom) {
        super();
        this.tresor = tresor;
        this.nom = nom;
        this.etatTuile = EtatTuile.ASSECHEE;
    }

    public Tresor getTresor() {
        return tresor;
    }

    public void setTresor(Tresor tresor) {
        this.tresor = tresor;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public EtatTuile getEtatTuile() {
        return etatTuile;
    }

    public void setEtatTuile(EtatTuile etatTuile) {
        this.etatTuile = etatTuile;
    }

    public ArrayList<Aventurier> getAventuriers() {
        return aventuriers;
    }

    public void setAventuriers(ArrayList<Aventurier> aventuriers) {
        this.aventuriers = aventuriers;
    }
    
    public void addAventurier(Aventurier a) {
        aventuriers.add(a);
    }
    
    public void removeAventurier(Aventurier a) {
        aventuriers.remove(a);
    }
    
}
