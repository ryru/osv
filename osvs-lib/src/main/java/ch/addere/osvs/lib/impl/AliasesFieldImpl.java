package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.AliasesField;
import ch.addere.osvs.lib.field.IdField;
import ch.addere.osvs.lib.validation.RuleResult;
import ch.addere.osvs.lib.validation.Validation;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

/** {@link ch.addere.osvs.lib.field.AliasesField} implementation. */
public final class AliasesFieldImpl implements AliasesField {

  private static final String JSON_KEY = "aliases";

  private final List<IdField> idFields;

  private AliasesFieldImpl(IdField... idFields) {
    Objects.requireNonNull(idFields, "argument idFields must not be null");

    this.idFields = List.of(idFields);

    Validation<AliasesFieldImpl> validator = new AliasesFieldValidation(this);
    RuleResult result = validator.validate();
    if (!result.isValid()) {
      throw new IllegalArgumentException(result.getViolationMsg());
    }
  }

  /** Create a new instance. */
  public static AliasesField of(IdField... idFields) {
    return new AliasesFieldImpl(idFields);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public List<IdField> getValue() {
    return new LinkedList<>(idFields);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AliasesFieldImpl that = (AliasesFieldImpl) o;

    return idFields.equals(that.idFields);
  }

  @Override
  public int hashCode() {
    return idFields.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY
        + ": [ "
        + getValue().stream().map(IdField::toString).collect(joining(", "))
        + " ]";
  }
}
