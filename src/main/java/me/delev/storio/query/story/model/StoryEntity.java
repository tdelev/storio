package me.delev.storio.query.story.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.delev.storio.coniguration.LocalDateTimeSerializer;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Entity used for read model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stories")
public class StoryEntity {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @Column(name = "author", nullable = false)
  private String author;

  @Column(name = "text", nullable = false)
  private String text;

  @Column(name = "likes", nullable = false)
  private int likes;

  @Column(name = "time_posted")
  @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime timePosted;
}
