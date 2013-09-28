package org.drinkalarm.presentation;


import org.drinkalarm.application.JeuControlleur;
import org.drinkalarm.metier.Joueur;
import org.drinkalarm.metier.Action;
import org.drinkalarm.util.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DrinkAlarmMainFrame{

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
    private JPanel panel;
    private JeuControlleur controlleur = new JeuControlleur();;
    public DrinkAlarmMainFrame() {
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
        Joueur j1 = new Joueur();
        j1.setNom("Toto");
        joueurs.add(j1);
        this.controlleur.initialiser(joueurs,"hard");

        player = new AudioPlayer();
        frame = new JFrame("DrinkAlarm");
        contentPane = new JPanel(new BorderLayout());
        title = new JLabel("Bienvenue dans le DRINKAlarm !");
        audioPlayerThread = new Thread(player);

        contentPane.setPreferredSize(new Dimension(700, 300));
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
                modeSoft.setBackground(new Color(0, 0, 0));
                modeMedium.setBackground(new Color(240, 240, 240));
                modeHard.setBackground(new Color(240, 240, 240));
                modeLegend.setBackground(new Color(240, 240, 240));
            }
        });
        modeSoft.setIcon(new ImageIcon("DrinkAlarm/SOFT.png"));
        toolbar.add(modeSoft);

        modeMedium = new JButton();
        modeMedium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                modeSoft.setBackground(new Color(240, 240, 240));
                modeMedium.setBackground(new Color(0, 0, 0));
                modeHard.setBackground(new Color(240, 240, 240));
                modeLegend.setBackground(new Color(240, 240, 240));
            }
        });
        modeMedium.setIcon(new ImageIcon("DrinkAlarm/MEDIUM.png"));
        toolbar.add(modeMedium);

        modeHard = new JButton();
        modeHard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                modeSoft.setBackground(new Color(240, 240, 240));
                modeMedium.setBackground(new Color(240, 240, 240));
                modeHard.setBackground(new Color(0, 0, 0));
                modeLegend.setBackground(new Color(240, 240, 240));
            }
        });
        modeHard.setIcon(new ImageIcon("DrinkAlarm/HARD.png"));
        toolbar.add(modeHard);

        modeLegend = new JButton();
        modeLegend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                modeSoft.setBackground(new Color(240, 240, 240));
                modeMedium.setBackground(new Color(240, 240, 240));
                modeHard.setBackground(new Color(240, 240, 240));
                modeLegend.setBackground(new Color(0, 0, 0));
            }
        });
        modeLegend.setIcon(new ImageIcon("DrinkAlarm/LEGEND.png"));
        toolbar.add(modeLegend);
        toolbar.add(title);

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

        modeMedium.setBackground(new Color(0, 0, 0));

        contentPane.add(toolbar, BorderLayout.NORTH);
        contentPane.setAutoscrolls(true);
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //frame.pack();
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.repaint();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.repaint();

        while (true) {
            // On attend 1 seconde
            // Déclenchement du chrono
            int cpt = 0;
            Integer valSecondes = 59;

            while (cpt <= 59) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
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

            if (audioPlayerThread.isAlive() == false) {
                Action action = this.controlleur.tour();
                if(action != null){
                    audioFile = new File(action.getCheminSon());
                    player.setFile(audioFile);
                    now = new Date();

                    player.init();

                    logs.insert(mediumDateFormat.format(now) + "   -->\t"
                            + action.play(joueurs) + "\n", 0);

                    contentPane.paintAll(contentPane.getGraphics());
                    contentPane.repaint();
                    audioPlayerThread = new Thread(player);
                    audioPlayerThread.start();

                }
            }
        }

    }

}
