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
    private UserRepository repository;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void shouldSaveUserSuccessful() throws Exception {
        User user = User.builder().id(userService.generateId())
                .userType(UserType.STUDENT)
                .address("Address")
                .birthDate(LocalDate.of(1978, 8, 2))
                .description("Test")
                .email("aemail@gmail.com")
                .password("password")
                .firstName("Alaa")
                .lastName("Aloush")
                .build();
        User savedUser = userService.save(user);
        Assert.assertThat(savedUser, Matchers.equalTo(user));
    }

    @Test
    public void shouldFindById() {
        User user = User.builder().id(userService.generateId())
                .userType(UserType.STUDENT)
                .address("Address")
                .birthDate(LocalDate.of(1978, 8, 2))
                .description("Test")
                .email("aemail1@gmail.com")
                .password("password")
                .firstName("Alaa")
                .lastName("Aloush")
                .build();

        userService.save(user);
        User user1 = userService.findById(user.getId());
        Assert.assertThat(user, Matchers.equalTo(user1));
    }

    @Test
    public void shouldFindByEmail() {
        User user = User.builder().id(userService.generateId())
                .userType(UserType.STUDENT)
                .address("Address")
                .birthDate(LocalDate.of(1978, 8, 2))
                .description("Test")
                .email("aemail1@gmail.com")
                .password("password")
                .firstName("Alaa")
                .lastName("Aloush")
                .build();

        repository.save(user);
        User user1 = userService.findByEmail("aemail1@gmail.com");
        Assert.assertThat(user1, Matchers.equalTo(user));
    }


    @Test
    public void shouldFindByUserType() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(User.builder().id(userService.generateId())
                    .userType(UserType.STUDENT)
                    .address("Address")
                    .birthDate(LocalDate.of(1978, 8, 2))
                    .description("Test")
                    .email("aemail1@" + i + "gmail.com")
                    .password("password")
                    .firstName("Alaa")
                    .lastName("Aloush")
                    .build());
        }

        repository.save(users);
        List<User> users1 = userService.findByUserType(UserType.STUDENT);
        Assert.assertThat(users1, Matchers.equalTo(users));
    }


    @Test
    public void shouldSuccesfulReturnFirstPage() {
        for (int i = 0; i < 10; i++)
            repository.save(User.builder().id(userService.generateId())
                    .userType(UserType.STUDENT)
                    .address("Address")
                    .birthDate(LocalDate.of(1978, 8, 2))
                    .description("Test")
                    .email("aemail1" + i + "@gmail.com")
                    .password("password")
                    .firstName("Alaa")
                    .lastName("Aloush")
                    .build());
        Page<User> users = userService.findAll(new PageRequest(0, 5));
        Assert.assertThat(users,
                Matchers.allOf(
                        Matchers.hasProperty("totalPages", Matchers.equalTo(2)),
                        Matchers.hasProperty("totalElements", Matchers.equalTo(10L))));
    }


}