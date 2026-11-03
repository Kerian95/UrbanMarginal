package controleur;

/**
 * Constantes de l'application
 */
public interface Global {
	// connexion
	int PORT = 6666;
	String SERVEUR = "serveur";
	String SEPARATOR = "~";
	String PSEUDO = "pseudo";
	// chemins
	String CHEMINSEPARATOR = "/";
	String CHEMINFONDS = "fonds" + CHEMINSEPARATOR;
	String FONDCHOIX = CHEMINFONDS + "fondchoix.jpg";
	String FONDARENE = CHEMINFONDS + "fondarene.jpg";
	String CHEMINPERSONNAGES = "personnages" + CHEMINSEPARATOR;
	String PERSO = "perso";
	String MARCHE = "marche";
	String EXTFICHIERPERSO = ".gif";
	// personnages
	int MAXPERSO = 3;
}