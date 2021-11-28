package ch.addere.osv.domain.model.fields;

import java.time.Instant;

/**
 * Published is the time an entry was published. Time is represented as an FRC3339-formatted
 * timestamp.
 */
public final class Published extends EntryDate {

  public static final String PUBLISHED_KEY = "published";

  private Published(Instant date) {
    super(date);
  }

  public static Published of(Instant date) {
    return new Published(date);
  }

  @Override
  public Instant value() {
    return super.value();
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return PUBLISHED_KEY + ": " + super.value().toString();
  }
}
