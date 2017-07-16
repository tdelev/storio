package me.delev.storio.domain.story.commands;

import lombok.Value;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

/**
 * Post story command
 */
@Value
public class PostStoryCommand {

  @NotNull
  @TargetAggregateIdentifier
  private final String id;

  @NotNull
  private final String author;

  @NotNull
  private final String text;

}
