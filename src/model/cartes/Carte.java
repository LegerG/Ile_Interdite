package model.cartes;

import model.ObjetIdentifie;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Carte extends ObjetIdentifie {
    private  String nomFichier;

    public Carte(String nomFichier) {
        super();
        this.nomFichier = nomFichier;
    }

    public String getNomFichier() {
        return nomFichier;
    }
    
    
}
