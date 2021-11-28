package ch.addere.osv.domain.model.fields;

import java.time.Instant;

/**
 * Modified is the time an entry was last modified. Time is represented as an FRC3339-formatted
 * timestamp.
 */
public final class Modified extends EntryDate {

  public static final String MODIFIED_KEY = "modified";

  private Modified(Instant date) {
    super(date);
  }

  public static Modified of(Instant date) {
    return new Modified(date);
  }

  @Override
  public Instant value() {
    return super.value();
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return MODIFIED_KEY + ": " + super.value().toString();
  }
}
