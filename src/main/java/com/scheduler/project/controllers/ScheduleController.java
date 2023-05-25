package com.scheduler.project.controllers;

import com.scheduler.project.DTO.ScheduleDTO;
import com.scheduler.project.services.scheduleServices.CreateScheduleService;
import com.scheduler.project.services.scheduleServices.CreateScheduleService.CreateScheduleServiceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private CreateScheduleService createScheduleService;

    @Autowired
    public ScheduleController(CreateScheduleService createScheduleService) {
        this.createScheduleService = createScheduleService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO) {
        try {
            return ResponseEntity.ok().body(createScheduleService.createSchedule(scheduleDTO));
        }
        catch (CreateScheduleServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
