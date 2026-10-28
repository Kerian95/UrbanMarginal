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

public class ChoixJoueur extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPseudo;
	private Arene frmArene;
	
	/**
	 * Clic sur la flèche précédent
	 */
	private void lblPrecedent_clic() {
		System.out.println("précédent");
	}

	/**
	 * Clic sur la flèche suivant
	 */
	private void lblSuivant_clic() {
		System.out.println("suivant");
	}

	/**
	 * Clic sur GO : ouvre l'arène
	 */
	private void lblGo_clic() {
		this.frmArene = new Arene();
		this.frmArene.setVisible(true);
		this.dispose();
	}
	
	/**
	 * Create the frame.
	 */
	public ChoixJoueur() {
		setTitle("Choice");
		setBackground(new Color(240, 240, 240));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
		this.pack();
						
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
		});
		
		JLabel lblSuivant = new JLabel("");
		lblSuivant.setBounds(289, 142, 46, 48);
		contentPane.add(lblSuivant);
		lblSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSuivant_clic();
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
		});
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		String chemin = "fonds/fondchoix.jpg";
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));
		contentPane.add(lblFond);
		
	}
	
}
