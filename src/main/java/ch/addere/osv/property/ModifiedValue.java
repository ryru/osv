package ch.addere.osv.property;

import ch.addere.osv.Value;
import java.time.Instant;
import java.util.Objects;

/**
 * Modified is the time an entry was last modified. Time is represented as an FRC3339-formatted
 * timestamp.
 */
public final class ModifiedValue implements Value<Instant> {

  public static final String MODIFIED_KEY = "modified";

  private final Instant value;

  private ModifiedValue(Instant value) {
    Objects.requireNonNull(value, "argument value must not be null");
    this.value = value;
  }

  public static ModifiedValue of(Instant date) {
    return new ModifiedValue(date);
  }

  @Override
  public Instant value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModifiedValue modified = (ModifiedValue) o;
    return Objects.equals(value, modified.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return MODIFIED_KEY + ": " + value;
  }
}
