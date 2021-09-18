package ch.addere.osv.domain.model.fields;

import static java.time.Instant.parse;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

/**
 * Withdrawn is the time an entry should be considered to have been withdrawn. Time is represented
 * as an FRC3339-formatted timestamp.
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class Withdrawn extends EntryDate {

  public Withdrawn(@NonNull Instant date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return date.toString();
  }

  public static Withdrawn create(@NonNull String date) {
    return new Withdrawn(parse(date));
  }
}
