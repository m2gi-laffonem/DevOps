package me.escoffier.workshop.fight;

import org.jboss.logging.Logger;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
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
    @Path("/fight")
    @Counted("fight.fight.invocations")
    @Timed("fight.fight.time")
    public Fight fight() {
        return fight(
                heros.getHero(),
                villains.getVillain()
        );
    }

    @GET
    @Path("/crash/hero")
    @Produces(MediaType.TEXT_PLAIN)
    public String crashHeroService() {
        return heros.crash();
    }

    @GET
    @Path("/crash/villain")
    @Produces(MediaType.TEXT_PLAIN)
    public String crashVillainService() {
        return villains.crash();
    }

    private final Random random = new Random();

    private Fight fight(Hero hero, Villain villain) {
        int heroAdjust = random.nextInt(20);
        int villainAdjust = random.nextInt(20);

        if ((hero.level + heroAdjust) >= (villain.level + villainAdjust)) {
            return new Fight(hero, villain, hero.name);
        } else {
            return new Fight(hero, villain, villain.name);
        }
    }
}
