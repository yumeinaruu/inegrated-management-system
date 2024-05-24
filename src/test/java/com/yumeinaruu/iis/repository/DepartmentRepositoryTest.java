package com.yumeinaruu.iis.repository;

import com.yumeinaruu.iis.model.Department;
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
public class DepartmentRepositoryTest {
    @Autowired
    DepartmentRepository departmentRepository;
    static Department department;

    @BeforeAll
    static void beforeAll() {
        department = new Department();
        department.setName("test_name");
        department.setId(5L);
    }

    @Test
    void findAllTest_Success() {
        List<Department> departments = departmentRepository.findAll();

        Assertions.assertNotNull(departments);
    }

    @Test
    void findByIdTest_Success() {
        Department departmentFromDb = departmentRepository.findAll().get(0);
        Optional<Department> departmentOptional = departmentRepository.findById(departmentFromDb.getId());

        Assertions.assertTrue(departmentOptional.isPresent());
    }

    @Test
    void FindByNameTest_Success() {
        Department departmentFromDb = departmentRepository.findAll().get(0);
        Optional<Department> departmentOptional = departmentRepository.findByName(departmentFromDb.getName());
        Assertions.assertTrue(departmentOptional.isPresent());
    }

    @Test
    void saveTest_Success() {
        Department savedDepartment = departmentRepository.save(department);
        Optional<Department> departmentOptional = departmentRepository.findById(savedDepartment.getId());

        Assertions.assertTrue(departmentOptional.isPresent());
    }

    @Test
    void updateTest_Success(){
        Department savedDepartment = departmentRepository.save(department);
        Department resultDepartment = departmentRepository.saveAndFlush(savedDepartment);

        Assertions.assertNotNull(resultDepartment);
    }

    @Test
    void deleteUserTest_Success(){
        Department savedDepartment = departmentRepository.save(department);
        departmentRepository.deleteById(savedDepartment.getId());

        Optional<Department> departmentOptional = departmentRepository.findById(savedDepartment.getId());
        Assertions.assertFalse(departmentOptional.isPresent());
    }
}
