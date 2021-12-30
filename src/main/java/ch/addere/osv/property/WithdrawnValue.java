package ch.addere.osv.property;

import ch.addere.osv.Value;
import java.time.Instant;
import java.util.Objects;

/**
 * Withdrawn is the time an entry should be considered to have been withdrawn. Time is represented
 * as an FRC3339-formatted timestamp.
 */
public final class WithdrawnValue implements Value<Instant> {

  public static final String WITHDRAWN_KEY = "withdrawn";

  private final Instant value;

  private WithdrawnValue(Instant value) {
    Objects.requireNonNull(value, "argument value must not be null");
    this.value = value;
  }

  public static WithdrawnValue of(Instant date) {
    return new WithdrawnValue(date);
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
    WithdrawnValue withdrawn = (WithdrawnValue) o;
    return Objects.equals(value, withdrawn.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return WITHDRAWN_KEY + ": " + value;
  }
}
