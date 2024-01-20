package representation.decorators;

import jeu.Game;
import representation.Event;
import univers.ErrDurabilite;
/**
 * Classe permettant de decorer un evenement (Node).
 */
public class NodeDecorator implements Event{
	 	/**
	 	 * L'evenement decore.
	 	 */
	 	public Event decoratedEvent;

	 	/**
	     * Initialise le decorateur avec l'evenement specifie.
	     *
	     * @param decoratedEvent L'evenement à decorer.
	     */
	    public NodeDecorator(Event decoratedEvent) {
	        this.decoratedEvent = decoratedEvent;
	    }
	    
	    /**
	     * Affiche l'evenement decore.
	     */
	    @Override
	    public void display() {
	        decoratedEvent.display();
	    }
	    
	    /**
	     * Choisi le prochain evenement (Node) base sur l'inventaire specifie.
	     *
	     * @param g Le jeu en cours.
	     * @return L'evenement suivant base sur l'inventaire.
	     * @throws ErrDurabilite 
	     */
	    @Override
	    public Event chooseNext(Game g) throws ErrDurabilite {
	        return decoratedEvent.chooseNext(g);
	    }
}

   