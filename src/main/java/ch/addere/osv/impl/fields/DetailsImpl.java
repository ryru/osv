package ch.addere.osv.impl.fields;

import ch.addere.osv.fields.Details;
import java.util.Objects;

/**
 * Details describes additional details about a vulnerability.
 */
public final class DetailsImpl implements Details {

  public static final String DETAILS_KEY = "details";

  private final String details;

  private DetailsImpl(String details) {
    Objects.requireNonNull(details, "argument details must not be null");
    this.details = details;
  }

  public static DetailsImpl of(String details) {
    return new DetailsImpl(details);
  }

  @Override
  public String value() {
    return details;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DetailsImpl details1 = (DetailsImpl) o;
    return Objects.equals(details, details1.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(details);
  }

  @Override
  public String toString() {
    return DETAILS_KEY + ": " + details;
  }
}
