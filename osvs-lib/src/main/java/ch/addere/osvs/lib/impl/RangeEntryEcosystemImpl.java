package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RepoField;
import ch.addere.osvs.lib.field.types.RangeEntry;

public final class RangeEntryEcosystemImpl extends RangeEntryImpl {

  private RangeEntryEcosystemImpl(RangeTypeField type, RepoField repo, EventsField events) {
    super(type, repo, events);
  }

  public static RangeEntry of(RangeTypeField type, RepoField repo, EventsField events) {
    return new RangeEntryEcosystemImpl(type, repo, events);
  }
}
