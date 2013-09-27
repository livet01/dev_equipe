package view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import player.DrinkAlarm;

public class FenetreNbJoueurs extends JFrame {
	//pop up
	private JFrame frame = new JFrame();
	
	private JTextField textField;
	private JButton valBtn;
	private JButton cancelBtn;
	
	//LIMITE MAXI DU NOMBRE DE JOUEURS
	private final int NBMAX = 20;


	// Constructeurs
	public FenetreNbJoueurs() {
		setType(Type.POPUP);
		setTitle("DrinkAlarm");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 2, 15));
		
		JLabel saisieLabel = new JLabel("Entrez le nombre de joueurs : ");
		saisieLabel.setHorizontalAlignment(SwingConstants.CENTER);
		saisieLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().add(saisieLabel);
		
		textField = new JTextField(20);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.addKeyListener(new KeyPress());
		
		JPanel panelBtns = new JPanel();
		getContentPane().add(panelBtns);
		
		valBtn = new JButton("Valider");
		panelBtns.add(valBtn);
		
		cancelBtn = new JButton("Annuler");
		panelBtns.add(cancelBtn);
		
		//Affichage de la fenêtre
		this.show();
		this.setSize(350, 150);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		//Gestion des évènements : clic sur les boutons
		valBtn.addActionListener(new MonActionListener());
		cancelBtn.addActionListener(new MonActionListener());
	}
	
	class MonActionListener implements ActionListener {
				
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			// TODO Auto-generated method stub
			if (e.getSource() == valBtn) {
				//vérification du résultat saisi
				verificationSaisi();
			}
			if (e.getSource() == cancelBtn) {
				System.exit(0);
			}
			
		}
		
	}
	
	class KeyPress implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == 10) {
				verificationSaisi();
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
	
	public void verificationSaisi() {
		int nbJoueurs = 0;
		
		if (textField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Vous n'avez rien saisi...",
					"Nombre de joueurs nul", JOptionPane.WARNING_MESSAGE);
		}
		else {
			try {
				nbJoueurs = Integer.parseInt(textField.getText());

				if (nbJoueurs != 0) {
					//vérification du nombre de joueurs (ne doit pas dépasser 20)
					if (nbJoueurs <= NBMAX) {
						//Saisie des noms des joueurs
						this.dispose();
						FenetreNomJoueurs fenetre = new FenetreNomJoueurs(nbJoueurs);
					}
					else {
						JOptionPane.showMessageDialog(frame,
								"Vous avez dépassé le nombre de joueurs autorisé (<= 20)", "Nombre trop grand",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (NumberFormatException f) {
				if (!(f.toString().endsWith("\"\""))) {
					JOptionPane.showMessageDialog(frame,
							"Veuillez entrer un nombre entier", "Nombre incorrect",
							JOptionPane.ERROR_MESSAGE);
				}
				textField.selectAll();
			}
		}
	}
	
	// TEST
	public static void main(String[] args) {
		FenetreNbJoueurs fenetre = new FenetreNbJoueurs();
		
//		String[] part = {"Frank", "Joseph", "Eric"};
//		DrinkAlarm drink = new DrinkAlarm(part);
//		drink.afficheParticipants();
//		drink.waitDrink();
	}
}
