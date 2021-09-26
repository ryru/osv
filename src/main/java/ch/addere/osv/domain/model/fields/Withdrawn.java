package ch.addere.osv.domain.model.fields;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Withdrawn is the time an entry should be considered to have been withdrawn. Time is represented
 * as an FRC3339-formatted timestamp.
 */
@EqualsAndHashCode(callSuper = true)
@Value
@Accessors(fluent = true)
public class Withdrawn extends EntryDate {

  public static final String WITHDRAWN_KEY = "withdrawn";

  public Withdrawn(@NonNull Instant date) {
    this.date = date;
  }

  public Instant withdrawn() {
    return this.date;
  }
}
