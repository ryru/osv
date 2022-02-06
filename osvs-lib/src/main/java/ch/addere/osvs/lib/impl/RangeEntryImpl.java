package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RepoField;
import ch.addere.osvs.lib.field.types.RangeEntry;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public abstract class RangeEntryImpl implements RangeEntry {

  private final RangeTypeField type;
  private final RepoField repo;
  private final EventsField events;

  protected RangeEntryImpl(RangeTypeField type, RepoField repo, EventsField events) {
    Objects.requireNonNull(type, "argument type must not be null");
    Objects.requireNonNull(events, "argument events must not be null");

    this.type = type;
    this.repo = repo;
    this.events = events;
  }

  @Override
  public RangeTypeField type() {
    return type;
  }

  @Override
  public Optional<RepoField> repo() {
    return Optional.ofNullable(repo);
  }

  @Override
  public EventsField events() {
    return events;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RangeEntryImpl that = (RangeEntryImpl) o;

    if (!type.equals(that.type)) return false;
    if (repo != null ? !repo.equals(that.repo) : that.repo != null) return false;
    return events.equals(that.events);
  }

  @Override
  public int hashCode() {
    int result = type.hashCode();
    result = 31 * result + (repo != null ? repo.hashCode() : 0);
    result = 31 * result + events.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return Stream.of(type.toString(), repo().map(Objects::toString).orElse(""), events.toString())
        .filter(Objects::nonNull)
        .filter(s -> !s.isBlank())
        .collect(joining("\n"));
  }
}
