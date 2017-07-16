package me.delev.storio.coniguration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom Jackson parser for client side date strings.
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  private static final Logger log = LoggerFactory.getLogger(LocalDateTimeDeserializer.class);

  @Override
  public LocalDateTime deserialize(JsonParser parser,
                                   DeserializationContext context) throws IOException {
    String date = parser.getText();
    try {
      return LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
    } catch (IllegalArgumentException e) {
      log.debug("InvalidArgumentException", e);
      return null;
    }
  }
}
