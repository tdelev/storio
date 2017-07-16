package me.delev.storio.coniguration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @LocalDateTime serializer
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
  @Override
  public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException {
    jgen.writeString(DateTimeFormatter.ISO_DATE_TIME.format(value));
  }
}
