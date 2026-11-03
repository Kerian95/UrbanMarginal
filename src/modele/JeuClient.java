package modele;

import javax.swing.JPanel;
import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 */
public class JeuClient extends Jeu implements Global {
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
		if (info instanceof JPanel) {
			this.controle.evenementJeuClient(AJOUTPANELMURS, info);
		}
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