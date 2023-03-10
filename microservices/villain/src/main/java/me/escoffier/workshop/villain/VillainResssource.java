package me.escoffier.workshop.villain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.ws.rs.GET;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import java.util.Random;
import io.vertx.core.Vertx;
import java.io.IOException;
import javax.annotation.PostConstruct;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class VillainResssource {

 	private static final Logger LOGGER = Logger.getLogger("my-villain-ressource");
 
 	@Inject
    Vertx vertx;

    boolean slow;

    Random random = new Random();

    @PostConstruct
    public void configure() {
        String env = System.getenv("SLOW");
        if (env != null) {
            slow = Boolean.parseBoolean(env);
        }  else {
            slow = false;
        }

        LOGGER.info("Slow mode enabled? " + slow);
    }

    @GET
    @Path("/villains/random")
    public Villain getRandomVillain() {
        Villain villain = Villain.findRandom();

        if (slow) {
            int delay = random.nextInt(2000) + 1;
            LOGGER.infof("Delaying response by %d ms", delay);
            nap(delay);
        }

        LOGGER.debug("Found random villain " + villain);
        return villain;
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

    private void nap(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
