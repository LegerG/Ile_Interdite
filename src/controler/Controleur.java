package controler;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import model.aventuriers.Aventurier;
import model.aventuriers.Explorateur;
import model.aventuriers.Ingenieur;
import model.aventuriers.Messager;
import model.aventuriers.Navigateur;
import model.aventuriers.Pilote;
import model.aventuriers.Plongeur;
import view.VueAventurier;
import view.VueNiveau;
import view.VuePlateau;
import model.cases.Grille;
import model.cases.Tuile;
import util.Utils;
import util.Utils.Commandes;
import util.Utils.RoleAventurier;
import static util.Utils.RoleAventurier.Ingenieur;
import static util.Utils.RoleAventurier.Messager;
import static util.Utils.RoleAventurier.Navigateur;
import util.Utils.Tresor;
import static util.Utils.melangerPositions;
import static util.Utils.melangerRole;
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
    private ArrayList<VueAventurier> vueAventuriers;
    private ArrayList<Aventurier> joueurs = new ArrayList<>();
    private VuePlateau vuePlateau;
    private VueNiveau vueNiveau;
    private int nbJoueurs;
    private int nbCartesInnondationsPioches;
    private int niveauEau;
    
    
   
    
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
            this.lancementInscription();
        } 
        else if (arg == Commandes.QUITTER) {
            this.quitter(o);
        }
        else if (arg == Commandes.RETOUR) {
            this.retour();
        }
    }
    
    public void initialiserPartie() {
        //Creation des cartes
        
        
        //Création du plateau
        remplirTuiles();
        this.vueInscription.fermerFenetre();
        this.vuePlateau = new VuePlateau(grille);
        this.vueNiveau = new VueNiveau(nbCartesInnondationsPioches);
        this.vuePlateau.addObserver(this);
        //piocher 6 cartes innondations
        
        //Création des joueurs
        attribuerRoleJoueurs();
        
        //donner deux cartes aux joueurs
        
        //init niveau d'eau
        
        
    }
    
    public void attribuerRoleJoueurs() {
        RoleAventurier[] rolesAventurier = melangerRole(RoleAventurier.values());
        int i = 0;
        Aventurier a = null;
        while (i < vueInscription.getNomJoueur().size()) {
            if (rolesAventurier[i] == RoleAventurier.Explorateur) {
                a = new Explorateur(tuiles[16], Utils.Pion.VERT, vueInscription.getNomJoueur().get(i));
            }
            else if (rolesAventurier[i] == RoleAventurier.Ingenieur){
                a = new Ingenieur(tuiles[17], Utils.Pion.ROUGE, vueInscription.getNomJoueur().get(i));
            }
            else if (rolesAventurier[i] == RoleAventurier.Messager){
                a = new Messager(tuiles[19], Utils.Pion.ORANGE, vueInscription.getNomJoueur().get(i));
            }
            else if (rolesAventurier[i] == RoleAventurier.Navigateur){
                a = new Navigateur(tuiles[20], Utils.Pion.JAUNE, vueInscription.getNomJoueur().get(i));
            }
            else if (rolesAventurier[i] == RoleAventurier.Pilote){
                a = new Pilote(tuiles[21], Utils.Pion.BLEU, vueInscription.getNomJoueur().get(i));
            }
            else if (rolesAventurier[i] == RoleAventurier.Plongeur){
                a = new Plongeur(tuiles[14], Utils.Pion.VIOLET, vueInscription.getNomJoueur().get(i));
            }
            
            joueurs.add(a);
            i++;
        }
    }
    
    
    public void remplirTuiles() {
        //Création des tuiles
        tuiles[0] = new Tuile(Tresor.CRISTAL, "CaverneDuBrasier");
        tuiles[1] = new Tuile(null, "Les Dunes de L'Illusion");
        tuiles[2] = new Tuile(null, "Les falaises del'Oublis");
        tuiles[3] = new Tuile(Tresor.PIERRE, "LeTempleDuSoleil");
        tuiles[4] = new Tuile(null, "ValDuCrépuscule");
        tuiles[5] = new Tuile(null, "Observatoire");
        tuiles[6] = new Tuile(Tresor.CALICE, "LePalaisDeCorail");
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
        
        Tuile[] tuilesMelange = melangerPositions(tuiles);
        
        this.grille = new Grille(tuilesMelange);
        

    }

    public void lancementInscription() {
        nbJoueurs = vueConnexion.getNbJoueurs();
        niveauEau = vueConnexion.getDifficulte() + 1;
        setNbCartesInnondationsPioches(niveauEau);
        
        if (nbJoueurs == -1 && niveauEau == 0) {
            vueConnexion.setMessageErreur("Veuillez choisir un nombre de joueur ainsi qu'une difficulté.");
        }
        else if (nbJoueurs == -1) {
            vueConnexion.setMessageErreur("Veuillez choisir un nombre de joueur.");
        } 
        else if(niveauEau == 0) {
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
    
    public void setNbCartesInnondationsPioches(int nvEau) {
        if (nvEau <= 2) {
            nbCartesInnondationsPioches = 2;
        }
        else if (nvEau >= 3 && nvEau <= 5) {
            nbCartesInnondationsPioches = 3;
        }
        else if (nvEau >= 6 && nvEau <= 7) {
            nbCartesInnondationsPioches = 4;
        }
        else if (nvEau >= 8 && nvEau < 10) {
            nbCartesInnondationsPioches = 5;
        }
        else {
            nbCartesInnondationsPioches = 6;
        }
        
    }
        
}
        
        

        
