package ch.addere.osv.domain.model.fields.affected.ranges;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

import ch.addere.osv.domain.model.fields.affected.Ranges;
import ch.addere.osv.domain.model.fields.affected.ranges.events.GitEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Git type to be used in ranges.
 */
public final class TypeGit implements Ranges {

  private final Type type;
  private final Repo repo;
  private final List<GitEvent> events;

  private TypeGit(Repo repo, GitEvent... events) {
    this.type = Type.GIT;
    this.repo = repo;
    this.events = List.of(events);
  }

  public static TypeGit of(Repo repo, GitEvent... events) {
    return new TypeGit(repo, events);
  }

  @Override
  public Type type() {
    return type;
  }

  @Override
  public Optional<Repo> repo() {
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
    TypeGit that = (TypeGit) o;
    return type == that.type && Objects.equals(repo, that.repo) && Objects.equals(
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
