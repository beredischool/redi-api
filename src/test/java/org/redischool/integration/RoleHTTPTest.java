package org.redischool.integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redischool.App;
import org.redischool.models.Role;
import org.redischool.services.RoleService;
import org.redischool.services.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by raouf on 1/27/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = App.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleHTTPTest {
    Client client = ClientBuilder.newClient();

    @LocalServerPort
    private int port;

    @Autowired
    private RoleService roleService;

    private String rolUrl = null;
    private String roleStudent = "Student";
    private UUID roleId = null;

    @Autowired
    private RoleRepository repository;


    @Before
    public void setUp() throws Exception {
        rolUrl = "http://localhost:" + port + "/api/role/";
        roleId = roleService.generateId();
        Role role = Role.builder().id((roleId)).name(roleStudent).build();
        repository.save(role);
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void getRoleById() {
        WebTarget target = client.target(rolUrl + roleId.toString());
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void getRoleByName() {
        String url = rolUrl + "name/";
        WebTarget target = client.target(url);
        Response response = target.queryParam("name", roleStudent).request(MediaType.APPLICATION_JSON).get();
        Role role = response.readEntity(Role.class);
        Assert.assertEquals(roleStudent, role.getName());
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void getAllRoles() {
        String url = rolUrl + "findAll/";
        WebTarget target = client.target(url);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
    }


}
