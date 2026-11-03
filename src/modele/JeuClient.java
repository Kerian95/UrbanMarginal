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
	 * vrai si le panel des murs a déjà été reçu
	 */
	private boolean mursOk = false;

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
			if (!this.mursOk) {
				// premier panel reçu : les murs
				this.controle.evenementJeuClient(AJOUTPANELMURS, info);
				this.mursOk = true;
			} else {
				// panels suivants : le jeu
				this.controle.evenementJeuClient(MODIFPANELJEU, info);
			}
		} else if (info instanceof String) {
			this.controle.evenementJeuClient(MODIFTCHAT, info);
		} else if (info instanceof Integer) {
			this.controle.evenementJeuClient(JOUESON, info);
		}
	}

	@Override
	public void deconnexion(Connection connection) {
		// le serveur est parti : on arrête l'application
		System.exit(0);
	}

	/**
	 * Envoi d'une information vers le serveur
	 */
	public void envoi(String info) {
		super.envoi(this.connectionServeur, info);
	}
}