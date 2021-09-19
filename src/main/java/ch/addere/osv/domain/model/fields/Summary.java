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

  public static final String SUMMARY = "summary";

  String summary;

  /**
   * An open source vulnerability summary.
   *
   * @param summary sum up of the vulnerability
   */
  public Summary(@NonNull String summary) {
    if (summary.length() > 120) {
      throw new IllegalArgumentException(
          "summary must not exceed 120 characters, was " + summary.length());
    }
    this.summary = summary;
  }
}
