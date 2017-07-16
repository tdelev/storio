package me.delev.storio.domain.story.commands;

import lombok.Value;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

/**
 * Like story command
 */
@Value
public class StoryChangeAuthorCommand {

  @NotNull
  @TargetAggregateIdentifier
  private final String id;

  private final String author;

}
