package me.escoffier.workshop.fight;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.faulttolerance.Retry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;

@RegisterRestClient(configKey = "villain")
@Produces(MediaType.APPLICATION_JSON)
public interface VillainClient {

    @Timeout(value = 1, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "getFallbackVillain")
    @Retry(retryOn = TimeoutException.class,
            maxRetries = 4,
            maxDuration = 10,
            durationUnit = ChronoUnit.SECONDS)

    @Path("/villains/random")
    @GET
    Villain getVillain();

    @Path("/crash")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String crash();

    default Villain getFallbackVillain() {
        Villain villain = new Villain();
        villain.name = "Nebula";
        villain.otherName = "Nebula";
        villain.level = 69;
        villain.picture = "https://github.com/cescoffier/supes/raw/main/pics/13897.jpg";
        return villain;
    }

}
