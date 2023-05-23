package com.scheduler.project.services.tagServices;

import com.scheduler.project.DTO.TagDTO;
import com.scheduler.project.entities.TagEntity;
import com.scheduler.project.repos.TagRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class CreateTagService {
    @NoArgsConstructor
    public static class CreateTagServiceException extends Exception {
        public CreateTagServiceException(String message) {
            super(message);
        }
    }
    private final TagRepo tagRepo;

    @Autowired
    public CreateTagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    public TagEntity createTag(TagDTO tagDTO) throws CreateTagServiceException {
        String tagName = tagDTO.getTag_name();
        Collection<TagEntity> tags = tagRepo.getByTagName(tagName);

        if (tags.size() > 0) {
            throw new CreateTagServiceException("tag with name=" + tagName + " already exists - choose another name");
        }
        TagEntity tag = TagEntity.builder().tag_name(tagDTO.getTag_name()).build();

        return tagRepo.save(tag);
    }
}
