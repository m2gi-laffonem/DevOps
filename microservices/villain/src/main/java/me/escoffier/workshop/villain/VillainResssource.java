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

@Path("/")
public class VillainResssource {

 	private static final Logger LOGGER = Logger.getLogger("my-villain-ressource");
 
 	@Inject
    Vertx vertx;
    
    @GET
    @Path("/villain")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRandomVillain() {
        return Villain.findRandom().toString();
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
