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
	String ACTION = "action";
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
	String CHEMINBOULES = "boules" + CHEMINSEPARATOR;
	String BOULE = CHEMINBOULES + "boule.gif";
	// sons
	String CHEMINSONS = "sons" + CHEMINSEPARATOR;
	String SONWELCOME = CHEMINSONS + "welcome.wav";
	String SONPRECEDENT = CHEMINSONS + "precedent.wav";
	String SONSUIVANT = CHEMINSONS + "suivant.wav";
	String SONGO = CHEMINSONS + "go.wav";
	String SONFIGHT = CHEMINSONS + "fight.wav";
	String SONHURT = CHEMINSONS + "hurt.wav";
	String SONDEATH = CHEMINSONS + "death.wav";
	String[] SON = { SONFIGHT, SONHURT, SONDEATH };
	int FIGHT = 0;
	int HURT = 1;
	int DEATH = 2;
	// personnages
	int MAXPERSO = 3;
	int HAUTEURPERSO = 44;
	int LARGEURPERSO = 39;
	int HAUTEURMESSAGE = 8;
	int MARGEMESSAGE = 10;
	String MARCHE = "marche";
	String TOUCHE = "touche";
	String MORT = "mort";
	int NBETAPESMARCHE = 4;
	int NBETAPESTOUCHE = 2;
	int NBETAPESMORT = 2;
	int GAUCHE = 0;
	int DROITE = 1;
	int PAS = 10;
	// boule
	int LARGEURBOULE = 17;
	int HAUTEURBOULE = 17;
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
	String JOUESON = "joue son";
}