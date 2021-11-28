package ch.addere.osv.domain.model.fields;

/**
 * Summary sums up the open source vulnerability.
 */
public final class Summary {

  public static final String SUMMARY_KEY = "summary";
  private static final int MAX_LENGTH = 120;

  private final String summary;

  /**
   * An open source vulnerability summary.
   *
   * @param summary sum up of the vulnerability
   */
  public Summary(String summary) {
    if (summary.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          "summary must not exceed 120 characters, was " + summary.length());
    }
    this.summary = summary;
  }

  public String value() {
    return summary;
  }
}
