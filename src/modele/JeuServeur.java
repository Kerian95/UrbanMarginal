package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté serveur
 */
public class JeuServeur extends Jeu implements Global {

	/**
	 * Collection de murs
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>();
	/**
	 * Collection de joueurs
	 */
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>();

	/**
	 * Constructeur
	 */
	public JeuServeur(Controle controle) {
		this.controle = controle;
	}

	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}

	@Override
	public void reception(Connection connection, Object info) {
		String[] message = ((String) info).split(SEPARATOR);
		switch (message[0]) {
		case PSEUDO:
			// envoi du panel des murs au nouveau client
			this.controle.evenementJeuServeur(AJOUTPANELMURS, connection);
			String pseudo = message[1];
			Integer numPerso = Integer.parseInt(message[2]);
			this.lesJoueurs.get(connection).initPerso(pseudo, numPerso, this.lesJoueurs.values(), this.lesMurs);
			// annonce de l'arrivée dans le tchat
			String arrivee = "*** " + pseudo + " vient de se connecter ***";
			this.controle.evenementJeuServeur(AJOUTPHRASE, arrivee);
			break;
		case TCHAT:
			String phrase = this.lesJoueurs.get(connection).getPseudo() + " > " + message[1];
			this.controle.evenementJeuServeur(AJOUTPHRASE, phrase);
			break;
		}
	}

	@Override
	public void deconnexion() {
	}

	/**
	 * Envoi d'une information vers tous les clients
	 * 
	 * @param info information à envoyer
	 */
	public void envoi(Object info) {
		for (Connection connection : this.lesJoueurs.keySet()) {
			super.envoi(connection, info);
		}
	}

	/**
	 * Demande d'ajout d'un label (joueur, message...) dans l'arène du serveur
	 * 
	 * @param jLabel label à ajouter
	 */
	public void ajoutJLabelJeuArene(JLabel jLabel) {
		this.controle.evenementJeuServeur(AJOUTJLABELJEU, jLabel);
	}

	/**
	 * Envoi du panel du jeu à tous les joueurs
	 */
	public void envoiJeuATous() {
		for (Connection connection : this.lesJoueurs.keySet()) {
			this.controle.evenementJeuServeur(MODIFPANELJEU, connection);
		}
	}

	/**
	 * Génération des murs
	 */
	public void constructionMurs() {
		for (int k = 0; k < NBMURS; k++) {
			this.lesMurs.add(new Mur());
			this.controle.evenementJeuServeur(AJOUTMUR, lesMurs.get(lesMurs.size() - 1).getJLabel());
		}
	}
}