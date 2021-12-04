package ch.addere.osv.impl.fields;

import ch.addere.osv.fields.Summary;
import java.util.Objects;

/**
 * Summary sums up the open source vulnerability.
 */
public final class SummaryImpl implements Summary {

  public static final String SUMMARY_KEY = "summary";
  private static final int MAX_LENGTH = 120;

  private final String summary;

  /**
   * An open source vulnerability summary.
   *
   * @param summary sum up of the vulnerability
   */
  private SummaryImpl(String summary) {
    Objects.requireNonNull(summary, "argument summary must not be null");
    if (summary.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          "summary must not exceed 120 characters, was " + summary.length());
    }
    this.summary = summary;
  }

  public static Summary of(String summary) {
    return new SummaryImpl(summary);
  }

  @Override
  public String value() {
    return summary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SummaryImpl summary1 = (SummaryImpl) o;
    return Objects.equals(summary, summary1.summary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(summary);
  }

  @Override
  public String toString() {
    return SUMMARY_KEY + ": " + summary;
  }
}
