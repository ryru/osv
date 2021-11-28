package ch.addere.osv.impl.fields;

import ch.addere.osv.fields.Published;
import java.time.Instant;

/**
 * Published is the time an entry was published. Time is represented as an FRC3339-formatted
 * timestamp.
 */
public final class PublishedImpl extends EntryDate implements Published {

  public static final String PUBLISHED_KEY = "published";

  private PublishedImpl(Instant date) {
    super(date);
  }

  public static PublishedImpl of(Instant date) {
    return new PublishedImpl(date);
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
