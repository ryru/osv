package ch.addere.osv.property;

import ch.addere.osv.Value;
import java.util.Objects;

/**
 * Details describes additional details about a vulnerability.
 */
public final class DetailsValue implements Value<String> {

  public static final String DETAILS_KEY = "details";

  private final String value;

  private DetailsValue(String value) {
    Objects.requireNonNull(value, "argument details must not be null");
    this.value = value;
  }

  public static DetailsValue fromString(String details) {
    return new DetailsValue(details);
  }

  @Override
  public String value() {
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
    DetailsValue detailsValue1 = (DetailsValue) o;
    return Objects.equals(value, detailsValue1.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return DETAILS_KEY + ": " + value;
  }
}
