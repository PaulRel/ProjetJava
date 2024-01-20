package jeu;
/**
 * Classe representant le nœud de debut du jeu.
 * Ce nœud introduit le joueur dans l'histoire et lui presente le contexte du jeu.
 * Il presente le scenario, explique la situation initiale du joueur et son objectif principal.
 * Herite de la classe DecisionNode.
 */
import representation.DecisionNode;

/**
 * Constructeur de la classe Debut.
 * Initialise le nœud de debut avec une description detaillee du contexte du jeu.
 */
public class Debut extends DecisionNode{
	private static final long serialVersionUID = 1L;

	public Debut() {
		super("Vous êtes un apprenti des éléments dans un monde où la plupart des humains sont sans pouvoir."
				+ "\nVous pouvez contrôler l'un des quatre éléments: l'air, la terre, l'eau ou le feu."
				+ "\nMais vous n'êtes pas le seul. Un maître des éléments a décidé d'utiliser son pouvoir pour faire le mal."
				+ "\nIl a incendié les villages, asservi les habitants et défié les autres maîtres des éléments. "
				+ "\nVotre but est de l'arrêter et de rétablir la paix. Êtes-vous prêt à relever le défi ?");
	}
}
