package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.PackageTypeField;
import ch.addere.osvs.lib.field.types.Ecosystem;

import java.util.Objects;

/** {@link ch.addere.osvs.lib.field.PackageTypeField} implementation. */
public final class PackageTypeFieldImpl implements PackageTypeField {

  private static final String JSON_KEY = "ecosystem";

  private final Ecosystem ecosystemType;

  private PackageTypeFieldImpl(Ecosystem ecosystemType) {
    Objects.requireNonNull(ecosystemType, "argument ecosystemType must not be null");

    this.ecosystemType = ecosystemType;
  }

  /** Create a new instance. */
  public static PackageTypeField of(Ecosystem ecosystem) {
    return new PackageTypeFieldImpl(ecosystem);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public Ecosystem getValue() {
    return ecosystemType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PackageTypeFieldImpl that = (PackageTypeFieldImpl) o;

    return ecosystemType == that.ecosystemType;
  }

  @Override
  public int hashCode() {
    return ecosystemType.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY + ": " + getValue();
  }
}
