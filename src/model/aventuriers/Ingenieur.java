/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.aventuriers;

import model.cases.Tuile;
import util.Utils.Pion;
import util.Utils.RoleAventurier;

/**
 *
 * @author legergw
 */
public class Ingenieur extends Aventurier {
    private int pouvoirdisposi1=0;
    public Ingenieur(Tuile positionDepart, Pion pion, String nom) {
        super(positionDepart, pion, nom);
        this.roleAventurier=RoleAventurier.Ingenieur;
    }

    public int getPouvoirdisposi1() {
        return pouvoirdisposi1;
    }

    public void setPouvoirdisposi1(int pouvoirdisposi1) {
        this.pouvoirdisposi1 = pouvoirdisposi1;
    }
   
    
    
}
