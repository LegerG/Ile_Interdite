package model.cases;

import java.util.ArrayList;
import java.util.Scanner;
import view.VueNiveau;
import java.util.Collections;
import java.util.HashMap;

/**
 * Classe permettant de gérer la grille des tuiles du jeu
 Elle gère un unique attribut : un tableau de 6 x 6 tuiles
 Il y a 12 tuiles null et 24 tuiles réelles.
 * Les tuiles sont donc (ligne, colonne)
  null    null    (0,2)   (0,3)   null    null
  null    (1,1)   (1,2)   (1,3)   (1,4)   null
  (2,0)   (2,1)   (2,2)   (2,3)   (2,4)   (2,5)
  (3,0)   (3,1)   (3,2)   (3,3)   (3,4)   (3,5)
  null    (4,1)   (4,2)   (4,3)   (4,4)   null
  null    null    (5,2)   (5,3)   null    null
 * @author IUT2-Dept Info
 */
public class Grille {

    Tuile[][] tuiles ; // Les tuiles du jeu
    
    
    /** -------------------------------------------------------------------------------------------------------------
     * Constructeur
     */
    public Grille() {
        this.tuiles = new Tuile[6][6];
        
    }
    
    public void initTuiles(){
        
        
        
    }

    public Tuile[][] getTuiles() {
        return tuiles;
    }
    
      public ArrayList<Integer> getTuilesAccessibles(HashMap<String, Boolean> listeContrainte,Tuile pos){
        
      ArrayList<Integer> listeID = new ArrayList<>();
  
        
        
        
        return listeID;
        
    }


public static void main(String[] args) {   
        
    Grille grille = new Grille();
    if (grille.getTuiles()[0][0] == null){
        System.out.println("NULL");
    }
    }    

}
