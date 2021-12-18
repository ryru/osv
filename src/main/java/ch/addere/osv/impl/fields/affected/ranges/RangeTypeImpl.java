package ch.addere.osv.impl.fields.affected.ranges;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.fields.affected.ranges.RangeType;

/**
 * Range types that are supported.
 */
public enum RangeTypeImpl implements RangeType {

  ECOSYSTEM("ECOSYSTEM"),
  GIT("GIT"),
  SEMVER("SEMVER");

  public static final String TYPE_KEY = "type";

  private final String rangeType;

  RangeTypeImpl(String rangeType) {
    this.rangeType = rangeType;
  }

  @Override
  public String value() {
    return this.rangeType;
  }

  /**
   * Get RangeType from rangeType string.
   *
   * @param rangeType string value of a range type
   * @return          valid Ecosystem
   */
  public static RangeTypeImpl of(String rangeType) {
    return stream(RangeTypeImpl.values())
        .filter(values -> values.value().equals(rangeType))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid range type", rangeType)));
  }

  /**
   * Human-readable key-value string.
   *
   * @return key-value string
   */
  @Override
  public String toString() {
    return TYPE_KEY + ": " + rangeType;
  }
}
