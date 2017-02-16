package org.redischool.services;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redischool.models.User;
import org.redischool.models.UserType;
import org.redischool.services.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aurel on 16/01/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }


    private User createUser() {
        return User.builder().id(userService.generateId())
                .userType(UserType.STUDENT)
                .address("Address")
                .birthDate(LocalDate.of(1978, 8, 2))
                .description("Test")
                .email("aemail" + Math.random() + "@gmail.com")
                .password("password")
                .firstName("Alaa")
                .lastName("Aloush")
                .build();
    }


    @Test
    public void shouldSaveUserSuccessful() throws Exception {
        User user = createUser();
        User savedUser = userService.save(user);
        Assert.assertThat(savedUser, Matchers.equalTo(user));
    }

    @Test
    public void shouldFindById() {
        User user = createUser();
        userService.save(user);
        User user1 = userService.findById(user.getId());
        Assert.assertThat(user, Matchers.equalTo(user1));
    }

    @Test
    public void shouldFindByEmail() {
        User user = createUser();
        userService.save(user);
        User user1 = userService.findByEmail(user.getEmail());
        Assert.assertThat(user1, Matchers.equalTo(user));
    }


    @Test
    public void shouldFindByUserType() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(createUser());
        }

        userRepository.save(users);
        List<User> users1 = userService.findByUserType(UserType.STUDENT);
        Assert.assertThat(users1, Matchers.equalTo(users));
    }


    @Test
    public void shouldSuccessfulReturnFirstPage() {
        for (int i = 0; i < 10; i++)
            userRepository.save(createUser());
        Page<User> users = userService.findAll(new PageRequest(0, 5));
        Assert.assertThat(users,
                Matchers.allOf(
                        Matchers.hasProperty("totalPages", Matchers.equalTo(2)),
                        Matchers.hasProperty("totalElements", Matchers.equalTo(10L))));
    }


}