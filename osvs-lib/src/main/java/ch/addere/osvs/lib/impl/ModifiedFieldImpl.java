package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ModifiedField;

import java.time.Instant;
import java.util.Objects;

public final class ModifiedFieldImpl implements ModifiedField {

  private static final String JSON_KEY = "modified";

  private final Instant modified;

  private ModifiedFieldImpl(Instant modified) {
    Objects.requireNonNull(modified, "argument modified must not be null");

    this.modified = modified;
  }

  public static ModifiedField of(Instant modified) {
    return new ModifiedFieldImpl(modified);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public Instant getValue() {
    return modified;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ModifiedFieldImpl that = (ModifiedFieldImpl) o;

    return modified.equals(that.modified);
  }

  @Override
  public int hashCode() {
    return modified.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY + ": " + getValue();
  }
}
