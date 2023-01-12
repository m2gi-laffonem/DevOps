package me.escoffier.workshop.fight;

import org.jboss.logging.Logger;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import java.util.Random;
import io.vertx.core.Vertx;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FightResource {

    private static final Logger LOGGER = Logger.getLogger(FightResource.class);
    
    @Inject @RestClient HerosClient heros;
    @Inject @RestClient VillainClient villains;

    @GET
    @Path("/heros")
    public Heros getRandomHero() {
        Heros hero = new Heros(heros.getHeros());
        LOGGER.debug("Found random hero " + hero);
        return hero;
    }

    @GET
    @Path("/villain")
    public Villain getRandomVillain() {
        Villain villain = new Villain(villains.getVillain());
        LOGGER.debug("Found random villain " + villain);
        return villain;
    }

    @GET
    @Path("/fight")
    public Fight fight() {
        return fight(
                getRandomHero(),
                getRandomVillain()
        );
    }

    private final Random random = new Random();

    private Fight fight(Heros hero, Villain villain) {
        int heroAdjust = random.nextInt(20);
        int villainAdjust = random.nextInt(20);

        if ((hero.level + heroAdjust) >= (villain.level + villainAdjust)) {
            return new Fight(hero, villain, hero.name);
        } else {
            return new Fight(hero, villain, villain.name);
        }
    }
}
