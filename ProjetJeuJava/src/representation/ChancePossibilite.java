package representation;

import java.io.Serializable;

import univers.Armes;
import univers.Outils;

/**
 * Represente une possibilite associee à une chance dans le jeu.
 * Chaque possibilite contient une description, un nœud suivant, ainsi qu'eventuellement une nouvelle arme et/ou un nouvel outil.
 */

public class ChancePossibilite implements Serializable{
	private static final long serialVersionUID = 1L;
	private String description;
	private Node nextNode;
	private Armes NvArme;
	private Outils NvOutil;
	private int expbonus;
	
	/**
     * Constructeur pour creer une possibilite sans nouvelle arme ni nouvel outil en recompense.
     * 
     * @param description La description de la possibilite.
     * @param nextNode    Le nœud suivant lie à cette possibilite.
     */
	
	public ChancePossibilite(String description, Node nextNode) {
		this.description = description;
		this.nextNode=nextNode;
	}
	
	/**
	 * Construit une instance de ChancePossibilite avec les elements fournis.
	 *
	 * @param description La description de la chance ou de la possibilite.
	 * @param nextNode Le noeud suivant associe à cette chance ou possibilite.
	 * @param NvArme La nouvelle arme attribuee en cas de cette chance ou possibilite.
	 * @param NvOutil Le nouvel outil attribue en cas de cette chance ou possibilite.
	 * @param exp Le bonus d'experience associe à cette chance ou possibilite.
	 */
	public ChancePossibilite(String description, Node nextNode, Armes NvArme, Outils NvOutil, int exp) {
		this.description = description;
		this.nextNode=nextNode;
		this.NvArme = NvArme;
		this.NvOutil=NvOutil;
		this.expbonus=exp;
	}

	/**
     * Obtient la description de la possibilite.
     *
     * @return La description de la possibilite.
     */
	
	 public String getDescription() {
		 return description;
	 }
	 /**
	 * Obtient le nœud suivant lie à cette possibilite.
	 *
	 * @return Le nœud suivant lie à cette possibilite.
	 */
	 public Node getNextNode() {
		 return nextNode;
	 }
	 
	 /**
	  * Obtient la nouvelle arme associee à cette possibilite.
	  *
	  * @return La nouvelle arme associee à cette possibilite.
	  */
	 public Armes getNvArme() {
		 return NvArme;
	 }
	 
	 /**
	 * Obtient le nouvel outil associe à cette possibilite.
	 *
	 * @return Le nouvel outil associe à cette possibilite.
	 */
	 public Outils getNvOutil() {
		 return NvOutil;
	 }

	public int getExpbonus() {
		return expbonus;
	}

}