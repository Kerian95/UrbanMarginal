package controleur;

import vue.EntreeJeu;
import vue.Arene;
import vue.ChoixJoueur;
import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import outils.connexion.AsyncResponse;
import outils.connexion.Connection;
import outils.connexion.ClientSocket;
import outils.connexion.ServeurSocket;

public class Controle implements AsyncResponse, Global {
	private EntreeJeu frmEntreeJeu;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	private Jeu leJeu;

	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this);
		this.frmEntreeJeu.setVisible(true);
	}

	/**
	 * Événement provenant de la frame EntreeJeu
	 * 
	 * @param info "serveur" si Start, ou l'IP si Connect
	 */
	public void evenementEntreeJeu(String info) {
		if (info.equals(SERVEUR)) {
			new ServeurSocket(this, PORT);
			this.leJeu = new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene();
			this.frmArene.setVisible(true);
		} else {
			new ClientSocket(this, info, PORT);
		}
	}

	/**
	 * Événement provenant de la frame ChoixJoueur
	 * 
	 * @param pseudo   pseudo saisi
	 * @param numPerso numéro du personnage choisi
	 */
	public void evenementChoixJoueur(String pseudo, int numPerso) {
		((JeuClient) this.leJeu).envoi(PSEUDO + SEPARATOR + pseudo + SEPARATOR + numPerso);
		this.frmChoixJoueur.dispose();
		this.frmArene.setVisible(true);
	}

	/**
	 * Envoi d'une information vers l'ordinateur distant
	 * 
	 * @param connection connexion vers l'ordinateur distant
	 * @param info       information à envoyer
	 */
	public void envoi(Connection connection, Object info) {
		connection.envoi(info);
	}

	/**
	 * Méthode d'entrée dans l'application
	 * 
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		new Controle();
	}

	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch (ordre) {
		case "connexion":
			if (!(this.leJeu instanceof JeuServeur)) {
				this.frmEntreeJeu.dispose();
				this.frmArene = new Arene();
				this.frmChoixJoueur = new ChoixJoueur(this);
				this.frmChoixJoueur.setVisible(true);
				this.leJeu = new JeuClient(this);
				this.leJeu.connexion(connection);
			} else {
				this.leJeu.connexion(connection);
			}
			break;
		case "reception":
			this.leJeu.reception(connection, info);
			break;
		case "deconnexion":
			break;
		}
	}
}