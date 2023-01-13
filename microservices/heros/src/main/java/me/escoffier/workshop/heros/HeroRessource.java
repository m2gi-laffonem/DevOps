package me.escoffier.workshop.heros;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import io.vertx.core.Vertx;
import javax.inject.Inject;

import java.util.Random;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HeroRessource {

 	private static final Logger LOGGER = Logger.getLogger("my-heros-resource");
 	
 	@Inject
    Vertx vertx;

    @GET
    @Path("/heroes/random")
    public Hero getRandomHero() {
        Hero hero = Hero.findRandom();
        LOGGER.debug("Found random hero " + hero);
        return hero;
    }
    
    @GET
    @Path("/crash")
    @Produces(MediaType.TEXT_PLAIN)
    public String crash() {
        LOGGER.info("Dying in ~1 second");
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            vertx.close();
        }).start();
        return "bye bye";
    }
}
