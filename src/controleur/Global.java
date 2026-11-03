package controleur;

/**
 * Constantes de l'application
 */
public interface Global {
	// connexion
	int PORT = 6666;
	String SERVEUR = "serveur";
	String CLIENT = "client";
	String SEPARATOR = "~";
	String PSEUDO = "pseudo";
	String TCHAT = "tchat";
	// chemins
	String CHEMINSEPARATOR = "/";
	String CHEMINFONDS = "fonds" + CHEMINSEPARATOR;
	String FONDCHOIX = CHEMINFONDS + "fondchoix.jpg";
	String FONDARENE = CHEMINFONDS + "fondarene.jpg";
	String CHEMINPERSONNAGES = "personnages" + CHEMINSEPARATOR;
	String PERSO = "perso";
	String EXTFICHIERPERSO = ".gif";
	String CHEMINMURS = "murs" + CHEMINSEPARATOR;
	String MUR = CHEMINMURS + "mur.gif";
	// personnages
	int MAXPERSO = 3;
	int HAUTEURPERSO = 44;
	int LARGEURPERSO = 39;
	int HAUTEURMESSAGE = 8;
	int MARGEMESSAGE = 10;
	String MARCHE = "marche";
	String TOUCHE = "touche";
	String MORT = "mort";
	int GAUCHE = 0;
	int DROITE = 1;
	// arène et murs
	int HAUTEURARENE = 600;
	int LARGEURARENE = 800;
	int HAUTEURMUR = 35;
	int LARGEURMUR = 34;
	int NBMURS = 20;
	// ordres
	String AJOUTMUR = "ajout mur";
	String AJOUTPANELMURS = "ajout panel murs";
	String AJOUTJLABELJEU = "ajout jlabel jeu";
	String MODIFPANELJEU = "modif panel jeu";
	String AJOUTPHRASE = "ajout phrase";
	String MODIFTCHAT = "modif tchat";
}