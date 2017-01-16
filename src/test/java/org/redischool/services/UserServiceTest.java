package org.redischool.services;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redischool.models.User;
import org.redischool.models.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
/**
 * Created by aurel on 16/01/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldSaveUserSuccesfull() throws Exception {
        User user = User.builder().id(service.generateId())
                .userType(UserType.STUDENT)
                .address("Address")
                .birthDate(LocalDate.of(1978, 8, 2))
                .description("Test")
                .email("aemail@gmail.com")
                .password("password")
                .firstName("Alaa")
                .lastName("Aloush")
                .build();
        User savedUser = service.save(user);
        Assert.assertThat(savedUser, Matchers.equalTo(user));
    }

}