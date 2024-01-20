package representation;

import jeu.Game;
import univers.ErrDurabilite;

/**
 * Classe abstraite representant un nœud interne (InnerNode).
 */
public abstract class InnerNode extends Node{
	private static final long serialVersionUID = 1L;

	/**
     * Constructeur pour un nœud interne avec une description donnee.
     *
     * @param Description La description du nœud interne.
     */
	public InnerNode(String Description){
		super (Description);
	}
	
	
	/**
    * Choisi le prochain nœud.
    *
    * @param g Le jeu en cours.
    * @return Le nœud suivant base sur l'inventaire.
	 * @throws ErrDurabilite 
    */
	@Override
	public abstract Node chooseNext(Game g) throws ErrDurabilite;
	
	/**
     * Choisit le prochain nœud pour une interface graphique en fonction du choix donne.
     *
     * @param g          Le jeu actuel.
     * @param choix      Le choix specifie.
     * @return Le nœud suivant pour l'interface graphique.
     */
	@Override
	public abstract Node chooseNextIG(Game g, int choix);
}
