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
import model.cartes.CarteHelicoptere;
import model.cartes.CarteInondation;
import model.cartes.CarteMonteeDesEaux;
import model.cartes.CarteSacsDeSable;
import model.cartes.CarteTirage;
import model.cartes.CarteTresor;
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
import static util.Utils.melangerCartesInondations;
import static util.Utils.melangerCartesTirages;
import static util.Utils.melangerPositions;
import static util.Utils.melangerRole;
import view.VueConnexion;
import view.VueInscription;
import view.VueRegles;


/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {
    //IHMs
    private VueInscription vueInscription;
    private VueConnexion vueConnexion;
    private ArrayList<VueAventurier> vueAventuriers;
    private VuePlateau vuePlateau;
    private VueNiveau vueNiveau;
    private VueRegles vueRegles;
    
    //Plateau
    private Grille grille;
    private Tuile[] tuiles = new Tuile[24];
    private int niveauEau;
    
    //Partie
    private ArrayList<Aventurier> joueurs = new ArrayList<>();
    private Aventurier jCourant;
    private Aventurier jExceptionnel; //a prevoir pour les inturuptions de parties lors des deffausses de cartes ou autre
    private int nbJoueurs;
    private int nbCartesInnondationsPioches;
    private ArrayList<Tresor> tresorsGagnes;
    private boolean powerpilote;
    private boolean phaseDeDeplacement;
    private int actionsRestantes;


    
    //Cartes
    private CarteInondation[] deffausseInondation = new CarteInondation[24];
    private CarteInondation[] piocheInondation = new CarteInondation[24];
    private CarteTirage[] deffausseTirage = new CarteTirage[27];
    private CarteTirage[] piocheTirage = new CarteTirage[27];
    private boolean phaseAssechement;
    
    
    public Controleur() {
        this.vueConnexion = new VueConnexion();
        this.vueConnexion.addObserver(this);
        
        vueRegles = new VueRegles();        
        this.vueRegles.addObserver(this);
    
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
        else if (arg == Commandes.REGLES) {
            this.afficherRegles();
        }
        else if (arg == Commandes.RECUPERER_TRESOR) {
            this.recupererTresor();
        }
        else if (arg == Commandes.BOUGER){
            for(int i : this.grille.getTuilesAccessibles(jCourant.getContraintes(), jCourant.getPosition().getId(), powerpilote)){
                this.vuePlateau.surbriller(i); //fonctionnel, créer une bordure jaune sur les tuiles sur lesquelles ont peut cliquer
            }
            this.phaseDeDeplacement=true;
        }
        else if(arg == Commandes.ASSECHER){
            for(int i :  this.grille.getTuilesAssechables(jCourant.getContraintes(), jCourant.getPosition().getId())){
              //this.vuePlateau.surbriller(i);
          }  
            this.phaseAssechement=true;
        }
        else if(arg == Commandes.TERMINER){
            this.finTour(); // fin du tour
        }
        else if(arg instanceof Integer){
            System.out.println("azerty");
            if(phaseDeDeplacement==true){
                if (this.grille.getTuilesAccessibles(jCourant.getContraintes(), 
                        jCourant.getPosition().getId(), powerpilote).contains(arg))
                {
                    this.jCourant.getPosition().getAventuriers().remove(jCourant);
                    this.jCourant.setPosition(this.grille.getTuileAvecID((int)arg));
                    this.phaseDeDeplacement=false;
                    actionsRestantes--;
                }
                else{
                    //on ne peut pas se déplacer là
                }

            }
            if(phaseAssechement==true && this.grille.getTuilesAccessibles(jCourant.getContraintes(), 
                    jCourant.getPosition().getId(), powerpilote).contains(arg))
            {
                this.grille.getTuileAvecID((int) arg).setEtatTuile(EtatTuile.ASSECHEE);
                this.phaseAssechement=false;
                actionsRestantes--;
            }
        }  
        
    }
    
    public void initialiserPartie() {
        //Creation des cartes
        remplirPioches();
        
        
        //Création du plateau
        
        remplirTuiles();
        this.vueInscription.fermerFenetre();
        this.vuePlateau = new VuePlateau(grille, this);
        this.vueNiveau = new VueNiveau(nbCartesInnondationsPioches);
        this.vuePlateau.addObserver(this);
        //piocher 6 cartes innondations
        
        //Création des joueurs
        attribuerRoleJoueurs();
        
        //donner deux cartes aux joueurs
        
        //init niveau d'eau
        
        
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
    
     public void remplirPioches() {
        //Carte inondation
        piocheInondation[0] = new CarteInondation(("LaCarverneDuBrasier"));
        piocheInondation[1] = new CarteInondation(("LesDunesDeLIllusion"));
        piocheInondation[2] = new CarteInondation(("LesFalaisesDeLOubli"));
        piocheInondation[3] = new CarteInondation(("LeTempleDuSoleil"));
        piocheInondation[4] = new CarteInondation(("LeValDuCrepuscule"));
        piocheInondation[5] = new CarteInondation(("Observatoire"));
        piocheInondation[6] = new CarteInondation(("LePalaisDeCorail"));
        piocheInondation[7] = new CarteInondation(("LeLagonPerdu"));
        piocheInondation[8] = new CarteInondation(("LeMaraisBrumeux"));
        piocheInondation[9] = new CarteInondation(("LeJardinDesMurmures"));
        piocheInondation[10] = new CarteInondation(("LePontDesAbimes"));
        piocheInondation[11] = new CarteInondation(("LeRocherFantome"));
        piocheInondation[12] = new CarteInondation(("LaPortedOr"));
        piocheInondation[13] = new CarteInondation(("LeJardinDesHurlements"));
        piocheInondation[14] = new CarteInondation(("LaPorteDeBronze"));
        piocheInondation[15] = new CarteInondation(("LaPorteDeFer"));
        piocheInondation[16] = new CarteInondation(("LaTourDuGuet"));
        piocheInondation[17] = new CarteInondation(("LaPorteDeCuivre"));
        piocheInondation[18] = new CarteInondation(("LaPortedArgent"));
        piocheInondation[19] = new CarteInondation(("Heliport"));
        piocheInondation[20] = new CarteInondation(("LaForetPourpre"));
        piocheInondation[21] = new CarteInondation(("LaCarverneDesOmbres"));
        piocheInondation[22] = new CarteInondation(("LePalaisDesMarees"));
        piocheInondation[23] = new CarteInondation(("LeTempleDeLaLune"));
        
        //Carte Tirage
        piocheTirage[0] = new CarteTresor("Calice", Tresor.CALICE);
        piocheTirage[1] = new CarteTresor("Calice", Tresor.CALICE);
        piocheTirage[2] = new CarteTresor("Calice", Tresor.CALICE);
        piocheTirage[3] = new CarteTresor("Calice", Tresor.CALICE);
        piocheTirage[4] = new CarteTresor("Calice", Tresor.CALICE);
        piocheTirage[5] = new CarteTresor("Cristal", Tresor.CRISTAL);
        piocheTirage[6] = new CarteTresor("Cristal", Tresor.CRISTAL);
        piocheTirage[7] = new CarteTresor("Cristal", Tresor.CRISTAL);
        piocheTirage[8] = new CarteTresor("Cristal", Tresor.CRISTAL);
        piocheTirage[9] = new CarteTresor("Cristal", Tresor.CRISTAL);
        piocheTirage[10] = new CarteTresor("Pierre", Tresor.PIERRE);
        piocheTirage[11] = new CarteTresor("Pierre", Tresor.PIERRE);
        piocheTirage[12] = new CarteTresor("Pierre", Tresor.PIERRE);
        piocheTirage[13] = new CarteTresor("Pierre", Tresor.PIERRE);
        piocheTirage[14] = new CarteTresor("Pierre", Tresor.PIERRE);
        piocheTirage[15] = new CarteTresor("Zephyr", Tresor.ZEPHYR);
        piocheTirage[16] = new CarteTresor("Zephyr", Tresor.ZEPHYR);
        piocheTirage[17] = new CarteTresor("Zephyr", Tresor.ZEPHYR);
        piocheTirage[18] = new CarteTresor("Zephyr", Tresor.ZEPHYR);
        piocheTirage[19] = new CarteTresor("Zephyr", Tresor.ZEPHYR);
        piocheTirage[20] = new CarteSacsDeSable("SacsDeSable");
        piocheTirage[21] = new CarteSacsDeSable("SacsDeSable");
        piocheTirage[22] = new CarteHelicoptere("Helicoptere");
        piocheTirage[23] = new CarteHelicoptere("Helicoptere");
        piocheTirage[24] = new CarteHelicoptere("Helicoptere");
        piocheTirage[25] = new CarteMonteeDesEaux("MonteeDesEaux");
        piocheTirage[26] = new CarteMonteeDesEaux("MonteeDesEaux");
        
        //melange des pioches initiales
        piocheInondation = melangerCartesInondations(piocheInondation);
        piocheTirage = melangerCartesTirages(piocheTirage);
        
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
        else if (o instanceof VueRegles) {
            this.vueRegles.fermerFenetre();
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
    
    public void afficherRegles() {
        vueRegles.afficherFenetre();
        
    }
    
    public void piocherCartesTirage(){
        
        
        this.jCourant.addCarte(this.piocheTirage[this.piocheTirage.length-1]);
        this.jCourant.addCarte(this.piocheTirage[this.piocheTirage.length-2]);
        //passer en arraylist
        this.piocheTirage[this.piocheTirage.length-1]=null; //ça ne remove pas
        this.piocheTirage[this.piocheTirage.length-1]=null;

//        this.deffausseTirage.add();
//        this.deffausseTirage.add();
    }

    public void recupererTresor(){
        if(this.jCourant.getPosition().getTresor()!=null){ //test si le suite sur la quelle se trouve le jCourant possède un trésor
            int nbTresor=0;
            for (CarteTresor t :this.jCourant.getTresors()){
                if(t.getTypeTresor()==this.jCourant.getPosition().getTresor()){
                    nbTresor++;
                }
            }
            if(nbTresor==4 && !this.tresorsGagnes.contains(this.jCourant.getPosition().getTresor())){ // si on a 4 cartes trésor et qu'on a pas déjà le trésor
                this.tresorsGagnes.add(this.jCourant.getPosition().getTresor());
                //mettre dans la défausse les 4 cartes trésor
            }
            else if(nbTresor!=4){
                //nombre de carte trésor insuffisante
            }
            else {
                //le trésor en question à déjà été récupéré
            }
        }   
        else{
            //la tuile sur laquelle se trouve le jCourant n'as pas de trésor. 
        }
        
        
    }

    private void finTour() {
        //faire la distribution des cartes
        //passer au joueur suivant
        this.actionsRestantes=3;
    }
}
        
        

        
        
