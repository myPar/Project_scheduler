package com.scheduler.project.controllers;

import com.scheduler.project.DTO.EditScheduleDTO;
import com.scheduler.project.DTO.ScheduleDTO;
import com.scheduler.project.services.scheduleServices.CreateScheduleService;
import com.scheduler.project.services.scheduleServices.CreateScheduleService.CreateScheduleServiceException;
import com.scheduler.project.services.scheduleServices.EditScheduleService;
import com.scheduler.project.services.scheduleServices.EditScheduleService.EditScheduleServiceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final CreateScheduleService createScheduleService;
    private final EditScheduleService editSCheduleService;

    @Autowired
    public ScheduleController(CreateScheduleService createScheduleService, EditScheduleService editSCheduleService) {
        this.createScheduleService = createScheduleService;
        this.editSCheduleService = editSCheduleService;
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
    @PutMapping("/edit")
    public ResponseEntity<?> editSchedule(@RequestBody @Valid EditScheduleDTO editScheduleDTO) {
        try {
            editSCheduleService.editSchedule(editScheduleDTO);

            return ResponseEntity.ok().body("schedule with id=" + editScheduleDTO.getId() + " was successfully edited");
        }
        catch (EditScheduleServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
