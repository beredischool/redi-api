package org.redischool.resources;

import org.redischool.models.UserCourse;
import org.redischool.services.UserCourseService;
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
 * Created by ReDI on 2/4/2017.
 */
@Path("userCourse")
public class UserCourseResource {
    private final UserCourseService userCourseService;

    @Autowired
    public UserCourseResource(UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }


    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response create() {
        UUID id = userCourseService.generateId();
        return Response.created(URI.create("userCourse/" + id)).entity(id.toString()).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @PUT
    public Response saveUserCourse(@PathParam("id") UUID id, UserCourse userCourse) {
        return Response.ok().entity(userCourseService.save(userCourse)).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @GET
    public Response getUserCourse(@PathParam("id") UUID id) {
        return Response.ok().entity(userCourseService.findById(id)).build();
    }
}
