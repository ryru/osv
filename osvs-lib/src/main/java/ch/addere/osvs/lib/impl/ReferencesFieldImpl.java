package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ReferencesField;
import ch.addere.osvs.lib.field.types.ReferencesEntry;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

/** {@link ch.addere.osvs.lib.field.ReferencesField} implementation. */
public final class ReferencesFieldImpl implements ReferencesField {

  private static final String JSON_KEY = "references";

  private final List<ReferencesEntry> referencesEntries;

  private ReferencesFieldImpl(ReferencesEntry... referencesEntries) {
    Objects.requireNonNull(referencesEntries, "argument referencesEntries must not be null");

    this.referencesEntries = List.of(referencesEntries);
  }

  /** Create a new instance. */
  public static ReferencesField of(ReferencesEntry... referencesEntries) {
    return new ReferencesFieldImpl(referencesEntries);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public List<ReferencesEntry> getValue() {
    return new LinkedList<>(referencesEntries);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ReferencesFieldImpl that = (ReferencesFieldImpl) o;

    return referencesEntries.equals(that.referencesEntries);
  }

  @Override
  public int hashCode() {
    return referencesEntries.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY
        + ": [ "
        + getValue().stream().map(Object::toString).collect(joining(", "))
        + " ]";
  }
}
