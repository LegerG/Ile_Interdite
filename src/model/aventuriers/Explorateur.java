/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.aventuriers;

import java.util.HashMap;
import model.cases.Tuile;
import util.Utils;
import util.Utils.Pion;
import util.Utils.RoleAventurier;

/**
 *
 * @author legergw
 */
public class Explorateur extends Aventurier{

    public Explorateur(Tuile positionDepart, Pion pion, String nom) {
        super(positionDepart, pion, nom);
        this.roleAventurier=RoleAventurier.Explorateur;
    }

   
  
  
}
