package model.cases;

import java.util.ArrayList;
import model.ObjetIdentifie;
import model.aventuriers.Aventurier;
import util.Utils;
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

    public String getNom() {
        return nom;
    }
    
    
    
}
