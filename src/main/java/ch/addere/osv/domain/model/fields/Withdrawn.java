package ch.addere.osv.domain.model.fields;

import java.time.Instant;

/**
 * Withdrawn is the time an entry should be considered to have been withdrawn. Time is represented
 * as an FRC3339-formatted timestamp.
 */
public final class Withdrawn extends EntryDate {

  public static final String WITHDRAWN_KEY = "withdrawn";

  private Withdrawn(Instant date) {
    super(date);
  }

  public static Withdrawn of(Instant date) {
    return new Withdrawn(date);
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
    return WITHDRAWN_KEY + ": " + super.value().toString();
  }
}
