package me.escoffier.workshop.fight;

public class Fight {

    public Heros hero;
    public Villain villain;

    public String winner;

    public Fight() {
        // USed by mapper
    }

    public Fight(Heros h, Villain v, String w) {
        hero = h;
        villain = v;
        winner = w;
    }

}
