package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.validation.Validation;

import java.util.function.Predicate;

/** Content validation used in {@link ch.addere.osvs.lib.impl.VersionsFieldImpl}. */
public final class VersionsFieldValidation extends Validation<VersionsFieldImpl> {

  public VersionsFieldValidation(VersionsFieldImpl value) {
    super(value);

    this.addRuleForValidation(nonEmptyValue(), "value must not be empty");
  }

  private Predicate<VersionsFieldImpl> nonEmptyValue() {
    return v -> !v.getValue().isEmpty();
  }
}
