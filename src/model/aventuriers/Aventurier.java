package model.aventuriers;

import model.ObjetIdentifie;

/**
 *
 * @author IUT2-Dept Info
 */
public  class Aventurier extends ObjetIdentifie {
    private int position =2;
    
    public int[][] getCasesAccessibles(){
        
      int[][] table = new int[10][2];
        table[0][0]=this.position+1;
        table[0][1]=this.position;
        table[1][0]=this.position-1;
        table[1][1]=this.position;
        
        
        
        return table;
        
    }
    
}
