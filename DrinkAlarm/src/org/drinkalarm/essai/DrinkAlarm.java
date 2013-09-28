package org.drinkalarm.essai;

import org.drinkalarm.application.JeuControlleur;
import org.drinkalarm.metier.Joueur;

import java.util.ArrayList;

/**
 * Classe de test à lancer
 */
public class DrinkAlarm {
    public DrinkAlarm() {
        JeuControlleur ctr = new JeuControlleur();
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
        Joueur j1 = new Joueur();
        j1.setNom("Toto");
        joueurs.add(j1);
        ctr.initialiser(joueurs,"hard");
        System.out.println(ctr.tour());
    }

    public static void main(String[] args) {
        new DrinkAlarm();
    }
}
