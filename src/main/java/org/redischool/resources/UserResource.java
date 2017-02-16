package org.redischool.resources;

import org.redischool.models.CourseStatus;
import org.redischool.models.User;
import org.redischool.models.UserCourse;
import org.redischool.models.UserType;
import org.redischool.services.UserCourseService;
import org.redischool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jms.core.JmsTemplate;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ReDI on 1/20/2017.
 */
@Path("user")
public class UserResource {

    private final UserService userService;

    private final UserCourseService userCourseService;

    private final JmsTemplate jmsTemplate;

    @Autowired
    public UserResource(UserService userService, UserCourseService userCourseService, JmsTemplate jmsTemplate) {
        this.userService = userService;
        this.userCourseService = userCourseService;
        this.jmsTemplate = jmsTemplate;
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


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @GET
    public Response getUser(@PathParam("id") UUID id) {
        return Response.ok().entity(userService.findById(id)).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("email")
    public Response getUserByEmail(@QueryParam("email") String email) {
        return Response.ok().entity(userService.findByEmail(email)).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("userType")
    public Response getUserByUserType(@QueryParam("userType") UserType userType) {
        return Response.ok().entity(userService.findByUserType(userType)).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("AllUsers")
    @GET
    public Response getAllUsers(@QueryParam("page") final int page, @QueryParam("size") final int size) {
        return Response.ok().entity(userService.findAll(new PageRequest(page, size))).build();
    }


    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    @POST
    public Response login(@FormParam("email") final String email, @FormParam("password") final String password) {
        User user = userService.login(email, password);
        if (user == null) {
            return Response.noContent().build();
        }
        return Response.ok(user).build();
    }


    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sign_up")
    @POST
    public Response signUp(@FormParam("email") final String email, @FormParam("password") final String password,
                           @FormParam("firstName") final String firstName, @FormParam("lastName") final String lastName,
                           @FormParam("address") final String address,
                           @FormParam("description") final String description, @FormParam("userType") final UserType userType,
                           @FormParam("passwordConfirm") final String passwordConfirm) {

        if (!password.equals(passwordConfirm)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UUID id = userService.generateId();

        User user = userService.signUp(id, email, password, firstName, lastName, address,
                description, userType);

        if (user == null) {
            return Response.serverError().build();
        }

        return Response.ok().entity(user).build();

    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("apply/{id}")
    @PUT
    public Response apply(@PathParam("id") UUID id, Set<UUID> courseIdsSet) {
        List<UserCourse> userCourseList = userService.apply(id, courseIdsSet);
        if (userCourseList == null) {
            return Response.serverError().build();
        }
        return Response.ok().entity(userCourseList).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("course/{id}")
    @PUT
    public Response changeStatus(@PathParam("id") UUID id, UserCourse userCourse) {
        UserCourse currentUserCourse = userCourseService.findById(id);

        if (userCourse.getCourseStatus().equals(CourseStatus.APPROVED) &&
                currentUserCourse.getCourseStatus().equals(CourseStatus.APPLIED)) {


            jmsTemplate.convertAndSend("activate", userCourse);

        }
        userCourseService.save(userCourse);
        return Response.ok().entity(userCourse).build();

    }

}

