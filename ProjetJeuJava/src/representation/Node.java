package representation;



import java.io.Serializable;

import jeu.Game;
import univers.ErrDurabilite;

/**
 * Classe abstraite representant un nœud dans le système.
 */
public abstract class Node implements Event, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String Description;
	private int id;
	private static int nextId = 0;
	
	
	public Node(String Description){
		this.Description=Description;
		this.id = nextId;
		nextId++;
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
     * Affiche la description du nœud actuel.
     */
	@Override
	public void display() {
		System.out.println(this.Description);
	}
	/**
     * Recupère l'identifiant du nœud.
     *
     * @return L'identifiant du nœud.
     */
	public int getid() {
		return id;
	}
	
	/**
     * Recupère la description du nœud.
     *
     * @return La description du nœud.
     */
	public String getDescription() {
		return Description;
	};
	
	/**
     * Modifie la description du nœud.
     *
     * @param description La nouvelle description du nœud.
     */
	public void setDescription(String description) {
		Description = description;
	}
	
	/**
     * Choisi le prochain nœud pour une interface graphique du choix donne.
     *
     * @param g     Le jeu actuel.
     * @param choix Le choix specifie.
     * @return Le nœud suivant pour l'interface graphique.
     */
	public abstract Node chooseNextIG(Game g, int choix);
	
	/**
     * Redefinition de la methode equals pour comparer les IDs.
     *
     * @param obj L'objet à comparer.
     * @return true si les IDs des nœuds sont identiques, sinon false.
     */
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        Node otherNode = (Node) obj;
        
        return this.id == otherNode.id;
    }

}		
