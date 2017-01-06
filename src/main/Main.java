package main;

import controler.Controleur;
import view.VueInscription;

public class Main {
  
    public static void main(String[] args) {   
        Controleur controleur = new Controleur() ;
        
        VueInscription vueInscription = new VueInscription();
        vueInscription.addObserver(controleur);
    }
}
