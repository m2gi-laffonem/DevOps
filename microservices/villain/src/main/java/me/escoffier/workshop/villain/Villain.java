package me.escoffier.workshop.villain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.util.Random;

@Entity
public class Villain extends PanacheEntity {
 
    public String name;
    public String otherName;
    public int level;
    public String picture;

    @Column(columnDefinition = "TEXT")
    public String powers;

    public static Villain findRandom() {
        long countVillains = count();
        Random random = new Random();
        int randomVillain = random.nextInt((int) countVillains);
        return findAll().page(randomVillain, 1).firstResult();
    }

    @Override
    public String toString() {
        return "" + name + "," + otherName + "," + level + "," + picture + "," + powers;
    }
}
