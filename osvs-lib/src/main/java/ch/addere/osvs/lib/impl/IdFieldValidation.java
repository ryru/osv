package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.validation.Validation;

import java.util.Objects;
import java.util.function.Predicate;

/** Content validation us in {@link ch.addere.osvs.lib.impl.IdFieldImpl}. */
public final class IdFieldValidation extends Validation<IdFieldImpl> {

  public IdFieldValidation(IdFieldImpl value) {
    super(value);

    this.addRuleForValidation(nonEmptyEntryId(), "entryId must not be empty");
  }

  private Predicate<IdFieldImpl> nonEmptyEntryId() {
    return v -> !Objects.equals(v.getEntryId(), "");
  }
}
