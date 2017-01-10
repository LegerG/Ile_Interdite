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
public class Plongeur extends Aventurier {
    
    public Plongeur(Tuile positionDepart, Pion pion, String nom) {
        super(positionDepart, pion, nom);
        this.roleAventurier=Utils.RoleAventurier.Plongeur;
    }
    @Override
    public Boolean isPlongeur() {
        return true ;
    }
}
