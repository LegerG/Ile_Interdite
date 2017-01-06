package controler;


import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import model.aventuriers.Aventurier;
import model.aventuriers.Explorateur;
import model.aventuriers.Ingenieur;
import model.cases.Grille;
import model.cases.Tuile;
import view.VueAventurier;
import view.VueNiveau;
import view.VuePlateau;
import model.cases.Grille;
import model.cases.Tuile;
import util.Utils;
import util.Utils.Commandes;
import util.Utils.Tresor;
import view.VueInscription;


/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {
    private VueInscription vueInscription;
    private Grille grille;
    private ArrayList<Tuile> tuiles = new ArrayList<>();
    private ArrayList<VueAventurier> vueaventuriers;
    private Explorateur av;
    private VuePlateau vp;
    private VueNiveau vn;
   
    
    public Controleur() {

        
        grille = new Grille();
        av = new Explorateur();
        vp = new VuePlateau();
        vn = new VueNiveau(1);
        this.vueaventuriers=new ArrayList<>();
        initVueAventuriers(3);
        this.vueInscription = new VueInscription();
        this.vueInscription.addObserver(this);
        this.grille = new Grille();
        

        
    }


    @Override
    public void update(Observable o, Object arg) {

        if (arg == Commandes.VALIDER_JOUEURS) {
            initialiserPartie();
        }
        
    }
    
    public void initialiserPartie() {
        //Création du plateau
        remplirTuiles();
        
        //Création des joueurs
        
    }
    
    public void remplirTuiles() {
        Tuile t1 = new Tuile(Tresor.CRISTAL, "Caverne du Brasier");
        Tuile t2 = new Tuile(null, "Les Dunes de L'Illusion");
        Tuile t3 = new Tuile(null, "Les falaises del'Oublis");
        Tuile t4 = new Tuile(Tresor.PIERRE, "Le Temple du Soleil");
        Tuile t5 = new Tuile(null, "Val du Crépuscule");
        Tuile t6 = new Tuile(null, "L'Observatoire");
        Tuile t7 = new Tuile(Tresor.CALICE, "Le Palais de Corail");
        Tuile t8 = new Tuile(null, "Le Lagon Perdu");
        Tuile t9 = new Tuile(null, "Le Marais Brumeux");
        Tuile t10 = new Tuile(Tresor.ZEPHYR, "Le Jardin des Murmures");
        Tuile t11 = new Tuile(null, "Le Pont des Abîmes");
        Tuile t12 = new Tuile(Tresor.CALICE, "Le Palais des Marées");
        Tuile t13 = new Tuile(null, "Le Rocher Fantôme");
        Tuile t14 = new Tuile(Tresor.PIERRE, "Le Temple de la Lune");
        Tuile t15 = new Tuile(null, "La Porte d'Or");
        Tuile t16 = new Tuile(Tresor.ZEPHYR, "Le Jardin des Hurlements");
        Tuile t17 = new Tuile(null, "La Porte de Bronze");
        Tuile t18 = new Tuile(null, "La Porte de Fer");
        Tuile t19 = new Tuile(null, "La Tour de Guet");
        Tuile t20 = new Tuile(null, "La Porte de Cuivre");
        Tuile t21 = new Tuile(null, "La Porte d'Argent");
        Tuile t22 = new Tuile(null, "L'Héliport");
        Tuile t23 = new Tuile(null, "La Forêt Pourpre");
        Tuile t24 = new Tuile(Tresor.CRISTAL, "La Caverne des Ombres");
        

    }

    
     public void initVueAventuriers(int nbAventuriers){
        
         for(int i=0;i<nbAventuriers;i++){
             VueAventurier va = new VueAventurier(i);
             this.vueaventuriers.add(va);
         }
     }    
         
         
     public void deplacer(){

         
         grille.getTuilesAccessibles(av.getContraintes(),av.getPosition());
             
         }
         
     
     
     public static void main(String[] args) {   
        
    Controleur c = new Controleur();
    c.deplacer();
         
     }
}
