package controler;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import model.aventuriers.Explorateur;
import view.VueAventurier;
import view.VueNiveau;
import view.VuePlateau;
import model.cases.Grille;
import model.cases.Tuile;
import util.Utils.Commandes;
import util.Utils.Tresor;
import static util.Utils.melangerPositions;
import view.VueConnexion;
import view.VueInscription;


/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {
    private VueInscription vueInscription;
    private VueConnexion vueConnexion;
    private Grille grille;
    private Tuile[] tuiles = new Tuile[24];
    private ArrayList<VueAventurier> vueaventuriers;
    private Explorateur av;
    private VuePlateau vuePlateau;
    private VueNiveau vueNiveau;
    private int nbJoueurs;
    private int difficulte;
   
    
    public Controleur() {
        this.vueConnexion = new VueConnexion();
        this.vueConnexion.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg == Commandes.VALIDER_INSCRIPTION) {
            initialiserPartie();    
        } 
        else if (arg == Commandes.VALIDER_CONNEXION) {
            this.inscription();
        } 
        else if (arg == Commandes.QUITTER) {
            this.quitter(o);
        }
        else if (arg == Commandes.RETOUR) {
            this.retour();
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
        tuiles[6] = new Tuile(Tresor.CALICE, "Le Palais de Corail");
        tuiles[7] = new Tuile(null, "Le Lagon Perdu");
        tuiles[8] = new Tuile(null, "Le Marais Brumeux");
        tuiles[9] = new Tuile(Tresor.ZEPHYR, "Le Jardin des Murmures");
        tuiles[10] = new Tuile(null, "Le Pont des Abîmes");
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
        
        tuiles = melangerPositions(tuiles);
        
        this.grille = new Grille(tuiles);
        

    }

    public void inscription() {
        nbJoueurs = vueConnexion.getNbJoueurs();
        difficulte = vueConnexion.getDifficulte();
        
        if (nbJoueurs == -1 && difficulte == -1) {
            vueConnexion.setMessageErreur("Veuillez choisir un nombre de joueur ainsi qu'une difficulté.");
        }
        else if (nbJoueurs == -1) {
            vueConnexion.setMessageErreur("Veuillez choisir un nombre de joueur.");
        } 
        else if(difficulte == -1) {
            vueConnexion.setMessageErreur("Veuillez choisir une difficulté.");
        } 
        else {
            this.vueInscription = new VueInscription(nbJoueurs);
            this.vueInscription.addObserver(this);
            this.vueConnexion.fermerFenetre();
        }  
    }
    
    public void quitter(Object o){
        if (o instanceof VueConnexion) {
            this.vueConnexion.fermerFenetre();
        }
        else if (o instanceof VueInscription) {
            this.vueInscription.fermerFenetre();
        }
    } 
    
    public void retour(){
        this.vueInscription.fermerFenetre();
        this.vueConnexion.ouvrirFenetre();
       
    }
    

        
}
        
        

        
