package com.scheduler.project.services.taskServices;

import com.scheduler.project.entities.TagEntity;
import com.scheduler.project.entities.TaskEntity;
import com.scheduler.project.entities.TasksTagsEntity;
import com.scheduler.project.repos.TagRepo;
import com.scheduler.project.repos.TaskRepo;
import com.scheduler.project.repos.TasksTagsRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AddTagToTaskService {
    @NoArgsConstructor
    public static class AddTagToTaskServiceException extends Exception {
        public AddTagToTaskServiceException(String message) {
            super(message);
        }
    }
    private final TasksTagsRepo tasksTagsRepo;
    private final TaskRepo taskRepo;
    private final TagRepo tagRepo;

    @Autowired
    public AddTagToTaskService(TasksTagsRepo tasksTagsRepo, TagRepo tagRepo, TaskRepo taskRepo) {
        this.tasksTagsRepo = tasksTagsRepo;
        this.tagRepo = tagRepo;
        this.taskRepo = taskRepo;
    }

    public void addTagToTask(Long task_id, Long tag_id) throws AddTagToTaskServiceException {
        Optional<TaskEntity> task = taskRepo.findById(task_id);

        if (task.isEmpty()) {
            throw new AddTagToTaskServiceException("no task with id=" + tag_id);
        }
        Optional<TagEntity> tag = tagRepo.findById(tag_id);
        if (tag.isEmpty()) {
            throw new AddTagToTaskServiceException("no tag with id=" + tag_id);
        }
        Collection<TasksTagsEntity> entity = tasksTagsRepo.checkTaskHasTag(task_id, tag_id);
        if (entity.size() > 0) {
            throw new AddTagToTaskServiceException("task with id=" + task_id + " already contains tag with id=" + tag_id);
        }
        tasksTagsRepo.insertItem(tag_id, task_id);
    }
}
