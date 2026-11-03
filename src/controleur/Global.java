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
	String CHEMINMURS = "murs" + CHEMINSEPARATOR;
	String MUR = CHEMINMURS + "mur.gif";
	// personnages
	int MAXPERSO = 3;
	// arène et murs
	int HAUTEURARENE = 600;
	int LARGEURARENE = 800;
	int HAUTEURMUR = 35;
	int LARGEURMUR = 34;
	int NBMURS = 20;
	// ordres
	String AJOUTMUR = "ajout mur";
	String AJOUTPANELMURS = "ajout panel murs";
}