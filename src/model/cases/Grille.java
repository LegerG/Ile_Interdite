package model.cases;

import java.util.ArrayList;
import java.util.Scanner;
import view.VueNiveau;
import java.util.Collections;
import java.util.HashMap;
import util.Utils;
import util.Utils.EtatTuile;

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
        int t =0; //indice d'une tuile dans le tableau de tuile en parametre
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
    
    
      public ArrayList<Integer> getTuilesAccessibles(HashMap<String, Boolean> listeContrainte,int idTuile, boolean powerpilote){
        
          ArrayList<Integer> listeID = new ArrayList<>();
          int i=0,j=0;
          while (i<6) {
              while(j<6 && idTuiles[i][j]!=idTuile){
                  j++;
              }
              i++;
          }
          if(listeContrainte.get("pilote") && powerpilote){
              for (int x = 0; x<6; i++) {
                for (int y = 0 ; y<6; j++) {
                        listeID.add(idTuiles[x][y]);
                    }
                }
            }
          else{
          listeID.add(idTuiles[i+1][j]);
          listeID.add(idTuiles[i-1][j]);
          listeID.add(idTuiles[i][j+1]);
          listeID.add(idTuiles[i][j-1]);
          if(listeContrainte.get("explorateur")){
              listeID.add(idTuiles[i+1][j+1]);
              listeID.add(idTuiles[i-1][j-1]);
              listeID.add(idTuiles[i+1][j-1]);
              listeID.add(idTuiles[i-1][j+1]);
          }
          if(listeContrainte.get("plongeur")){
              for(Integer m : listeID){
                  if (tuiles.get(m).getEtatTuile()!=EtatTuile.ASSECHEE){
                       
                  }                 
              }
          }
          }
          
          for (Integer d : listeID){
              if (tuiles.get(d)!=null || tuiles.get(d).getEtatTuile()!=EtatTuile.COULEE){
                  listeID.remove(d);
              }
          }
          
          
      
  
        
        
        
        return listeID;
        
    }
      private void plongeurTuiles(int i, int j, ArrayList<Integer> newTuiles){
          newTuiles.add(idTuiles[i+1][j]);
          newTuiles.add(idTuiles[i-1][j]);
          newTuiles.add(idTuiles[i][j+1]);
          newTuiles.add(idTuiles[i][j-1]);
      }



}
