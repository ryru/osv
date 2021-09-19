package ch.addere.osv.domain.model.fields;

import static java.time.Instant.parse;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Modified is the time an entry was last modified. Time is represented as an FRC3339-formatted
 * timestamp.
 */
@EqualsAndHashCode(callSuper = true)
@Value
@Accessors(fluent = true)
public class Modified extends EntryDate {

  public static final String MODIFIED = "modified";

  public Modified(@NonNull Instant date) {
    this.date = date;
  }

  @Override
  public String toJson() {
    return date.toString();
  }

  public static Modified create(@NonNull String date) {
    return new Modified(parse(date));
  }
}
