package me.delev.storio.domain;

import me.delev.storio.domain.story.Story;
import me.delev.storio.domain.story.commands.PostStoryCommand;
import me.delev.storio.domain.story.events.StoryPostedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Testing the story
 */
public class StoryTest {

  private FixtureConfiguration<Story> fixture;

  @Before
  public void setUp() throws Exception {
    fixture = Fixtures.newGivenWhenThenFixture(Story.class);
  }

  @Test
  public void testCreateStory() {
    fixture.given()
      .when(new PostStoryCommand("story1", "The author of the story", "The text"))
      .expectEvents(new StoryPostedEvent("story1", "The author of the story", "The text", LocalDateTime.now()));
  }
}
