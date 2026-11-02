package modele;
import controleur.Controle;
import outils.connexion.Connection;
/**
 * Gestion du jeu côté client
 *
 */
public class JeuClient extends Jeu {
	private Connection connectionServeur;
	
	/**
	 * Controleur
	 */
	public JeuClient(Controle controle) {
		this.controle = controle;
	}
	
	@Override
	public void connexion(Connection connection) {
		this.connectionServeur = connection;
	}

	@Override
	public void reception() {
	}
	
	@Override
	public void deconnexion() {
	}

	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à l'envoi dans la classe Jeu
	 */
	public void envoi() {
	}

}
