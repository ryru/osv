package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RepoField;
import ch.addere.osvs.lib.field.types.RangeEntry;

import java.util.Objects;

public final class RangeEntryGitImpl extends RangeEntryImpl {

  private RangeEntryGitImpl(RangeTypeField type, RepoField repo, EventsField events) {
    super(type, repo, events);
    Objects.requireNonNull(repo, "argument repo must not be null");
  }

  public static RangeEntry of(RangeTypeField type, RepoField repo, EventsField events) {
    return new RangeEntryGitImpl(type, repo, events);
  }
}
