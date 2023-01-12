package me.escoffier.workshop.fight;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "heros")
@Produces(MediaType.TEXT_PLAIN)
public interface HerosClient {

    @Path("/heros")
    @GET
    String getHeros();

    @Path("/crash")
    @GET
    String crash();

}
