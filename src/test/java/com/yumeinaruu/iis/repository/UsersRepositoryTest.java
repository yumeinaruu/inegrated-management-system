package com.yumeinaruu.iis.repository;

import com.yumeinaruu.iis.model.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersRepositoryTest {
    @Autowired
    UsersRepository usersRepository;
    static Users users;

    @BeforeAll
    static void beforeAll() {
        users = new Users();
        users.setUsername("test_name");
        users.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        users.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        users.setId(5L);
    }

    @Test
    void findAllTest_Success() {
        List<Users> usersList = usersRepository.findAll();

        Assertions.assertNotNull(usersList);
    }

    @Test
    void findByIdTest_Success() {
        Users usersFromDb = usersRepository.findAll().get(0);
        Optional<Users> optionalUsers = usersRepository.findById(usersFromDb.getId());

        Assertions.assertTrue(optionalUsers.isPresent());
    }

    @Test
    void FindByNameTest_Success() {
        Users usersFromDb = usersRepository.findAll().get(0);
        Optional<Users> usersOptional = usersRepository.findByUsername(usersFromDb.getUsername());
        Assertions.assertTrue(usersOptional.isPresent());
    }


    @Test
    void saveTest_Success() {
        Users savedUsers = usersRepository.save(users);
        Optional<Users> usersOptional = usersRepository.findById(savedUsers.getId());

        Assertions.assertTrue(usersOptional.isPresent());
    }

    @Test
    void updateTest_Success(){
        Users savedUsers = usersRepository.save(users);
        Users resultUsers = usersRepository.saveAndFlush(savedUsers);

        Assertions.assertNotNull(resultUsers);
    }

    @Test
    void deleteUserTest_Success(){
        Users savedUsers = usersRepository.save(users);
        usersRepository.deleteById(savedUsers.getId());

        Optional<Users> usersOptional = usersRepository.findById(savedUsers.getId());
        Assertions.assertFalse(usersOptional.isPresent());
    }
}
