package controler;


import java.util.ArrayList;
import java.util.HashMap;
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
import util.Utils.EtatTuile;
import util.Utils.Tresor;
import static util.Utils.melangerPositions;
import view.VueInscription;


/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {
    private VueInscription vueInscription;
    private Grille grille;
    private Tuile[] tuiles = new Tuile[24];
    private ArrayList<VueAventurier> vueaventuriers;
    private Explorateur av;
    private VuePlateau vuePlateau;
    private VueNiveau vueNiveau;
    private Boolean joueurEnDeplacement;
   
    
    public Controleur() {
        this.vueInscription = new VueInscription();
        this.vueInscription.addObserver(this);
        this.remplirTuiles();
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
        this.vuePlateau = new VuePlateau(grille);
        this.vuePlateau.addObserver(this);
        //Création des joueurs
        
    }
    
    public void remplirTuiles() {
        //Création des tuiles
        tuiles[0] = new Tuile(Tresor.CRISTAL, "Caverne du Brasier");
        tuiles[1] = new Tuile(null, "Les Dunes de L'Illusion");
        tuiles[2] = new Tuile(null, "Les falaises del'Oublis");
        tuiles[3] = new Tuile(Tresor.PIERRE, "Le Temple du Soleil");
        tuiles[4] = new Tuile(null, "Val du Crépuscule");
        tuiles[5] = new Tuile(null, "L'Observatoire"); 
        tuiles[6] = new Tuile(Tresor.CALICE, "Le Palais de Corail");this.tuiles[5].setEtatTuile(EtatTuile.INONDEE);
        tuiles[7] = new Tuile(null, "Le Lagon Perdu");
        tuiles[8] = new Tuile(null, "Le Marais Brumeux");
        tuiles[9] = new Tuile(Tresor.ZEPHYR, "Le Jardin des Murmures");
        tuiles[10] = new Tuile(null, "Le Pont des Abîmes");this.tuiles[10].setEtatTuile(EtatTuile.INONDEE);
        tuiles[11] = new Tuile(Tresor.CALICE, "Le Palais des Marées");
        tuiles[12] = new Tuile(null, "Le Rocher Fantôme");
        tuiles[13] = new Tuile(Tresor.PIERRE, "Le Temple de la Lune");
        tuiles[14] = new Tuile(null, "La Porte d'Or");
        tuiles[15] = new Tuile(Tresor.ZEPHYR, "Le Jardin des Hurlements");
        tuiles[16] = new Tuile(null, "La Porte de Bronze");
        tuiles[17] = new Tuile(null, "La Porte de Fer");
        tuiles[18] = new Tuile(null, "La Tour de Guet");
        tuiles[19] = new Tuile(null, "La Porte de Cuivre");
        tuiles[20] = new Tuile(null, "La Porte d'Argent");
        tuiles[21] = new Tuile(null, "L'Héliport");
        tuiles[22] = new Tuile(null, "La Forêt Pourpre");
        tuiles[23] = new Tuile(Tresor.CRISTAL, "La Caverne des Ombres");
        
       // tuiles = melangerPositions(tuiles);
        
        this.grille = new Grille(tuiles);
        
        HashMap<String,Boolean> listeContraintes = new HashMap<>();
        listeContraintes.put("plongeur", true);
        listeContraintes.put("explorateur", false);
        listeContraintes.put("pilote", false);
        for (Integer i :this.grille.getTuilesAccessibles(listeContraintes, 2,true)){
            System.out.println("    "+i);
        }
      
        
    }

    

        
     
}
        
        

        
