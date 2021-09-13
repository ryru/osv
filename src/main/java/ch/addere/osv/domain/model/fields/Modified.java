package ch.addere.osv.domain.model.fields;

import static java.time.Instant.parse;

import java.time.Instant;
import lombok.NonNull;
import lombok.Value;

/**
 * Modified is the time an entry was last modified. Time is represented as an FRC3339-formatted
 * timestamp.
 */
@Value
public class Modified {

  Instant date;

  @Override
  public String toString() {
    return date.toString();
  }

  public static Modified create(@NonNull String date) {
    return new Modified(parse(date));
  }
}
