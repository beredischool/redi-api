package org.redischool.integration;

import jersey.repackaged.com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redischool.App;
import org.redischool.models.Course;
import org.redischool.models.CourseStatus;
import org.redischool.models.User;
import org.redischool.models.UserCourse;
import org.redischool.models.UserType;
import org.redischool.services.CourseService;
import org.redischool.services.UserCourseService;
import org.redischool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by ReDI on 1/20/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = App.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserHTTPTest {

    Client client = ClientBuilder.newClient();

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserCourseService userCourseService;

    private String basic_url = null;

    @Before
    public void setUp() {
        basic_url = "http://localhost:" + port + "/api/user/";
    }

    User createUser() {

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
        Response response = target.request().post(Entity.entity(createUser(), MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        String locationHeader = response.getHeaders().getFirst("Location").toString();
        Assert.assertTrue(locationHeader.endsWith(response.readEntity(String.class)));
    }


    @Test
    public void shouldExecuteSuccessfulPut() {

        User user = createUser();
        WebTarget target = client.target(basic_url + user.getId().toString());
        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(user));
        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void shouldExecuteSuccessfulGetById() {

        User user = createUser();

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

        User user = createUser();

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
    public void shouldExecuteSuccessfulGetByUserType() {

        User user = createUser();

        String put_url = basic_url + user.getId().toString();
        String userType_url = basic_url + "userType/";

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
        target = client.target(userType_url);
        response = target.queryParam("userType", user.getUserType()).request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
    }


    @Test
    public void shouldExecuteSuccessfulGetAll() {

        User user = createUser();
        User user1 = createUser();
        User user2 = createUser();


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

        User user = createUser();

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
    public void shouldReturnBadRequestDuringSignUp() {
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
        form.param("passwordConfirm", "1243");

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        Assert.assertEquals(400, response.getStatus());
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
        form.param("passwordConfirm", "1234");

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        Assert.assertEquals(200, response.getStatus());
    }


    @Test
    public void shouldExecuteSuccessfulApply() {
        String sign_up_url = basic_url + "sign_up/";

        //sign up
        WebTarget target = client.target(sign_up_url);

        Form form = new Form();
        form.param("email", "sbaihi.alaa111000@gmail.com");
        form.param("password", "1234");
        form.param("firstName", "Alaa");
        form.param("lastName", "SBAIHI");
        form.param("address", "Pestalozzistr 6, 10625 Berlin");
        form.param("description", "lerner");
        form.param("userType", UserType.STUDENT.toString());
        form.param("passwordConfirm", "1234");

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        User user = response.readEntity(User.class);

        Assert.assertEquals(200, response.getStatus());

        String apply_url = basic_url + "apply/" + user.getId().toString();

        //apply
        target = client.target(apply_url);

        Set<Course> courseSet = new HashSet<>();

        courseSet.add(Course.builder().id(userService.generateId()).name("Android").build());
        courseSet.add(Course.builder().id(userService.generateId()).name("JAVA").build());
        courseSet.add(Course.builder().id(userService.generateId()).name("C++").build());

        List<Course> courseList = courseService.save(Lists.newArrayList(courseSet));

        Set<UUID> coursesIds = courseList.stream().map(c -> c.getId())
                .collect(Collectors.toSet());


        response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.json(coursesIds));
        Assert.assertEquals(200, response.getStatus());


    }

    @Test
    public void shouldDoChangeStatusAndSendMessageSuccessful() throws InterruptedException {
        String sign_up_url = basic_url + "sign_up/";

        //sign up
        WebTarget target = client.target(sign_up_url);

        Form form = new Form();
        form.param("email", "sbaihi.alaa111088@gmail.com");
        form.param("password", "1234");
        form.param("firstName", "Alaa");
        form.param("lastName", "SBAIHI");
        form.param("address", "Pestalozzistr 6, 10625 Berlin");
        form.param("description", "lerner");
        form.param("userType", UserType.STUDENT.toString());
        form.param("passwordConfirm", "1234");

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        User user = response.readEntity(User.class);

        Assert.assertEquals(200, response.getStatus());

        String apply_url = basic_url + "apply/" + user.getId().toString();

        //apply
        target = client.target(apply_url);

        Set<Course> courseSet = new HashSet<>();

        courseSet.add(Course.builder().id(userService.generateId()).name("Android Development").build());
        courseSet.add(Course.builder().id(userService.generateId()).name("JAVA Development").build());
        courseSet.add(Course.builder().id(userService.generateId()).name("C++ Development").build());


        List<Course> courseList = courseService.save(Lists.newArrayList(courseSet));

        System.out.println("Courses:");
        System.out.println(courseList.get(0).getId().toString() + ":" + courseList.get(0).getName().toString());
        System.out.println(courseList.get(1).getId().toString() + ":" + courseList.get(1).getName().toString());
        System.out.println(courseList.get(2).getId().toString() + ":" + courseList.get(2).getName().toString());
        System.out.println("User:");
        System.out.println(user.getId().toString() + ":" + user.getFirstName().toString());

        Set<UUID> coursesIds = courseList.stream().map(c -> c.getId())
                .collect(Collectors.toSet());


        response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.json(coursesIds));
        Assert.assertEquals(200, response.getStatus());


        //Change Status
        List<UserCourse> userCourseList = response.readEntity(new GenericType<List<UserCourse>>() {
        });

        userCourseList = userCourseService.save(userCourseList);

        System.out.println("The applied Courses:" + userCourseList.size());

        UserCourse userCourse = userCourseList.get(0);

        System.out.println("User Course: The applied Courses:");
        System.out.println("user course id: " + userCourseList.get(0).getId().toString());
        System.out.println("course id: " + userCourseList.get(0).getCourseId().toString());
        System.out.println("user id: " + userCourseList.get(0).getUserId().toString());
        System.out.println("course status: " + userCourseList.get(0).getCourseStatus().toString());

        String change_status_url = basic_url + "course/" + userCourse.getId().toString();

        target = client.target(change_status_url);

        response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(userCourse.toBuilder()
                        .courseStatus(CourseStatus.APPROVED)
                        .build(), MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());

        Thread.currentThread().sleep(5000);

        userCourse = response.readEntity(UserCourse.class);
        userCourse = userCourseService.findById(userCourse.getId());
        User user1 = userService.findById(userCourse.getUserId());

        Course course = courseService.findById(userCourse.getCourseId());

        System.out.println("user course id after response: " + userCourse.getId().toString());
        System.out.println("status: " + userCourse.getCourseStatus().toString());
        System.out.println("user: " + user1.getId().toString() + " :" + user1.getFirstName());

        List<Course> courses = new ArrayList<>(user1.getCourses());

        System.out.println("courses in user: " + courses.size());

        for (int i = 0; i < courses.size(); i++) {
            System.out.println(courses.get(i).toString() + " :" + courses.get(i).getName());
        }

        System.out.println(course.getId().toString() + " :" + course.getName());

        List<User> users = new ArrayList<>(course.getUsers());

        System.out.println("users in course: " + users.size());

        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).toString() + " :" + users.get(i).getFirstName());
        }
    }
}

