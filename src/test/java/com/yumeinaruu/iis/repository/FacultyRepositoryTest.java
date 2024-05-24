package com.yumeinaruu.iis.repository;

import com.yumeinaruu.iis.model.Faculty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FacultyRepositoryTest {
    @Autowired
    FacultyRepository facultyRepository;
    static Faculty faculty;

    @BeforeAll
    static void beforeAll() {
        faculty = new Faculty();
        faculty.setName("test_name");
        faculty.setId(5L);
    }

    @Test
    void findAllTest_Success() {
        List<Faculty> faculties = facultyRepository.findAll();

        Assertions.assertNotNull(faculties);
    }

    @Test
    void findByIdTest_Success() {
        Faculty facultyFromDb = facultyRepository.findAll().get(0);
        Optional<Faculty> facultyOptional = facultyRepository.findById(facultyFromDb.getId());

        Assertions.assertTrue(facultyOptional.isPresent());
    }

    @Test
    void findByNameTest_Success() {
        Faculty facultyFromDb = facultyRepository.findAll().get(0);
        Optional<Faculty> facultyOptional = facultyRepository.findByName(facultyFromDb.getName());
        Assertions.assertTrue(facultyOptional.isPresent());
    }

    @Test
    void saveTest_Success() {
        Faculty savedFaculty = facultyRepository.save(faculty);
        Optional<Faculty> optionalFaculty = facultyRepository.findById(savedFaculty.getId());

        Assertions.assertTrue(optionalFaculty.isPresent());
    }

    @Test
    void updateTest_Success(){
        Faculty savedFaculty = facultyRepository.save(faculty);
        Faculty resultFaculty = facultyRepository.saveAndFlush(savedFaculty);

        Assertions.assertNotNull(resultFaculty);
    }

    @Test
    void deleteUserTest_Success(){
        Faculty savedFaculty = facultyRepository.save(faculty);
        facultyRepository.deleteById(savedFaculty.getId());

        Optional<Faculty> optionalFaculty = facultyRepository.findById(savedFaculty.getId());
        Assertions.assertFalse(optionalFaculty.isPresent());
    }
}
