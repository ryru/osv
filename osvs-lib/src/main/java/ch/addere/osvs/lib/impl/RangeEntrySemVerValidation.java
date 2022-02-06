package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.validation.Validation;
import de.skuzzle.semantic.Version;

import java.util.function.Predicate;

public final class RangeEntrySemVerValidation extends Validation<RangeEntrySemVerImpl> {

  public RangeEntrySemVerValidation(RangeEntrySemVerImpl value) {
    super(value);

    this.addRuleForValidation(isValidSemVer(), "must be a valid semantic version");
  }

  private Predicate<RangeEntrySemVerImpl> isValidSemVer() {
    try {
      getValue().events().getValue().forEach(entry -> Version.parseVersion(entry.eventValue()));
      return v -> true;
    } catch (Version.VersionFormatException e) {
      return v -> false;
    }
  }
}
