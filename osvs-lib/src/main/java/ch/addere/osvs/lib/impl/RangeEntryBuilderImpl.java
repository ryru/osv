package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RepoField;
import ch.addere.osvs.lib.field.types.RangeEntry;
import ch.addere.osvs.lib.field.types.RangeEntryBuilder;

import java.util.Objects;

public final class RangeEntryBuilderImpl implements RangeEntryBuilder {

  private final RangeTypeField type;
  private final EventsField events;
  private RepoField repo = null;

  private RangeEntryBuilderImpl(RangeTypeField type, EventsField events) {
    Objects.requireNonNull(type, "argument type must not be null");
    Objects.requireNonNull(events, "argument events must not be null");

    this.type = type;
    this.events = events;
  }

  public static RangeEntryBuilder of(RangeTypeField type, EventsField events) {
    return new RangeEntryBuilderImpl(type, events);
  }

  @Override
  public RangeEntryBuilder setRepo(RepoField repo) {
    Objects.requireNonNull(repo, "argument repo must not be null");
    this.repo = repo;
    return this;
  }

  @Override
  public RangeEntry build() {
    switch (type.getValue()) {
      case ECOSYSTEM:
        return RangeEntryEcosystemImpl.of(type, repo, events);
      case GIT:
        return RangeEntryGitImpl.of(type, repo, events);
      case SEMVER:
        return RangeEntrySemVerImpl.of(type, repo, events);
      default:
        throw new IllegalStateException("invalid rangeType");
    }
  }
}
