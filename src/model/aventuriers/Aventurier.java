package model.aventuriers;

import java.util.ArrayList;
import model.ObjetIdentifie;
import model.cases.Grille;

/**
 *
 * @author IUT2-Dept Info
 */
public  class Aventurier extends ObjetIdentifie {
    private int position =2;
    
    public ArrayList<Integer> getCasesAccessibles(Grille grille){
        
      ArrayList<Integer> listeID = new ArrayList<>();
  
        listeID.add(grille.getTuiles()[][])
        
        
        
        return listeID;
        
    }
    
}
