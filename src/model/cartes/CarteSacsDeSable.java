package model.cartes;

/**
 *
 * @author IUT2-Dept Info
 */
public class CarteSacsDeSable extends CarteTirage {

    public CarteSacsDeSable(String nomFichier) {
        super(nomFichier);
    }
    
    @Override
    public boolean isCarteSac() {
        return true;    
    }
}
