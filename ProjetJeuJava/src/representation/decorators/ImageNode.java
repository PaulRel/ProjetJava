package representation.decorators;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import jeu.Game;
import representation.Event;
import univers.ErrDurabilite;


/**
 * ImageNode est un des deux decorateurs concrets qui implemente Event comme demande
 * ImageNode permet de lie une image à un noeud
 * 
 */
public class ImageNode extends NodeDecorator {
	/**
	 * Le chemin vers l'image. Cette image sera dans le dossier Images du projet.
	 */
	private String imagePath;
	
	/**
	 * Le JLabel de l'interface graphique qui contient l'image
	 */
	private JLabel ImageLabel;
	
    /**
     * Constructeur de ImageNode
     * @param decoratedEvent Le Noeud auquel l'image correspond
     * @param imagePath Le lien vers l'image
     * @param ImageLabel Le JLabel de la classe Game ou s'affichera l'image
     */
    public ImageNode(Event decoratedEvent, String imagePath, JLabel ImageLabel) {
        super(decoratedEvent);
        this.imagePath = imagePath;
        this.ImageLabel=ImageLabel;
    }

    /**
     * Cree une ImageIcon avec le chemin passe en paramètre
     * Affiche cette image dans le JLabel de l'interfecagraphique
     *
     */
    @Override
    public void display() {
    	try {
			ImageIcon icon = new ImageIcon(imagePath);
            ImageLabel.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     *@return le noeud suivant
     * @throws ErrDurabilite 
     */
    @Override
    public Event chooseNext(Game g) throws ErrDurabilite {
        return decoratedEvent.chooseNext(g);
    }
    
    
    /**

     */

}

