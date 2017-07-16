package me.delev.storio.api.story.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Post story request
 */
@Data
public class PostStoryRequest {
  @NotNull
  private String author;
  
  @NotNull
  private String text;
}
