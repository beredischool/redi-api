package org.redischool.resources;

import org.redischool.models.User;
import org.redischool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;

/**
 * Created by ReDI on 1/20/2017.
 */
@Path("user")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response create() {
        UUID id = userService.generateId();
        return Response.created(URI.create("user/" + id)).entity(id.toString()).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @PUT
    public Response saveUser(@PathParam("id") UUID id, User user) {
        return Response.ok().entity(userService.save(user)).build();
    }


    @Path("{id}")
    @GET
    public Response getUser(@PathParam("id") UUID id) {
        return Response.ok().entity(userService.findById(id)).build();
    }
}
