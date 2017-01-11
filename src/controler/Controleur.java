package controler;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
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
//import view.VueDefausse;
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
    private VuePlateau vuePlateau;
    private VueRegles vueRegles;
//    private VueDefausse vueDefausse;
    
    //Plateau
    private Grille grille;
    private Tuile[] tuiles = new Tuile[24];
    private int niveauEau;
    
    //Partie
    private ArrayList<Aventurier> joueurs = new ArrayList<>();
    private ArrayList<Integer> listeIDDynamic = new ArrayList<>();
    private ArrayList<Tresor> tresorsGagnes= new ArrayList<>();
    private Aventurier jCourant;
    private Aventurier jExceptionnel;
    private int nbJoueurs;
    private int nbCartesInnondationsPioches;
    private int nbActions;
    private boolean phaseDeDeplacement;
    private boolean phaseAssechement;
    private boolean phaseJouerCarte;
    private boolean phaseDonnerCarte;

    //Cartes
    private ArrayList<CarteInondation> defausseInondation = new ArrayList<>();
    private ArrayList<CarteInondation> piocheInondation = new ArrayList<>();
    private ArrayList<CarteTirage> defausseTirage = new ArrayList<>();
    private ArrayList<CarteTirage> piocheTirage = new ArrayList<>();
    
    
    public Controleur() {
        this.vueConnexion = new VueConnexion();
        this.vueConnexion.addObserver(this);
        vueRegles = new VueRegles();        
        this.vueRegles.addObserver(this);
        this.nbActions = 0;
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
        else if(arg == Commandes.TERMINER){
            this.finTour(); // fin du tour
            System.out.println("blbllblblbll");
        }
        
        else if(arg == Commandes.VOIR_DEFAUSSE) {
//            vueDefausse = new VueDefausse(tresorsGagnes, defausseInondation, defausseTirage);
//            vueDefausse.addObserver(this);
        }
    if(jCourant!=null && nbActions<jCourant.getNbAction())  {
        
        if (arg == Commandes.BOUGER){
            phaseAssechement=false;
            listeIDDynamic.clear();
            listeIDDynamic.addAll(this.grille.getTuilesAccessibles(jCourant));
             
            for(int i : listeIDDynamic){
                this.vuePlateau.surbriller(i); //fonctionnel, créer une bordure jaune sur les tuiles sur lesquelles ont peut cliquer
            }
            
            this.phaseDeDeplacement=true;
        }
        else if(arg == Commandes.ASSECHER){
            phaseDeDeplacement=false;
            listeIDDynamic.clear();
            listeIDDynamic.addAll(this.grille.getTuilesAssechables(jCourant));
            
            for(int i :  listeIDDynamic){
                this.vuePlateau.surbriller(i);
            }
            
            this.phaseAssechement=true;
        }
                
        else if (arg == Commandes.RECUPERER_TRESOR) {
            this.recupererTresor();
        }
        
        else if (arg == Commandes.DONNER) {
            phaseDonnerCarte=true;
        }
       
        else if(arg instanceof Integer){
            
            System.out.println((int)arg);
            grille.aff((int)arg);
            if(phaseDeDeplacement){
                this.vuePlateau.desurbriller();
                if (this.listeIDDynamic.contains(arg))
                {
                    if(jCourant.isPilote()){
                        if(!grille.getAdjacentes(this.jCourant.getPosition().getId()).contains((int)arg)){
                            ((Pilote)jCourant).setPouvoirdispo(false);
                        }
                    }
                    this.deplacerJCourant(this.grille.getTuileAvecID((int)arg)); // pour déplacer sur l'ihm

                    
                    this.phaseDeDeplacement=false;
                    this.nbActions++;
                    if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0);
                    System.out.println(jCourant.getPosition().getNom());
                    
                }
                else{
                    //on ne peut pas se déplacer là
                    
                }
                
                    }
                    if(phaseAssechement && listeIDDynamic.contains(arg))
                    {
                        this.vuePlateau.desurbriller();
                        this.grille.getTuileAvecID((int) arg).setEtatTuile(EtatTuile.ASSECHEE);
                        this.vuePlateau.assecherTuile(this.grille.getTuileAvecID((int) arg));
                        this.phaseAssechement=false;

                        if(jCourant.getRoleAventurier()==RoleAventurier.Ingenieur) {
                            if (((Ingenieur)jCourant).getPouvoirdisposi1()==1){
                                System.out.println("J'ai fait mon action pour 0");
                                
                                this.nbActions--; 
                                // il faut que l'ingénieur vienne d'assécher une case, et qu'il n'ait pas bougé entre temps.
                            }
                            ((Ingenieur)jCourant).setPouvoirdisposi1(((Ingenieur)jCourant).getPouvoirdisposi1()+1); // +1                     
                    }
                        this.nbActions++;
                }  
                    if(phaseJouerCarte){
                        if (jCourant.getMain().get((int)arg).isCarteHelicoptere()){
                            for (int i =0;i<24;i++){
                            this.vuePlateau.surbriller(i);
                            }
                        this.phaseDeDeplacement=true;
                    }
                    else if(jCourant.getMain().get((int)arg).isCarteSac()){
                        for (int i =0;i<24;i++){
                            this.vuePlateau.surbriller(i);
                        }
                        this.phaseAssechement=true;
                    }
                    else{
                        //On ne peut pas jouer cette carte
                    }

                }

                if(phaseDeDeplacement && phaseJouerCarte){ //carte hélico  | la case de départ est toujours la position de jCourant. Trop lourd sinon
                    this.deplacerJCourant(this.grille.getTuileAvecID((int)arg)); // pour déplacer sur l'ihm
                    for(Aventurier j : this.jCourant.getPosition().getAventuriers()) {

                        j.setPosition(this.grille.getTuileAvecID((int)arg));
                        j.getPosition().getAventuriers().add(j);
                    }

                    this.jCourant.getPosition().getAventuriers().clear();
                    this.phaseDeDeplacement=false;
                    if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0); // sert juste à réinitialiser les conditions de pouvoir de l'ingénieur
                    phaseJouerCarte=false;
                }

                if(phaseAssechement && phaseJouerCarte){ //carte bac à sable

                    this.grille.getTuileAvecID((int) arg).setEtatTuile(EtatTuile.ASSECHEE);
                    this.phaseAssechement=false;
                    //assecherlacarte
                    phaseJouerCarte=false;
                }

                 if(phaseDonnerCarte){

                    if(!jCourant.equals(jCourant.getPosition().getAventuriers().get(0))){
                        this.jExceptionnel.addCarte(jCourant.getMain().get((int)arg)); // qui est joueur exceptionnel?
                        jCourant.removeCarte(jCourant.getMain().get((int)arg));
                        //mettre a jour l'ihm
                    }
                 }

            }
            }    
            else if(jCourant != null && this.nbActions==jCourant.getNbAction()){
                System.out.println("PLUS D'ACTION");
            }
            else{
                System.out.println("jCourant est null ce petit batard");
            }
        
       
       
    
}
    
    public void initialiserPartie() {
        //Creation des cartes
        remplirPioches();
        
        remplirTuiles();
        //Création des joueurs
        attribuerRoleJoueurs();
        
        Tuile[] tuilesMelange = melangerPositions(tuiles);
        this.grille = new Grille(tuilesMelange);
        //Création du plateau
        
    
        this.vueInscription.fermerFenetre();
        this.vuePlateau = new VuePlateau(grille, joueurs, niveauEau, this);
        this.vuePlateau.addObserver(this);
        
        //piocher 6 cartes innondations
        piocherCarteInondation(6);
      
        //donner deux cartes aux joueurs
        
        //Poser les joueurs sur le plateau
        for (Aventurier a : joueurs) {
            placerPion(a, a.getPosition());
        }
        jCourant=joueurs.get(0);
        
        jCourant = joueurs.get(0);
        this.vuePlateau.getMessageBox().displayMessage("A "+jCourant.getNom()+" de jouer !", jCourant.getPion().getCouleur(), phaseDeDeplacement, phaseJouerCarte);
//        this.piocherCartesTirage();
        this.vuePlateau.afficherCartesAventurier(jCourant, joueurs.indexOf(jCourant));

       
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
        tuiles[10] = new Tuile(null, "LePontDesAbimes"); 
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
        
        
        
        

      
        
    }
    
    public void remplirPioches() {
        
        
        //Carte inondation
        piocheInondation.add(new CarteInondation(("LaCarverneDuBrasier")));
        piocheInondation.add(new CarteInondation(("LesDunesDeLIllusion")));
        piocheInondation.add(new CarteInondation(("LesFalaisesDeLOubli")));
        piocheInondation.add(new CarteInondation(("LeTempleDuSoleil")));
        piocheInondation.add(new CarteInondation(("LeValDuCrepuscule")));
        piocheInondation.add(new CarteInondation(("Observatoire")));
        piocheInondation.add(new CarteInondation(("LePalaisDeCorail")));
        piocheInondation.add(new CarteInondation(("LeLagonPerdu")));
        piocheInondation.add(new CarteInondation(("LeMaraisBrumeux")));
        piocheInondation.add(new CarteInondation(("LeJardinDesMurmures")));
        piocheInondation.add(new CarteInondation(("LePontDesAbimes")));
        piocheInondation.add(new CarteInondation(("LeRocherFantome")));
        piocheInondation.add(new CarteInondation(("LaPortedOr")));
        piocheInondation.add(new CarteInondation(("LeJardinDesHurlements")));
        piocheInondation.add(new CarteInondation(("LaPorteDeBronze")));
        piocheInondation.add(new CarteInondation(("LaPorteDeFer")));
        piocheInondation.add(new CarteInondation(("LaTourDuGuet")));
        piocheInondation.add(new CarteInondation(("LaPorteDeCuivre")));
        piocheInondation.add(new CarteInondation(("LaPortedArgent")));
        piocheInondation.add(new CarteInondation(("Heliport")));
        piocheInondation.add(new CarteInondation(("LaForetPourpre")));
        piocheInondation.add(new CarteInondation(("LaCarverneDesOmbres")));
        piocheInondation.add(new CarteInondation(("LePalaisDesMarees")));
        piocheInondation.add(new CarteInondation(("LeTempleDeLaLune")));
        
        //Carte Tirage
        piocheTirage.add(new CarteTresor("Calice", Tresor.CALICE));
        piocheTirage.add(new CarteTresor("Calice", Tresor.CALICE));
        piocheTirage.add(new CarteTresor("Calice", Tresor.CALICE));
        piocheTirage.add(new CarteTresor("Calice", Tresor.CALICE));
        piocheTirage.add(new CarteTresor("Calice", Tresor.CALICE));
        piocheTirage.add(new CarteTresor("Cristal", Tresor.CRISTAL));
        piocheTirage.add(new CarteTresor("Cristal", Tresor.CRISTAL));
        piocheTirage.add(new CarteTresor("Cristal", Tresor.CRISTAL));
        piocheTirage.add(new CarteTresor("Cristal", Tresor.CRISTAL));
        piocheTirage.add(new CarteTresor("Cristal", Tresor.CRISTAL));
        piocheTirage.add(new CarteTresor("Pierre", Tresor.PIERRE));
        piocheTirage.add(new CarteTresor("Pierre", Tresor.PIERRE));
        piocheTirage.add(new CarteTresor("Pierre", Tresor.PIERRE));
        piocheTirage.add(new CarteTresor("Pierre", Tresor.PIERRE));
        piocheTirage.add(new CarteTresor("Pierre", Tresor.PIERRE));
        piocheTirage.add(new CarteTresor("Zephyr", Tresor.ZEPHYR));
        piocheTirage.add(new CarteTresor("Zephyr", Tresor.ZEPHYR));
        piocheTirage.add(new CarteTresor("Zephyr", Tresor.ZEPHYR));
        piocheTirage.add(new CarteTresor("Zephyr", Tresor.ZEPHYR));
        piocheTirage.add(new CarteTresor("Zephyr", Tresor.ZEPHYR));
        piocheTirage.add(new CarteSacsDeSable("SacsDeSable"));
        piocheTirage.add(new CarteSacsDeSable("SacsDeSable"));
        piocheTirage.add(new CarteHelicoptere("Helicoptere"));
        piocheTirage.add(new CarteHelicoptere("Helicoptere"));
        piocheTirage.add(new CarteHelicoptere("Helicoptere"));
        piocheTirage.add(new CarteMonteeDesEaux("MonteeDesEaux"));
        piocheTirage.add(new CarteMonteeDesEaux("MonteeDesEaux"));
        
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
                a = new Explorateur(tuiles[19], Utils.Pion.VERT, vueInscription.getNomJoueur().get(i)); 
            }
            else if (rolesAventurier[i] == RoleAventurier.Ingenieur){
                a = new Ingenieur(tuiles[16], Utils.Pion.ROUGE, vueInscription.getNomJoueur().get(i)); 
            }
            else if (rolesAventurier[i] == RoleAventurier.Messager){
                a = new Messager(tuiles[20], Utils.Pion.BRONZE, vueInscription.getNomJoueur().get(i)); 
            }
            else if (rolesAventurier[i] == RoleAventurier.Navigateur){
                a = new Navigateur(tuiles[14], Utils.Pion.JAUNE, vueInscription.getNomJoueur().get(i)); 
            }
            else if (rolesAventurier[i] == RoleAventurier.Pilote){
                a = new Pilote(tuiles[21], Utils.Pion.BLEU, vueInscription.getNomJoueur().get(i)); 
            }
            else if (rolesAventurier[i] == RoleAventurier.Plongeur){
                a = new Plongeur(tuiles[17], Utils.Pion.VIOLET, vueInscription.getNomJoueur().get(i));
            }
            
            joueurs.add(a);
            a.addCarte(piocheTirage.get(2));
            a.addCarte(piocheTirage.get(3));
            a.addCarte(piocheTirage.get(10));
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
//        } else if (o instanceof VueDefausse) {
//            this.vueDefausse.fermerFenetre();
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
        
        for (int i=1;i<=2;i++){
           if(this.piocheTirage.isEmpty()){
               melangerCartesTirages(defausseTirage);
               this.piocheTirage.addAll(defausseTirage);
           }
           if(this.piocheTirage.get(this.piocheTirage.size()-1).isCarteMonteeDesEaux()){
              // on ajoute la défausse inondation mélangée à sa pioche, et on met la carte à la défausse.
               melangerCartesInondations(defausseInondation);
              this.piocheInondation.addAll(defausseInondation);
              this.niveauEau++;
              this.defausseTirage.add(this.piocheTirage.get(this.piocheTirage.size()-1));
           }else {
               if(this.piocheTirage.get(this.piocheTirage.size()-1).isCarteTresor()){
                   this.jCourant.addCarteTresor((CarteTresor)this.piocheTirage.get(this.piocheTirage.size()-1));
               }
              this.jCourant.addCarte(this.piocheTirage.get(this.piocheTirage.size()-1)); // ajout de la carte tirage à la main
           }
              this.piocheTirage.remove(this.piocheTirage.get(this.piocheTirage.size()-1)); // retrait de la carte piochée de la pioche
           }
        }
    
    public void piocherCarteInondation(int nbCarteInondation) {
        for (int i = 0; i < nbCarteInondation; i++) {
            if(piocheInondation.isEmpty()){
                melangerCartesInondations(defausseInondation);
                this.piocheInondation.addAll(defausseInondation);
            }
            
            CarteInondation carteInondation = piocheInondation.get(piocheInondation.size() - 1);
            Tuile tuileAInonder = trouverTuile(carteInondation);
            
            if (tuileAInonder.getEtatTuile() == EtatTuile.ASSECHEE) {
                vuePlateau.inonderTuile(tuileAInonder);
                tuileAInonder.setEtatTuile(EtatTuile.INONDEE);
                piocheInondation.remove(piocheInondation.size() - 1);
                defausseInondation.add(carteInondation);
            }
            else if (tuileAInonder.getEtatTuile() == EtatTuile.INONDEE) {
                vuePlateau.coulerTuile(tuileAInonder);
                tuileAInonder.setEtatTuile(EtatTuile.COULEE);
                piocheInondation.remove(piocheInondation.size() - 1);
            }
            
            
            
        }
    }
    
    public void recupererTresor(){
//        for(int i=0;i<piocheTirage.size();i++){
//          if(piocheTirage.get(i).isCarteTresor())  this.jCourant.getTresors().add((CarteTresor)piocheTirage.get(i));
//        }
        if(this.jCourant.getPosition().getTresor()!=null){ //teste si la tuile sur laquelle se trouve le jCourant possède un trésor
            int nbCarteTresor=0;
            for (CarteTresor t :this.jCourant.getTresors()){
                if(t.getTypeTresor()==this.jCourant.getPosition().getTresor()){
                    nbCarteTresor++;
                }
            }
            if(nbCarteTresor>=4 && !this.tresorsGagnes.contains(this.jCourant.getPosition().getTresor())){ // si on a 4 cartes trésor et qu'on a pas déjà le trésor
                if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0); // sert juste à réinitialiser les conditions de pouvoir de l'ingénieur
                this.tresorsGagnes.add(this.jCourant.getPosition().getTresor());
                int compteur=0;
                    Iterator it = this.jCourant.getTresors().iterator();
                    while(it.hasNext()){
                        CarteTresor c =(CarteTresor) it.next();
                        if (c.getTypeTresor().equals(jCourant.getPosition().getTresor()) && compteur <4){                
                            it.remove();
                            defausseTirage.add(c);
                            compteur++;
                        }
                    }
                       
                this.nbActions++;
            }
            else if(nbCarteTresor<4){
                System.out.println("nombre de carte trésor insuffisante");
            }
            else {
                System.out.println("le trésor en question à déjà été récupéré");
            }
        }   
        else{
            System.out.println("la tuile sur laquelle se trouve le jCourant n'as pas de trésor."); 
        }
     
        
        
    }

    private void finTour() {
        //redonner les pouvoirs uniques
        if(jCourant.isPilote()) ((Pilote)jCourant).setPouvoirdispo(true);
        if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0);
       
        
        //faire la distribution des cartes
        piocherCartesTirage();
        piocherCarteInondation(nbCartesInnondationsPioches);
//        vuePlateau.getWindow().setVisible(true);
        //passer au joueur suivant
        changerJCourant();
        this.nbActions=0;
        
      //  this.defausserCartes();
    }
    
    /**
        Cette méthode déplace le joueur a une nouvelle position
     * @param nouvellePosition
    */
    public void deplacerJCourant(Tuile nouvellePosition) {
        Tuile anciennePosition = jCourant.getPosition();
        anciennePosition.removeAventurier(jCourant);
        nouvellePosition.addAventurier(jCourant);
        jCourant.setPosition(nouvellePosition);
        vuePlateau.setPosition(jCourant, nouvellePosition, anciennePosition);
    }
    
    public Tuile trouverTuile(CarteInondation carteInondation) {
        Tuile tuile = null;
        for (Tuile t : tuiles) {
            if (t.getNom() == carteInondation.getNomFichier()) {
                tuile = t;
            }
        }
        
        return tuile;
    }
    
    public void placerPion(Aventurier aventurier, Tuile position) {
        aventurier.setPosition(position);
        position.addAventurier(aventurier);
        vuePlateau.setPosition(aventurier, position, position);
    }
    
    public void changerJCourant() {
        jCourant = joueurs.get((joueurs.indexOf(jCourant) + 1) % nbJoueurs);
        this.vuePlateau.getMessageBox().displayMessage("A "+jCourant.getNom()+" de jouer !", jCourant.getPion().getCouleur(), phaseDeDeplacement, phaseJouerCarte);
    }

    private void donnerCarte() {
        
    }
    
}
     
        

        
        
