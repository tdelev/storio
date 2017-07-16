package me.delev.storio.domain.story.events;

import lombok.Value;

/**
 * Story changed author event
 */
@Value
public class StoryChangedAuthorEvent implements StoryEvent {
  private final String id;

  private final String author;
}
