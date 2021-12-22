package ch.addere.osv.impl.fields.affected.ranges;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.fields.Value;

/**
 * Range types that are supported.
 */
public enum RangeTypeValue implements Value<String> {

  ECOSYSTEM("ECOSYSTEM"),
  GIT("GIT"),
  SEMVER("SEMVER");

  public static final String TYPE_KEY = "type";

  private final String value;

  RangeTypeValue(String value) {
    this.value = value;
  }

  /**
   * Get RangeType from rangeType string.
   *
   * @param rangeType string value of a range type
   * @return valid Ecosystem
   */
  public static RangeTypeValue of(String rangeType) {
    return stream(RangeTypeValue.values())
        .filter(values -> values.value().equals(rangeType))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid range type", rangeType)));
  }

  @Override
  public String value() {
    return this.value;
  }

  /**
   * Human-readable key-value string.
   *
   * @return key-value string
   */
  @Override
  public String toString() {
    return TYPE_KEY + ": " + value;
  }
}
