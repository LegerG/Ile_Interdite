package model.cases;

import java.util.ArrayList;
import java.util.Scanner;
import view.VueNiveau;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import util.Utils;
import util.Utils.EtatTuile;
import static util.Utils.getFORME_GRILLE;

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
    
    
    /** -------------------------------------------------------------------------------------------------------------
     * Constructeur
     */
    public Grille(Tuile[] tuiles) {
        //Remplissage de this.tuiles
        for (int i=0; i < tuiles.length; i++){ 
            this.tuiles.put(tuiles[i].getId(), tuiles[i]);
        }
        
        //Remplissage de idTuiles
        int t =0; //indice d'une tuile dans le tableau de tuile en parametre
        for (int i = 0; i<6; i++) {
            for (int j = 0 ; j< 6; j++) {
                if (getFORME_GRILLE()[i][j] != 0) {
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

      public ArrayList<Integer> getTuilesAccessibles(HashMap<String, Boolean> listeContrainte,int idTuile, boolean powerpilote){
        
          ArrayList<Integer> listeID = new ArrayList<>();
          int i,j;
          int[] coor = new int[2];
          coor=this.getCoordonneesAvecId(idTuile);
          i=coor[0];
          j=coor[1];
          if(listeContrainte.get("pilote") && powerpilote){
              for (int x = 0; x<6; x++) {
                for (int y = 0 ; y<6; y++) {
                        listeID.add(idTuiles[x][y]);
                    }
                }
            }
          else{
          this.addCasesAdjacentes(i, j, listeID);
          if(listeContrainte.get("explorateur")){
              this.addCasesDiagonales(i, j, listeID);
              
          }
          if(listeContrainte.get("plongeur")){
              for(Integer m : listeID){
                  if (tuiles.get(m).getEtatTuile()!=EtatTuile.ASSECHEE){
                       int x,y;
                       int[] coorxy = new int[2];
                       coorxy=this.getCoordonneesAvecId(m);
                       x=coorxy[0];
                       y=coorxy[1];
                       this.addCasesAdjacentes(x, y, listeID);
                  }                 
              }
          }
          }
          
        
          Iterator it = listeID.iterator();
          while(it.hasNext()){
              Integer id = (Integer) it.next();
             if (tuiles.get(id)==null || tuiles.get(id).getEtatTuile()==EtatTuile.COULEE){
                 it.remove();
              }
          }
             
        return listeID;
        
    }
      public ArrayList<Integer> getTuilesAssechables(HashMap<String, Boolean> listeContrainte, int idTuile){
          ArrayList<Integer> listeID = new ArrayList<>();
          int i,j;
          int[] coor = new int[2];
          coor=this.getCoordonneesAvecId(idTuile);
          i=coor[0];
          j=coor[1];
          this.addCasesAdjacentes(i, j, listeID);
          listeID.add(idTuiles[i][j]);
          if(listeContrainte.get("explorateur")){
             this.addCasesDiagonales(i, j, listeID);
          }
        
          
           Iterator it = listeID.iterator();
           while(it.hasNext()){
              Integer id = (Integer) it.next();
             if (tuiles.get(id)==null || tuiles.get(id).getEtatTuile()!=EtatTuile.INONDEE){
                 System.out.println(id);
                 it.remove();
              }
          }
         
         
          return listeID;
      }
      
      private void addCasesAdjacentes(int i, int j, ArrayList<Integer> newTuiles){
        if(i!=5) newTuiles.add(idTuiles[i+1][j]);
        if(i!=0)  newTuiles.add(idTuiles[i-1][j]);
        if(j!=5)  newTuiles.add(idTuiles[i][j+1]);
        if(j!=0)  newTuiles.add(idTuiles[i][j-1]);
          
          Iterator it = newTuiles.iterator(); // boucle pour supprimer les cases nulles duquel le plongeur pourrait repartir
          while(it.hasNext()){
              Integer id = (Integer) it.next();
              if (tuiles.get(id)==null){
                 it.remove();
              }
          }
          
          
          
      }
      private void addCasesDiagonales(int i, int j, ArrayList<Integer> newTuiles){
              if(i!=5 && j!=5) newTuiles.add(idTuiles[i+1][j+1]);
              if(i!=0 && j!=0) newTuiles.add(idTuiles[i-1][j-1]);  //tests permettant de savoir si on est hors de la grile ou non
              if(i!=5 && j!=0) newTuiles.add(idTuiles[i+1][j-1]);
              if(i!=0 && j!=5) newTuiles.add(idTuiles[i-1][j+1]);
          }
      private int[] getCoordonneesAvecId(int idTuile){
          int[] coor= new int[2];
          int i=0,j=0;
          boolean trouve=false;
          while (i<6 && !trouve) {
              System.out.println(i);
              j=0;
              while(j<6 && !trouve){                 
                 if (idTuiles[i][j]!=null && idTuiles[i][j]==idTuile) trouve=true;
                   System.out.println("j :" +j);
                    j++;
                }
              i++;
              }
          i--;
          j--;
          System.out.println(i); System.out.println(j);
          System.out.println("TROUVE");
          coor[0]=i;
          coor[1]=j;
          return coor;
      }



}
    



