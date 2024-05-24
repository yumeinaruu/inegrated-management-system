package com.yumeinaruu.iis.repository;

import com.yumeinaruu.iis.model.Subject;
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
public class SubjectRepositoryTest {
    @Autowired
    SubjectRepository subjectRepository;
    static Subject subject;

    @BeforeAll
    static void beforeAll() {
        subject = new Subject();
        subject.setDepartmentId(1L);
        subject.setName("test_name");
        subject.setId(5L);
    }

    @Test
    void findAllTest_Success() {
        List<Subject> subjects = subjectRepository.findAll();

        Assertions.assertNotNull(subjects);
    }

    @Test
    void findByIdTest_Success() {
        Subject subjectFromDb = subjectRepository.findAll().get(0);
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectFromDb.getId());

        Assertions.assertTrue(subjectOptional.isPresent());
    }

    @Test
    void FindByNameTest_Success() {
        Subject subjectFromDb = subjectRepository.findAll().get(0);
        Optional<Subject> subjectOptional = subjectRepository.findByName(subjectFromDb.getName());
        Assertions.assertTrue(subjectOptional.isPresent());
    }


    @Test
    void saveTest_Success() {
        Subject savedSubject = subjectRepository.save(subject);
        Optional<Subject> optionalSubject = subjectRepository.findById(savedSubject.getId());

        Assertions.assertTrue(optionalSubject.isPresent());
    }

    @Test
    void updateTest_Success(){
        Subject savedSubject = subjectRepository.save(subject);
        Subject resultSubject = subjectRepository.saveAndFlush(savedSubject);

        Assertions.assertNotNull(resultSubject);
    }

    @Test
    void deleteUserTest_Success(){
        Subject savedSubject = subjectRepository.save(subject);
        subjectRepository.deleteById(savedSubject.getId());

        Optional<Subject> optionalSubject = subjectRepository.findById(savedSubject.getId());
        Assertions.assertFalse(optionalSubject.isPresent());
    }
}
