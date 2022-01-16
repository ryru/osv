package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.validation.Validation;

import java.util.function.Predicate;

/** Content validation used in {@link ch.addere.osvs.lib.impl.AliasesFieldImpl}. */
public final class AliasesFieldValidation extends Validation<AliasesFieldImpl> {

  public AliasesFieldValidation(AliasesFieldImpl value) {
    super(value);

    this.addRuleForValidation(nonEmptyValue(), "value must not be empty");
  }

  private Predicate<AliasesFieldImpl> nonEmptyValue() {
    return v -> !v.getValue().isEmpty();
  }
}
