package representation;

import jeu.Game;
/**
 * Represente un nœud terminal dans le scenario.
 */
public class TerminalNode extends Node{

	private static final long serialVersionUID = 1L;
	
	
	/**
     * Initialise un nœud terminal avec la description specifiee.
     *
     * @param description La description du nœud terminal.
     */
	public TerminalNode(String description) {
		super (description);
	}
	
	/**
     * Selectionne le prochain nœud base sur l'inventaire specifie.
     * Dans un nœud terminal, il affiche simplement la description.
     *
     * @param g Le jeu en cours.
     * @return Le nœud terminal actuel.
     */
	@Override
	public Node chooseNext(Game g) {
		this.display();
		return this;
	}
	
	
	/**
     * Selectionne le prochain nœud dans le cas d'une interface graphique.
     * Dans un nœud terminal, il affiche simplement la description.
     *
     * @param g     Le jeu actuel.
     * @param choix Le choix effectue dans l'interface graphique.
     * @return Le prochain nœud (null dans ce cas).
     */
	@Override
	public Node chooseNextIG(Game g, int choix) {
		g.getResultArea().setText(this.getDescription());
		return null;
	}


}
