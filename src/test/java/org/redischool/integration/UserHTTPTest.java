package org.redischool.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redischool.models.User;
import org.redischool.models.UserType;
import org.redischool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ReDI on 1/20/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserHTTPTest {

    Client client = ClientBuilder.newClient();

    @Autowired
    private UserService userService;


    @Test
    public void shouldExecuteSuccessfulPost() {
        WebTarget target = client.target("http://localhost:8080/api/user");
        Response response = target.request().post(null);
        Assert.assertEquals(201, response.getStatus());
        String locationHeader = response.getHeaders().getFirst("Location").toString();
        Assert.assertTrue(locationHeader.endsWith(response.readEntity(String.class)));
    }


    @Test
    public void shouldExecuteSuccessfulPut() {
        User user = User.builder().id(userService.generateId())
                .userType(UserType.STUDENT)
                .address("Address")
                .description("Test")
                .email("aemail@gmail.com")
                .password("password")
                .firstName("Alaa")
                .lastName("Aloush")
                .build();

        WebTarget target = client.target("http://localhost:8080/api/user/" + user.getId().toString());
        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(user));
        Assert.assertEquals(200, response.getStatus());
    }


    @Test
    public void getUser() throws Exception {

    }

}