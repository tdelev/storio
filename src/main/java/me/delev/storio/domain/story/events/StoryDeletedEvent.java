package me.delev.storio.domain.story.events;

import lombok.Value;

/**
 * Story liked event
 */
@Value
public class StoryDeletedEvent implements StoryEvent {
  private final String id;
}
