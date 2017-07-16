package me.delev.storio.query.story;

import me.delev.storio.domain.story.commands.StoryChangeAuthorCommand;
import me.delev.storio.domain.story.events.StoryDeletedEvent;
import me.delev.storio.domain.story.events.StoryLikedEvent;
import me.delev.storio.domain.story.events.StoryPostedEvent;
import me.delev.storio.query.story.model.StoryEntity;
import me.delev.storio.query.story.repository.StoryReadRepository;
import me.delev.storio.utils.DateUtils;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Story persist event handler
 */
@Component
public class StoryPersistEventHandler {

  final Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private StoryReadRepository repository;

  @Inject
  private EventBus eventBus;

  @EventHandler
  void on(StoryPostedEvent storyPostedEvent, @Timestamp DateTime time) {
    log.debug("Persisting story: {}", storyPostedEvent);
    StoryEntity entity = StoryEntity.builder()
      .id(storyPostedEvent.getId())
      .author(storyPostedEvent.getAuthor())
      .text(storyPostedEvent.getText())
      .timePosted(DateUtils.jodaToJava(time))
      .likes(0).build();
    repository.save(entity);
    log.debug("Story persisted");
  }

  @EventHandler
  void on(StoryLikedEvent storyLikedEvent) {
    log.debug("Updating story likes: {}", storyLikedEvent);
    StoryEntity entity = repository.findOne(storyLikedEvent.getId());
    entity.setLikes(entity.getLikes() + 1);
    repository.save(entity);
  }

  @EventHandler
  void on(StoryChangeAuthorCommand changeAuthorCommand) {
    log.debug("Updating story author: {}", changeAuthorCommand);
    StoryEntity entity = repository.findOne(changeAuthorCommand.getId());
    entity.setAuthor(changeAuthorCommand.getAuthor());
    repository.save(entity);
  }

  @EventHandler
  void on(StoryDeletedEvent storyDeletedEvent) {
    log.debug("Deleting story: {}", storyDeletedEvent.getId());
    repository.delete(storyDeletedEvent.getId());
  }

}
