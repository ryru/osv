package ch.addere.osv.property;

import ch.addere.osv.Value;
import java.util.Objects;

/**
 * Summary sums up the open source vulnerability.
 */
public final class SummaryValue implements Value<String> {

  public static final String SUMMARY_KEY = "summary";
  private static final int MAX_LENGTH = 120;

  private final String value;

  /**
   * An open source vulnerability summary.
   *
   * @param value sum up of the vulnerability
   */
  private SummaryValue(String value) {
    Objects.requireNonNull(value, "argument summary must not be null");
    if (value.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          "summary must not exceed 120 characters, was " + value.length());
    }
    this.value = value;
  }

  public static SummaryValue fromString(String summary) {
    return new SummaryValue(summary);
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
    SummaryValue summary1 = (SummaryValue) o;
    return Objects.equals(value, summary1.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return SUMMARY_KEY + ": " + value;
  }
}
