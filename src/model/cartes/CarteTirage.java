package model.cartes;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class CarteTirage extends Carte {
    
    
    public CarteTirage(String nomFichier) {
        super(nomFichier);
    }
    public boolean isCarteMonteeDesEaux(){
        return false;
    }
    public boolean isCarteTresor(){
        return false;
    }
    public boolean isCarteHelicoptere(){
        return false;
    }

    public boolean isCarteSac() {
return false;    }
}
