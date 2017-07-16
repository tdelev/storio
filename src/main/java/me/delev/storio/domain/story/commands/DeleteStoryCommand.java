package me.delev.storio.domain.story.commands;

import lombok.Value;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

/**
 * Delete story command
 */
@Value
public class DeleteStoryCommand {

  @NotNull
  @TargetAggregateIdentifier
  private final String id;

}
