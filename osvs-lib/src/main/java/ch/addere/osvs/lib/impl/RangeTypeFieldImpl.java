package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.types.RangeType;

import java.util.Objects;

public final class RangeTypeFieldImpl implements RangeTypeField {

  private static final String JSON_KEY = "type";

  private final RangeType rangeType;

  private RangeTypeFieldImpl(RangeType rangeType) {
    Objects.requireNonNull(rangeType, "argument rangeType must not be null");

    this.rangeType = rangeType;
  }

  public static RangeTypeField of(RangeType rangeType) {
    return new RangeTypeFieldImpl(rangeType);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public RangeType getValue() {
    return rangeType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RangeTypeFieldImpl that = (RangeTypeFieldImpl) o;

    return rangeType == that.rangeType;
  }

  @Override
  public int hashCode() {
    return rangeType.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY + ": " + getValue();
  }
}
