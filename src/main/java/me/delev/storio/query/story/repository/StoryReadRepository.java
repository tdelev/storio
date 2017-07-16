package me.delev.storio.query.story.repository;

import me.delev.storio.query.story.model.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * JPA repository for stories
 */
public interface StoryReadRepository extends JpaRepository<StoryEntity, String> {
}
