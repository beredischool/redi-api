package org.redischool.resources;

import org.redischool.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}
