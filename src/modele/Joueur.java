package modele;

import java.awt.Font;
import java.net.URL;
import java.util.ArrayList;
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
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position,
	 * affichage)
	 * 
	 * @param pseudo     pseudo du joueur
	 * @param numPerso   numéro du personnage
	 * @param lesJoueurs collection des joueurs
	 * @param lesMurs    collection des murs
	 */
	public void initPerso(String pseudo, int numPerso, Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		System.out.println("joueur " + pseudo + " - num perso " + numPerso + " créé");
		// création du personnage et de son message
		super.jLabel = new JLabel();
		this.message = new JLabel();
		this.message.setHorizontalAlignment(SwingConstants.CENTER);
		this.message.setFont(new Font("Dialog", Font.PLAIN, 8));
		// position de départ
		this.premierePosition(lesJoueurs, lesMurs);
		// ajout dans l'arène
		this.jeuServeur.ajoutJLabelJeuArene(super.jLabel);
		this.jeuServeur.ajoutJLabelJeuArene(this.message);
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
	private void premierePosition(Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO));
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE));
		} while (this.toucheJoueur(lesJoueurs) || this.toucheMur(lesMurs));
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
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 */
	public void action() {
	}

	/**
	 * Gère le déplacement du personnage
	 */
	private void deplace() {
	}

	/**
	 * Contrôle si le joueur touche un des autres joueurs
	 * 
	 * @param lesJoueurs collection des joueurs
	 * @return true si deux joueurs se touchent
	 */
	private Boolean toucheJoueur(Collection<Joueur> lesJoueurs) {
		for (Joueur unJoueur : lesJoueurs) {
			if (!this.equals(unJoueur)) {
				if (super.toucheObjet(unJoueur)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
	}

	/**
	 * Perte de points de vie après avoir été touché
	 */
	public void perteVie() {
	}

	/**
	 * Contrôle si le joueur touche un des murs
	 * 
	 * @param lesMurs collection des murs
	 * @return true si un joueur touche un mur
	 */
	private Boolean toucheMur(ArrayList<Mur> lesMurs) {
		for (Mur unMur : lesMurs) {
			if (super.toucheObjet(unMur)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * vrai si la vie est à 0
	 * 
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return null;
	}

	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
	}
}