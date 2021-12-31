package ch.addere.osv.property.affected.ranges;

import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.INTRODUCED;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import ch.addere.osv.property.affected.Ranges;
import ch.addere.osv.property.affected.ranges.events.SemVerEventValues;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Semantic version type to be used in ranges.
 */
public final class TypeSemVerValues implements Ranges {

  private final RangeTypeValue type;
  private final List<SemVerEventValues> events;
  private RepoValue repo;

  private TypeSemVerValues(List<SemVerEventValues> events) {
    this.type = RangeTypeValue.SEMVER;
    this.events = events;
  }

  @Override
  public RangeTypeValue type() {
    return type;
  }

  @Override
  public Optional<RepoValue> repo() {
    return Optional.ofNullable(repo);
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
    TypeSemVerValues that = (TypeSemVerValues) o;
    return type.equals(that.type) && Objects.equals(repo, that.repo) && Objects.equals(
        events, that.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, repo, events);
  }

  @Override
  public String toString() {
    Stream<Object> streamTypeRepo = Stream.of(type, repo);
    Stream<SemVerEventValues> streamSemVers = events.stream();
    Stream<Object> concat = Stream.concat(streamTypeRepo, streamSemVers);

    return RANGES_KEY + ": " + concat
        .filter(Objects::nonNull)
        .map(Object::toString)
        .filter(s -> !s.isEmpty())
        .collect(joining(", "));
  }

  /**
   * TypeSemVerBuilder builder to create TypeSemVerValues.
   */
  public static final class TypeSemVerBuilder {

    private final List<SemVerEventValues> events;
    private RepoValue repo = null;

    /**
     * Builder constructor.
     *
     * @param events SemVerEventValues to add
     */
    public TypeSemVerBuilder(SemVerEventValues... events) {
      Objects.requireNonNull(events, "argument events must not be null");
      this.events = List.of(events);

      if (stream(events).noneMatch(e -> e.event().equals(INTRODUCED))) {
        throw new IllegalStateException("at least one 'introduced' event required");
      }
    }

    /**
     * Add RepoValue to property.
     *
     * @param repo RepoValue to add
     * @return valid TypeSemVerBuilder
     */
    public TypeSemVerBuilder repo(RepoValue repo) {
      this.repo = repo;
      return this;
    }

    /**
     * Build TypeSemVerValues property.
     *
     * @return valid TypeSemVerValue
     */
    public TypeSemVerValues build() {
      TypeSemVerValues semVer = new TypeSemVerValues(events);
      semVer.repo = this.repo;
      return semVer;
    }
  }
}
