package representation.decorators;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import jeu.Game;
import representation.Event;
import univers.ErrDurabilite;

/**
 * SoundNode est un des deux decorateurs concrets qui implemente Event comme demande
 * SoundNode permet de lie un son à un noeud
 */
public class SoundNode extends NodeDecorator {
	/**
	 * Le chemin vers le son. Ce son sera dans le dossier Sons du projet.
	 */
	private String soundPath;
	
	
	/**
	 *Le Clip qui represente un son
	 */
	private Clip clip;
	
	
    /**
     * Constructeur de ImageNode
     * @param decoratedEvent Le noeud à decore
     * @param soundPath Le chemin vers le son
     */
    public SoundNode(Event decoratedEvent, String soundPath) {
        super(decoratedEvent);
        this.soundPath = soundPath;
    }

    
    /**
     * Cree un clip avec le chemin passe en paramètre
     * Joue le son
     */
    @Override
    public void display() {
    	try {
            File file = new File(soundPath);
            // AudioInputStream est utilise pour lire les donnees audio à partir du fichier.
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            // Clip est un objet qui peut être utilise pour jouer des clips audio courts.
            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
        }
        catch (Exception e) {e.printStackTrace(); }
//      decoratedEvent.display();
    }

    
    /**
     *@param g Le jeu en cours.
     *@return le noeud suivant
     * @throws ErrDurabilite 
     */
    @Override
    public Event chooseNext(Game g) throws ErrDurabilite {
        return decoratedEvent.chooseNext(g);
    }
    
    
    /**
     * Arrete le son
     */
    public void stopSound() {
        if (clip != null && clip.isOpen() && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}

