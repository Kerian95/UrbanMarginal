package modele;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

/**
 * Gestion des joueurs
 */
public class Joueur extends Objet implements Global {

	/**
	 * vie de départ pour tous les joueurs
	 */
	private static final int MAXVIE = 10;
	/**
	 * gain de points de vie lors d'une attaque
	 */
	private static final int GAIN = 1;
	/**
	 * perte de points de vie lors d'une attaque
	 */
	private static final int PERTE = 2;

	/**
	 * pseudo saisi
	 */
	private String pseudo;
	/**
	 * n° correspondant au personnage (avatar) pour le fichier correspondant
	 */
	private int numPerso;
	/**
	 * instance de JeuServeur pour communiquer avec lui
	 */
	private JeuServeur jeuServeur;
	/**
	 * numéro d'étape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape;
	/**
	 * la boule du joueur
	 */
	private Boule boule;
	/**
	 * vie restante du joueur
	 */
	private int vie;
	/**
	 * tourné vers la gauche (0) ou vers la droite (1)
	 */
	private int orientation;
	/**
	 * message sous le personnage (pseudo et vie)
	 */
	private JLabel message;

	/**
	 * Constructeur
	 * 
	 * @param jeuServeur instance de JeuServeur pour communiquer avec lui
	 */
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		this.vie = MAXVIE;
		this.etape = 1;
		this.orientation = DROITE;
	}

	/**
	 * @return le pseudo du joueur
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * @return l'orientation du joueur (GAUCHE ou DROITE)
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position,
	 * affichage, création de la boule)
	 * 
	 * @param pseudo     pseudo du joueur
	 * @param numPerso   numéro du personnage
	 * @param lesJoueurs collection des joueurs
	 * @param lesMurs    collection des murs
	 */
	public void initPerso(String pseudo, int numPerso, Collection<? extends Objet> lesJoueurs,
			Collection<? extends Objet> lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		System.out.println("joueur " + pseudo + " - num perso " + numPerso + " créé");
		// création du personnage et de son message
		super.jLabel = new JLabel();
		this.message = new JLabel();
		this.message.setHorizontalAlignment(SwingConstants.CENTER);
		this.message.setFont(new Font("Dialog", Font.PLAIN, 8));
		// création de la boule
		this.boule = new Boule(this.jeuServeur);
		// position de départ
		this.premierePosition(lesJoueurs, lesMurs);
		// ajout dans l'arène
		this.jeuServeur.ajoutJLabelJeuArene(super.jLabel);
		this.jeuServeur.ajoutJLabelJeuArene(this.message);
		this.jeuServeur.ajoutJLabelJeuArene(this.boule.getJLabel());
		// affichage
		this.affiche(MARCHE, this.etape);
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre
	 * joueur ou un mur)
	 * 
	 * @param lesJoueurs collection des joueurs
	 * @param lesMurs    collection des murs
	 */
	private void premierePosition(Collection<? extends Objet> lesJoueurs, Collection<? extends Objet> lesMurs) {
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO));
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE));
		} while (this.toucheCollectionObjets(lesJoueurs) != null || this.toucheCollectionObjets(lesMurs) != null);
	}

	/**
	 * Affiche le personnage et son message
	 * 
	 * @param etat  "marche", "touche" ou "mort"
	 * @param etape étape dans l'animation
	 */
	public void affiche(String etat, int etape) {
		// personnage
		super.jLabel.setBounds(posX, posY, LARGEURPERSO, HAUTEURPERSO);
		String chemin = CHEMINPERSONNAGES + PERSO + this.numPerso + etat + etape + "d" + this.orientation
				+ EXTFICHIERPERSO;
		URL resource = getClass().getClassLoader().getResource(chemin);
		super.jLabel.setIcon(new ImageIcon(resource));
		// message
		this.message.setBounds(posX - MARGEMESSAGE, posY + HAUTEURPERSO, LARGEURPERSO + MARGEMESSAGE, HAUTEURMESSAGE);
		this.message.setText(pseudo + " : " + vie);
		// envoi du panel du jeu à tous les joueurs
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue (déplacement, tir de boule)
	 * 
	 * @param action     code de la touche utilisée
	 * @param lesJoueurs collection des joueurs
	 * @param lesMurs    collection des murs
	 */
	public void action(Integer action, Collection<? extends Objet> lesJoueurs, Collection<? extends Objet> lesMurs) {
		if (!this.estMort()) {
			switch (action) {
			case KeyEvent.VK_LEFT:
				this.orientation = GAUCHE;
				posX = deplace(posX, action, -PAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
				break;
			case KeyEvent.VK_RIGHT:
				this.orientation = DROITE;
				posX = deplace(posX, action, PAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
				break;
			case KeyEvent.VK_UP:
				posY = deplace(posY, action, -PAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
				break;
			case KeyEvent.VK_DOWN:
				posY = deplace(posY, action, PAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
				break;
			case KeyEvent.VK_SPACE:
				if (!this.boule.getJLabel().isVisible()) {
					this.boule.tireBoule(this, lesMurs);
				}
				break;
			}
			this.affiche(MARCHE, this.etape);
		}
	}

	/**
	 * Gère le déplacement du personnage
	 * 
	 * @param position   position de départ
	 * @param action     gauche, droite, haut ou bas
	 * @param lepas      valeur de déplacement (positif ou négatif)
	 * @param max        valeur à ne pas dépasser
	 * @param lesJoueurs collection de joueurs pour éviter les collisions
	 * @param lesMurs    collection de murs pour éviter les collisions
	 * @return nouvelle position
	 */
	private int deplace(int position, int action, int lepas, int max, Collection<? extends Objet> lesJoueurs,
			Collection<? extends Objet> lesMurs) {
		int ancpos = position;
		position += lepas;
		position = Math.max(position, 0);
		position = Math.min(position, max);
		if (action == KeyEvent.VK_LEFT || action == KeyEvent.VK_RIGHT) {
			posX = position;
		} else {
			posY = position;
		}
		// contrôle s'il y a collision, dans ce cas, le personnage reste sur place
		if (toucheCollectionObjets(lesJoueurs) != null || toucheCollectionObjets(lesMurs) != null) {
			position = ancpos;
		}
		// passe à l'étape suivante de l'animation de la marche
		etape = (etape % NBETAPESMARCHE) + 1;
		return position;
	}

	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
		this.vie += GAIN;
		this.affiche(MARCHE, this.etape);
	}

	/**
	 * Perte de points de vie après avoir été touché
	 */
	public void perteVie() {
		this.vie = Math.max(this.vie - PERTE, 0);
		this.affiche(MARCHE, this.etape);
	}

	/**
	 * vrai si la vie est à 0
	 * 
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return (this.vie == 0);
	}

	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
		if (super.jLabel != null) {
			super.jLabel.setVisible(false);
			this.message.setVisible(false);
			this.boule.getJLabel().setVisible(false);
			this.jeuServeur.envoiJeuATous();
		}
	}
}