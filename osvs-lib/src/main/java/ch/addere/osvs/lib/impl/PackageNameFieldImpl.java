package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.PackageNameField;

import java.util.Objects;

public final class PackageNameFieldImpl implements PackageNameField {

  private static final String JSON_KEY = "name";

  private final String ecosystemName;

  private PackageNameFieldImpl(String ecosystemName) {
    Objects.requireNonNull(ecosystemName, "argument ecosystemName must not be null");

    this.ecosystemName = ecosystemName;
  }

  public static PackageNameField of(String ecosystemName) {
    return new PackageNameFieldImpl(ecosystemName);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public String getValue() {
    return ecosystemName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PackageNameFieldImpl that = (PackageNameFieldImpl) o;

    return ecosystemName.equals(that.ecosystemName);
  }

  @Override
  public int hashCode() {
    return ecosystemName.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY + ": " + getValue();
  }
}
