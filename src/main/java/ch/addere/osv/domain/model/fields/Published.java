package ch.addere.osv.domain.model.fields;

import static java.time.Instant.parse;

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

  public Published(@NonNull Instant date) {
    this.date = date;
  }

  @Override
  public String toJson() {
    return date.toString();
  }

  public static Published create(@NonNull String date) {
    return new Published(parse(date));
  }
}
