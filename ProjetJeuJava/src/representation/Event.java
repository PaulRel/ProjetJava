package representation;

import jeu.Game;
import univers.ErrDurabilite;

/**
 * Interface decrivant un evenement.
 */
public interface Event{
	/**
     * Affiche les details de l'evenement.
     */
	void display();
	
	/**
     * Choisi le prochain evenement.
     *
     * @param g Le jeu en cours.
     * @return L'evenement suivant base sur l'inventaire.
	 * @throws ErrDurabilite 
     */
	Event chooseNext(Game g) throws ErrDurabilite;
}
