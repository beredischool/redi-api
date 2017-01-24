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
import javax.ws.rs.core.Form;
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

    private String basic_url = "http://localhost:8080/api/user/";

    User creatUser() {

        User user = User.builder().id(userService.generateId())
                .userType(UserType.STUDENT)
                .address("Address")
                .description("Test")
                .email("aemail" + Math.random() + "@gmail.com")
                .password("password")
                .firstName("Alaa")
                .lastName("Aloush")
                .active(true)
                .build();
        return user;
    }

    @Test
    public void shouldExecuteSuccessfulPost() {
        WebTarget target = client.target(basic_url);
        Response response = target.request().post(Entity.entity(creatUser(), MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        String locationHeader = response.getHeaders().getFirst("Location").toString();
        Assert.assertTrue(locationHeader.endsWith(response.readEntity(String.class)));
    }


    @Test
    public void shouldExecuteSuccessfulPut() {

        WebTarget target = client.target(basic_url + creatUser().getId().toString());
        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(creatUser()));
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldExecuteSuccessfulGetById() {

        User user = creatUser();

        String put_get_url = basic_url + user.getId().toString();

        //post
        WebTarget target = client.target(basic_url);
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        String locationHeader = response.getHeaders().getFirst("Location").toString();
        Assert.assertTrue(locationHeader.endsWith(response.readEntity(String.class)));

        //put
        target = client.target(put_get_url);
        response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(user));
        Assert.assertEquals(200, response.getStatus());

        //get
        target = client.target(put_get_url);
        response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
    }


    @Test
    public void shouldExecuteSuccessfulGetByEmail() {

        User user = creatUser();

        String put_url = basic_url + user.getId().toString();
        String mail_url = basic_url + "email/";

        //post
        WebTarget target = client.target(basic_url);
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        String locationHeader = response.getHeaders().getFirst("Location").toString();
        Assert.assertTrue(locationHeader.endsWith(response.readEntity(String.class)));

        //put
        target = client.target(put_url);
        response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(user));
        Assert.assertEquals(200, response.getStatus());

        //get
        target = client.target(mail_url);
        response = target.queryParam("email", user.getEmail()).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
    }


    @Test
    public void shouldExecuteSuccessfulGetAll() {

        User user = creatUser();
        User user1 = creatUser();
        User user2 = creatUser();


        String all_url = basic_url + "AllUsers/";

        //post
        WebTarget target = client.target(basic_url);
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        response = target.request().post(Entity.entity(user1, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        response = target.request().post(Entity.entity(user2, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());


        //put
        target = client.target(basic_url + user.getId().toString());
        response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(user));
        Assert.assertEquals(200, response.getStatus());
        target = client.target(basic_url + user1.getId().toString());
        response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(user1));
        Assert.assertEquals(200, response.getStatus());
        target = client.target(basic_url + user2.getId().toString());
        response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(user2));
        Assert.assertEquals(200, response.getStatus());

        //get
        target = client.target(all_url);
        response = target.queryParam("page", 0).queryParam("size", 10).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
    }


    @Test
    public void shouldExecuteSuccessfulLogin() {

        User user = creatUser();

        String put_url = basic_url + user.getId().toString();
        String login_url = basic_url + "login/";

        //post
        WebTarget target = client.target(basic_url);
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        String locationHeader = response.getHeaders().getFirst("Location").toString();
        Assert.assertTrue(locationHeader.endsWith(response.readEntity(String.class)));

        //put
        target = client.target(put_url);
        response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(user));
        Assert.assertEquals(200, response.getStatus());

        //login
        target = client.target(login_url);

        Form form = new Form();
        form.param("email", user.getEmail());
        form.param("password", user.getPassword());

        response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        Assert.assertEquals(200, response.getStatus());
    }


    @Test
    public void shouldExecuteSuccessfulSignUp() {
        String sign_up_url = basic_url + "sign_up/";

        //sign up
        WebTarget target = client.target(sign_up_url);

        Form form = new Form();
        form.param("email", "sbaihi.alaa@gmail.com");
        form.param("password", "1234");
        form.param("firstName", "Alaa");
        form.param("lastName", "SBAIHI");
        form.param("address", "Pestalozzistr 6, 10625 Berlin");
        form.param("description", "lerner");
        form.param("userType", UserType.STUDENT.toString());
        form.param("active", String.valueOf(true));

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        Assert.assertEquals(200, response.getStatus());
    }


}