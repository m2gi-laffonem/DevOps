package me.escoffier.workshop.fight;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;

@RegisterRestClient(configKey = "heros")
@Produces(MediaType.APPLICATION_JSON)
public interface HerosClient {
    @Timeout(value = 1, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "getFallbackHero")
    @Retry(retryOn = TimeoutException.class,
            maxRetries = 4,
            maxDuration = 10,
            durationUnit = ChronoUnit.SECONDS)

    @Path("/heroes/random")
    @GET
    Hero getHero();

    @Path("/crash")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String crash();

    default Hero getFallbackHero() {
        Hero hero = new Hero();
        hero.name = "Great Kung Lao";
        hero.otherName = "Kung Lao";
        hero.level = 75;
        hero.picture = "https://github.com/cescoffier/supes/raw/main/pics/49776.jpg";

        return hero;
    }
}
