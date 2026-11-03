package controleur;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
	 * @param info "serveur" si Start, ou l'IP si Connect
	 */
	public void evenementEntreeJeu(String info) {
		if (info.equals(SERVEUR)) {
			new ServeurSocket(this, PORT);
			this.leJeu = new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene(this, SERVEUR);
			((JeuServeur) this.leJeu).constructionMurs();
			this.frmArene.setVisible(true);
		} else {
			new ClientSocket(this, info, PORT);
		}
	}

	/**
	 * Événement provenant de la frame ChoixJoueur
	 * @param pseudo   pseudo saisi
	 * @param numPerso numéro du personnage choisi
	 */
	public void evenementChoixJoueur(String pseudo, int numPerso) {
		((JeuClient) this.leJeu).envoi(PSEUDO + SEPARATOR + pseudo + SEPARATOR + numPerso);
		this.frmChoixJoueur.dispose();
		this.frmArene.setVisible(true);
	}

	/**
	 * Événement provenant de la frame Arene (phrase saisie dans le tchat)
	 * @param info phrase saisie
	 */
	public void evenementArene(String info) {
		((JeuClient) this.leJeu).envoi(TCHAT + SEPARATOR + info);
	}

	/**
	 * Événement provenant de JeuServeur
	 * @param ordre ce qu'il faut faire
	 * @param info  information à traiter
	 */
	public void evenementJeuServeur(String ordre, Object info) {
		if (ordre.equals(AJOUTMUR)) {
			this.frmArene.ajoutMurs(info);
		} else if (ordre.equals(AJOUTPANELMURS)) {
			this.leJeu.envoi((Connection) info, this.frmArene.getJpnMurs());
		} else if (ordre.equals(AJOUTJLABELJEU)) {
			this.frmArene.ajoutJLabelJeu((JLabel) info);
		} else if (ordre.equals(MODIFPANELJEU)) {
			this.leJeu.envoi((Connection) info, this.frmArene.getJpnJeu());
		} else if (ordre.equals(AJOUTPHRASE)) {
			this.frmArene.ajoutTchat((String) info);
			((JeuServeur) this.leJeu).envoi(this.frmArene.getTxtChat());
		}
	}

	/**
	 * Événement provenant de JeuClient
	 * @param ordre ce qu'il faut faire
	 * @param info  information à traiter
	 */
	public void evenementJeuClient(String ordre, Object info) {
		if (ordre.equals(AJOUTPANELMURS)) {
			this.frmArene.setJpnMurs((JPanel) info);
		} else if (ordre.equals(MODIFPANELJEU)) {
			this.frmArene.setJpnJeu((JPanel) info);
		} else if (ordre.equals(MODIFTCHAT)) {
			this.frmArene.setTxtChat((String) info);
		}
	}

	/**
	 * Envoi d'une information vers l'ordinateur distant
	 * @param connection connexion vers l'ordinateur distant
	 * @param info information à envoyer
	 */
	public void envoi(Connection connection, Object info) {
		connection.envoi(info);
	}

	/**
	 * Méthode d'entrée dans l'application
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
				this.frmArene = new Arene(this, CLIENT);
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