package ch.addere.osv.domain.model.fields;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Published is the time an entry was published. Time is represented as an FRC3339-formatted
 * timestamp.
 */
@EqualsAndHashCode(callSuper = true)
@Value
@Accessors(fluent = true)
public class Published extends EntryDate {

  public static final String PUBLISHED_KEY = "published";

  public Published(@NonNull Instant date) {
    this.date = date;
  }

  public Instant published() {
    return this.date;
  }
}
