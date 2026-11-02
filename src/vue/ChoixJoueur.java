package vue;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import controleur.Controle;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.JOptionPane;

public class ChoixJoueur extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPseudo;
	private Arene frmArene;
	private Controle controle;
	private JLabel lblPersonnage;
	private int numPerso = 1;
	private static final int MAXPERSO = 3;

	/**
	 * Clic sur la flèche précédent
	 */
	private void lblPrecedent_clic() {
		this.numPerso--;
		if (this.numPerso < 1) {
			this.numPerso = MAXPERSO;
		}
		this.affichePerso();
	}

	/**
	 * Clic sur la flèche suivant
	 */
	private void lblSuivant_clic() {
		this.numPerso++;
		if (this.numPerso > MAXPERSO) {
			this.numPerso = 1;
		}
		this.affichePerso();
	}

	/**
	 * Clic sur GO : ouvre l'arène
	 */
	private void lblGo_clic() {
		if (this.txtPseudo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire");
			this.txtPseudo.requestFocus();
		} else {
			this.controle.evenementChoixJoueur(this.txtPseudo.getText(), this.numPerso);
		}
	}

	/**
	 * Affichage du personnage dans la zone prévue
	 */
	private void affichePerso() {
		String chemin = "personnages/perso" + this.numPerso + "marche1d1.gif";
		URL resource = getClass().getClassLoader().getResource(chemin);
		this.lblPersonnage.setIcon(new ImageIcon(resource));
	}

	/**
	 * Curseur normal
	 */
	private void sourisNormale() {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Curseur en forme de doigt
	 */
	private void sourisDoigt() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Create the frame.
	 */
	public ChoixJoueur(Controle controle) {
		setTitle("Choice");
		setBackground(new Color(240, 240, 240));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
		this.pack();
		lblPersonnage = new JLabel("");
		lblPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonnage.setBounds(144, 113, 118, 123);
		contentPane.add(lblPersonnage);

		txtPseudo = new JTextField();
		txtPseudo.setBounds(144, 244, 118, 20);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);

		JLabel lblPrecedent = new JLabel("");
		lblPrecedent.setBounds(62, 142, 46, 48);
		contentPane.add(lblPrecedent);
		lblPrecedent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblPrecedent_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});

		JLabel lblSuivant = new JLabel("");
		lblSuivant.setBounds(289, 142, 46, 48);
		contentPane.add(lblSuivant);
		lblSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSuivant_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});

		JLabel lblGo = new JLabel("");
		lblGo.setBounds(316, 201, 60, 63);
		contentPane.add(lblGo);
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblGo_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});

		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		String chemin = "fonds/fondchoix.jpg";
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));
		contentPane.add(lblFond);
		this.controle = controle;
		this.affichePerso();
	}
}
