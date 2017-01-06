package model.cases;

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

    private Integer[][] idTuiles = new Integer[6][6]; // Tableau d'ID
    private HashMap<Integer, Tuile> tuiles = new HashMap<>();
    private final Integer[][] tableauBase = 
    {
     {0,0,1,1,0,0},
     {0,1,1,1,1,0},
     {1,1,1,1,1,1},
     {1,1,1,1,1,1},
     {0,1,1,1,1,0},
     {0,0,1,1,0,0},        
    };
    
    /** -------------------------------------------------------------------------------------------------------------
     * Constructeur
     */
    public Grille(Tuile[] tuiles) {
        for (int i=0; i < tuiles.length; i++){ //Remplissage de this.tuiles
            this.tuiles.put(tuiles[i].getId(), tuiles[i]);
        }
        
        //Remplissage de idTuiles
        int t =0; //indice d'une truile dans le tableau de tuile en parametre
        for (int i = 0; i<6; i++) {
            for (int j = 0 ; j< 6; j++) {
                if (tableauBase[i][j] != 0) {
                    this.idTuiles[i][j] = tuiles[t].getId();
                    t++;
                }
            }
        }
        
        
    }   

    public Integer[][] getIdTuiles() {
        return idTuiles;
    }

    public void setIdTuiles(Integer[][] idTuiles) {
        this.idTuiles = idTuiles;
    }

    public HashMap<Integer, Tuile> getTuiles() {
        return tuiles;
    }

    public void setTuiles(HashMap<Integer, Tuile> tuiles) {
        this.tuiles = tuiles;
    }

    
    
    public void addTuile(Tuile tuile ){
        
    }
    
    


public static void main(String[] args) {   
        
    Grille grille = new Grille();
    if (grille.getTuiles()[0][0] == null){
        System.out.println("NULL");
    }
    }    

}
