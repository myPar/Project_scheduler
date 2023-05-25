package com.scheduler.project.DTO.dto_converters;

import com.scheduler.project.DTO.ResponseScheduleDTO;
import com.scheduler.project.DTO.ScheduleItemDTO;
import com.scheduler.project.entities.ScheduleEntity;
import com.scheduler.project.entities.ScheduleItemEntity;
import com.scheduler.project.mappers.ScheduleMapper;

import java.util.ArrayList;
import java.util.List;

public class DTOconverter {
    public static ResponseScheduleDTO getScheduleResponseDTOfromEntity(ScheduleEntity entity) {
        ScheduleMapper mapper = ScheduleMapper.INSTANCE;
        ResponseScheduleDTO responseDTO = mapper.getResponseScheduleDTO(entity);

        // map schedule items
        List<ScheduleItemDTO> scheduleItemDTOlist = new ArrayList<>();
        if (entity.getScheduleItems() != null) {
            for (ScheduleItemEntity scheduleItemEntity : entity.getScheduleItems()) {
                ScheduleItemDTO itemDTO = mapper.getScheduleItemDTO(scheduleItemEntity);
                scheduleItemDTOlist.add(itemDTO);
            }
        }
        responseDTO.setScheduleItems(scheduleItemDTOlist);

        return responseDTO;
    }
}