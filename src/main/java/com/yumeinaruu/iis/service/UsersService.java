package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Users;
import com.yumeinaruu.iis.model.dto.users.UsersCreateDto;
import com.yumeinaruu.iis.model.dto.users.UsersUpdateDto;
import com.yumeinaruu.iis.model.dto.users.UsersUpdateGroupDto;
import com.yumeinaruu.iis.model.dto.users.UsersUsernameUpdateDto;
import com.yumeinaruu.iis.repository.GroupRepository;
import com.yumeinaruu.iis.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, GroupRepository groupRepository) {
        this.usersRepository = usersRepository;
        this.groupRepository = groupRepository;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Cacheable(value = "UsersService::getUserById", key = "#id")
    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    @Cacheable(value = "UsersService::getUserByUsername", key = "#username")
    public Optional<Users> getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public List<Users> getUsersSortedByUsername() {
        return usersRepository.findAll(Sort.by("username"));
    }

    @Caching(cacheable = {
            @Cacheable(value = "UsersService::getUserByUsername", key = "#usersCreateDto.username")

    })
    public Boolean createUser(UsersCreateDto usersCreateDto) {
        Users user = new Users();
        user.setUsername(usersCreateDto.getUsername());
        if (groupRepository.findByName(usersCreateDto.getGroup().getName()).isPresent()) {
            user.setGroupId(groupRepository.findByName(usersCreateDto.getGroup().getName()).get());
        }
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        Users savedUser = usersRepository.save(user);
        return getUserById(savedUser.getId()).isPresent();
    }

    @Caching(put = {
            @CachePut(value = "UsersService::getUserById", key = "#usersUpdateDto.id"),
            @CachePut(value = "UsersService::getUserByUsername", key = "#usersUpdateDto.username")

    })
    public Boolean updateUser(UsersUpdateDto usersUpdateDto) {
        Optional<Users> optionalUser = usersRepository.findById(usersUpdateDto.getId());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setUsername(usersUpdateDto.getUsername());
            if (groupRepository.findByName(usersUpdateDto.getGroup().getName()).isPresent()) {
                user.setGroupId(groupRepository.findByName(usersUpdateDto.getGroup().getName()).get());
            }
            user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Users savedUser = usersRepository.save(user);
            return savedUser.equals(user);
        }
        return false;
    }

    @Caching(put = {
            @CachePut(value = "UsersService::getUserById", key = "#usersUpdateDto.id"),
            @CachePut(value = "UsersService::getUserByUsername", key = "#usersUpdateDto.username")

    })
    public Boolean updateUsersUsername(UsersUsernameUpdateDto usersUsernameUpdateDto) {
        Optional<Users> optionalUser = usersRepository.findById(usersUsernameUpdateDto.getId());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setUsername(usersUsernameUpdateDto.getUsername());
            user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Users savedUser = usersRepository.save(user);
            return savedUser.equals(user);
        }
        return false;
    }

    @Caching(put = {
            @CachePut(value = "UsersService::getUserById", key = "#usersUpdateDto.id")
    })
    public Boolean updateUsersGroup(UsersUpdateGroupDto usersUpdateGroupDto) {
        Optional<Users> optionalUser = usersRepository.findById(usersUpdateGroupDto.getId());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (groupRepository.findByName(usersUpdateGroupDto.getGroup().getName()).isPresent()) {
                user.setGroupId(groupRepository.findByName(usersUpdateGroupDto.getGroup().getName()).get());
            }
            user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Users savedUser = usersRepository.save(user);
            return savedUser.equals(user);
        }
        return false;
    }

    @CacheEvict(value = "UsersService::getUserById", key = "#id")
    public Boolean deleteUser(Long id) {
        Optional<Users> optionalUser = usersRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return false;
        }
        usersRepository.delete(optionalUser.get());
        return true;
    }
}
