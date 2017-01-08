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
        for (Tuile tuile : tuiles) {
            this.tuiles.put(tuile.getId() - 1, tuile);
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
        ArrayList<Integer> listefinale = new ArrayList<>();
        ArrayList<Integer> listeID = new ArrayList<>();
        int i,j;
        int[] coor = new int[2];
        coor=this.getCoordonneesAvecId(idTuile);
        i=coor[0];
        j=coor[1];
        
        // Déplacement du pilote avec son pouvoir
        if(listeContrainte.get("pilote") && powerpilote){
            for (int x = 0; x<6; x++) {
                for (int y = 0 ; y<6; y++) {
                    listeID.add(idTuiles[x][y]);
                }
            }
        }
        else {
            this.addCasesAdjacentes(i, j, listeID);

            // Déplacement explorateur
            if(listeContrainte.get("explorateur")) {
                this.addCasesDiagonales(i, j, listeID);
            }
            
            // Déplacement plongeur
            if(listeContrainte.get("plongeur")){                
                int x= 0; int y=0;
                boolean connexion=true;
                ArrayList<Integer> listeconnectee = new ArrayList<>(); 
                do {
                    this.ajoutListeSansDoublon(listefinale, listeID); 
                    // on ajoute les cases de listeID dans la liste finale de tuiles accessibles
                    for (Integer n : listefinale){
                        System.out.println("liste finale : "+n);
                    }
                    connexion=true;


                    for(Integer m : listeID){

                        if (tuiles.get(m).getEtatTuile()!=EtatTuile.ASSECHEE){ 
                            int[] coorxy = new int[2];
                            coorxy=this.getCoordonneesAvecId(m);
                            x=coorxy[0];
                            y=coorxy[1];
                            this.addCasesAdjacentes(x, y, listeconnectee); 
                            // on trouve la position des tuiles inondées ou coulées et on ajoute leurs cases adjacentes
                            connexion=false; // il y au moins 1 tuile inondéé ou coulée
                        }
                    }
                    listeID.clear(); // on veut maintenant reboucler sur la listeconnectée
                    listeID.addAll(listeconnectee);
                    for (Integer n : listefinale){ 
                        if (listeID.contains(n)) listeID.remove(n);  
                        // On retire a la liste ID ce qui a été déjà pris en compte = ce qui est dans la liste finale
                    } 
                    for (Integer n : listeconnectee){
                        System.out.println("liste connectée : "+n);
                    }
                    listeconnectee.clear(); 
                    // on veut maintenant avoir dans la liste connectée les tuiles inondées adjacentes des tuiles inondées
                } while (!connexion);
               listeID.addAll(listefinale);
               listeID.remove(idTuiles[i][j]); // enlever la case ou on se trouve dans le cas du plongeur

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
      
      private void ajoutListeSansDoublon(ArrayList<Integer> listeID, ArrayList<Integer> listeconnectee){
          for (Integer n : listeconnectee){
              if (!listeID.contains(n)) listeID.add(n);
          }
              
      }
      
      public Tuile getTuileAvecID(int id){
         return this.tuiles.get(id);
      }
      



}
    



