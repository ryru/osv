package ch.addere.osv.impl.fields;

import ch.addere.osv.fields.Value;
import java.time.Instant;
import java.util.Objects;

/**
 * Published is the time an entry was published. Time is represented as an FRC3339-formatted
 * timestamp.
 */
public final class PublishedValue implements Value<Instant> {

  public static final String PUBLISHED_KEY = "published";

  private final Instant value;

  private PublishedValue(Instant value) {
    Objects.requireNonNull(value, "argument value must not be null");
    this.value = value;
  }

  public static PublishedValue of(Instant date) {
    return new PublishedValue(date);
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
    PublishedValue published = (PublishedValue) o;
    return Objects.equals(value, published.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return PUBLISHED_KEY + ": " + value;
  }
}
