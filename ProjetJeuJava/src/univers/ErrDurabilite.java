package univers;

/**
 * Une exception personnalisee pour gerer les problemes de durabilite.
 */
public class ErrDurabilite extends Exception{
	private static final long serialVersionUID = 1L;
	protected int n;
	
	/**
     * Constructeur prenant en parametre le niveau de durabilite concerne.
     *
     * @param n Le niveau de durabilite associe à l'exception.
     */
	public ErrDurabilite(int n) {
		this.n=n;
	}
	/**
     * Affiche un message d'erreur concernant la durabilite.
     */
	public void printErr() {
		System.out.println("La durabilité "+n+" doit être positive." );
	}
}
