package ch.addere.osv.impl.fields.affected.ranges;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.impl.fields.affected.ranges.events.GitEventValues;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Git type to be used in ranges.
 */
public final class TypeGitValues implements Ranges {

  private final RangeTypeValue type;
  private final RepoValue repo;
  private final List<GitEventValues> events;

  private TypeGitValues(RepoValue repo, List<GitEventValues> events) {
    this.type = RangeTypeValue.GIT;
    this.repo = repo;
    this.events = events;
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
  public List<Event> events() {
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
    TypeGitValues that = (TypeGitValues) o;
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
        events.stream().map(GitEventValues::toString).collect(joining()));
  }

  /**
   * TypeGitBuilder builder to create TypeSemVerValues.
   */
  public static final class TypeGitBuilder {

    private final RepoValue repo;
    private final List<GitEventValues> events;

    /**
     * Builder constructor.
     *
     * @param repo   RepoValue to add
     * @param events GitEventValues to add
     */
    public TypeGitBuilder(RepoValue repo, GitEventValues... events) {
      Objects.requireNonNull(repo, "argument repo must not be null");
      Objects.requireNonNull(events, "argument events must not be null");
      this.repo = repo;
      this.events = List.of(events);
    }

    /**
     * Build TypeGitValues property.
     *
     * @return valid TypeGitValue
     */
    public TypeGitValues build() {
      return new TypeGitValues(repo, events);
    }
  }
}
