package controler;


import java.util.ArrayList;
import java.util.HashMap;
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
import util.Utils.EtatTuile;
import util.Utils.RoleAventurier;
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
        remplirPioche();
        
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
    
    public void remplirPioche() {
        
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
        tuiles[0] = new Tuile(Tresor.CRISTAL, "LaCarverneDuBrasier");
        tuiles[1] = new Tuile(null, "LesDunesDeLIllusion");
        tuiles[2] = new Tuile(null, "LesFalaisesDeLOubli");
        tuiles[3] = new Tuile(Tresor.PIERRE, "LeTempleDuSoleil");
        tuiles[4] = new Tuile(null, "LeValDuCrepuscule");
        tuiles[5] = new Tuile(null, "Observatoire");
        tuiles[6] = new Tuile(Tresor.CALICE, "LePalaisDeCorail");
        tuiles[7] = new Tuile(null, "LeLagonPerdu");
        tuiles[8] = new Tuile(null, "LeMaraisBrumeux");
        tuiles[9] = new Tuile(Tresor.ZEPHYR, "LeJardinDesMurmures");
        tuiles[10] = new Tuile(null, "LePontDesAbimes"); this.tuiles[10].setEtatTuile(EtatTuile.INONDEE);
        tuiles[11] = new Tuile(Tresor.CALICE, "LePalaisDesMarees");
        tuiles[12] = new Tuile(null, "LeRocherFantome");
        tuiles[13] = new Tuile(Tresor.PIERRE, "LeTempleDeLaLune");
        tuiles[14] = new Tuile(null, "LaPortedOr");
        tuiles[15] = new Tuile(Tresor.ZEPHYR, "LeJardinDesHurlements");
        tuiles[16] = new Tuile(null, "LaPorteDeBronze");
        tuiles[17] = new Tuile(null, "LaPorteDeFer");
        tuiles[18] = new Tuile(null, "LaTourDuGuet");
        tuiles[19] = new Tuile(null, "LaPorteDeCuivre");
        tuiles[20] = new Tuile(null, "LaPortedArgent");
        tuiles[21] = new Tuile(null, "Heliport");
        tuiles[22] = new Tuile(null, "LaForetPourpre");
        tuiles[23] = new Tuile(Tresor.CRISTAL, "LaCarverneDesOmbres");
        
        
        
        Tuile[] tuilesMelange = melangerPositions(tuiles);
        this.grille = new Grille(tuilesMelange);
        
        HashMap<String,Boolean> listeContraintes = new HashMap<>();
            listeContraintes.put("plongeur", true);
            listeContraintes.put("explorateur", false);
            listeContraintes.put("pilote", false);
            
        for (Integer i : this.grille.getTuilesAccessibles(listeContraintes, 2,true)){
            System.out.println("    "+i);
        }
      
        
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
        
        

        
        
