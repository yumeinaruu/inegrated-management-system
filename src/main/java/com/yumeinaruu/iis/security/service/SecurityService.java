package com.yumeinaruu.iis.security.service;

import com.yumeinaruu.iis.exception.custom_exception.SameUserInDatabase;
import com.yumeinaruu.iis.model.Users;
import com.yumeinaruu.iis.repository.GroupRepository;
import com.yumeinaruu.iis.repository.UsersRepository;
import com.yumeinaruu.iis.security.model.Security;
import com.yumeinaruu.iis.security.model.dto.AuthRequestDto;
import com.yumeinaruu.iis.security.model.dto.GiveRoleDto;
import com.yumeinaruu.iis.security.model.dto.RegistrationDto;
import com.yumeinaruu.iis.security.model.dto.Roles;
import com.yumeinaruu.iis.security.model.dto.NotStudentRegistrationDto;
import com.yumeinaruu.iis.security.repository.SecurityRepository;
import com.yumeinaruu.iis.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SecurityService {
    private final SecurityRepository securityRepository;
    private final GroupRepository groupRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    @Autowired
    public SecurityService(SecurityRepository securityRepository, UsersRepository usersRepository,
                           GroupRepository groupRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils,
                           EmailService emailService) {
        this.securityRepository = securityRepository;
        this.usersRepository = usersRepository;
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void registration(RegistrationDto registrationDto) {
        Optional<Security> security = securityRepository.findByLogin(registrationDto.getLogin());
        if (security.isPresent()) {
            throw new SameUserInDatabase(registrationDto.getLogin());
        }
        Users user = new Users();
        user.setUsername(registrationDto.getUsername());
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        if (groupRepository.findByName(registrationDto.getGroup()).isPresent()) {
            user.setGroupId(groupRepository.findByName(registrationDto.getGroup()).get());
        }
        Users savedUser = usersRepository.save(user);

        Security userSecurity = new Security();
        userSecurity.setLogin(registrationDto.getLogin());
        userSecurity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userSecurity.setRole(Roles.STUDENT);
        userSecurity.setUserId(savedUser.getId());
        securityRepository.save(userSecurity);
        emailService.sendEmailNoAttachment(userSecurity.getLogin(), emailService.getCc(),
                "Registration in integrated management system", emailService.getRegistrationBody()
        + "\n Your login: " + registrationDto.getLogin() + "\n Your password: " + registrationDto.getPassword() +
                "\n Don't share to anyone this information");
    }

    @Transactional(rollbackFor = Exception.class)
    public void registrationForTeachers(NotStudentRegistrationDto registrationDto) {
        Optional<Security> security = securityRepository.findByLogin(registrationDto.getLogin());
        if (security.isPresent()) {
            throw new SameUserInDatabase(registrationDto.getLogin());
        }
        Users user = new Users();
        user.setUsername(registrationDto.getUsername());
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        Users savedUser = usersRepository.save(user);

        Security userSecurity = new Security();
        userSecurity.setLogin(registrationDto.getLogin());
        userSecurity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userSecurity.setRole(Roles.TEACHER);
        userSecurity.setUserId(savedUser.getId());
        securityRepository.save(userSecurity);
        emailService.sendEmailNoAttachment(userSecurity.getLogin(), emailService.getCc(),
                "Registration in integrated management system", emailService.getRegistrationBody()
                        + "\n Your login: " + registrationDto.getLogin() + "\n Your password: " + registrationDto.getPassword() +
                        "\n Don't share to anyone this information");
    }

    @Transactional(rollbackFor = Exception.class)
    public void registrationForAdmin(NotStudentRegistrationDto registrationDto) {
        Optional<Security> security = securityRepository.findByLogin(registrationDto.getLogin());
        if (security.isPresent()) {
            throw new SameUserInDatabase(registrationDto.getLogin());
        }
        Users user = new Users();
        user.setUsername(registrationDto.getUsername());
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        Users savedUser = usersRepository.save(user);

        Security userSecurity = new Security();
        userSecurity.setLogin(registrationDto.getLogin());
        userSecurity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userSecurity.setRole(Roles.ADMIN);
        userSecurity.setUserId(savedUser.getId());
        securityRepository.save(userSecurity);
        emailService.sendEmailNoAttachment(userSecurity.getLogin(), emailService.getCc(),
                "Registration in integrated management system", emailService.getRegistrationBody()
                        + "\n Your login: " + registrationDto.getLogin() + "\n Your password: " + registrationDto.getPassword() +
                        "\n Don't share to anyone this information");
    }

    public Optional<String> generateToken(AuthRequestDto authRequestDto) {
        Optional<Security> security = securityRepository.findByLogin(authRequestDto.getLogin());
        if (security.isPresent()
                && passwordEncoder.matches(authRequestDto.getPassword(), security.get().getPassword())) {
            return Optional.of(jwtUtils.generateToken(authRequestDto.getLogin()));
        }
        return Optional.empty();
    }

    public Boolean giveAdmin(GiveRoleDto giveRoleDto) {
        Optional<Security> securityOptional = securityRepository.findById(giveRoleDto.getId());
        if (securityOptional.isPresent()) {
            Security security = securityOptional.get();
            if (security.getRole().equals(Roles.STUDENT) || security.getRole().equals(Roles.TEACHER)) {
                security.setRole(Roles.ADMIN);
            }
            Security savedSecurity = securityRepository.saveAndFlush(security);
            return savedSecurity.equals(security);
        }
        return false;
    }

    public Boolean giveTeacher(GiveRoleDto giveRoleDto) {
        Optional<Security> securityOptional = securityRepository.findById(giveRoleDto.getId());
        if (securityOptional.isPresent()) {
            Security security = securityOptional.get();
            if (security.getRole().equals(Roles.STUDENT)) {
                security.setRole(Roles.TEACHER);
            }
            Security savedSecurity = securityRepository.saveAndFlush(security);
            return savedSecurity.equals(security);
        }
        return false;
    }

    public Boolean downgradeAdmin(GiveRoleDto giveRoleDto) {
        Optional<Security> securityOptional = securityRepository.findById(giveRoleDto.getId());
        if (securityOptional.isPresent()) {
            Security security = securityOptional.get();
            if (security.getRole().equals(Roles.ADMIN)) {
                security.setRole(Roles.TEACHER);
            }
            Security savedSecurity = securityRepository.saveAndFlush(security);
            return savedSecurity.equals(security);
        }
        return false;
    }

    public Boolean downgradeTeacher(GiveRoleDto giveRoleDto) {
        Optional<Security> securityOptional = securityRepository.findById(giveRoleDto.getId());
        if (securityOptional.isPresent()) {
            Security security = securityOptional.get();
            if (security.getRole().equals(Roles.TEACHER)) {
                security.setRole(Roles.STUDENT);
            }
            Security savedSecurity = securityRepository.saveAndFlush(security);
            return savedSecurity.equals(security);
        }
        return false;
    }
}
