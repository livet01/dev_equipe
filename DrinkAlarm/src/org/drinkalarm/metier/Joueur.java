package org.drinkalarm.metier;

/**
 * Classe métier Joueur
 */
public class Joueur {

    /**
     * Nom du joueur
     */
    private String nom;

    /**
     * Score
     */
    private Integer score = 0;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
