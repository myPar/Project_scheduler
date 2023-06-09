package com.scheduler.project.repos;

import com.scheduler.project.DTO.projections.ScheduleCompletionProjection;
import com.scheduler.project.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepo extends JpaRepository<ScheduleEntity, Long> {
    @Query(value = "with schedules_difficult as (\n" +
            "select SUM(count_to_complete * difficult) as schedule_difficult, id from\n" +
            "(\n" +
            "select * from schedules inner join\n" +
            "(select si.schedule_id, si.count_to_complete, si.difficult from schedule_items si) sc_items\n" +
            "on schedules.id=sc_items.schedule_id where schedules.user_id = :user_id) sc_si_joined\n" +
            "group by sc_si_joined.id)\n" +
            "\n" +
            "select id from schedules_difficult where schedules_difficult.schedule_difficult = (select max(schedule_difficult) from schedules_difficult)",
            nativeQuery = true)
    List<Long> selectMostDifficultSchedules(@Param(value = "user_id") Long user_id);

    @Query(value = "select * from schedules inner join (select si.schedule_id, si.count_to_complete, si.difficult\n" +
            "from schedule_items si) sc_items on schedules.id=sc_items.schedule_id\n" +
            "where schedules.id = :sc_id_param", nativeQuery = true)
    ScheduleEntity selectScheduleById(@Param(value = "sc_id_param") Long scheduleId);

    @Query(value = "select id from schedules where schedules.user_id = :userId", nativeQuery = true)
    List<Long> selectUserSchedulesIds(@Param(value = "userId") Long userId);

    @Query(value = "with schedules_completion_data as\n" +
            "\n" +
            "(select sc_si_joined.difficult, completed_data.stat_difficult, sc_si_joined.schedule_name, sc_si_joined.count_to_complete, sc_si_joined.schedule_id\n" +
            "from (schedules inner join (select si.schedule_id, si.count_to_complete, si.difficult\n" +
            "from schedule_items si) sc_items on schedules.id=sc_items.schedule_id) sc_si_joined\n" +
            "left outer join (select difficult as stat_difficult from completed_task_data ctd \n" +
            "where ctd.schedule_completion = false and ctd.user_id = :user_id) completed_data\n" +
            "on completed_data.stat_difficult = sc_si_joined.difficult\n" +
            "where user_id= :user_id)\n" +
            "\n" +
            "select sc_cmp_dt.count_to_complete, total.total_completed, total.difficult, total.schedule_id, total.schedule_name\n" +
            "from (select distinct difficult, count_to_complete, schedule_id from schedules_completion_data) sc_cmp_dt\n" +
            "inner join\n" +
            "(select COUNT(sch_comp_data.stat_difficult) as total_completed, \n" +
            "sch_comp_data.difficult, sch_comp_data.schedule_id, sch_comp_data.schedule_name \n" +
            "from (select * from schedules_completion_data) sch_comp_data\n" +
            "group by sch_comp_data.schedule_id, sch_comp_data.schedule_name, sch_comp_data.difficult) total\n" +
            "on sc_cmp_dt.schedule_id = total.schedule_id and sc_cmp_dt.difficult = total.difficult\n" +
            "order by schedule_id, difficult", nativeQuery = true)
    List<ScheduleCompletionProjection> getSchedulesCompletionPossibilityData(@Param(value = "user_id") Long userId);

    @Query(value = "with schedules_completion_data as\n" +
            "\n" +
            "(select sc_si_joined.difficult, completed_data.stat_difficult, sc_si_joined.schedule_name, sc_si_joined.count_to_complete, sc_si_joined.schedule_id\n" +
            "from (schedules inner join (select si.schedule_id, si.count_to_complete, si.difficult\n" +
            "from schedule_items si) sc_items on schedules.id=sc_items.schedule_id) sc_si_joined\n" +
            "left outer join (select difficult as stat_difficult from completed_task_data ctd \n" +
            "where ctd.schedule_completion = false and ctd.user_id = :user_id) completed_data\n" +
            "on completed_data.stat_difficult = sc_si_joined.difficult\n" +
            "where user_id= :user_id)\n" +
            "\n" +
            "select schedules.id from schedules left outer join\n" +
            "(select distinct schedule_id from\n" +
            "(select sc_cmp_dt.count_to_complete, total.total_completed, total.difficult, total.schedule_id, total.schedule_name\n" +
            "from (select distinct difficult, count_to_complete, schedule_id from schedules_completion_data) sc_cmp_dt\n" +
            "inner join\n" +
            "(select COUNT(sch_comp_data.stat_difficult) as total_completed, \n" +
            "sch_comp_data.difficult, sch_comp_data.schedule_id, sch_comp_data.schedule_name \n" +
            "from (select * from schedules_completion_data) sch_comp_data\n" +
            "group by sch_comp_data.schedule_id, sch_comp_data.schedule_name, sch_comp_data.difficult) total\n" +
            "on sc_cmp_dt.schedule_id = total.schedule_id and sc_cmp_dt.difficult = total.difficult\n" +
            "order by schedule_id, difficult) complete_sc_stat\n" +
            "where complete_sc_stat.total_completed < complete_sc_stat.count_to_complete) cant_be_completed\n" +
            "on schedules.id=cant_be_completed.schedule_id\n" +
            "where schedules.user_id= :user_id and cant_be_completed.schedule_id is null", nativeQuery = true)
    List<Long> getSchedulesIdsWhichCanBeCompleted(@Param(value="user_id") Long user_id);
}
