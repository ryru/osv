package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ReferencesTypeField;
import ch.addere.osvs.lib.field.types.Reference;

import java.util.Objects;

/** {@link ch.addere.osvs.lib.field.ReferencesTypeField} implementation. */
public final class ReferencesTypeFieldImpl implements ReferencesTypeField {

  private static final String JSON_KEY = "type";

  private final Reference referenceType;

  private ReferencesTypeFieldImpl(Reference referenceType) {
    Objects.requireNonNull(referenceType, "argument referenceType must not be null");

    this.referenceType = referenceType;
  }

  /** Create a new instance. */
  public static ReferencesTypeField of(Reference referenceType) {
    return new ReferencesTypeFieldImpl(referenceType);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public Reference getValue() {
    return referenceType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ReferencesTypeFieldImpl that = (ReferencesTypeFieldImpl) o;

    return referenceType == that.referenceType;
  }

  @Override
  public int hashCode() {
    return referenceType.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY + ": " + getValue();
  }
}
