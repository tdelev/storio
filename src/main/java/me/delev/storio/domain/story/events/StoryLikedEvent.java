package me.delev.storio.domain.story.events;

import lombok.Value;

/**
 * Story posted event
 */
@Value
public class StoryLikedEvent implements StoryEvent {
  private final String id;
}
