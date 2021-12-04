package ch.addere.osv.impl.fields;

import java.time.Instant;
import java.util.Objects;

/**
 * Date representation of all date fields within the specification.
 */
public abstract class EntryDate {

  private final Instant date;

  protected EntryDate(Instant date) {
    Objects.requireNonNull(date, "argument date must not be null");
    this.date = date;
  }

  protected Instant value() {
    return date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntryDate entryDate = (EntryDate) o;
    return Objects.equals(date, entryDate.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date);
  }
}
