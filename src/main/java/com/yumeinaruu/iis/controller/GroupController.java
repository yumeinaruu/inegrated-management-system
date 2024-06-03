package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.model.Group;
import com.yumeinaruu.iis.model.dto.group.GroupCreateDto;
import com.yumeinaruu.iis.model.dto.group.GroupFacultyUpdateDto;
import com.yumeinaruu.iis.model.dto.group.GroupNameUpdateDto;
import com.yumeinaruu.iis.model.dto.group.GroupSpecialityUpdateDto;
import com.yumeinaruu.iis.model.dto.group.GroupUpdateDto;
import com.yumeinaruu.iis.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
@Tag(name = "Work with groups", description = "Methods to work with groups")
@SecurityRequirement(name = "Bearer Authentication")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "Gives info about all groups")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Groups info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Groups not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        if (groups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about group by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupService.getGroupById(id);
        if (group.isPresent()) {
            return new ResponseEntity<>(group.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gives info about group by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<Group> getGroupByName(@PathVariable String name) {
        Optional<Group> group = groupService.getGroupByName(name);
        if (group.isPresent()) {
            return new ResponseEntity<>(group.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gives info about groups sorted by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Groups info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Groups not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/name-sort")
    public ResponseEntity<List<Group>> getGroupsSortedByName() {
        List<Group> groups = groupService.getGroupsSortedByName();
        if (groups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @Operation(summary = "Creates group")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Group was created successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> createGroup(@RequestBody @Valid GroupCreateDto groupCreateDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(groupService.createGroup(groupCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates group")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateGroup(@RequestBody @Valid GroupUpdateDto groupUpdateDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(groupService.updateGroup(groupUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates group name")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateGroupName(@RequestBody @Valid GroupNameUpdateDto groupNameUpdateDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(groupService.updateName(groupNameUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates group faculty")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/faculty")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateFaculty(@RequestBody @Valid GroupFacultyUpdateDto groupFacultyUpdateDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(groupService.updateFaculty(groupFacultyUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates group speciality")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/speciality")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateSpeciality(@RequestBody @Valid GroupSpecialityUpdateDto groupSpecialityUpdateDto,
                                                       BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(groupService.updateSpeciality(groupSpecialityUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Deletes group")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group was deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> deleteGroupById(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.deleteGroup(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}

