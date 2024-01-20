package representation;

import java.util.ArrayList;
import java.util.Scanner;
import jeu.Game;
import univers.Armes;
import univers.Outils;
/**
 * Nœud representant une enigme à resoudre par le joueur.
 */
public class EnigmeNode extends InnerNode{
	private static final long serialVersionUID = 1L;
	private ArrayList<String> reponsesPossibles;
	private Node NextNode;
	private String resultSiBienRepondu;
    private String resultSiMalRepondu;
	private int erreurs;
	private Armes NvArme;
	private Outils NvOutil;
	private int exp;
    
	 /**
     * Constructeur pour initialiser une enigme avec ses reponses possibles et les resultats en cas de bonne et mauvaise reponse.
     *
     * @param Description          La description de l'enigme.
     * @param NextNode             Le nœud suivant en cas de reponse correcte.
     * @param resultSiBienRepondu  Le resultat si la reponse est correcte.
     * @param resultSiMalRepondu   Le resultat si la reponse est incorrecte.
     */
	public EnigmeNode(String Description, Node NextNode, String resultSiBienRepondu, String resultSiMalRepondu) {
		super(Description);
		reponsesPossibles = new ArrayList<>();
		this.NextNode=NextNode;
		this.resultSiBienRepondu = resultSiBienRepondu;
        this.resultSiMalRepondu = resultSiMalRepondu;
		erreurs=0;
	}
	
	/**
     * Constructeur pour initialiser une enigme avec ses reponses possibles, les resultats en cas de bonne et mauvaise reponse,
     * ainsi que les recompenses associees.
     *
     * @param Description          La description de l'enigme.
     * @param NextNode             Le nœud suivant en cas de reponse correcte.
     * @param resultSiBienRepondu  Le resultat si la reponse est correcte.
     * @param resultSiMalRepondu   Le resultat si la reponse est incorrecte.
     * @param NvArme               L'arme en recompense si la reponse est correcte, peut être null.
     * @param NvOutil              L'outil en recompense si la reponse est correcte, peut être null.
     * @param exp					Des points d'experience supplementaires
     */
	public EnigmeNode(String Description, Node NextNode, String resultSiBienRepondu, String resultSiMalRepondu, Armes NvArme, Outils NvOutil, int exp) {
		super(Description);
		reponsesPossibles = new ArrayList<>();
		this.NextNode=NextNode;
		this.resultSiBienRepondu = resultSiBienRepondu;
        this.resultSiMalRepondu = resultSiMalRepondu;
		erreurs=0;
		this.NvArme=NvArme;
		this.NvOutil=NvOutil;
		this.exp=exp;				
	}
	
	/**
     * Selectionne le nœud suivant en fonction de la reponse du joueur.
     *
     * @param g Le jeu actuel
     * @return Le nœud suivant en fonction de la reponse du joueur.
     */
	@Override
	public Node chooseNext(Game g) {
		super.display();
		System.out.println("Entrez votre réponse : ");
		Scanner scanner = new Scanner(System.in);
		String repUtilisateur = scanner.nextLine();
		
		if (reponsesPossibles.contains(repUtilisateur.toLowerCase())){
			System.out.println(resultSiBienRepondu + "\n");
			
			if (NvArme != null) {g.getInventaire().ajouterArme(NvArme);
			System.out.println("\nVous obtenez en récompense un(e) "+NvArme);}
			if (NvOutil != null) {g.getInventaire().ajouterOutil(NvOutil);
			System.out.println("\nVous obtenez en récompense un(e) "+NvOutil);}
			if (exp != 0) {g.getElementJoueur().gagnerExp(exp, g, true);}
            return NextNode;
        }
		else {
			erreurs++;
			if (erreurs >= 3) {
                System.out.println("Desole, vous n'avez pas trouvé la bonne réponse. Vous manquez la récompense.\n");
                return NextNode;
            }
            else {System.out.println("Mauvaise réponse. Réessayez."+ resultSiMalRepondu +"\n");return this;}
		}
	}
	
	
	
	//CHOOSE_NEXT_IG CONCERNE LE CAS D'UNE INTERFACE GRAPHIQUE (IG)
	
	@Override
	public Node chooseNextIG(Game g, int choix) {
		String repUtilisateur=g.getRepUtilisateur();
		
		if (reponsesPossibles.contains(repUtilisateur.toLowerCase())){
			g.getResultArea().setText(resultSiBienRepondu + "\n");
			
			if (NvArme != null) {g.getInventaire().ajouterArme(NvArme);
			g.getResultArea().setText(g.getResultArea().getText()+"\nVous obtenez en récompense un(e) "+NvArme);
			}
			if (NvOutil != null) {g.getInventaire().ajouterOutil(NvOutil);
			g.getResultArea().setText(g.getResultArea().getText()+"\nVous obtenez en récompense un(e) "+NvOutil);
			}
			if (exp != 0) {
				g.getElementJoueur().gagnerExp(exp, g, false);
			}
			
            return NextNode;
        }
		else {
			erreurs++;
			if (erreurs >= 3) {
				g.getResultArea().setText("Désolé, vous n'avez pas trouvé la bonne réponse." + resultSiMalRepondu + "\n");
                return NextNode;
            }
            else {g.getResultArea().setText(resultSiMalRepondu + "\n");return this;}
		}
	}
	
	/**
     * Methode pour ajouter une reponse possible à l'enigme.
     *
     * @param reponse La reponse possible à ajouter.
     */
	 public void addReponsePossible(String reponse) {
	        reponsesPossibles.add(reponse);
	    }
	 
	 /**
	  * Renvoie la liste des reponses possibles pour cette enigme.
	  *
	  * @return La liste des reponses possibles.
	  */
	 public ArrayList<String> getReponsesPossibles() {
			return reponsesPossibles;
		}
	 
	 /**
	  * Renvoie le nœud suivant après avoir resolu l'enigme.
	  *
	  * @return Le nœud suivant après resolution de l'enigme.
	  */
	 public Node getNextNode() {
		return NextNode;
	 }
	 
	/**
	 * Renvoie l'arme obtenue en resolvant l'enigme, si elle existe.
	 *
	 * @return L'arme obtenue suite à la resolution de l'enigme.
	 */
	public Armes getNvArme() {
		return NvArme;
	}

	public void setNextNode(Node nextNode) {
		NextNode = nextNode;
	}	

}
