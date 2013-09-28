package org.drinkalarm.essai;

import org.drinkalarm.application.JeuControlleur;
import org.drinkalarm.metier.Joueur;
import org.drinkalarm.presentation.DrinkAlarmMainFrame;

import java.io.File;
import java.util.ArrayList;

/**
 * Classe de test à lancer
 */
public class DrinkAlarm {
    public DrinkAlarm() {
        JeuControlleur ctr = new JeuControlleur();
    }

    public static void main(String[] args) {
        new DrinkAlarmMainFrame();
    }
}
