package model.cartes;

import util.Utils.Tresor;

/**
 *
 * @author IUT2-Dept Info
 */
public class CarteTresor extends CarteTirage {
    private final Tresor typeTresor;
    
    public CarteTresor(String nomFichier, Tresor typeTresor) {
        super(nomFichier);
        this.typeTresor = typeTresor;
        
    }
}
