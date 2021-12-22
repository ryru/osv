package ch.addere.osv.impl.fields.affected.ranges;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.impl.fields.affected.ranges.events.GitEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Git type to be used in ranges.
 */
public final class TypeGitImpl implements Ranges {

  private final RangeTypeValue type;
  private final RepoValue repo;
  private final List<GitEvent> events;

  private TypeGitImpl(RepoValue repo, GitEvent... events) {
    Objects.requireNonNull(repo, "argument repo must not be null");
    Objects.requireNonNull(events, "argument events must not be null");
    this.type = RangeTypeValue.GIT;
    this.repo = repo;
    this.events = List.of(events);
  }

  public static TypeGitImpl of(RepoValue repo, GitEvent... events) {
    return new TypeGitImpl(repo, events);
  }

  @Override
  public RangeTypeValue type() {
    return type;
  }

  @Override
  public Optional<RepoValue> repo() {
    return Optional.of(repo);
  }

  @Override
  public List<? extends Event> events() {
    return new LinkedList<>(events);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypeGitImpl that = (TypeGitImpl) o;
    return type.equals(that.type) && Objects.equals(repo, that.repo) && Objects.equals(
        events, that.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, repo, events);
  }

  @Override
  public String toString() {
    return RANGES_KEY + ": " + join(", ",
        type.toString(),
        repo.toString(),
        events.stream().map(GitEvent::toString).collect(joining()));
  }
}
