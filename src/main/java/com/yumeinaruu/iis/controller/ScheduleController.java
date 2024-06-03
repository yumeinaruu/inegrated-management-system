package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.model.Schedule;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleCreateDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateGroupDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateSubjectDto;
import com.yumeinaruu.iis.model.dto.schedule.ScheduleUpdateTimeDto;
import com.yumeinaruu.iis.service.ScheduleService;
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
@RequestMapping("/schedule")
@Tag(name = "Work with schedules", description = "Methods to work with schedules")
@SecurityRequirement(name = "Bearer Authentication")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Operation(summary = "Gives info about all schedules")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedules info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Schedules not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        if (schedules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about schedule by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedule info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Schedule not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        if (schedule.isPresent()) {
            return new ResponseEntity<>(schedule.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gives info about schedule by group name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedule info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Schedule not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/group/{group}")
    public ResponseEntity<List<Schedule>> getSchedulesByGroup(@PathVariable String group) {
        List<Schedule> schedules = scheduleService.getScheduleByGroupName(group);
        if (schedules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @Operation(summary = "Gives info about schedule by subject name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedule info has returned successfully"),
            @ApiResponse(responseCode = "404", description = "Schedule not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<Schedule>> getSchedulesBySubject(@PathVariable String subject) {
        List<Schedule> schedules = scheduleService.getScheduleBySubjectName(subject);
        if (schedules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @Operation(summary = "Creates schedule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Schedule was created successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> createSchedule(@RequestBody @Valid ScheduleCreateDto scheduleCreateDto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(scheduleService.createSchedule(scheduleCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates schedule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Schedule was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateSchedule(@RequestBody @Valid ScheduleUpdateDto scheduleUpdateDto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleUpdateDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates schedule time")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Schedule was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/time")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateScheduleTime(@RequestBody @Valid ScheduleUpdateTimeDto scheduleUpdateTimeDto,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(scheduleService.scheduleUpdateTime(scheduleUpdateTimeDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates schedule's subject")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Schedule was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/subject")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateScheduleSubject(@RequestBody @Valid ScheduleUpdateSubjectDto scheduleUpdateSubjectDto,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(scheduleService.scheduleUpdateSubject(scheduleUpdateSubjectDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Updates schedule's group")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Schedule was updated successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PutMapping("/group")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> updateScheduleGroup(@RequestBody @Valid ScheduleUpdateGroupDto scheduleUpdateGroupDto,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(scheduleService.scheduleUpdateGroup(scheduleUpdateGroupDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Deletes schedule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Schedule was deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSchedule(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.deleteScheduleById(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }


}
