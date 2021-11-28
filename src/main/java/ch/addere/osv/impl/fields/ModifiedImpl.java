package ch.addere.osv.impl.fields;

import ch.addere.osv.fields.Modified;
import java.time.Instant;

/**
 * Modified is the time an entry was last modified. Time is represented as an FRC3339-formatted
 * timestamp.
 */
public final class ModifiedImpl extends EntryDate implements Modified {

  public static final String MODIFIED_KEY = "modified";

  private ModifiedImpl(Instant date) {
    super(date);
  }

  public static ModifiedImpl of(Instant date) {
    return new ModifiedImpl(date);
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
