package org.redischool.services;

import org.hibernate.validator.constraints.Range;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redischool.models.Role;
import org.redischool.services.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by raouf on 1/20/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleService service;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void shouldFindRoleById() throws Exception {
        UUID id = service.generateId();
        repository.save(Role.builder().id(id)
                .name("ADMIN").build());
        Role role = service.findById(id);
        assertEquals("ADMIN", role.getName());
    }
    @Test
    public void findAll() throws Exception {
        for(int i=0 ; i<10 ;i++){
            repository.save(Role.builder().id(service.generateId())
                    .name("ADMIN"+i).build());
        }
        List<Role> roles = service.findAll();
        assertEquals(10, roles.size());

    }

}