package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Department;
import com.yumeinaruu.iis.model.dto.DepartmentDtoCreate;
import com.yumeinaruu.iis.model.dto.DepartmentDtoUpdate;
import com.yumeinaruu.iis.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Boolean createDepartment(DepartmentDtoCreate departmentDtoCreate) {
        Department department = new Department();
        department.setName(departmentDtoCreate.getName());
        Department createDepartment = departmentRepository.save(department);
        return getDepartmentById(createDepartment.getId()).isPresent();
    }

    public Boolean updateDepartment(DepartmentDtoUpdate departmentDtoUpdate) {
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentDtoUpdate.getId());
        if (optionalDepartment.isPresent()) {
            Department updatedDepartment = optionalDepartment.get();
            updatedDepartment.setName(departmentDtoUpdate.getName());
            Department updateDep = departmentRepository.saveAndFlush(updatedDepartment);
            return updatedDepartment.equals(updateDep);
        }
        return false;
    }

    public Boolean deleteDepartment(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()) {
            return false;
        }
        departmentRepository.delete(optionalDepartment.get());
        return true;
    }

    public List<Department> getDepartmentsSortedByName() {
        return departmentRepository.findAll(Sort.by("name"));
    }
}
