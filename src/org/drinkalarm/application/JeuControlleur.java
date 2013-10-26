package org.drinkalarm.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.drinkalarm.metier.Action;
import org.drinkalarm.metier.Joueur;
import org.drinkalarm.metier.Mode;

/**
 * Controlleur principale du jeu
 */
public class JeuControlleur {

    private ArrayList<Joueur> joueurs;
    private Collection<Action> actions = new ArrayList<Action>();
    private Mode mode;
    private Collection<Integer> randomAlarmNumbers = new ArrayList<Integer>();

    // Différents mode de jeu
    private Mode soft = new Mode("Soft",20F);
    private Mode medium = new Mode("Medium",30F);
    private Mode hard = new Mode("Hard",50F);
    private Mode legend = new Mode("Legend",80F);

    // CONSTANTES CHEMINS DES FICHIERS
    private final String HORNTUNE = "DrinkAlarm/res/horn_tune.wav";
    private final String DANSE_DU_VENTRE = "DrinkAlarm/res/danse_du_ventre.wav";
    private final String CALL_TO_ARMS = "DrinkAlarm/res/call_to_arms.wav";
    private final String TOUR_DE_FRANCE = "DrinkAlarm/res/tour_de_france.wav";
    private final String AIR_RAID_2 = "DrinkAlarm/res/air_raid_2.wav";
    private final String POUET_POUET = "DrinkAlarm/res/pouet_pouet.wav";
    private final String WOW = "DrinkAlarm/res/wow.wav";

    /**
     * Méthode permettant d'intialiser les paramètres du jeu :
     * - les différents modes
     * - les différentes actions
     * Insertion du mode de la partie
     * @param mode Mode de la partie
     */
    public JeuControlleur(String mode) {
        joueurs = new ArrayList<Joueur>();

        // Attribution des actions
        Action a = new Action("... Olé !!!! Une gorgée pour TOUT le monde",HORNTUNE);
        a.setChance(soft, 40F);
        a.setChance(medium, 37F);
        a.setChance(hard, 30F);
        a.setChance(legend,50F);
        this.actions.add(a);

        a = new Action("Une gorgée pour le BIZUT",DANSE_DU_VENTRE);
        a.setChance(soft, 20F);
        a.setChance(medium,23F);
        a.setChance(hard, 20F);
        a.setChance(legend, 0F);
        a.setNbParticipant(1);
        this.actions.add(a);

        a = new Action("CUL SEC !",AIR_RAID_2);
        a.setChance(soft, 20F);
        a.setChance(medium,22F);
        a.setChance(hard,5F);
        a.setChance(legend, 13F);
        a.setNbParticipant(1);
        this.actions.add(a);

        a = new Action("FREEZE",POUET_POUET);
        a.setChance(soft,10F);
        a.setChance(medium,8F);
        a.setChance(hard, 14F);
        a.setChance(legend, 13F);
        this.actions.add(a);

        a = new Action("Tout le monde boit, sauf le ROI",CALL_TO_ARMS);
        a.setChance(soft,5F);
        a.setChance(medium,8F);
        a.setChance(hard, 30F);
        a.setChance(legend, 35F);
        this.actions.add(a);

        a = new Action(" gorgée(s) pour le bizut du ROI",TOUR_DE_FRANCE);
        a.setChance(soft, 5F);
        a.setChance(medium, 2F);
        a.setChance(hard,1F);
        a.setChance(legend, 2F);
        a.setGorgeRamdom(true);
        this.actions.add(a);

        setMode(mode);
    }

    public JeuControlleur(){
        new JeuControlleur("medium");
    }

    public void addJoueur(String nom) {
        this.joueurs.add(new Joueur(nom));
    }

    public void setMode(String mode) {
        // Attribution du mode de jeu
        if (mode.equals("soft")) {
            this.mode = soft;

        } else if (mode.equals("medium")) {
            this.mode = medium;

        } else if (mode.equals("hard")) {
            this.mode = hard;

        } else if (mode.equals("legend")) {
            this.mode = legend;

        } else {
            this.mode = medium;
        }
    }

    /**
     * Teste de façon aléatoire si le joueur joue l'action ou pas
     * @param collection
     * @param proba
     * @param randomNumber
     * @return Boolean
     */
    private Boolean testDrink(Collection<Integer> collection, Float proba,
                              Integer randomNumber) {
        int nb = 0;

        collection.clear();
        // Génération aléatoire de n nombres
        int i = 0;
        while (i < proba) {
            nb = ((int) (Math.random() * 10000000000000.0 / 100000000000.0));
            if (!collection.contains(nb)) {
                collection.add(nb);
                i++;
            }
        }

        // On regarde si le nombre généré est contenu dans la
        // collection
        return collection.contains(randomNumber);
    }

    /**
     * Nouveau tour de jeu. Renvoi l'action à faire
     * @return String
     */
    public Action tour() {
        Action action;
        Iterator<Action> iterator = this.actions.iterator();

        int randomNumber = (int) (Math.random() * 100000000000.0 / 1000000000.0);

        if(testDrink(randomAlarmNumbers, mode.getChance(), randomNumber)){
            //System.out.println("on peut boire !");
            // répartition des nombres aléatoires
            while (iterator.hasNext()) {
                action = iterator.next();

                if (testDrink(randomAlarmNumbers, action.getChance(mode), randomNumber)) {
                    return action;
                }
            }
        }
        return null;
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public ArrayList<String> getJoueursAsString(){
        ArrayList<String> retour = new ArrayList<String>();
        for(Joueur j : joueurs){
            retour.add(j.getNom());
        }
        return retour;
    }
}
