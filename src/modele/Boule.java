package modele;

import java.net.URL;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/**
 * Gestion de la boule
 */
public class Boule extends Objet implements Global, Runnable {

	/**
	 * instance de JeuServeur pour la communication
	 */
	private JeuServeur jeuServeur;
	/**
	 * collection des murs
	 */
	private Collection<? extends Objet> lesMurs;
	/**
	 * joueur qui a tiré la boule
	 */
	private Joueur attaquant;

	/**
	 * Constructeur
	 * 
	 * @param jeuServeur instance de JeuServeur pour la communication
	 */
	public Boule(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		super.jLabel = new JLabel();
		super.jLabel.setVisible(false);
		URL resource = getClass().getClassLoader().getResource(BOULE);
		super.jLabel.setIcon(new ImageIcon(resource));
		super.jLabel.setBounds(0, 0, LARGEURBOULE, HAUTEURBOULE);
	}

	/**
	 * Tir d'une boule
	 * 
	 * @param attaquant joueur qui tire
	 * @param lesMurs   collection des murs
	 */
	public void tireBoule(Joueur attaquant, Collection<? extends Objet> lesMurs) {
		this.attaquant = attaquant;
		this.lesMurs = lesMurs;
		// position de départ : à mi-hauteur, à gauche ou à droite du joueur
		if (attaquant.getOrientation() == GAUCHE) {
			posX = attaquant.getPosX() - LARGEURBOULE - 1;
		} else {
			posX = attaquant.getPosX() + LARGEURPERSO + 1;
		}
		posY = attaquant.getPosY() + HAUTEURPERSO / 2;
		// lancement du thread (méthode run)
		new Thread(this).start();
	}

	/**
	 * Déplacement de la boule et gestion de la collision (thread indépendant)
	 */
	@Override
	public void run() {
		// son du tir
		this.jeuServeur.envoi(FIGHT);
		// l'attaquant se met en position de tir
		this.attaquant.affiche(MARCHE, 1);
		super.jLabel.setVisible(true);
		Joueur victime = null;
		int lePas;
		if (this.attaquant.getOrientation() == GAUCHE) {
			lePas = -PAS;
		} else {
			lePas = PAS;
		}
		// trajectoire de la boule
		do {
			posX += lePas;
			super.jLabel.setBounds(posX, posY, LARGEURBOULE, HAUTEURBOULE);
			this.jeuServeur.envoiJeuATous();
			this.pause(10, 0);
			Collection<Joueur> lesJoueurs = this.jeuServeur.getLesJoueurs();
			victime = (Joueur) super.toucheCollectionObjets(lesJoueurs);
		} while (posX >= 0 && posX <= LARGEURARENE && this.toucheCollectionObjets(lesMurs) == null && victime == null);
		// un joueur a été touché
		if (victime != null && !victime.estMort()) {
			victime.perteVie();
			this.attaquant.gainVie();
			if (victime.estMort()) {
				this.jeuServeur.envoi(DEATH);
			} else {
				this.jeuServeur.envoi(HURT);
			}
			// animation du blessé
			for (int k = 1; k <= NBETAPESTOUCHE; k++) {
				victime.affiche(TOUCHE, k);
				this.pause(80, 0);
			}
			if (victime.estMort()) {
				// animation de la mort
				for (int k = 1; k <= NBETAPESMORT; k++) {
					victime.affiche(MORT, k);
					this.pause(80, 0);
				}
			} else {
				// retour à la position de repos
				victime.affiche(MARCHE, 1);
			}
		}
		// la boule disparait
		super.jLabel.setVisible(false);
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Pause dans le thread
	 * 
	 * @param millis millisecondes
	 * @param nanos  nanosecondes
	 */
	private void pause(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		} catch (InterruptedException e) {
			System.out.println("erreur pause : " + e);
		}
	}
}