package vue;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import controleur.Global;

public class Arene extends JFrame implements Global {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSaisie;
	/**
	 * panel contenant les murs
	 */
	private JPanel jpnMurs;

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
	 * Ajoute un mur dans le panel des murs (côté serveur)
	 * 
	 * @param unMur le label du mur
	 */
	public void ajoutMurs(Object unMur) {
		jpnMurs.add((JLabel) unMur);
		jpnMurs.repaint();
	}

	/**
	 * Create the frame.
	 */
	public Arene() {
		setTitle("Arena");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.getContentPane().setPreferredSize(new Dimension(LARGEURARENE, HAUTEURARENE + 25 + 140));
		this.pack();

		jpnMurs = new JPanel();
		jpnMurs.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnMurs.setOpaque(false);
		jpnMurs.setLayout(null);
		contentPane.add(jpnMurs);

		txtSaisie = new JTextField();
		txtSaisie.setBounds(0, HAUTEURARENE, LARGEURARENE, 25);
		contentPane.add(txtSaisie);
		txtSaisie.setColumns(10);

		JScrollPane jspChat = new JScrollPane();
		jspChat.setBounds(0, HAUTEURARENE + 25, LARGEURARENE, 140);
		jspChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(jspChat);

		JTextArea txtChat = new JTextArea();
		jspChat.setViewportView(txtChat);

		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		URL resource = getClass().getClassLoader().getResource(FONDARENE);
		lblFond.setIcon(new ImageIcon(resource));
		contentPane.add(lblFond);
	}
}