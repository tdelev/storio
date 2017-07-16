package me.delev.storio.utils;

import org.joda.time.DateTime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Date conversion utils
 */
public class DateUtils {
  public static LocalDateTime jodaToJava(DateTime time) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time.toDate().getTime()), ZoneId.systemDefault());
  }
}
