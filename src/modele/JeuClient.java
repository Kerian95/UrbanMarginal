package modele;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 */
public class JeuClient extends Jeu {
	private Connection connectionServeur;

	/**
	 * Constructeur
	 */
	public JeuClient(Controle controle) {
		this.controle = controle;
	}

	@Override
	public void connexion(Connection connection) {
		this.connectionServeur = connection;
	}

	@Override
	public void reception(Connection connection, Object info) {
	}

	@Override
	public void deconnexion() {
	}

	/**
	 * Envoi d'une information vers le serveur
	 */
	public void envoi(String info) {
		super.envoi(this.connectionServeur, info);
	}
}