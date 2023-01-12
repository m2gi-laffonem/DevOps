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
public class HeroRessource {

 	private static final Logger LOGGER = Logger.getLogger("my-heros-resource");
 	
 	@Inject
    Vertx vertx;

    @GET
    @Path("/heros")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRandomHero() {
        return Hero.findRandom().toString();
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
