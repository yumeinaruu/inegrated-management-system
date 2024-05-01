package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Group;
import com.yumeinaruu.iis.model.dto.group.GroupCreateDto;
import com.yumeinaruu.iis.model.dto.group.GroupFacultyUpdateDto;
import com.yumeinaruu.iis.model.dto.group.GroupNameUpdateDto;
import com.yumeinaruu.iis.model.dto.group.GroupSpecialityUpdateDto;
import com.yumeinaruu.iis.model.dto.group.GroupUpdateDto;
import com.yumeinaruu.iis.repository.FacultyRepository;
import com.yumeinaruu.iis.repository.GroupRepository;
import com.yumeinaruu.iis.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final FacultyRepository facultyRepository;
    private final SpecialityRepository specialityRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, FacultyRepository facultyRepository, SpecialityRepository specialityRepository) {
        this.groupRepository = groupRepository;
        this.facultyRepository = facultyRepository;
        this.specialityRepository = specialityRepository;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public Optional<Group> getGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    public Boolean createGroup(GroupCreateDto groupCreateDto) {
        Group group = new Group();
        group.setName(groupCreateDto.getName());
        if (facultyRepository.findByName(groupCreateDto.getFaculty().getName()).isPresent()) {
            group.setFacultyId(facultyRepository.findByName(groupCreateDto.getFaculty().getName()).get());
        }
        if (specialityRepository.findByName(groupCreateDto.getSpeciality().getName()).isPresent()) {
            group.setSpecialityId(specialityRepository.findByName(groupCreateDto.getSpeciality().getName()).get());
        }
        Group savedGroup = groupRepository.save(group);
        return getGroupById(savedGroup.getId()).isPresent();
    }

    public Boolean updateGroup(GroupUpdateDto groupUpdateDto) {
        Optional<Group> optionalGroup = groupRepository.findById(groupUpdateDto.getId());
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            group.setName(groupUpdateDto.getName());
            if (facultyRepository.findByName(groupUpdateDto.getFaculty().getName()).isPresent()) {
                group.setFacultyId(facultyRepository.findByName(groupUpdateDto.getFaculty().getName()).get());
            }
            if (specialityRepository.findByName(groupUpdateDto.getSpeciality().getName()).isPresent()) {
                group.setSpecialityId(specialityRepository.findByName(groupUpdateDto.getSpeciality().getName()).get());
            }
            Group updatedGroup = groupRepository.saveAndFlush(group);
            return updatedGroup.equals(group);
        }
        return false;
    }

    public Boolean updateName(GroupNameUpdateDto groupNameUpdateDto) {
        Optional<Group> optionalGroup = groupRepository.findById(groupNameUpdateDto.getId());
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            group.setName(groupNameUpdateDto.getName());
            Group updatedGroup = groupRepository.saveAndFlush(group);
            return updatedGroup.equals(group);
        }
        return false;
    }

    public Boolean updateFaculty(GroupFacultyUpdateDto groupFacultyUpdateDto) {
        Optional<Group> optionalGroup = groupRepository.findById(groupFacultyUpdateDto.getId());
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            if (facultyRepository.findByName(groupFacultyUpdateDto.getFaculty().getName()).isPresent()) {
                group.setFacultyId(facultyRepository.findByName(groupFacultyUpdateDto.getFaculty().getName()).get());
            }
            Group updatedGroup = groupRepository.saveAndFlush(group);
            return updatedGroup.equals(group);
        }
        return false;
    }

    public Boolean updateSpeciality(GroupSpecialityUpdateDto groupSpecialityUpdateDto) {
        Optional<Group> optionalGroup = groupRepository.findById(groupSpecialityUpdateDto.getId());
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            if (specialityRepository.findByName(groupSpecialityUpdateDto.getSpeciality().getName()).isPresent()) {
                group.setSpecialityId(specialityRepository.findByName(groupSpecialityUpdateDto.getSpeciality().getName()).get());
            }
            Group updatedGroup = groupRepository.saveAndFlush(group);
            return updatedGroup.equals(group);
        }
        return false;
    }

    public Boolean deleteGroup(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()) {
            return false;
        }
        groupRepository.delete(optionalGroup.get());
        return true;
    }

    public List<Group> getGroupsSortedByName() {
        return groupRepository.findAll(Sort.by("name"));
    }
}
