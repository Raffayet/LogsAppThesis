package com.example.uberbackend.unit.repository;

import com.example.uberbackend.model.Role;
import com.example.uberbackend.repositories.jpa.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class RoleRepositoryTests {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void findAllBySenderEmailClientTest(){
        String roleName = "CLIENT";
        Optional<Role> optRole = roleRepository.findByName(roleName);

        assertTrue(optRole.isPresent());
        assertEquals("CLIENT", optRole.get().getName());
    }

    @Test
    void findAllBySenderEmailDriverTest(){
        String roleName = "DRIVER";
        Optional<Role> optRole = roleRepository.findByName(roleName);

        assertTrue(optRole.isPresent());
        assertEquals("DRIVER", optRole.get().getName());
    }

    @Test
    void findAllBySenderEmailAdminTest(){
        String roleName = "ADMIN";
        Optional<Role> optRole = roleRepository.findByName(roleName);

        assertTrue(optRole.isPresent());
        assertEquals("ADMIN", optRole.get().getName());
    }

    @Test
    void findAllBySenderEmailUnknownTest(){
        String roleName = "bad";
        Optional<Role> optRole = roleRepository.findByName(roleName);
        assertTrue(optRole.isEmpty());
    }
}
