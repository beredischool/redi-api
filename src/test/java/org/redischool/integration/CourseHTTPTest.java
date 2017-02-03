package org.redischool.integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redischool.App;
import org.redischool.models.Course;
import org.redischool.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ReDI on 2/3/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = App.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseHTTPTest {

    Client client = ClientBuilder.newClient();

    @LocalServerPort
    private int port;

    @Autowired
    private CourseService courseService;

    private String basic_url = null;


    @Before
    public void setUp() {
        basic_url = "http://localhost:" + port + "/api/course/";
    }


    @After
    public void tearDown() throws Exception {

    }

    public Course createCourse(String name) {

        Course course = Course.builder().id(courseService.generateId()).name(name).description("New Technologies")
                .url("www.redi-school.org/Courses/").build();
        return course;
    }


    @Test
    public void shouldExecuteSuccessfulPost() {
        WebTarget target = client.target(basic_url);
        Response response = target.request().post(Entity.entity(createCourse("Java"), MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        String locationHeader = response.getHeaders().getFirst("Location").toString();
        Assert.assertTrue(locationHeader.endsWith(response.readEntity(String.class)));
    }


    @Test
    public void shouldExecuteSuccessfulPut() {

        Course course = createCourse("Java8");
        WebTarget target = client.target(basic_url + course.getId().toString());
        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(course));
        Assert.assertEquals(200, response.getStatus());

    }


    @Test
    public void shouldExecuteSuccessfulGetById() {

        Course course = createCourse("Sales");

        String put_get_url = basic_url + course.getId().toString();

        //post
        WebTarget target = client.target(basic_url);
        Response response = target.request().post(Entity.entity(course, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        String locationHeader = response.getHeaders().getFirst("Location").toString();
        Assert.assertTrue(locationHeader.endsWith(response.readEntity(String.class)));

        //put
        target = client.target(put_get_url);
        response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(course));
        Assert.assertEquals(200, response.getStatus());

        //get
        target = client.target(put_get_url);
        response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
    }


    @Test
    public void shouldExecuteSuccessfulGetByName() {

        Course course = createCourse("Java2");

        String put_url = basic_url + course.getId().toString();
        String name_url = basic_url + "name/";

        //post
        WebTarget target = client.target(basic_url);
        Response response = target.request().post(Entity.entity(course, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        String locationHeader = response.getHeaders().getFirst("Location").toString();
        Assert.assertTrue(locationHeader.endsWith(response.readEntity(String.class)));

        //put
        target = client.target(put_url);
        response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(course));
        Assert.assertEquals(200, response.getStatus());

        //get
        target = client.target(name_url);
        response = target.queryParam("name", course.getName()).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
    }


}