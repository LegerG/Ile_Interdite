package controler;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import util.Utils.Phase;
import util.Utils.RoleAventurier;
import util.Utils.Tresor;
import static util.Utils.melangerCartesInondations;
import static util.Utils.melangerCartesTirages;
import static util.Utils.melangerPositions;
import static util.Utils.melangerRole;
import view.VueConnexion;
import view.VueDefausse;
import view.VueInscription;
import view.VueRegles;



public class Controleur implements Observer {
    //IHMs
    private VueInscription vueInscription;
    private VueConnexion vueConnexion;
    private VuePlateau vuePlateau;
    private VueRegles vueRegles;
    private VueDefausse vueDefausse;
    
    //Plateau
    private Grille grille;
    private Tuile[] tuiles = new Tuile[24];
    private int niveauEau;
    
    //Partie
    private ArrayList<Aventurier> joueurs = new ArrayList<>();
    private ArrayList<Tresor> tresorsGagnes= new ArrayList<>();
    private Aventurier jCourant;
    private Aventurier jExceptionnel;
    private int nbJoueurs;
    private int nbCartesInnondationsPioches;
    private int nbActions;
    private Phase phase;

    //Cartes
    private ArrayList<CarteInondation> defausseInondation = new ArrayList<>();
    private ArrayList<CarteInondation> piocheInondation = new ArrayList<>();
    private ArrayList<CarteTirage> defausseTirage = new ArrayList<>();
    private ArrayList<CarteTirage> piocheTirage = new ArrayList<>();
    private boolean deplacementForce=false;
    
    
    public Controleur() {
        this.vueConnexion = new VueConnexion();
        this.vueConnexion.addObserver(this);
        vueRegles = new VueRegles();        
        this.vueRegles.addObserver(this);
        this.nbActions = 0;
        
//        
//        tresorsGagnes.add(Tresor.PIERRE); // test : on donne des trésors aux joeurs
//        tresorsGagnes.add(Tresor.CALICE);
//        tresorsGagnes.add(Tresor.CRISTAL);
//        tresorsGagnes.add(Tresor.ZEPHYR);
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
        // on peut pas passer au joueur suivant si ce joueur n'a pas le bon nombre de cartes
        else if(arg == Commandes.TERMINER && jCourant.getMain().size()<=5){ 
            this.finTour(); 
        }
        else if(arg == Commandes.CHOISIR_CARTE && jCourant.getMain().size()<=5){
            this.phase=Phase.JOUERCARTE;
            this.vuePlateau.getMessageBox().displayMessage("Cliquer sur la carte que vous voulez jouer.", jCourant.getPion().getCouleur(), true, true);
        }
        
        else if(arg == Commandes.VOIR_DEFAUSSE) {
            vueDefausse = new VueDefausse(tresorsGagnes, defausseInondation, defausseTirage);
            vueDefausse.addObserver(this);
        }

    if(jCourant!=null && nbActions<jCourant.getNbAction() && jCourant.getMain().size()<=5)  { // On vérifie si le joueur a encore des actions
                
        if (arg == Commandes.BOUGER){
            this.phase=Phase.DEPLACEMENT;
            
             
            for(int i : (this.grille.getTuilesAccessibles(jCourant))){
                this.vuePlateau.surbriller(i); //fonctionnel, créer une bordure jaune sur les tuiles sur lesquelles ont peut cliquer
            }
            
            
        }
        else if(arg == Commandes.ASSECHER){
            this.phase=Phase.ASSECHEMENT;
            
            
            for(int i :  this.grille.getTuilesAssechables(jCourant)){
                this.vuePlateau.surbriller(i);
            }
            
            
        }
                
        else if (arg == Commandes.RECUPERER_TRESOR) {
            this.recupererTresor();
        }
        
        else if (arg == Commandes.DONNER) {
           if(!jCourant.getTresors().isEmpty() && jCourant.getPosition().getAventuriers().size()>1) {
               this.phase=Phase.DONNERCARTE;
               this.vuePlateau.getMessageBox().displayAlerte("Vous devez choisir une carte trésor à donner");
           }
           else if (jCourant.getPosition().getAventuriers().size()==1){
                    this.vuePlateau.getMessageBox().displayAlerte("Vous êtes seul sur votre tuile");
                }
           else this.vuePlateau.getMessageBox().displayAlerte("Vous n'avez pas de cartes trésor à donner");
        }
        
    }
        
         if(arg instanceof Integer){
            
           if((int)arg>10){
            
        // PHASE DE DEPLACEMENT
            if(phase==Phase.DEPLACEMENT){
               
                if (this.grille.getTuilesAccessibles(jCourant).contains(arg)) {
                    
                    this.vuePlateau.desurbriller();
                    if(jCourant.isPilote()){
                        if(!grille.getAdjacentes(this.jCourant.getPosition().getId()).contains((int)arg)){
                            ((Pilote)jCourant).setPouvoirdispo(false);
                        }
                    }
                    this.deplacerJoueur(this.grille.getTuileAvecID((int)arg)); // pour déplacer sur l'ihm

                    
                    
                    this.nbActions++;
                    this.vuePlateau.getMessageBox().displayMessage("Il vous reste "+(jCourant.getNbAction()-nbActions)+" actions.", Color.BLACK, true, true);
                    if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0);
                   
                    
                }
                else{
                    this.vuePlateau.getMessageBox().displayMessage("Vous ne pouvez pas vous déplacer ici.", jCourant.getPion().getCouleur(), true, true);
                    
                }
                     vuePlateau.enableBouton(true);
            }
            
            //PHASE D'ASSECHEMENT
            else if(phase==Phase.ASSECHEMENT && this.grille.getTuilesAssechables(jCourant).contains(arg))
                    {
                        this.vuePlateau.desurbriller();
                        this.grille.getTuileAvecID((int) arg).setEtatTuile(EtatTuile.ASSECHEE);
                        this.vuePlateau.assecherTuile(this.grille.getTuileAvecID((int) arg));

                        if(jCourant.getRoleAventurier()==RoleAventurier.Ingenieur) {
                            if (((Ingenieur)jCourant).getPouvoirdisposi1()==1){
                                this.vuePlateau.getMessageBox().displayMessage("Vous avez utilisé votre pouvoir", jCourant.getPion().getCouleur(), true, true);
                                
                                this.nbActions--; 
                                // il faut que l'ingénieur vienne d'assécher une case, et qu'il n'ait pas bougé entre temps.
                            }
                            ((Ingenieur)jCourant).setPouvoirdisposi1(((Ingenieur)jCourant).getPouvoirdisposi1()+1); // +1                     
                    }
                        this.nbActions++;
                        this.vuePlateau.getMessageBox().displayMessage("Il vous reste "+(jCourant.getNbAction()-nbActions)+" actions.", Color.BLACK, true, true);
                }
            else if(phase==Phase.ASSECHEMENT && !this.grille.getTuilesAssechables(jCourant).contains(arg)){
                this.vuePlateau.getMessageBox().displayMessage("Vous ne pouvez pas assécher ici.", jCourant.getPion().getCouleur(), true, true);
            }
            
              //PHASE JOUER HELICO
            else if(phase==Phase.HELICO){ //carte hélico  | la case de départ est toujours la position de jCourant. Trop lourd sinon
//                    for(Aventurier j : this.jCourant.getPosition().getAventuriers()) {
//
//                        this.deplacerJoueur(this.grille.getTuileAvecID((int)arg),j);
//                    }
                    this.deplacerJoueur(this.grille.getTuileAvecID((int)arg));
                    this.vuePlateau.desurbriller();
                    
                    if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0); // sert juste à réinitialiser les conditions de pouvoir de l'ingénieur
                    
                    
            }
            
            //PHASE JOUER SAC DE SABLE
            else if(phase==Phase.SACSABLE){ //carte bac à sable
                    this.vuePlateau.desurbriller();
                    if(grille.getTuileAvecID((int) arg).getEtatTuile()==EtatTuile.INONDEE){
                    this.grille.getTuileAvecID((int) arg).setEtatTuile(EtatTuile.ASSECHEE); // mise à jour du controleur
                    this.vuePlateau.assecherTuile(this.grille.getTuileAvecID((int) arg)); // mise à jour de l'ihm
                 
                    }
            }
           
           }
        else if((int)arg< jCourant.getMain().size()){ // Si on interagit avec les cartes des joueurs et non avec les tuiles
                
            // PHASE DONNER CARTE
            if(phase==Phase.DONNERCARTE){
                if(jCourant.getPosition().getAventuriers().size()>1){
                for(Aventurier a : jCourant.getPosition().getAventuriers() ) {
                    if(!a.equals(jCourant)) jExceptionnel=a;
                }
                    if(jExceptionnel.getMain().size()!=9){ // pas de donnation si la main du receveur est pleine
                        this.jExceptionnel.addCarte(jCourant.getMain().get((int)arg)); // qui est joueur exceptionnel?
                        jCourant.removeCarte(jCourant.getMain().get((int)arg));
                        this.vuePlateau.afficherCartesAventurier(jCourant, joueurs.indexOf(jCourant));     
                        this.vuePlateau.afficherCartesAventurier(jExceptionnel, joueurs.indexOf(jExceptionnel));
                        this.nbActions++;
                        this.vuePlateau.getMessageBox().displayMessage("Il vous reste "+(jCourant.getNbAction()-nbActions)+" actions.", Color.BLACK, true, true);
                    }
                    else{
                        this.vuePlateau.getMessageBox().displayAlerte("La main du receveur est pleine!");
                    }
                 }
                
                
            }
            
            //PHASE JOUER CARTE
            else if(phase==Phase.JOUERCARTE){
                        if (jCourant.getMain().get((int)arg).isCarteHelicoptere()){
                            for (Integer i : grille.getTuiles().keySet()){
                                if(grille.getTuileAvecID(i).getEtatTuile()!=EtatTuile.COULEE){
                            this.vuePlateau.surbriller(i);
                            }
                            }
                        phase=Phase.HELICO;
                        this.vuePlateau.getMessageBox().displayMessage("Vous jouez votre carte hélicoptère", jCourant.getPion().getCouleur(), true, true);
                defausseTirage.add(this.jCourant.getMain().get((int)arg));
                jCourant.removeCarte(jCourant.getMain().get((int)arg));
                this.vuePlateau.afficherCartesAventurier(jCourant, joueurs.indexOf(jCourant));
                verifierVictoire();// On verifie si les joueurs ont gagné au momnet de l'utilisation d'unhélico
                    }
                    // jouer une carte bac à sable
                    else if(jCourant.getMain().get((int)arg).isCarteSac()){
                        for (Integer i : grille.getTuiles().keySet()){
                            if(grille.getTuileAvecID(i).getEtatTuile()==EtatTuile.INONDEE){
                            this.vuePlateau.surbriller(i);
                            }
                        }
                        phase=Phase.SACSABLE;
                        this.vuePlateau.getMessageBox().displayMessage("Vous jouez votre carte sac de sable", jCourant.getPion().getCouleur(), true, true);
                        defausseTirage.add(this.jCourant.getMain().get((int)arg));
                jCourant.removeCarte(jCourant.getMain().get((int)arg));
                this.vuePlateau.afficherCartesAventurier(jCourant, joueurs.indexOf(jCourant));
                    }
                    else{
                        this.vuePlateau.getMessageBox().displayMessage("Vous ne pouvez pas jouer une carte trésor.", jCourant.getPion().getCouleur(), true, true);
                    }
                }
            
         
            
             // PHASE DE DEFAUSSE
            if(phase==Phase.DEFAUSSE){
                    defausseTirage.add(this.jCourant.getMain().get((int)arg));
                    jCourant.removeCarte(jCourant.getMain().get((int)arg));
                    
                   this.vuePlateau.afficherCartesAventurier(jCourant, joueurs.indexOf(jCourant));
                
               
                this.vuePlateau.getMessageBox().displayAlerte("Vous avez défaussé une carte");
                // SI le joueur a le bon nombre de cartes, on fait appel a forcer déplacement pour dire au joueur qu'il doit sortir de la carte coulée
                if(this.jCourant.getMain().size()<=5) forcerDeplacement();
            }
            }
            else {
               vuePlateau.getMessageBox().displayAlerte("Veuillez sélectionner une carte");
                
            }
            
            }
       
        
    
  
       
    else if(jCourant != null && this.nbActions==jCourant.getNbAction()){
        this.vuePlateau.getMessageBox().displayMessage("Vous n'avez plus d'actions.", Color.black, true, true);
    }
    
    else if (jCourant!=null &&jCourant.getMain().size()>5){
        this.vuePlateau.getMessageBox().displayMessage("Vous avez trop de cartes", Color.black, true, true);
    }
    else{
    }
            
            
       
       
    
    }
    
    public void initialiserPartie() {
        boolean validerInscription = true;
        for( int i=0; i < vueInscription.getjTextFieldsJoueurs().size(); i++) {
            if (vueInscription.getjTextFieldsJoueurs().get(i).getText().equals("")){
                validerInscription = false;
                vueInscription.setMessageErreur("Tous les joueurs doivent avoir un nom.");
            }
        }
        if (validerInscription == true) {
         
        //Creation des cartes
        remplirPioches();
        
        remplirTuiles();
        //Création des joueurs
        attribuerRoleJoueurs();
        
        Tuile[] tuileMelange = new Tuile[24];
        
        for (int i = 0; i < tuiles.length; i++) {
            tuileMelange[i] = tuiles[i];
        }
        
        melangerPositions(tuileMelange);
        this.grille = new Grille(tuileMelange);
        //Création du plateau
        
    
        this.vueInscription.fermerFenetre();
        this.vuePlateau = new VuePlateau(grille, joueurs, niveauEau, this);
        this.vuePlateau.addObserver(this);
        this.vuePlateau.getMessageBox().displayMessage("Bonne chance dans votre quête!", Color.BLACK, true, true);
        //piocher 6 cartes innondations
        this.vuePlateau.getMessageBox().displayMessage("6 tuiles sombrent..", Color.BLACK, true, true);
        piocherCarteInondation(6);
        //donner deux cartes aux joueurs
        this.vuePlateau.getMessageBox().displayMessage("Chaque aventurier pioche 2 cartes", Color.BLACK, true, true);
        for (Aventurier a : joueurs) {
            piocherCartesTirage(a);
            this.vuePlateau.afficherCartesAventurier(a, joueurs.indexOf(a));
        }
        //Poser les joueurs sur le plateau
        for (Aventurier a : joueurs) {
            placerPion(a, a.getPosition());
        }
        jCourant=joueurs.get(0);
//        this.jCourant.addCarte(piocheTirage.get(0));
//        this.jCourant.addCarte(piocheTirage.get(1));
//        this.jCourant.addCarte(piocheTirage.get(2));
//        this.jCourant.addCarte(piocheTirage.get(3));

        this.vuePlateau.getMessageBox().displayMessage("A "+jCourant.getNom()+" de jouer !", jCourant.getPion().getCouleur(), true, true);

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
//            a.addCarte(piocheTirage.get(2));
//            a.addCarte(piocheTirage.get(3));
//            a.addCarte(piocheTirage.get(10));
//            a.addCarte(piocheTirage.get(4));
//            a.addCarte(piocheTirage.get(5));
//            a.addCarte(piocheTirage.get(6));
//            a.addCarte(piocheTirage.get(7));
//            a.addCarte(piocheTirage.get(8));
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
        } else if (o instanceof VueDefausse) {
            this.vueDefausse.fermerFenetre();
        }
        else if (o instanceof JFrame) {
            vuePlateau.getWindow().dispose();
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
    
    public void piocherCartesTirage(Aventurier jCourant){
        
        for (int i=1;i<=2;i++){
           if(this.piocheTirage.isEmpty()){
               this.vuePlateau.getMessageBox().displayMessage("La pioche de cartes est vide. On mélange la défausse et elle devient la pioche", Color.BLACK, true, true);
               melangerCartesTirages(defausseTirage);
               this.piocheTirage.addAll(defausseTirage);
               this.defausseTirage.clear();
           }
           if(this.piocheTirage.get(this.piocheTirage.size()-1).isCarteMonteeDesEaux()){
              // on ajoute la défausse inondation mélangée à sa pioche, et on met la carte à la défausse.
              this.vuePlateau.getMessageBox().displayAlerte("Vous venez de piocher une carte Montée des Eaux. Le niveau est monté!");
               melangerCartesInondations(defausseInondation);
              this.piocheInondation.addAll(defausseInondation);
              this.niveauEau++;
              this.setNbCartesInnondationsPioches(niveauEau); 
              if (niveauEau < 10) {
                  this.vuePlateau.getVueNiveau().setNiveau(niveauEau);
              }
              this.defausseTirage.add(this.piocheTirage.get(this.piocheTirage.size()-1));
              this.setNbCartesInnondationsPioches(niveauEau);
              this.piocheTirage.remove(this.piocheTirage.size() - 1);
           }
           else {
               
              jCourant.addCarte(this.piocheTirage.get(this.piocheTirage.size()-1)); // ajout de la carte tirage à la main
           }    
                if (!piocheTirage.isEmpty()) {
                    this.piocheTirage.remove(this.piocheTirage.get(this.piocheTirage.size()-1)); // retrait de la carte piochée de la pioche
                }
           }
    }
        
    
    
    public void piocherCarteInondation(int nbCarteInondation) {
        for (int i = 0; i < nbCarteInondation; i++) {
            if(piocheInondation.isEmpty()){
                this.vuePlateau.getMessageBox().displayMessage("La pioche de cartes inondations est vide. On mélange la défausse et elle devient la pioche", Color.BLACK, true, true);
                melangerCartesInondations(defausseInondation);
                this.piocheInondation.addAll(defausseInondation);
                this.defausseInondation.clear();
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
                            this.jCourant.getMain().remove(c);
                            defausseTirage.add(c);
                            compteur++;
                        }
                    }
                this.vuePlateau.afficherCartesAventurier(jCourant, joueurs.indexOf(jCourant));
                this.vuePlateau.getMessageBox().displayMessage("Vous avez récupéré le "+this.jCourant.getPosition().getTresor().toString()+"!", Color.BLACK, true, true);
                if(this.tresorsGagnes.size()==4){
                    this.vuePlateau.getMessageBox().displayMessage("Vous avez tous les trésors! Rejoignez tous l'héliport et jouer une carte hélicoptère pour gagner!", Color.BLACK, true, true);
                }
                this.nbActions++;
                this.vuePlateau.getMessageBox().displayMessage("Il vous reste "+(jCourant.getNbAction()-nbActions)+" actions.", Color.BLACK, true, true);
            }
            else if(nbCarteTresor<4){
                this.vuePlateau.getMessageBox().displayMessage("Vous n'avez pas assez de cartes trésor", jCourant.getPion().getCouleur(), true, true);
            }
            else {
                this.vuePlateau.getMessageBox().displayMessage("Vous avez déjà récupérer le trésor de la case", jCourant.getPion().getCouleur(), true, true);
            }
        }   
        else{
            this.vuePlateau.getMessageBox().displayMessage("Il n'y a pas de trésor récupérable sur cette case", jCourant.getPion().getCouleur(), true, true);
        }
     
        
        
    }

    private void finTour() {
        //redonner les pouvoirs uniques
        if(jCourant.isPilote()) ((Pilote)jCourant).setPouvoirdispo(true);
        if(jCourant.isIngenieur()) ((Ingenieur)jCourant).setPouvoirdisposi1(0);
       
        this.vuePlateau.desurbriller();
        //faire la distribution des cartes
        this.vuePlateau.getMessageBox().displayMessage("Vous tirez 2 cartes", jCourant.getPion().getCouleur(), true, true);
        piocherCartesTirage(jCourant);
        this.vuePlateau.getMessageBox().displayMessage(this.nbCartesInnondationsPioches+" tuiles sombrent..", jCourant.getPion().getCouleur(), true, true);
        piocherCarteInondation(nbCartesInnondationsPioches);
        this.vuePlateau.afficherCartesAventurier(jCourant, joueurs.indexOf(jCourant));
        //passer au joueur suivant
        changerJCourant();
        this.nbActions=0;
       
        
        if(jCourant.getMain().size()>5){
            this.vuePlateau.getMessageBox().displayMessage("Vous devez avoir 5 cartes et donc vous défausser", jCourant.getPion().getCouleur(), true, true);
            phase=Phase.DEFAUSSE;
        }
       
        if(jCourant.getMain().size()<=5){      
            forcerDeplacement();
        }
        
        
        verifierDefaite();
    }
    
    /**
        Cette méthode déplace le joueur a une nouvelle position
     * @param nouvellePosition
    */
    public void deplacerJoueur(Tuile nouvellePosition) {
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
        this.vuePlateau.getMessageBox().displayMessage("A "+jCourant.getNom()+" de jouer !", jCourant.getPion().getCouleur(), true, true);
        vuePlateau.updateTabbedPane(joueurs.indexOf(jCourant));
    }
   
    public void verifierDefaite() {
        if (grille.getTuiles().get(tuiles[21].getId()).getEtatTuile() == EtatTuile.COULEE) {
            afficherFenetrePerdu("Votre Héliport à été coulé.");
        }
        if (niveauEau >= 10) {
            afficherFenetrePerdu("Vous avez été noyés par le niveau d'eau.");
        }
        else if (grille.getTuiles().get(tuiles[0].getId()).getEtatTuile() == EtatTuile.COULEE && grille.getTuiles().get(tuiles[23].getId()).getEtatTuile() == EtatTuile.COULEE && !tresorsGagnes.contains(Tresor.CRISTAL)) {
            afficherFenetrePerdu("Les deux tuiles CRISTALS ont été coulées.");
        }
        else if (grille.getTuiles().get(tuiles[3].getId()).getEtatTuile() == EtatTuile.COULEE && grille.getTuiles().get(tuiles[13].getId()).getEtatTuile() == EtatTuile.COULEE && !tresorsGagnes.contains(Tresor.PIERRE)) {
            afficherFenetrePerdu("Les deux tuiles PIERRES ont été coulées.");
        }
        else if (grille.getTuiles().get(tuiles[6].getId()).getEtatTuile() == EtatTuile.COULEE && grille.getTuiles().get(tuiles[11].getId()).getEtatTuile() == EtatTuile.COULEE && !tresorsGagnes.contains(Tresor.CALICE)) {
            afficherFenetrePerdu("Les deux tuiles CALICES ont été coulées.");
        }
        else if (grille.getTuiles().get(tuiles[9].getId()).getEtatTuile() == EtatTuile.COULEE && grille.getTuiles().get(tuiles[15].getId()).getEtatTuile() == EtatTuile.COULEE && !tresorsGagnes.contains(Tresor.ZEPHYR)) {
            afficherFenetrePerdu("Les deux tuiles ZEPHYRS ont été coulées.");
        }
    }

    private void forcerDeplacement() {
         if(jCourant.getPosition().getEtatTuile()==EtatTuile.COULEE){
            this.vuePlateau.getMessageBox().displayMessage("Vous devez quitter la tuile coulée", jCourant.getPion().getCouleur(), true, true);
            vuePlateau.enableBouton(false);
            for(Integer i :this.grille.getTuilesAccessibles(jCourant)){
                this.vuePlateau.surbriller(i);
            }
            phase=Phase.DEPLACEMENT;
         }
    }

    private void verifierVictoire() {
       
        if(this.tresorsGagnes.size()==4
           && this.jCourant.getPosition().getNom()=="Heliport" 
           && this.jCourant.getPosition().getAventuriers().size()==joueurs.size()){
                this.vuePlateau.getMessageBox().displayAlerte("VOUS AVEZ GAGNE!");
        }
    }
    
    private void afficherFenetrePerdu (String raison) {
        vuePlateau.getWindow().setEnabled(false);
        JFrame fenetrePerdu = new JFrame("Défaite !");
        fenetrePerdu.setSize(400, 100);
        fenetrePerdu.setAlwaysOnTop(true);
        fenetrePerdu.setLayout(new BorderLayout());
        fenetrePerdu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JLabel msg = new JLabel("Vous êtes mort.");
        msg.setText(msg.getText() + raison);
        msg.setForeground(Color.red);
        msg.setHorizontalAlignment(JLabel.CENTER);
        fenetrePerdu.add(msg, BorderLayout.CENTER);

        JButton quitter = new JButton("J'ai compris");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitter(fenetrePerdu);
                fenetrePerdu.dispose();
            }
        });

        fenetrePerdu.add(quitter, BorderLayout.SOUTH);

        fenetrePerdu.setLocationRelativeTo(null);
        fenetrePerdu.setVisible(true);
    }
}
     
        

        
        
