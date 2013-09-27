package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import player.DrinkAlarm;

public class FenetreNomJoueurs extends JFrame {
	// Déclaration des variables d'instance
	private JLabel descriptionLabel;

	private JPanel panelJoueur;
	private JLabel labelJoueur;
	private JTextField inputNomJoueur;
	// Collection de JTextfield
	private ArrayList<JTextField> txtFieldsCollec = new ArrayList<JTextField>();

	private JPanel panelBtns;
	private JButton retourBtn;
	private JButton okBtn;
	private JButton cancelBtn;

	// NOMBRE DE PARTICIPANTS
	private int nbJoueurs;
	private final int WIDTH_PANEL = 350;
	private final int HEIGHT_PANEL = 50;

	// Constructeurs
	public FenetreNomJoueurs() {

	}

	public FenetreNomJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;

		// Personnalisation de la fenêtre
		setType(Type.POPUP);
		setTitle("DrinkAlarm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Initialisation de la fenêtre
		descriptionLabel = new JLabel("Saisir le nom de chaque joueur : ");
		// Initialisation des boutons de la fenêtre
		panelBtns = new JPanel();
		panelBtns.setSize(WIDTH_PANEL, HEIGHT_PANEL);
		panelBtns.setLayout(new FlowLayout());
		ImageIcon icon = new ImageIcon("fleche-gauche.png");
		retourBtn = new JButton(icon);
		retourBtn.addActionListener(new MonActionListener());
		okBtn = new JButton("OK");
		okBtn.addActionListener(new MonActionListener());
		cancelBtn = new JButton("Annuler");
		cancelBtn.addActionListener(new MonActionListener());

		panelBtns.add(retourBtn);
		panelBtns.add(okBtn);
		panelBtns.add(cancelBtn);

		// ADAPTATION DE L'AFFICHAGE EN FONCTION DU NOMBRE DE PARTICIPANTS
		Dimension dimFenetre;

		if (nbJoueurs <= 10) {
			// Dimensionnement et placement de la fenêtre
			dimFenetre = new Dimension((int) WIDTH_PANEL, (int) (nbJoueurs
					* HEIGHT_PANEL + HEIGHT_PANEL + 80));

			// Choix de la mise en forme
			getContentPane()
					.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 15));

			// Ajout des composants
			getContentPane().add(descriptionLabel);

			// Initialisation des panels des noms de joueurs
			for (int i = 1; i <= nbJoueurs; i++) {
				panelJoueur = new JPanel();
				panelJoueur.setSize(WIDTH_PANEL, HEIGHT_PANEL);
				labelJoueur = new JLabel("Joueur " + i + " :");
				inputNomJoueur = new JTextField(20);
				txtFieldsCollec.add(inputNomJoueur);
				inputNomJoueur.addKeyListener(new KeyPress());

				panelJoueur.setLayout(new FlowLayout());
				panelJoueur.add(labelJoueur);
				panelJoueur.add(inputNomJoueur);

				getContentPane().add(panelJoueur);
			}

			// Ajout des éléments à la fenêtre
			getContentPane().add(panelBtns);
		} else {
			// Dimensionnement et placement de la fenêtre
			int multiplicateur = nbJoueurs / 2;
			if (nbJoueurs % 2 != 0) {
				multiplicateur++;
			}
			// System.out.println("multiplicateur : " + multiplicateur);

			dimFenetre = new Dimension(2 * WIDTH_PANEL, (int) (multiplicateur
					* HEIGHT_PANEL + 40));

			// Choix de la mise en forme
			getContentPane().setLayout(new BorderLayout(2, 15));

			// Ajout des composants
			getContentPane().add(descriptionLabel, BorderLayout.NORTH);
			descriptionLabel.setLocation(20, descriptionLabel.getLocation().y);
			descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

			JPanel panelGlobalCentre = new JPanel();
			panelGlobalCentre.setLayout(new FlowLayout());
			// Initialisation des panels des noms de joueurs
			for (int i = 1; i <= nbJoueurs; i++) {
				panelJoueur = new JPanel();
				panelJoueur.setSize(WIDTH_PANEL, HEIGHT_PANEL);
				labelJoueur = new JLabel("Joueur " + i + " :");
				inputNomJoueur = new JTextField(20);
				txtFieldsCollec.add(inputNomJoueur);
				inputNomJoueur.addKeyListener(new KeyPress());

				panelJoueur.setLayout(new FlowLayout());
				panelJoueur.add(labelJoueur);
				panelJoueur.add(inputNomJoueur);

				panelGlobalCentre.add(panelJoueur);
			}

			// Ajout des éléments à la fenêtre
			getContentPane().add(panelGlobalCentre, BorderLayout.CENTER);
			getContentPane().add(panelBtns, BorderLayout.SOUTH);
		}

		// Calibrage de la fenêtre
		this.setMinimumSize(dimFenetre);
		this.setMaximumSize(dimFenetre);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		// Affichage de la fenêtre
		this.setVisible(true);
	}

	public void close() {
		this.dispose();
	}

	// Gestion de clic sur les boutons
	class MonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == retourBtn) {
				// clic sur le bouton retour
				close();
				FenetreNbJoueurs nouvelleFenetre = new FenetreNbJoueurs();
			}
			if (e.getSource() == okBtn) {
				// clic sur le bouton ok
				ok();
			}
			if (e.getSource() == cancelBtn) {
				// clic sur le bouton annuler
				System.exit(0);
			}
		}

	}

	public void ok() {
		if (verificationSaisi()) {

			close();
			// préparation du tableau des joueurs
			String[] tabJoueurs = new String[this.nbJoueurs];
			remplissageTableau(tabJoueurs);

			// lancement du jeu
			DrinkAlarm drinkAlarm = new DrinkAlarm(tabJoueurs);
			drinkAlarm.afficheParticipants();
			drinkAlarm.waitDrink();
		}
	}

	public void remplissageTableau(String[] tableau) {
		int i = 0;
		for (JTextField element : txtFieldsCollec) {
			tableau[i] = element.getText();
			i++;
		}
	}

	class KeyPress implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == 10) {
				ok();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public boolean verificationSaisi() {
		int i = 1;
		for (JTextField element : txtFieldsCollec) {
			if (element.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this,
						"Veuillez saisir le nom du joueur " + i, "Nom vide",
						JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				// Test de doublon
				int[] retourDoublon = doublon();
				if (retourDoublon[0] != -1) {
					// System.out.println("doublon");
					JOptionPane.showMessageDialog(this,
							"Veuillez saisir un nom différent pour le joueur "
									+ retourDoublon[1],
							"Doublon détecté (joueur " + retourDoublon[0]
									+ " et joueur " + retourDoublon[1] + ")",
							JOptionPane.ERROR_MESSAGE);
					return false;
				} else {
					// vérification de la validité d'un nom à l'aide de chaînes
					// pré-formatées
					Pattern pattern;
					Matcher matcher;

					pattern = Pattern.compile("^[a-zA-Z_-éè]{3,15}$");
					matcher = pattern.matcher(element.getText());
					if (matcher.matches()) {
						// cas où le nom correspond
					} else {
						JOptionPane.showMessageDialog(this,
								"Veuillez saisir un nom avec des caractères valides (joueur "
										+ i + ")", "Saisie incorrecte",
								JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			}
			i++;
		}
		return true;
	}

	public int[] doublon() {
		int[] elementsDoublon = new int[2];
		elementsDoublon[0] = -1;
		elementsDoublon[1] = -1;

		// i = élément comparé
		int i = 0;
		while (i < txtFieldsCollec.size()) {
			// j = élément comparant
			int j = i + 1;
			while (j < txtFieldsCollec.size()) {
				if (txtFieldsCollec.get(i).getText()
						.compareTo(txtFieldsCollec.get(j).getText()) == 0) {
					elementsDoublon[0] = i + 1;
					elementsDoublon[1] = j + 1;
					return elementsDoublon;
				}
				j++;
			}
			i++;
		}
		return elementsDoublon;
	}

	// TEST
	public static void main(String[] args) {
		FenetreNomJoueurs fenetre = new FenetreNomJoueurs(4);

		// String[] part = {"Frank", "Joseph", "Eric"};
		// DrinkAlarm drink = new DrinkAlarm(part);
		// drink.afficheParticipants();
		// drink.waitDrink();
	}
}
