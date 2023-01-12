package me.escoffier.workshop.heros;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Column;
import javax.persistence.Entity;

import java.util.Random;


@Entity
public class Hero extends PanacheEntity {

    public String name;
    public String otherName;
    public int level;
    public String picture;

    @Column(columnDefinition = "TEXT")
    public String powers;

    public static Hero findRandom() {
        long countHeroes = Hero.count();
        Random random = new Random();
        int randomHero = random.nextInt((int) countHeroes);
        return Hero.findAll().page(randomHero, 1).firstResult();
    }

    @Override
    public String toString() {
        return "" + name + "," + otherName + "," + level + "," + picture + "," + powers;
    }
}
