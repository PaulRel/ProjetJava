package representation;

import java.io.Serializable;
import univers.Armes;
import univers.Outils;


/**
 * Represente un choix d'un noeud du jeu.
 * Chaque choix contient une description, un nœud suivant, ainsi qu'eventuellement une nouvelle arme et/ou un nouvel outil.
 */
public class DecisionChoix implements Serializable{
	private static final long serialVersionUID = 1L;
	private String description;
	 private Node nextNode;
	 private String result;
	 private Armes NvArme;
	 private Outils NvOutil;
	 private int exp;

	 
	 /**
	  * Initialise un choix avec sa description, le nœud suivant et le resultat associe.
	  *
	  * @param description La description du choix.
	  * @param nextNode    Le nœud suivant après avoir fait ce choix.
	  * @param result      Le resultat ou l'effet du choix.
	  */
	 public DecisionChoix(String description, Node nextNode, String result) {
		 this.description = description;
	     this.nextNode = nextNode;
	     this.result=result;
	 }
	 
	 
	 /**
	  * Constructeur dans le cas d'un decisionnode ou le joueur obtient une recompense
	  * Initialise un choix avec sa description, le nœud suivant, le resultat et les recompenses (armes et/ou outils et/ou points d'experience).
	  *
	  * @param description La description du choix.
	  * @param nextNode    Le nœud suivant après avoir fait ce choix.
	  * @param result      Le resultat ou l'effet du choix.
	  * @param NvArme      La nouvelle arme obtenue suite à ce choix, peut être null.
	  * @param NvOutil     Le nouvel outil obtenu suite à ce choix, peut être null.
	  * @param expbonus	   Des points d'experience supplementaires
	  */
	 public DecisionChoix(String description, Node nextNode, Armes NvArme, Outils NvOutil, int expbonus, String result) {
	        this.description = description;
	        this.nextNode = nextNode;
	        this.result = result;
	        this.NvArme = NvArme;
	        this.NvOutil = NvOutil;
	        this.exp=expbonus;
	    }
	 
	 /**
	  * Constructeur dans le cas d'un chancenode car le nextnode est choisi au hasard
	  * Initialise un choix sans nœud specifique pour les nœuds de chance où le nœud suivant est choisi aleatoirement.
	  *
	  * @param description La description du choix.
	  */
	 public DecisionChoix(String description) {
		 this.description = description;
	 }
	 
	 /**
	  * Renvoie la description du choix.
	  *
	  * @return La description du choix.
	  */
	 public String getDescription() {
		 return description;
	 }
	 
	 /**
	  * Renvoie le nœud suivant après avoir fait ce choix.
	  *
	  * @return Le nœud suivant après avoir fait ce choix.
	  */
	 public Node getNextNode() {
		 return nextNode;
	 }
	 
	/**
	 * Renvoie le resultat ou l'effet du choix.
	 *
	 * @return Le resultat ou l'effet du choix.
	 */
	public String getResult() {
		 return result;
	 }
	
	/**
	 * Renvoie la nouvelle arme obtenue suite à ce choix.
	 *
	 * @return La nouvelle arme obtenue suite à ce choix.
	 */
	public Armes getNvArme() {
		return NvArme;
	}
	
	/**
	 * Renvoie le nouvel outil obtenu suite à ce choix.
	 *
	 * @return Le nouvel outil obtenu suite à ce choix.
	 */
	public Outils getNvOutil() {
		return NvOutil;
	}


	public int getExpBonus() {
		return exp;
	}
}
