package player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.swing.JTextArea;
import java.awt.Cursor;
import java.awt.Rectangle;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

import view.FenetreNbJoueurs;
import view.FenetreNomJoueurs;

import java.awt.GridLayout;
import java.awt.Component;

public class DrinkAlarm {

	// CONSTANTES CHEMINS DES FICHIERS
	private final String HORNTUNE = "res/Horn_Tune.wav";
	private final String DANSE_DU_VENTRE = "res/Danse_du_ventre.wav";
	private final String CALL_TO_ARMS = "res/Call_to_Arms.wav";
	private final String TOUR_DE_FRANCE = "res/Tour _de_France.wav";
	private final String AIR_RAID_2 = "res/Air_Raid_2.wav";
	private final String POUET_POUET = "res/Pouet_pouet.wav";
	private final String WOW = "res/Wow.wav";

	// CONSTANTES MODE DE FONCTIONNEMENT
	// [0] = CHANCES DE BOIRE / [1] = CHANCES DE BOIRE UNE GORGEE / [2] =
	// CHANCES DE BOIRE POUR LE BIZUT / [3] = CHANCES DE BOIRE SAUF LE ROI
	// [4] = CHANCES DE BOIRE PLUSIEURS GORGEES / [5] = CHANCES DE BOIRE UN CUL
	// SEC / [6] = CHANCES DE BOIRE APRES UN FREEZE
	private Integer[] MODE_DE_JEU = null;
	// MODE SOFT
	private final Integer MODE_SOFT[] = { 20, 40, 20, 20, 10, 5, 5 };
	// MODE MEDIUM
	private final Integer MODE_MEDIUM[] = { 30, 37, 23, 22, 8, 8, 2 };
	// MODE HARD
	private final Integer MODE_HARD[] = { 50, 30, 20, 5, 14, 30, 1 };
	// MODE LEGEND
	private final Integer MODE_LEGEND[] = { 80, 50, 0, 13, 35, 2 };
	private boolean premierPassage = true;

	// DESCRIPTION CHAQUE ACTION
	private String[] uneGorgee = new String[2];
	private String[] bizut = new String[2];
	private String[] roi = new String[2];
	private String[] nbGorgees = new String[2];
	private String[] culSec = new String[2];
	private String[] freeze = new String[2];
	private String[] debut = new String[2];

	// TABLEAU DES PARTICIPANTS
	//private Collection<String> participants;
	private final int NB_MAX = 20;
	private String[] tabParticipants;

	private Thread audioPlayerThread;
	private AudioPlayer player;
	private Action play;
	private Action stop;
	private JFrame frame;
	private JPanel contentPane;
	private File audioFile;
	private JLabel title;
	private JTextArea logs;
	private Date now;

	// Personnalisation d'une date
	DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
			DateFormat.MEDIUM, DateFormat.MEDIUM);
	private JButton modeMedium;
	private JButton modeHard;
	private JButton modeLegend;
	private JButton modeSoft;
	private JPanel voletDroitePanel;
	private JLabel minuteChronoLabel;
	private JLabel valeurChronoLabel;
	private JLabel prefixeChronoLabel;
	private JPanel chronoPanel;
	private JLabel descriptionChronoLabel;
	private JButton addPlayerBtn;
	private JButton delPlayerBtn;
	private JButton updatePlayerBtn;
	private JPanel GestionJoueursPanel;
	private JPanel panel;
	private JPanel panel_1;

	public DrinkAlarm(String[] participants) {
		// PERSONNALISATION ACTIONS
		uneGorgee[0] = HORNTUNE;
		uneGorgee[1] = "... Olé !!!! Une gorgée pour TOUT le monde";

		bizut[0] = DANSE_DU_VENTRE;
		bizut[1] = "Une gorgée pour le BIZUT";

		culSec[0] = AIR_RAID_2;
		culSec[1] = "CUL SEC !";

		freeze[0] = POUET_POUET;
		freeze[1] = "FREEZE";

		roi[0] = CALL_TO_ARMS;
		roi[1] = "Tout le monde boit, sauf le ROI";

		nbGorgees[0] = TOUR_DE_FRANCE;
		nbGorgees[1] = " gorgée(s) pour le bizut du ROI";

		debut[0] = WOW;
		debut[1] = "";

		player = new AudioPlayer();
		frame = new JFrame("DrinkAlarm");
		contentPane = new JPanel(new BorderLayout());
		title = new JLabel("Bienvenue dans le DRINKAlarm !");
		audioPlayerThread = new Thread(player);

		contentPane.setPreferredSize(new Dimension(700, 300));
		// playAction();
		stopAction();

		logs = new JTextArea();
		logs.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		logs.setWrapStyleWord(true);
		logs.setRows(8);
		logs.setText("Le DRINKAlarm est en sommeil...");
		logs.setEditable(false);
		contentPane.add(logs, BorderLayout.CENTER);
		// Ajout de la scrollbar
		JScrollPane scrollPane = new JScrollPane(logs,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, null);

		JToolBar toolbar = new JToolBar();

		modeSoft = new JButton();
		modeSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MODE_DE_JEU = MODE_SOFT;
				modeSoft.setBackground(new Color(0, 0, 0));
				modeMedium.setBackground(new Color(240, 240, 240));
				modeHard.setBackground(new Color(240, 240, 240));
				modeLegend.setBackground(new Color(240, 240, 240));
			}
		});
		modeSoft.setIcon(new ImageIcon("SOFT.png"));
		toolbar.add(modeSoft);

		modeMedium = new JButton();
		modeMedium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modeSoft.setBackground(new Color(240, 240, 240));
				modeMedium.setBackground(new Color(0, 0, 0));
				modeHard.setBackground(new Color(240, 240, 240));
				modeLegend.setBackground(new Color(240, 240, 240));
				MODE_DE_JEU = MODE_MEDIUM;
			}
		});
		modeMedium.setIcon(new ImageIcon("MEDIUM.png"));
		toolbar.add(modeMedium);

		modeHard = new JButton();
		modeHard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MODE_DE_JEU = MODE_HARD;
				modeSoft.setBackground(new Color(240, 240, 240));
				modeMedium.setBackground(new Color(240, 240, 240));
				modeHard.setBackground(new Color(0, 0, 0));
				modeLegend.setBackground(new Color(240, 240, 240));
			}
		});
		modeHard.setIcon(new ImageIcon("HARD.png"));
		toolbar.add(modeHard);

		modeLegend = new JButton();
		modeLegend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MODE_DE_JEU = MODE_LEGEND;
				modeSoft.setBackground(new Color(240, 240, 240));
				modeMedium.setBackground(new Color(240, 240, 240));
				modeHard.setBackground(new Color(240, 240, 240));
				modeLegend.setBackground(new Color(0, 0, 0));
			}
		});
		modeLegend.setIcon(new ImageIcon("LEGEND.png"));
		toolbar.add(modeLegend);
		toolbar.add(stop);
		toolbar.add(title);

		// CHOIX DU MODE D'UTILISATION PAR DEFAUT
		MODE_DE_JEU = MODE_MEDIUM;

		// Mise en place chronomètre
		voletDroitePanel = new JPanel();
		contentPane.add(voletDroitePanel, BorderLayout.EAST);
		voletDroitePanel.setLayout(new BoxLayout(voletDroitePanel, BoxLayout.Y_AXIS));

		descriptionChronoLabel = new JLabel("Réveil possible dans... ");
		descriptionChronoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		descriptionChronoLabel.setForeground(Color.GRAY);
		descriptionChronoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		voletDroitePanel.add(descriptionChronoLabel);

		chronoPanel = new JPanel();
		FlowLayout fl_chronoPanel = (FlowLayout) chronoPanel.getLayout();
		voletDroitePanel.add(chronoPanel);

		minuteChronoLabel = new JLabel("1");
		minuteChronoLabel.setForeground(new Color(0, 128, 0));
		chronoPanel.add(minuteChronoLabel);
		minuteChronoLabel.setFont(new Font("Tahoma", Font.PLAIN, 42));

		prefixeChronoLabel = new JLabel(":");
		prefixeChronoLabel.setForeground(new Color(0, 128, 0));
		chronoPanel.add(prefixeChronoLabel);
		prefixeChronoLabel.setFont(new Font("Tahoma", Font.PLAIN, 42));

		valeurChronoLabel = new JLabel("00");
		valeurChronoLabel.setForeground(new Color(0, 128, 0));
		chronoPanel.add(valeurChronoLabel);
		valeurChronoLabel.setFont(new Font("Tahoma", Font.PLAIN, 42));
		
		panel = new JPanel();
		voletDroitePanel.add(panel);
		
		panel_1 = new JPanel();
		voletDroitePanel.add(panel_1);
		
		GestionJoueursPanel = new JPanel();
		voletDroitePanel.add(GestionJoueursPanel);
		GestionJoueursPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		addPlayerBtn = new JButton("Ajouter un joueur");
		GestionJoueursPanel.add(addPlayerBtn);
		addPlayerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		addPlayerBtn.setIcon(new ImageIcon("edit_add.png"));
		addPlayerBtn.setHorizontalAlignment(SwingConstants.CENTER);
		addPlayerBtn.addActionListener(new ActionListenerBoutons());
		
		updatePlayerBtn = new JButton("Modifier un joueur");
		GestionJoueursPanel.add(updatePlayerBtn);
		updatePlayerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		updatePlayerBtn.setIcon(new ImageIcon("update.png"));
		updatePlayerBtn.setHorizontalAlignment(SwingConstants.CENTER);
		updatePlayerBtn.addActionListener(new ActionListenerBoutons());
		
		delPlayerBtn = new JButton("Supprimer un joueur");
		GestionJoueursPanel.add(delPlayerBtn);
		delPlayerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		delPlayerBtn.setIcon(new ImageIcon("del.png"));
		delPlayerBtn.setHorizontalAlignment(SwingConstants.CENTER);
		delPlayerBtn.addActionListener(new ActionListenerBoutons());
		
		modeMedium.setBackground(new Color(0, 0, 0));

		contentPane.add(toolbar, BorderLayout.NORTH);
		contentPane.setAutoscrolls(true);
		frame.setContentPane(contentPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabParticipants = participants;
		
		//frame.pack();
		frame.setSize(800, 400);
		frame.setVisible(true);
		frame.repaint();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		//participants = new ArrayList<String>();
		// LECTURE D'UN FICHIER TEXTE
		// Récupération du fichier
		/*final File file = new File("res/participants.txt");

		// Création d'un flux de lecture de fichier
		InputStream fileReader;
		try {
			fileReader = new FileInputStream(file);

			// Création d'un lecteur
			Reader utfReader;
			try {
				utfReader = new InputStreamReader(fileReader, "ISO-8859-1");

				// Création d'un lecteur plus intelligent. Il lira ligne par
				// ligne
				// au lieu de caractère par caractère
				final BufferedReader input = new BufferedReader(utfReader);

				// Le séparateur de fin de ligne, suivant qu'on soit sous Linux
				// ou
				// Windows, il diffère
				final String separator = System.getProperty("line.separator");

				// L'objet qui contiendra le contenu du fichier
				final StringBuilder builder = new StringBuilder();

				String line = null;
				try {
					while ((line = input.readLine()) != null) {
						builder.append(line);
						participants.add(line);
						builder.append(separator);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// System.out.println(builder.toString());
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		
		//playAction(debut, null);
	}

	public void stopAction() {
		stop = new AbstractAction() {

			public void actionPerformed(ActionEvent arg0) {
				// Thread.currentThread().interrupt();
				player.stop();
			}
		};
		stop.putValue(Action.SMALL_ICON, new ImageIcon("stop.png"));
	}

	public void playAction(String[] event, String infoSup) {
		// System.out.println(MODE_DE_JEU.toString());
		audioFile = new File(event[0]);
		player.setFile(audioFile);
		now = new Date();

		player.init();

		// Mise à jour de la TextArea
		// Ajout d'infos supplémentaires
		int randomNbGorgees = 0;
		int randomParticipants = 0;
		String rajout = "";
		if (infoSup != null) {
			randomParticipants = (int) (Math.random() * ((tabParticipants.length - 1) * 10000000 / 10000000));
			// System.out.println("participant : " + randomParticipants);
			rajout = " : " + tabParticipants[randomParticipants];
		}

		if (event[0].contains("Wow") == false) {
			if (logs.getText().contains("Le DRINKAlarm"))
				if (event[0].contains("Tour _de_France")) {
					randomNbGorgees = (int) (Math.random() * (6 * 10000000 / 10000000));
					logs.setText(mediumDateFormat.format(now) + "   -->\t"
							+ randomNbGorgees + event[1] + rajout);
				} else {
					logs.setText(mediumDateFormat.format(now) + "   -->\t"
							+ event[1] + rajout);
				}
			else {
				if (event[0].contains("Tour _de_France")) {
					randomNbGorgees = (int) (Math.random() * (6 * 10000000 / 10000000));
					logs.insert(mediumDateFormat.format(now) + "   -->\t"
							+ randomNbGorgees + event[1] + rajout + "\n", 0);
				} else {
					logs.insert(mediumDateFormat.format(now) + "   -->\t"
							+ event[1] + rajout + "\n", 0);
				}
			}
		}

		contentPane.paintAll(contentPane.getGraphics());
		contentPane.repaint();
		audioPlayerThread = new Thread(player);
		audioPlayerThread.start();

		player.getLine().addLineListener(new LineListener() {

			public void update(LineEvent le) {
				if (le.getType() == LineEvent.Type.STOP) {
					play.setEnabled(true);
					stop.setEnabled(false);

				}
				if (le.getType() == LineEvent.Type.START) {
					play.setEnabled(false);
					stop.setEnabled(true);

				}
			}
		});

	}

	//
	// Génération aléatoire d'une alarme
	//
	public void waitDrink() {
		frame.repaint();
		
		Integer randomNumber = -1;
		Collection<Integer> randomAlarmNumbers = new ArrayList<Integer>();

		while (true) {
			try {
				// On attend 1 seconde
				// Déclenchement du chrono
				int cpt = 0;
				Integer valSecondes = 59;

				while (cpt <= 59) {
					Thread.sleep(1000);
					if (cpt == 0) {
						minuteChronoLabel.setText("0");

						// remise par défaut de l'affichage
						minuteChronoLabel.setForeground(new Color(0, 128, 0));
						minuteChronoLabel.setFont(new Font("Tahoma",
								Font.PLAIN, 42));

						prefixeChronoLabel.setForeground(new Color(0, 128, 0));
						prefixeChronoLabel.setFont(new Font("Tahoma",
								Font.PLAIN, 42));

						valeurChronoLabel.setForeground(new Color(0, 128, 0));
						valeurChronoLabel.setFont(new Font("Tahoma",
								Font.PLAIN, 42));
					} else if (cpt == 59) {
						valSecondes = 0;
					} else {
						valSecondes -= 1;
						if (cpt == 29) {
							minuteChronoLabel.setForeground(new Color(255, 140,
									0));
							prefixeChronoLabel.setForeground(new Color(255,
									140, 0));
							valeurChronoLabel.setForeground(new Color(255, 140,
									0));
						}
						if (cpt == 49) {
							minuteChronoLabel
									.setForeground(new Color(255, 0, 0));
							minuteChronoLabel.setFont(new Font("Tahoma",
									Font.BOLD, 42));
							prefixeChronoLabel.setForeground(new Color(255, 0,
									0));
							prefixeChronoLabel.setFont(new Font("Tahoma",
									Font.BOLD, 42));
							valeurChronoLabel
									.setForeground(new Color(255, 0, 0));
							valeurChronoLabel.setFont(new Font("Tahoma",
									Font.BOLD, 42));
						}
					}
					if (valSecondes < 10) {
						valeurChronoLabel.setText("0" + valSecondes.toString());
					} else {
						valeurChronoLabel.setText(valSecondes.toString());
					}

					cpt += 1;
				}
				//Thread.sleep(60000);

				if (MODE_DE_JEU == MODE_LEGEND && premierPassage) {
					playAction(culSec, null);
					premierPassage = false;
				} else {
					randomNumber = (int) (Math.random() * 100000000000.0 / 1000000000.0);
					System.out.println(randomNumber);

					if (audioPlayerThread.isAlive() == false) {
						if (testDrink(randomAlarmNumbers, MODE_DE_JEU[0],
								randomNumber)) {
							// System.out.println("on peut boire !");
							// répartition des nombres aléatoires
							int i = 0;
							int nb;
							Collection<Integer> randomUneGorgee = new ArrayList<Integer>();
							Collection<Integer> randomBizut = new ArrayList<Integer>();
							Collection<Integer> randomRoi = new ArrayList<Integer>();
							Collection<Integer> randomNbGorgees = new ArrayList<Integer>();
							Collection<Integer> randomCulSec = new ArrayList<Integer>();
							Collection<Integer> randomFreeze = new ArrayList<Integer>();

							// une gorgée
							while (i < MODE_DE_JEU[1]) {
								nb = (int) (Math.random() * 10000000000000.0 / 100000000000.0);
								if (randomUneGorgee.contains(nb) == false) {
									randomUneGorgee.add(nb);
									i++;
								}
							}
							if (randomUneGorgee.contains(randomNumber)) {
								playAction(uneGorgee, null);

								// bizut
							} else {
								i = 0;
								while (i < MODE_DE_JEU[2]) {
									nb = (int) (Math.random() * 10000000000000.0 / 100000000000.0);
									if (randomBizut.contains(nb) == false
											&& randomUneGorgee.contains(nb) == false) {
										randomBizut.add(nb);
										i++;
									}
								}
								if (randomBizut.contains(randomNumber)) {
									playAction(bizut, "bizut");
									// roi
								} else {
									i = 0;
									while (i < MODE_DE_JEU[3]) {
										nb = (int) (Math.random() * 10000000000000.0 / 100000000000.0);
										if (randomBizut.contains(nb) == false
												&& randomUneGorgee.contains(nb) == false
												&& randomRoi.contains(nb) == false) {
											randomRoi.add(nb);
											i++;
										}
									}
									if (randomRoi.contains(randomNumber)) {
										playAction(roi, "roi");
										// demi cul sec
									} else {
										i = 0;
										while (i < MODE_DE_JEU[4]) {
											nb = (int) (Math.random() * 10000000000000.0 / 100000000000.0);
											if (randomBizut.contains(nb) == false
													&& randomUneGorgee
															.contains(nb) == false
													&& randomRoi.contains(nb) == false
													&& randomNbGorgees
															.contains(nb) == false) {
												randomNbGorgees.add(nb);
												i++;
											}
										}
										if (randomNbGorgees
												.contains(randomNumber)) {
											playAction(nbGorgees, "roi");
											// cul sec
										} else {
											i = 0;
											while (i < MODE_DE_JEU[5]) {
												nb = (int) (Math.random() * 10000000000000.0 / 100000000000.0);
												if (randomBizut.contains(nb) == false
														&& randomUneGorgee
																.contains(nb) == false
														&& randomRoi
																.contains(nb) == false
														&& randomNbGorgees
																.contains(nb) == false
														&& randomCulSec
																.contains(nb) == false) {
													randomCulSec.add(nb);
													i++;
												}
											}
											if (randomCulSec
													.contains(randomNumber)) {
												playAction(culSec, null);
												// freeze
											} else {
												i = 0;
												while (i < MODE_DE_JEU[6]) {
													nb = (int) (Math.random() * 10000000000000.0 / 100000000000.0);
													if (randomBizut
															.contains(nb) == false
															&& randomUneGorgee
																	.contains(nb) == false
															&& randomRoi
																	.contains(nb) == false
															&& randomNbGorgees
																	.contains(nb) == false
															&& randomCulSec
																	.contains(nb) == false
															&& randomFreeze
																	.contains(nb) == false) {
														randomFreeze.add(nb);
														i++;
													}
												}
												if (randomFreeze
														.contains(randomNumber)) {
													playAction(freeze, null);
												} else {
													// title.setText("Le DRINKAlarm est en sommeil...");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				stopAction();
			}
		}
	}

	public boolean testDrink(Collection<Integer> collection, Integer proba,
			Integer randomNumber) {
		boolean retour = false;
		int nb = 0;

		collection.clear();
		// Génération aléatoire de n nombres
		int i = 0;
		while (i < proba) {
			nb = ((int) (Math.random() * 10000000000000.0 / 100000000000.0));
			if (collection.contains(nb) == false) {
				collection.add(nb);
				i++;
			}
		}
		// On regarde si le nombre généré est contenu dans la
		// collection
		for (Integer element : collection) {
			if (element == randomNumber) {
				retour = true;
			}
		}
		return retour;
	}

	public void afficheParticipants() {
		for (int i = 0; i < tabParticipants.length; i++) {
			System.out.println(tabParticipants[i]);
		}
	}
	
	class ActionListenerBoutons implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addPlayerBtn) {
				FenetreNomJoueurs ajoutJoueur = new FenetreNomJoueurs(1);
			}
			if (e.getSource() == updatePlayerBtn) {
				
			}
			if (e.getSource() == delPlayerBtn) {
				
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		FenetreNbJoueurs fenetre = new FenetreNbJoueurs();
		
//		String[] part = {"Frank", "Joseph", "Eric"};
//		DrinkAlarm drink = new DrinkAlarm(part);
//		drink.afficheParticipants();
//		drink.waitDrink();
	}
}
