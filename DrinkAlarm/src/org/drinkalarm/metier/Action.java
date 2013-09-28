package org.drinkalarm.metier;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe métier Action
 */
public class Action {
    /**
     * Description de l'action
     */
    private String texte;

    /**
     * Chemin du son à jouer
     */
    private String cheminSon;

    /**
     * Tableau de proba de l'action en fonction des modes de jeu
     * @see Mode
     */
    private Map<Mode,Float> chances = new HashMap<Mode,Float>();

    /**
     * Nombre de participant pour l'action.
     * 0 signifie que tout le monde participe
     */
    private Integer nbParticipant = 0;

    /**
     * Nombre de gorgé aléatoire
     */
    private Boolean gorgeRamdom = false;

    public Action(String texte, String cheminSon) {
        this.texte = texte;
        this.cheminSon = cheminSon;
    }

    public Boolean getGorgeRamdom() {
        return gorgeRamdom;
    }

    public void setGorgeRamdom(Boolean gorgeRamdom) {
        this.gorgeRamdom = gorgeRamdom;
    }

    public Integer getNbParticipant() {
        return nbParticipant;
    }

    public void setNbParticipant(Integer nbParticipant) {
        this.nbParticipant = nbParticipant;
    }

    public Float getChance(Mode mode) {
        return (chances.containsKey(mode))?chances.get(mode):0F;
    }

    public void setChance(Mode mode, Float chance) {
        this.chances.put(mode,chance);
    }

    public String getCheminSon() {
        return cheminSon;
    }

    public void setCheminSon(String cheminSon) {
        this.cheminSon = cheminSon;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    /**
     * Méthode qui choisi aléatoirement le joueur et le nombre de gorgé suivant les actions.
     *
     * @param joueurs Liste des joueurs
     * @return String
     */
    public String play(ArrayList<Joueur> joueurs) {
        String retour = "";

        if(nbParticipant != 0){
            int randomParticipants = (int) (Math.random() * ((joueurs.size() - 1) * 10000000 / 10000000));
            // System.out.println("participant : " + randomParticipants);
            retour = " : " + joueurs.get(randomParticipants).getNom();
        }
        if(gorgeRamdom){
            int randomNbGorgees = (int) (Math.random() * (6 * 10000000 / 10000000));
            return randomNbGorgees + texte + retour;
        }
        else
            return texte + retour;
    }
}
