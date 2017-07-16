package me.delev.storio.domain.story.events;

import lombok.Value;

/**
 * Story posted event
 */
@Value
public class StoryPostedEvent implements StoryEvent {
  private final String id;

  private final String author;

  private final String text;
}
