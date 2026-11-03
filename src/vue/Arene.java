package vue;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

public class Arene extends JFrame implements Global {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSaisie;
	private JTextArea txtChat;
	/**
	 * panel contenant les murs
	 */
	private JPanel jpnMurs;
	/**
	 * panel contenant les joueurs et les boules
	 */
	private JPanel jpnJeu;
	/**
	 * instance du contrôleur
	 */
	private Controle controle;
	/**
	 * vrai si c'est l'arène d'un client
	 */
	private boolean client;
	/**
	 * sons du jeu (tir, blessé, mort)
	 */
	private Son[] lesSons = new Son[SON.length];

	/**
	 * @return le panel des murs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;
	}

	/**
	 * Ajoute le panel des murs reçu (côté client)
	 * 
	 * @param jpnMurs panel des murs
	 */
	public void setJpnMurs(JPanel jpnMurs) {
		this.jpnMurs.add(jpnMurs);
		this.jpnMurs.repaint();
	}

	/**
	 * @return le panel du jeu
	 */
	public JPanel getJpnJeu() {
		return jpnJeu;
	}

	/**
	 * Remplace le contenu du panel du jeu (côté client)
	 * 
	 * @param jpnJeu panel du jeu
	 */
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll();
		this.jpnJeu.add(jpnJeu);
		this.jpnJeu.repaint();
		this.contentPane.requestFocus();
	}

	/**
	 * @return le texte du tchat
	 */
	public String getTxtChat() {
		return txtChat.getText();
	}

	/**
	 * Remplace le texte du tchat (côté client)
	 * 
	 * @param txtChat nouveau texte
	 */
	public void setTxtChat(String txtChat) {
		this.txtChat.setText(txtChat);
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}

	/**
	 * Ajoute un mur dans le panel des murs (côté serveur)
	 * 
	 * @param unMur le label du mur
	 */
	public void ajoutMurs(Object unMur) {
		jpnMurs.add((JLabel) unMur);
		jpnMurs.repaint();
	}

	/**
	 * Ajoute un label (joueur, message, boule) dans le panel du jeu (côté serveur)
	 * 
	 * @param jLabel label à ajouter
	 */
	public void ajoutJLabelJeu(JLabel jLabel) {
		this.jpnJeu.add(jLabel);
		this.jpnJeu.repaint();
	}

	/**
	 * Ajoute une phrase à la fin du tchat (côté serveur)
	 * 
	 * @param phrase phrase à ajouter
	 */
	public void ajoutTchat(String phrase) {
		this.txtChat.append(phrase + "\r\n");
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}

	/**
	 * Joue un son (côté client)
	 * 
	 * @param numSon numéro du son (FIGHT, HURT ou DEATH)
	 */
	public void joueSon(Integer numSon) {
		this.lesSons[numSon].play();
	}

	/**
	 * Touche appuyée dans la zone de saisie
	 * 
	 * @param e touche appuyée
	 */
	private void txtSaisie_KeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!this.txtSaisie.getText().equals("")) {
				this.controle.evenementArene(this.txtSaisie.getText());
				this.txtSaisie.setText("");
			}
			this.contentPane.requestFocus();
		}
	}

	/**
	 * Touche appuyée dans l'arène (flèches et barre d'espace)
	 * 
	 * @param e touche appuyée
	 */
	private void contentPane_KeyPressed(KeyEvent e) {
		int touche = -1;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_SPACE:
			touche = e.getKeyCode();
			break;
		}
		if (touche != -1) {
			this.controle.evenementArene(touche);
		}
	}

	/**
	 * Create the frame.
	 * 
	 * @param controle instance du contrôleur
	 * @param typeJeu  "client" ou "serveur"
	 */
	public Arene(Controle controle, String typeJeu) {
		this.client = typeJeu.equals(CLIENT);
		this.controle = controle;

		setTitle("Arena");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		if (this.client) {
			contentPane.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					contentPane_KeyPressed(e);
				}
			});
		}
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.getContentPane().setPreferredSize(new Dimension(LARGEURARENE, HAUTEURARENE + 25 + 140));
		this.pack();

		// panel du jeu (au-dessus des murs)
		jpnJeu = new JPanel();
		jpnJeu.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnJeu.setOpaque(false);
		jpnJeu.setLayout(null);
		contentPane.add(jpnJeu);

		// panel des murs
		jpnMurs = new JPanel();
		jpnMurs.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnMurs.setOpaque(false);
		jpnMurs.setLayout(null);
		contentPane.add(jpnMurs);

		// zone de saisie : uniquement pour un client
		if (this.client) {
			txtSaisie = new JTextField();
			txtSaisie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					txtSaisie_KeyPressed(e);
				}
			});
			txtSaisie.setBounds(0, HAUTEURARENE, LARGEURARENE, 25);
			contentPane.add(txtSaisie);
			txtSaisie.setColumns(10);
		}

		JScrollPane jspChat = new JScrollPane();
		jspChat.setBounds(0, HAUTEURARENE + 25, LARGEURARENE, 140);
		jspChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(jspChat);

		txtChat = new JTextArea();
		txtChat.setEditable(false);
		if (this.client) {
			// les flèches restent actives même si on a cliqué dans le tchat
			txtChat.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					contentPane_KeyPressed(e);
				}
			});
		}
		jspChat.setViewportView(txtChat);

		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		URL resource = getClass().getClassLoader().getResource(FONDARENE);
		lblFond.setIcon(new ImageIcon(resource));
		contentPane.add(lblFond);

		// chargement des sons (uniquement pour un client)
		if (this.client) {
			for (int k = 0; k < SON.length; k++) {
				lesSons[k] = new Son(getClass().getClassLoader().getResource(SON[k]));
			}
		}
	}
}