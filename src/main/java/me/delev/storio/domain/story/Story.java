package me.delev.storio.domain.story;

import me.delev.storio.domain.story.commands.DeleteStoryCommand;
import me.delev.storio.domain.story.commands.LikeStoryCommand;
import me.delev.storio.domain.story.commands.PostStoryCommand;
import me.delev.storio.domain.story.commands.StoryChangeAuthorCommand;
import me.delev.storio.domain.story.events.StoryDeletedEvent;
import me.delev.storio.domain.story.events.StoryLikedEvent;
import me.delev.storio.domain.story.events.StoryPostedEvent;
import me.delev.storio.utils.DateUtils;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * Story
 */
public class Story extends AbstractAnnotatedAggregateRoot<String> {
  final Logger log = LoggerFactory.getLogger(getClass());

  @AggregateIdentifier
  private String id;

  private int likes;
  private String author;
  private String text;

  private LocalDateTime timePosted;

  public Story() {
    log.debug("Story constuctor");
    likes = 0;
  }

  @CommandHandler
  public Story(PostStoryCommand postStoryCommand) {
    log.debug("post story command");
    apply(new StoryPostedEvent(postStoryCommand.getId(),
      postStoryCommand.getAuthor(), postStoryCommand.getText()));
  }

  @CommandHandler
  void on(LikeStoryCommand likeStoryCommand) {
    log.debug("LikeStoryCommand: {}", likeStoryCommand.getId());
    ++likes;
    apply(new StoryLikedEvent(likeStoryCommand.getId()));
  }

  @CommandHandler
  void on(DeleteStoryCommand deleteStoryCommand) {
    log.debug("Delete story command: {}", deleteStoryCommand.getId());
    markDeleted();
    apply(new StoryDeletedEvent(deleteStoryCommand.getId()));
  }

  @CommandHandler
  void on(StoryChangeAuthorCommand changeAuthorCommand) {
    log.debug("Story change author command: {}", changeAuthorCommand.getId());
    apply(new StoryChangeAuthorCommand(changeAuthorCommand.getId(), changeAuthorCommand.getAuthor()));
  }

  @EventSourcingHandler
  void on(StoryPostedEvent storyPostedEvent, @Timestamp DateTime time) {
    log.debug("Story event: {}", storyPostedEvent.getId());
    this.id = storyPostedEvent.getId();
    this.author = storyPostedEvent.getAuthor();
    this.text = storyPostedEvent.getText();
    this.timePosted = DateUtils.jodaToJava(time);
  }

}
