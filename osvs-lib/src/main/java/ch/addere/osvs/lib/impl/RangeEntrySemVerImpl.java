package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RepoField;
import ch.addere.osvs.lib.field.types.RangeEntry;
import ch.addere.osvs.lib.validation.RuleResult;
import ch.addere.osvs.lib.validation.Validation;

public final class RangeEntrySemVerImpl extends RangeEntryImpl {

  private RangeEntrySemVerImpl(RangeTypeField type, RepoField repo, EventsField events) {
    super(type, repo, events);

    Validation<RangeEntrySemVerImpl> validator = new RangeEntrySemVerValidation(this);
    RuleResult result = validator.validate();
    if (!result.isValid()) {
      throw new IllegalArgumentException(result.getViolationMsg());
    }
  }

  public static RangeEntry of(RangeTypeField type, RepoField repo, EventsField events) {
    return new RangeEntrySemVerImpl(type, repo, events);
  }
}
