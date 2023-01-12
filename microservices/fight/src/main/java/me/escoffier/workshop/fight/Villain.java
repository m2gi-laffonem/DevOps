package me.escoffier.workshop.fight;

public class Villain {

    public String name;
    public String otherName;
    public int level;
    public String picture;
    public String powers;

    public Villain(String s) {
    	String[] t = s.split(",");
    	name = t[0];
    	otherName = t[1];
    	level = Integer.parseInt(t[2]);
    	picture = t[3];
    	powers = t[4];
    }
}
