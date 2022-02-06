package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.RangesField;
import ch.addere.osvs.lib.field.types.RangeEntry;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class RangeFieldImpl implements RangesField {

  private static final String JSON_KEY = "ranges";

  private final List<RangeEntry> rangeEntries;

  private RangeFieldImpl(RangeEntry... rangeEntries) {
    Objects.requireNonNull(rangeEntries, "argument rangeEntries must not be null");

    this.rangeEntries = List.of(rangeEntries);
  }

  public static RangesField of(RangeEntry... rangeEntries) {
    return new RangeFieldImpl(rangeEntries);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public List<RangeEntry> getValue() {
    return new LinkedList<>(rangeEntries);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RangeFieldImpl that = (RangeFieldImpl) o;

    return rangeEntries.equals(that.rangeEntries);
  }

  @Override
  public int hashCode() {
    return rangeEntries.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY + ": " + getValue();
  }
}
