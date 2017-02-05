package org.redischool.resources;

import org.redischool.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by raouf on 1/27/17.
 */
@Path("role")
public class RoleResource {
    private final RoleService roleService;

    @Autowired
    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @GET
    public Response getRoleByID(@PathParam("id") UUID uuid) {
        return Response.ok().entity(roleService.findById(uuid)).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("name")
    public Response getRoleByName(@QueryParam("name") String name) {
        return Response.ok().entity(roleService.findByName(name)).build();

    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("findAll")
    public Response getAllRoles() {
        return Response.ok().entity(roleService.findAll()).build();
    }

}
