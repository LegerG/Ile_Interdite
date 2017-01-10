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
import model.cartes.CarteHelicoptere;
import model.cartes.CarteInondation;
import model.cartes.CarteMonteeDesEaux;
import model.cartes.CarteSacsDeSable;
import model.cartes.CarteTirage;
import model.cartes.CarteTresor;
import view.VueAventurier;
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
    private VueRegles vueRegles;
    
    //Plateau
    private Grille grille;
    private Tuile[] tuiles = new Tuile[24];
    private int niveauEau;
    
    //Partie
    private ArrayList<Aventurier> joueurs = new ArrayList<>();
    private Aventurier jCourant;
    private Aventurier jExceptionnel; //a prevoir pour les intéruptions de parties lors des deffausses de cartes ou autre
    private int nbJoueurs;
    private int nbCartesInnondationsPioches;
    private ArrayList<Tresor> tresorsGagnes;
    private boolean phaseDeDeplacement;
    private boolean phaseAssechement;
    private boolean phaseJouerCarte;


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
    
    }
    

    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Integer> listeIDDynamic = new ArrayList<>();
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
            listeIDDynamic.clear();
            listeIDDynamic.addAll(this.grille.getTuilesAccessibles(jCourant));
            for(int i : this.grille.getTuilesAccessibles(jCourant)){
                this.vuePlateau.surbriller(i); //fonctionnel, créer une bordure jaune sur les tuiles sur lesquelles ont peut cliquer
            }
            this.phaseDeDeplacement=true;
        }
        else if(arg == Commandes.ASSECHER){
            for(int i :  this.grille.getTuilesAssechables(jCourant)){
              this.vuePlateau.surbriller(i);
          }  
            this.phaseAssechement=true;
        }
        else if(arg == Commandes.TERMINER){
            this.finTour(); // fin du tour
            System.out.println("blbllblblbll");
        }
        else if(arg instanceof Integer){
            if(phaseDeDeplacement==true){
                this.vuePlateau.desurbriller();
                if (this.grille.getTuilesAccessibles(jCourant).contains(arg))
                {
                    if(jCourant.isPilote()){
                        if(grille.getAdjacentes(this.jCourant.getPosition().getId()).contains((int)arg)){
                            ((Pilote)jCourant).setPouvoirdispo(true);
                        }
                    }
                    this.deplacerJCourant(this.grille.getTuileAvecID((int)arg)); // pour déplacer sur l'ihm
                    this.jCourant.getPosition().getAventuriers().remove(jCourant);
                    this.jCourant.setPosition(this.grille.getTuileAvecID((int)arg));
                    this.jCourant.getPosition().getAventuriers().add(jCourant);
                    
                    this.phaseDeDeplacement=false;
                    jCourant.setNbAction(jCourant.getNbAction()-1);
                    if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0);
                    System.out.println(jCourant.getPosition().getNom());
                    
                }
                else{
                    //on ne peut pas se déplacer là
                    
                }
                
            }
            if(phaseAssechement==true && this.grille.getTuilesAccessibles(jCourant).contains(arg))
            {
                this.grille.getTuileAvecID((int) arg).setEtatTuile(EtatTuile.ASSECHEE);
                this.phaseAssechement=false;
                if(jCourant.getRoleAventurier()==RoleAventurier.Ingenieur) {
                    if (((Ingenieur)jCourant).getPouvoirdisposi1()==1){
                        jCourant.setNbAction(jCourant.getNbAction()+1); 
                        // il faut que l'ingénieur vienne d'assécher une case, et qu'il n'ait pas bougé entre temps.
                    }
                    ((Ingenieur)jCourant).setPouvoirdisposi1(((Ingenieur)jCourant).getPouvoirdisposi1()); // +1                     
            }
                jCourant.setNbAction(jCourant.getNbAction()-1); 
        }  
        if(phaseJouerCarte==true){
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
       
        if(phaseDeDeplacement==true && phaseJouerCarte==true){ //carte hélico  | la case de départ est toujours la position de jCourant. Trop lourd sinon
                    this.deplacerJCourant(this.grille.getTuileAvecID((int)arg)); // pour déplacer sur l'ihm
                    for(Aventurier j : this.jCourant.getPosition().getAventuriers()){
                       j.setPosition(this.grille.getTuileAvecID((int)arg));
                       j.getPosition().getAventuriers().add(j);
                    }

                    this.jCourant.getPosition().getAventuriers().clear();
                    this.phaseDeDeplacement=false;
                    if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0); // sert juste à réinitialiser les conditions de pouvoir de l'ingénieur
                    phaseJouerCarte=false;
        }
        
         if(phaseAssechement==true && phaseJouerCarte==true){ //carte bac à sable
                   this.grille.getTuileAvecID((int) arg).setEtatTuile(EtatTuile.ASSECHEE);
                   this.phaseAssechement=false;
                   //assecherlacarte
                   phaseJouerCarte=false;
        }
            
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
                ArrayList<CarteTresor> listeARemove= new ArrayList<>();
               for (CarteTresor t :this.jCourant.getTresors()){
                    
                    compteur++;
                
            }
            }
            else if(nbCarteTresor<4){
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
        if(jCourant.isPilote()) ((Pilote)jCourant).setPouvoirdispo(true);
        if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0);
       
        
        //faire la distribution des cartes
//        piocherCartesTirage();
        piocherCarteInondation(nbCartesInnondationsPioches);
        vuePlateau.getWindow().setVisible(true);
        //passer au joueur suivant
        changerJCourant();
      //  this.actionsRestantes=3; PROBLEME avec naviguateur : demander à lylian
    }
    
    /**
        Cette méthode déplace le joueur a une nouvelle position
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
    }
    
}
        
        

        
        
