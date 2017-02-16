package org.redischool.resources;

import org.redischool.models.Course;
import org.redischool.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;

/**
 * Created by ReDI on 1/27/2017.
 */
@Path("course")
public class CourseResources {

    private final CourseService courseService;

    @Autowired
    public CourseResources(CourseService courseService) {
        this.courseService = courseService;
    }


    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response create() {
        UUID id = courseService.generateId();
        return Response.created(URI.create("course/" + id)).entity(id.toString()).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @PUT
    public Response saveCourse(@PathParam("id") UUID id, Course course) {
        return Response.ok().entity(courseService.save(course)).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @GET
    public Response getCourse(@PathParam("id") UUID id) {
        return Response.ok().entity(courseService.findById(id)).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("name")
    public Response getUserByName(@QueryParam("name") String name) {
        return Response.ok().entity(courseService.findByName(name)).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("url")
    public Response getUserByUrl(@QueryParam("url") String url) {
        return Response.ok().entity(courseService.findByUrl(url)).build();
    }
}
