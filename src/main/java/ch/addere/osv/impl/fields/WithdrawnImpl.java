package ch.addere.osv.impl.fields;

import ch.addere.osv.fields.Withdrawn;
import java.time.Instant;

/**
 * Withdrawn is the time an entry should be considered to have been withdrawn. Time is represented
 * as an FRC3339-formatted timestamp.
 */
public final class WithdrawnImpl extends EntryDate implements Withdrawn {

  public static final String WITHDRAWN_KEY = "withdrawn";

  private WithdrawnImpl(Instant date) {
    super(date);
  }

  public static WithdrawnImpl of(Instant date) {
    return new WithdrawnImpl(date);
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
