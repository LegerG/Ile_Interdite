/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.aventuriers;

import model.cases.Tuile;
import util.Utils;
import util.Utils.Pion;

/**
 *
 * @author legergw
 */
public class Pilote extends Aventurier {
    private boolean pouvoirdispo=true;


    public Pilote(Tuile positionDepart, Pion pion, String nom) {
        super(positionDepart, pion, nom);
        this.roleAventurier=Utils.RoleAventurier.Pilote;
    }
    public Boolean isPilote() {
        return true ;
    }
    public boolean isPouvoirdispo() {
        return pouvoirdispo;
    }

    public void setPouvoirdispo(boolean pouvoirutilise) {
        this.pouvoirdispo = pouvoirutilise;
    }
}
