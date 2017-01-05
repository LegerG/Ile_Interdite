package controler;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import model.aventuriers.Aventurier;
import model.cases.Grille;
import model.cases.Tuile;
import view.VueAventurier;

/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {

    private Grille grille;
    private ArrayList<VueAventurier> vueaventuriers;
    private Aventurier av;
   
    
    public Controleur() {
        
        grille = new Grille();
        av = new Aventurier();
        
        this.vueaventuriers=new ArrayList<>();
        initVueAventuriers(3);
    }


    @Override
    public void update(Observable o, Object arg) {
    }

    
     public void initVueAventuriers(int nbAventuriers){
        
         for(int i=0;i<nbAventuriers;i++){
             VueAventurier va = new VueAventurier(i);
             this.vueaventuriers.add(va);
         }
     }    
         
         
     public void deplacer(){
         int[][] table = new int[10][2];
         table= av.getCasesAccessibles();
         System.out.println(table[0][0]+" :  " + table[0][1]);
         if (grille.getTuiles()[table[0][0]][table[0][1]]==null){
             System.out.println("Comme la tuile possible donnée par l'aventurier est null");
             
         }
         
     }
     
     public static void main(String[] args) {   
        
    Controleur c = new Controleur();
    c.deplacer();
         
     }
}
