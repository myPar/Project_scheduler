package com.scheduler.project.services.tagServices;


import com.scheduler.project.entities.TagEntity;
import com.scheduler.project.repos.TagRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteTagService {
    private final TagRepo tagRepo;

    @NoArgsConstructor
    public static class DeleteTagServiceException extends Exception {
        public DeleteTagServiceException(String message) {
            super(message);
        }
    }

    @Autowired
    public DeleteTagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    public void deleteTag(Long id) throws DeleteTagServiceException {
        Optional<TagEntity> tag = tagRepo.findById(id);

        if (tag.isEmpty()) {
            throw new DeleteTagServiceException("not tag with id=" + id);
        }
        tagRepo.removeTag(id);
    }
}
