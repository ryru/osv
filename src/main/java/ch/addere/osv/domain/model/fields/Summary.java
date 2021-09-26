package ch.addere.osv.domain.model.fields;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Summary sums up the open source vulnerability.
 */
@Value
@Accessors(fluent = true)
public class Summary {

  public static final String SUMMARY_KEY = "summary";
  private static final int MAX_LENGTH = 120;

  String summary;

  /**
   * An open source vulnerability summary.
   *
   * @param summary sum up of the vulnerability
   */
  public Summary(@NonNull String summary) {
    if (summary.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          "summary must not exceed 120 characters, was " + summary.length());
    }
    this.summary = summary;
  }
}
