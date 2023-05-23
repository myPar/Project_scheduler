package com.scheduler.project.repos;

import com.scheduler.project.entities.TagEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface TagRepo extends CrudRepository<TagEntity, Long> {

    @Modifying
    @Transactional
    @Query(value="Delete from tags tag where tag.id=:id", nativeQuery = true)
    void removeTag(@Param("id") Long id);

    @Query(value="Select * from tags t where t.tag_name=:tag_name", nativeQuery = true)
    Collection<TagEntity> getByTagName(@Param("tag_name") String name);
}
