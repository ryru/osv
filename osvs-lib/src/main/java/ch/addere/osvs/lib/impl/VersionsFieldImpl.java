package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.VersionsField;
import ch.addere.osvs.lib.field.types.Version;
import ch.addere.osvs.lib.validation.RuleResult;
import ch.addere.osvs.lib.validation.Validation;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

/** {@link ch.addere.osvs.lib.field.VersionsField} implementation. */
public final class VersionsFieldImpl implements VersionsField {

  private static final String JSON_KEY = "versions";

  private final List<Version> versions;

  private VersionsFieldImpl(Version... versions) {
    Objects.requireNonNull(versions, "argument versions must not be null");

    this.versions = List.of(versions);

    Validation<VersionsFieldImpl> validator = new VersionsFieldValidation(this);
    RuleResult result = validator.validate();
    if (!result.isValid()) {
      throw new IllegalArgumentException(result.getViolationMsg());
    }
  }

  /** Create a new instance. */
  public static VersionsField of(Version... versions) {
    return new VersionsFieldImpl(versions);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public List<Version> getValue() {
    return new LinkedList<>(versions);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VersionsFieldImpl that = (VersionsFieldImpl) o;

    return versions.equals(that.versions);
  }

  @Override
  public int hashCode() {
    return versions.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY
        + ": [ "
        + getValue().stream().map(Objects::toString).collect(joining(", "))
        + " ]";
  }
}
