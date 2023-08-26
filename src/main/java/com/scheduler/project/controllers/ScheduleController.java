package com.scheduler.project.controllers;

import com.scheduler.project.DTO.EditScheduleDTO;
import com.scheduler.project.DTO.ScheduleDTO;
import com.scheduler.project.DTO.ScheduleSelectDTO;
import com.scheduler.project.DTO.projections.ScheduleCompletionProjection;
import com.scheduler.project.services.scheduleServices.*;
import com.scheduler.project.services.scheduleServices.CreateScheduleService.CreateScheduleServiceException;
import com.scheduler.project.services.scheduleServices.EditScheduleService.EditScheduleServiceException;
import com.scheduler.project.services.scheduleServices.SelectMostDifficultScheduleService.SelectMostDifficultScheduleServiceException;
import com.scheduler.project.services.scheduleServices.SelectScheduleService.SelectScheduleServiceException;
import com.scheduler.project.services.scheduleServices.GetSchedulesCompletionDataService.GetSchedulesCompletionDataServiceException;
import com.scheduler.project.services.scheduleServices.GetSchedulesWhichCanBeCompletedService.GetSchedulesCompletedServiceException;
import com.scheduler.project.services.scheduleServices.DeleteScheduleService.DeleteScheduleServiceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final CreateScheduleService createScheduleService;
    private final EditScheduleService editSCheduleService;
    private final SelectScheduleService selectScheduleService;
    private final SelectMostDifficultScheduleService selectMostDifficultScheduleService;
    private final GetSchedulesCompletionDataService getSchedulesCompletionDataService;
    private final GetSchedulesWhichCanBeCompletedService getSchedulesWhichCanBeCompletedService;
    private final DeleteScheduleService deleteScheduleService;

    @Autowired
    public ScheduleController(CreateScheduleService createScheduleService, EditScheduleService editSCheduleService,
                              SelectScheduleService selectScheduleService, SelectMostDifficultScheduleService selectMostDifficultScheduleService,
                              GetSchedulesCompletionDataService getSchedulesCompletionDataService, GetSchedulesWhichCanBeCompletedService getSchedulesWhichCanBeCompletedService,
                              DeleteScheduleService deleteScheduleService) {
        this.createScheduleService = createScheduleService;
        this.editSCheduleService = editSCheduleService;
        this.selectScheduleService = selectScheduleService;
        this.selectMostDifficultScheduleService = selectMostDifficultScheduleService;
        this.getSchedulesCompletionDataService = getSchedulesCompletionDataService;
        this.getSchedulesWhichCanBeCompletedService = getSchedulesWhichCanBeCompletedService;
        this.deleteScheduleService = deleteScheduleService;
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

    @GetMapping("/selectMostDifficult")
    public ResponseEntity<?> selectMostDifficult(@RequestParam @NotNull Long user_id) {
        try {
            List<ScheduleSelectDTO> result = selectMostDifficultScheduleService.selectMostDifficultSchedules(user_id);
            return ResponseEntity.ok().body(result);
        }
        catch (SelectMostDifficultScheduleServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/selectSchedule")
    public ResponseEntity<?> selectSchedule(@RequestParam(name="schedule_id") @NotNull Long scheduleId) {
        try {
            ScheduleSelectDTO result = selectScheduleService.selectSchedule(scheduleId);

            return ResponseEntity.ok().body(result);
        }
        catch (SelectScheduleServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/selectUserSchedules")
    public ResponseEntity<?> selectUserSchedules(@RequestParam(name="user_id") @NotNull Long userId) {
        try {
            List<ScheduleSelectDTO> result = selectScheduleService.selectUserSchedules(userId);

            return ResponseEntity.ok().body(result);
        }
        catch (SelectScheduleServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getCompletionData")
    public ResponseEntity<?> getCompletionData(@RequestParam @NotNull Long user_id) {
        try {
            List<ScheduleCompletionProjection> result = getSchedulesCompletionDataService.getSchedulesCompletionData(user_id);
            return ResponseEntity.ok().body(result);
        }
        catch (GetSchedulesCompletionDataServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getPossibleComplete")
    public ResponseEntity<?> getCompletePossibleSchedules(@RequestParam @NotNull Long user_id) {
        try {
            List<ScheduleSelectDTO> result = getSchedulesWhichCanBeCompletedService.getSchedulesWhichCanBeCompleted(user_id);
            return ResponseEntity.ok().body(result);
        }
        catch (GetSchedulesCompletedServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/removeSchedule")
    public ResponseEntity<?> removeSchedule(@RequestParam(name = "schedule_id") @NotNull Long scheduleId) {
        try {
            deleteScheduleService.deleteSchedule(scheduleId);
            return ResponseEntity.ok().body("schedule with id=" + scheduleId + " was successfully deleted");
        }
        catch (DeleteScheduleServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
